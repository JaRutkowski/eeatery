package com.javafee.mainform;

import java.util.Date;

import javax.swing.JOptionPane;

import com.javafee.common.Constans;
import com.javafee.common.Constans.Role;
import com.javafee.common.IActionForm;
import com.javafee.common.SystemProperties;
import com.javafee.common.Utils;
import com.javafee.exception.LogGuiException;
import com.javafee.hibernate.dao.HibernateDao;
import com.javafee.hibernate.dao.HibernateUtil;
import com.javafee.hibernate.dto.eeatery.Dish;
import com.javafee.hibernate.dto.eeatery.Invoice;
import com.javafee.model.DishTableModel;
import com.javafee.service.impl.InvoiceServiceImpl;
import com.javafee.startform.LogInEvent;

public class Actions implements IActionForm {

	private MainForm mainForm = new MainForm();
	private DishAddEvent dishAddEvent = null;
	private WorkerManagementEvent workerManagementEvent = null;

	public void control() {
		this.initializeForm();
		mainForm.setVisible(true);

		mainForm.getBtnLogOut().addActionListener(e -> onClickBtnLogOut());
		mainForm.getActionPanel().getBtnAdd().addActionListener(e -> onClickBtnAdd());
		mainForm.getActionPanel().getBtnDelete().addActionListener(e -> onClickBtnDelete());
		mainForm.getActionPanel().getBtnInvoice().addActionListener(e -> onClickBtnInvoice());
		mainForm.getBtnManageWorkers().addActionListener(e -> onClickBtnManageWorkers());
	}

	@Override
	public void initializeForm() {
		this.setControlsVisibility();
		this.reloadLblLogInInformationDynamic();
	}

	private void reloadLblLogInInformationDynamic() {
		StringBuilder logInInformation = new StringBuilder(mainForm.getLblLogInInformation().getText());

		Role role = LogInEvent.getRole();
		String stringRole = null;
		if (role == Role.CLIENT)
			stringRole = Constans.ROLE_CLIENT;
		else if (role == Role.ADMIN)
			stringRole = Constans.ROLE_ADMIN;
		else if (role == Role.WORKER)
			stringRole = Constans.ROLE_WORKER;

		if (LogInEvent.getLoggedUser() != null)
			logInInformation.append(LogInEvent.getLoggedUser().getName() + " " + LogInEvent.getLoggedUser().getSurname()
					+ ", " + stringRole + " [" + Constans.APPLICATION_DATE_TIME_FORMAT.format(LogInEvent.getLogInDate())
					+ "]");
		else
			logInInformation.append(
					stringRole + " [" + Constans.APPLICATION_DATE_TIME_FORMAT.format(LogInEvent.getLogInDate()) + "]");

		mainForm.getLblLogInInformation().setText(logInInformation.toString());
	}

	private void setControlsVisibility() {
		mainForm.getActionPanel().getBtnAdd()
				.setVisible(LogInEvent.getIsAdmin() || LogInEvent.getRole() == Role.WORKER);
		mainForm.getActionPanel().getBtnDelete()
				.setVisible(LogInEvent.getIsAdmin() || LogInEvent.getRole() == Role.WORKER);
		mainForm.getActionPanel().getBtnInvoice()
				.setVisible(LogInEvent.getIsAdmin() || LogInEvent.getRole() == Role.CLIENT);
		mainForm.getBtnManageWorkers().setVisible(LogInEvent.getIsAdmin());
	}

	private void onClickBtnLogOut() {
		LogInEvent.clearLogInData();
		mainForm.dispose();
		mainForm = null;
		openStartForm();
	}

	private void openStartForm() {
		com.javafee.startform.Actions actions = new com.javafee.startform.Actions();
		actions.control();
	}

	private void onClickBtnAdd() {
		if (dishAddEvent == null)
			dishAddEvent = new DishAddEvent();
		dishAddEvent.control((DishTableModel) mainForm.getDishTablePanel().getTable().getModel());
	}

