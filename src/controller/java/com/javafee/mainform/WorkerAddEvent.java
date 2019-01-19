package com.javafee.mainform;

import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.Comparator;
import java.util.List;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JOptionPane;

import com.javafee.common.Constans.Context;
import com.javafee.common.Params;
import com.javafee.common.SystemProperties;
import com.javafee.common.Utils;
import com.javafee.common.Validator;
import com.javafee.exception.LogGuiException;
import com.javafee.exception.RefusedRegistrationException;
import com.javafee.hibernate.dao.HibernateDao;
import com.javafee.hibernate.dao.HibernateUtil;
import com.javafee.hibernate.dto.association.City;
import com.javafee.hibernate.dto.common.Address;
import com.javafee.hibernate.dto.common.User;
import com.javafee.hibernate.dto.eeatery.Worker;
import com.javafee.mainform.frames.WorkerAddFrame;
import com.javafee.model.WorkerTableModel;
import com.javafee.startform.LogInEvent;
import com.javafee.startform.RegistrationEvent;

public class WorkerAddEvent {
	private WorkerAddFrame workerAddModFrame;

	private RegistrationEvent registrationEvent;
	private WorkerTableModel workerTableModel;

	public void control(Context context, WorkerTableModel workerTableModel) {
		this.workerTableModel = workerTableModel;
		openWorkerAddModFrame(context);

		workerAddModFrame.addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosed(WindowEvent e) {
				Params.getInstance().remove("selectedRowIndex");
				Params.getInstance().remove("selectedWorker");
			}
		});

		workerAddModFrame.getCockpitConfirmationPanel().getBtnAccept()
				.addActionListener(e -> onClickBtnAccept(context));
	}

	private void onClickBtnAccept(Context context) {
		if (context == Context.ADDITION) {
			if (validateRegistration())
				registerNow();
		} else if (context == Context.MODIFICATION) {
			modificateClient();
		}
	}

	private void modificateClient() {
		Worker workerShallowClone = (Worker) Params.getInstance().get("selectedWorker");

		workerShallowClone.setName(workerAddModFrame.getWorkerDataPanel().getTextFieldName().getText());
		workerShallowClone.setSurname(workerAddModFrame.getWorkerDataPanel().getTextFieldSurname().getText());

		Address address = new Address();
		address.setCity((City) workerAddModFrame.getWorkerDataPanel().getComboBoxCity().getSelectedItem());
		address.setStreet(workerAddModFrame.getWorkerDataPanel().getTextFieldAddress().getText());

		workerShallowClone.setAddress(address);

		workerShallowClone.setEMail(workerAddModFrame.getWorkerDataPanel().getTextFieldEMail().getText());
		workerShallowClone.setLogin(workerAddModFrame.getWorkerDataPanel().getTextFieldLogin().getText());

		if (Validator.validateClientUpdate(workerShallowClone)) {
			HibernateUtil.beginTransaction();
			HibernateUtil.getSession()
					.evict(workerTableModel.getWorker((Integer) Params.getInstance().get("selectedRowIndex")));
			HibernateUtil.getSession().update(Worker.class.getName(), workerShallowClone);
			HibernateUtil.commitTransaction();
			workerTableModel.setWorker((Integer) Params.getInstance().get("selectedRowIndex"), workerShallowClone);
			workerTableModel.fireTableDataChanged();
			Utils.displayOptionPane(
					SystemProperties.getInstance().getResourceBundle()
							.getString("workerAddModEvent.updatingWorkerSuccess"),
					SystemProperties.getInstance().getResourceBundle().getString(
							"workerAddModEvent.updatingWorkerSuccessTitle"),
					JOptionPane.INFORMATION_MESSAGE);
			Params.getInstance().remove("selectedWorker");
			Params.getInstance().remove("selectedRowIndex");
			workerAddModFrame.dispose();
		} else {
			Utils.displayOptionPane(
					SystemProperties.getInstance().getResourceBundle()
							.getString("clientAddModEvent.updatingClientError"),
					SystemProperties.getInstance().getResourceBundle()
							.getString("clientAddModEvent.updatingClientErrorTitle"),
					JOptionPane.ERROR_MESSAGE);
		}

	}

	private void openWorkerAddModFrame(Context context) {
		if (workerAddModFrame == null || (workerAddModFrame != null && !workerAddModFrame.isDisplayable())) {
			workerAddModFrame = new WorkerAddFrame();
			if (context == Context.MODIFICATION) {
				workerAddModFrame.getWorkerDataPanel().getLblPassword().setVisible(false);
				workerAddModFrame.getWorkerDataPanel().getPasswordField().setVisible(false);
				reloadRegistrationPanel();
			}
			reloadComboBoxCity();
			workerAddModFrame.setVisible(true);
		} else {
			workerAddModFrame.toFront();
		}
	}

	private void reloadRegistrationPanel() {
		reloadComboBoxCity();
		fillRegistrationPanel();
	}

	private void reloadComboBoxCity() {
		DefaultComboBoxModel<City> comboBoxCityModel = new DefaultComboBoxModel<City>();
		HibernateDao<City, Integer> city = new HibernateDao<City, Integer>(City.class);
		List<City> cityListToSort = city.findAll();
		cityListToSort.sort(Comparator.comparing(City::getName, Comparator.nullsFirst(Comparator.naturalOrder())));
		cityListToSort.forEach(c -> comboBoxCityModel.addElement(c));

		workerAddModFrame.getWorkerDataPanel().getComboBoxCity().setModel(comboBoxCityModel);
	}

	private void fillRegistrationPanel() {

		workerAddModFrame.getWorkerDataPanel().getTextFieldLogin()
				.setText(((Worker) Params.getInstance().get("selectedWorker")).getLogin() != null
						? ((Worker) Params.getInstance().get("selectedWorker")).getLogin().toString()
						: "");

		workerAddModFrame.getWorkerDataPanel().getTextFieldEMail()
				.setText(((Worker) Params.getInstance().get("selectedWorker")).getEMail() != null
						? ((Worker) Params.getInstance().get("selectedWorker")).getEMail().toString()
						: "");

		workerAddModFrame.getWorkerDataPanel().getTextFieldName()
				.setText(((Worker) Params.getInstance().get("selectedWorker")).getName() != null
						? ((Worker) Params.getInstance().get("selectedWorker")).getName().toString()
						: "");

		workerAddModFrame.getWorkerDataPanel().getTextFieldSurname()
				.setText(((Worker) Params.getInstance().get("selectedWorker")).getSurname() != null
						? ((Worker) Params.getInstance().get("selectedWorker")).getSurname().toString()
						: "");

		workerAddModFrame.getWorkerDataPanel().getTextFieldAddress()
				.setText(((Worker) Params.getInstance().get("selectedWorker")).getAddress() != null
						? ((Worker) Params.getInstance().get("selectedWorker")).getAddress().getStreet().toString()
						: "");

		workerAddModFrame.getWorkerDataPanel().getComboBoxCity().getModel()
				.setSelectedItem(((Worker) Params.getInstance().get("selectedWorker")).getAddress().getCity() != null
						? ((Worker) Params.getInstance().get("selectedWorker")).getAddress().getCity()
						: null);

	}

	@SuppressWarnings("unchecked")
	private void registerNow() {

		try {
			RegistrationEvent.forceClearRegistrationEvenet();
			boolean result = true;
			List<User> ud = HibernateUtil.getSession().createQuery("from User").list();
			for (User u : ud) {
				if (u.getLogin() != null
						&& u.getLogin().equals(workerAddModFrame.getWorkerDataPanel().getTextFieldLogin().getText()))
					result = false;
			}
			if (result) {

				registrationEvent = RegistrationEvent.getInstance(
						workerAddModFrame.getWorkerDataPanel().getTextFieldName().getText(),
						workerAddModFrame.getWorkerDataPanel().getTextFieldSurname().getText(),
						workerAddModFrame.getWorkerDataPanel().getTextFieldAddress().getText(),
						(City) workerAddModFrame.getWorkerDataPanel().getComboBoxCity().getSelectedItem(),
						workerAddModFrame.getWorkerDataPanel().getTextFieldLogin().getText(),
						workerAddModFrame.getWorkerDataPanel().getTextFieldEMail().getText(),
						String.valueOf(workerAddModFrame.getWorkerDataPanel().getPasswordField().getPassword()),
						LogInEvent.getRole());
				workerTableModel.add((Worker) RegistrationEvent.user);
				workerAddModFrame.dispose();

			} else {
				Utils.displayOptionPane(
						SystemProperties.getInstance().getResourceBundle().getString("workerAddModEvent.existingLogin"),
						SystemProperties.getInstance().getResourceBundle()
								.getString("workerAddModEvent.existingLoginTitle"),
						JOptionPane.ERROR_MESSAGE);
			}
		} catch (RefusedRegistrationException e) {
			StringBuilder errorBuilder = new StringBuilder();

			if (Params.getInstance().get("ALREADY_REGISTERED") != null) {
				errorBuilder.append(
						SystemProperties.getInstance().getResourceBundle().getString("startForm.registrationError5"));
				Params.getInstance().remove("ALREADY_REGISTERED");
			}
			if (Params.getInstance().get("PARAMETERS_ERROR") != null) {
				errorBuilder.append(
						SystemProperties.getInstance().getResourceBundle().getString("startForm.registrationError6"));
			}
			if (Params.getInstance().get("WEAK_PASSWORD") != null) {
				errorBuilder.append(
						SystemProperties.getInstance().getResourceBundle().getString("startForm.registrationError7"));
			}
			if (Params.getInstance().get("INCORRECT_BIRTH_DATE") != null) {
				errorBuilder.append(
						SystemProperties.getInstance().getResourceBundle().getString("startForm.registrationError9"));
			}

			LogGuiException.logError(
					SystemProperties.getInstance().getResourceBundle().getString("startForm.registrationErrorTitle"),
					errorBuilder.toString(), e);
			clearRegistrationFailsInParams();
		}
		if (registrationEvent != null)
			Utils.displayOptionPane(
					SystemProperties.getInstance().getResourceBundle().getString("startForm.registrationSuccess2"),
					SystemProperties.getInstance().getResourceBundle().getString("startForm.registrationSuccess2Title"),
					JOptionPane.INFORMATION_MESSAGE);
	}

	private void clearRegistrationFailsInParams() {
		Params.getInstance().remove("ALREADY_REGISTERED");
		Params.getInstance().remove("PARAMETERS_ERROR");
		Params.getInstance().remove("WEAK_PASSWORD");
		Params.getInstance().remove("INCORRECT_BIRTH_DATE");

	}

	private boolean validateRegistration() {
		boolean result = false;

		if (workerAddModFrame.getWorkerDataPanel().getTextFieldLogin().getText().isEmpty()
				|| workerAddModFrame.getWorkerDataPanel().getPasswordField().getPassword().length == 0)
			JOptionPane.showMessageDialog(workerAddModFrame,
					SystemProperties.getInstance().getResourceBundle()
							.getString("startForm.validateRegistrationError8"),
					SystemProperties.getInstance().getResourceBundle()
							.getString("startForm.validateRegistrationError8Title"),
					JOptionPane.ERROR_MESSAGE);
		else
			result = true;

		return result;
	}
}