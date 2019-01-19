package com.javafee.startform;

import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.Comparator;
import java.util.List;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JOptionPane;

import com.javafee.common.Constans.Role;
import com.javafee.common.IRegistrationForm;
import com.javafee.common.Params;
import com.javafee.common.SystemProperties;
import com.javafee.common.Utils;
import com.javafee.exception.LogGuiException;
import com.javafee.exception.RefusedLogInException;
import com.javafee.exception.RefusedRegistrationException;
import com.javafee.hibernate.dao.HibernateDao;
import com.javafee.hibernate.dto.association.City;

public class Actions implements IRegistrationForm {

	private StartForm startForm = new StartForm();

	private LogInEvent logInEvent;
	private RegistrationEvent registrationEvent;

	public void control() {
		startForm.getFrame().setVisible(true);

		startForm.getFrame().addWindowListener(new WindowAdapter() {
			@Override
			public void windowOpened(WindowEvent e) {
				super.windowOpened(e);
			}
		});

		startForm.getBtnLogIn().addActionListener(e -> onClickBtnLogIn());
		startForm.getBtnRegistrationMode().addActionListener(e -> onClickBtnRegistrationMode());
		startForm.getNavigationPanel().getBtnBack().addActionListener(e -> onClickBtnBack());
		startForm.getRegistrationPanel().getBtnRegisterNow().addActionListener(e -> onClickBtnRegisterNow());
	}

	@Override
	public void reloadRegistrationPanel() {
		reloadComboBoxCity();
	}

	private void reloadComboBoxCity() {
		DefaultComboBoxModel<City> comboBoxCityModel = new DefaultComboBoxModel<City>();
		HibernateDao<City, Integer> city = new HibernateDao<City, Integer>(City.class);
		List<City> cityListToSort = city.findAll();
		cityListToSort.sort(Comparator.comparing(City::getName, Comparator.nullsFirst(Comparator.naturalOrder())));
		cityListToSort.forEach(c -> comboBoxCityModel.addElement(c));

		startForm.getRegistrationPanel().getComboBoxCity().setModel(comboBoxCityModel);
	}

	private void onClickBtnLogIn() {
		if (validateLogIn()) {
			try {
				logInEvent = LogInEvent.getInstance(startForm.getLogInPanel().getTextFieldLogin().getText(),
						String.valueOf(startForm.getLogInPanel().getPasswordField().getPassword()));
			} catch (RefusedLogInException e) {
				StringBuilder errorBuilder = new StringBuilder();

				if (Params.getInstance().get("NO_USER") != null) {
					errorBuilder.append(
							SystemProperties.getInstance().getResourceBundle().getString("startForm.logInError3"));
					Params.getInstance().remove("NO_USER");
				}
				if (Params.getInstance().get("BAD_PASSWORD") != null) {
					errorBuilder.append(
							SystemProperties.getInstance().getResourceBundle().getString("startForm.logInError2"));
					Params.getInstance().remove("BAD_PASSWORD");
				}
				if (Params.getInstance().get("NOT_REGISTERED") != null) {
					errorBuilder.append(
							SystemProperties.getInstance().getResourceBundle().getString("startForm.logInError4"));
					Params.getInstance().remove("NOT_REGISTERED");
				}
				if (Params.getInstance().get("NOT_HIRED") != null) {
					errorBuilder.append(
							SystemProperties.getInstance().getResourceBundle().getString("startForm.logInError9"));
					Params.getInstance().remove("NOT_HIRED");
				}

				LogGuiException.logError(
						SystemProperties.getInstance().getResourceBundle().getString("startForm.logInErrorTitle"),
						errorBuilder.toString(), e);

				clearLogInFailsInParams();
			}

			if (logInEvent != null) {
				JOptionPane.showMessageDialog(startForm.getFrame(),
						SystemProperties.getInstance().getResourceBundle().getString("startForm.logInSuccess1"),
						SystemProperties.getInstance().getResourceBundle().getString("startForm.logInSuccess1Title"),
						JOptionPane.INFORMATION_MESSAGE);
				openMainForm();
			}
		}
	}

	private void clearLogInFailsInParams() {
		Params.getInstance().remove("NO_USER");
		Params.getInstance().remove("BAD_PASSWORD");
		Params.getInstance().remove("NOT_REGISTERED");
		Params.getInstance().remove("NOT_HIRED");
	}