	private void onClickBtnDelete() {
		if (mainForm.getDishTablePanel().getTable().getSelectedRow() != -1) {
			int[] selectedRowsIndexes = mainForm.getDishTablePanel().getTable().getSelectedRows();

			if (selectedRowsIndexes.length > 0) {
				if (Utils.displayConfirmDialog(
						SystemProperties.getInstance().getResourceBundle().getString("confirmDialog.deleteMessage"),
						"") == JOptionPane.YES_OPTION) {
					for (int i = 0; i < selectedRowsIndexes.length; i++) {
						int selectedRowIndex = mainForm.getDishTablePanel().getTable()
								.convertRowIndexToModel(selectedRowsIndexes[i]);
						Dish selectedDish = ((DishTableModel) mainForm.getDishTablePanel().getTable().getModel())
								.getDish(selectedRowIndex);

						HibernateUtil.beginTransaction();
						HibernateUtil.getSession().delete(selectedDish);
						HibernateUtil.commitTransaction();
					}

				}
			}
		} else {
			LogGuiException.logWarning(
					SystemProperties.getInstance().getResourceBundle()
							.getString("tabClientEvent.notSelectedClientWarningTitle"),
					SystemProperties.getInstance().getResourceBundle()
							.getString("tabClientEvent.notSelectedClientWarning"));
		}

		((DishTableModel) mainForm.getDishTablePanel().getTable().getModel()).reloadData();
	}

	private void onClickBtnInvoice() {
		createInvoice();
	}

	private void createInvoice() {
		if (mainForm.getDishTablePanel().getTable().getSelectedRow() != -1) {
			int[] selectedRowsIndexes = mainForm.getDishTablePanel().getTable().getSelectedRows();
			boolean isOrdered = false;

			if (selectedRowsIndexes.length > 0) {
				Invoice invoice = new Invoice();
				invoice.setClient(LogInEvent.getClient());
				invoice.setRealizationDate(new Date());

				for (int i = 0; i < selectedRowsIndexes.length; i++) {
					int selectedRowIndex = mainForm.getDishTablePanel().getTable()
							.convertRowIndexToModel(selectedRowsIndexes[i]);
					Dish selectedDish = ((DishTableModel) mainForm.getDishTablePanel().getTable().getModel())
							.getDish(selectedRowIndex);

					if (selectedDish.getMaxInvoiceNumber() > 0) {
						invoice.getDish().add(selectedDish);
						selectedDish.setMaxInvoiceNumber(selectedDish.getMaxInvoiceNumber() - 1);

						HibernateUtil.beginTransaction();
						HibernateUtil.getSession().update(selectedDish);
						HibernateUtil.commitTransaction();

						isOrdered = true;

					} else
						LogGuiException.logWarning(
								SystemProperties.getInstance().getResourceBundle()
										.getString("mainForm.orderErrorTitle"),
								SystemProperties.getInstance().getResourceBundle().getString("mainForm.orderError")
										+ selectedDish.toString());

				}

				HibernateUtil.beginTransaction();
				HibernateUtil.getSession().save(invoice);
				HibernateUtil.commitTransaction();

				if (isOrdered)
					JOptionPane.showMessageDialog(null,
							SystemProperties.getInstance().getResourceBundle().getString("mainForm.dishOrderSuccess")
									+ new InvoiceServiceImpl(new HibernateDao<>(Invoice.class))
											.calculateInvoiceCost(Invoice.class, invoice.getIdInvoice()),
							SystemProperties.getInstance().getResourceBundle()
									.getString("mainForm.dishOrderSuccessTitle"),
							JOptionPane.INFORMATION_MESSAGE);
			}
		} else {
			LogGuiException.logWarning(
					SystemProperties.getInstance().getResourceBundle()
							.getString("tabClientEvent.notSelectedClientWarningTitle"),
					SystemProperties.getInstance().getResourceBundle()
							.getString("tabClientEvent.notSelectedClientWarning"));
		}

		((DishTableModel) mainForm.getDishTablePanel().getTable().getModel()).reloadData();

	}

	private void onClickBtnManageWorkers() {
		if (workerManagementEvent == null)
			workerManagementEvent = new WorkerManagementEvent();
		workerManagementEvent.control();
	}

}