	@Override
	public void onClickBtnRegisterNow() {
		reloadRegistrationPanel();
		if (validateRegistration()) {
			switchPerspectiveToRegistrationOrLogIn(false);
			try {
				registrationEvent = RegistrationEvent.getInstance(
						startForm.getRegistrationPanel().getTextFieldName().getText(),
						startForm.getRegistrationPanel().getTextFieldSurname().getText(),
						startForm.getRegistrationPanel().getTextFieldAddress().getText(),
						(City) startForm.getRegistrationPanel().getComboBoxCity().getSelectedItem(),
						startForm.getRegistrationPanel().getTextFieldLogin().getText(),
						startForm.getRegistrationPanel().getTextFieldEMail().getText(),
						String.valueOf(startForm.getRegistrationPanel().getPasswordField().getPassword()), Role.CLIENT);

			} catch (RefusedRegistrationException e) {
				StringBuilder errorBuilder = new StringBuilder();

				if (Params.getInstance().get("ALREADY_REGISTERED") != null) {
					errorBuilder.append(SystemProperties.getInstance().getResourceBundle()
							.getString("startForm.registrationError5"));
					Params.getInstance().remove("ALREADY_REGISTERED");
				}
				if (Params.getInstance().get("PARAMETERS_ERROR") != null) {
					errorBuilder.append(SystemProperties.getInstance().getResourceBundle()
							.getString("startForm.registrationError6"));
				}
				if (Params.getInstance().get("WEAK_PASSWORD") != null) {
					errorBuilder.append(SystemProperties.getInstance().getResourceBundle()
							.getString("startForm.registrationError7"));
				}
				if (Params.getInstance().get("INCORRECT_BIRTH_DATE") != null) {
					errorBuilder.append(SystemProperties.getInstance().getResourceBundle()
							.getString("startForm.registrationError9"));
				}

				LogGuiException.logError(SystemProperties.getInstance().getResourceBundle()
						.getString("startForm.registrationErrorTitle"), errorBuilder.toString(), e);
				clearRegistrationFailsInParams();
			}

			if (registrationEvent != null)
				Utils.displayOptionPane(
						SystemProperties.getInstance().getResourceBundle().getString("startForm.registrationSuccess2"),
						SystemProperties.getInstance().getResourceBundle()
								.getString("startForm.registrationSuccess2Title"),
						JOptionPane.INFORMATION_MESSAGE);
		}
	}

	private void clearRegistrationFailsInParams() {
		Params.getInstance().remove("ALREADY_REGISTERED");
		Params.getInstance().remove("PARAMETERS_ERROR");
		Params.getInstance().remove("WEAK_PASSWORD");
		Params.getInstance().remove("INCORRECT_BIRTH_DATE");

	}

	private void onClickBtnRegistrationMode() {
		switchPerspectiveToRegistrationOrLogIn(true);
		reloadRegistrationPanel();
	}

	private void onClickBtnBack() {
		switchPerspectiveToRegistrationOrLogIn(false);
	}

	private void openMainForm() {
		startForm.getFrame().setVisible(false);
		com.javafee.mainform.Actions actions = new com.javafee.mainform.Actions();
		actions.control();
	}

	private void switchPerspectiveToRegistrationOrLogIn(boolean registration) {
		startForm.getLogInPanel().setEnabled(!registration);
		startForm.getLogInPanel().setVisible(!registration);
		startForm.getBtnLogIn().setEnabled(!registration);
		startForm.getBtnLogIn().setVisible(!registration);
		startForm.getBtnRegistrationMode().setEnabled(!registration);
		startForm.getBtnRegistrationMode().setVisible(!registration);
		startForm.getNavigationPanel().setEnabled(registration);
		startForm.getNavigationPanel().setVisible(registration);
		startForm.getRegistrationPanel().setEnabled(registration);
		startForm.getRegistrationPanel().setVisible(registration);
		startForm.getFrame().pack();
	}

	private boolean validateLogIn() {
		boolean result = false;
		if (startForm.getLogInPanel().getTextFieldLogin().getText().isEmpty()
				|| startForm.getLogInPanel().getPasswordField().getPassword().length == 0)
			JOptionPane.showMessageDialog(startForm.getFrame(),
					SystemProperties.getInstance().getResourceBundle().getString("startForm.validateLogInError1"),
					SystemProperties.getInstance().getResourceBundle().getString("startForm.validateLogInError1Title"),
					JOptionPane.ERROR_MESSAGE);
		else
			result = true;

		return result;
	}

	@Override
	public boolean validateRegistration() {
		boolean result = false;
		if (startForm.getRegistrationPanel().getTextFieldLogin().getText().isEmpty()
				|| startForm.getRegistrationPanel().getPasswordField().getPassword().length == 0)
			JOptionPane.showMessageDialog(startForm.getFrame(),
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
