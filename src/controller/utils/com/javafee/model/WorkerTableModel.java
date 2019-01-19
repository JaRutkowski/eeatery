package com.javafee.model;

import java.util.List;

import javax.swing.event.TableModelEvent;
import javax.swing.table.AbstractTableModel;

import com.javafee.common.Constans.WorkerTableColumn;
import com.javafee.common.SystemProperties;
import com.javafee.common.Validator;
import com.javafee.exception.LogGuiException;
import com.javafee.hibernate.dao.HibernateDao;
import com.javafee.hibernate.dao.HibernateUtil;
import com.javafee.hibernate.dto.association.City;
import com.javafee.hibernate.dto.eeatery.Worker;

public class WorkerTableModel extends AbstractTableModel {
	private static final long serialVersionUID = 1L;

	private List<Worker> workers;
	private String[] columns;

	private HibernateDao<Worker, Integer> clientDao;

	public WorkerTableModel() {
		super();
		this.prepareHibernateDao();
		this.columns = new String[] {
				SystemProperties.getInstance().getResourceBundle().getString("clientTableModel.loginCol"),
				SystemProperties.getInstance().getResourceBundle().getString("clientTableModel.eMailCol"),
				SystemProperties.getInstance().getResourceBundle().getString("clientTableModel.nameCol"),
				SystemProperties.getInstance().getResourceBundle().getString("clientTableModel.surnameCol"),
				SystemProperties.getInstance().getResourceBundle().getString("clientTableModel.addressCol"),
				SystemProperties.getInstance().getResourceBundle().getString("clientTableModel.cityCol") };
	}

	public Worker getWorker(int index) {
		return workers.get(index);
	}

	public void setWorker(int index, Worker worker) {
		workers.set(index, worker);
	}

	public void add(Worker worker) {
		workers.add(worker);
		this.fireTableDataChanged();
	}

	public void remove(Worker worker) {
		workers.remove(worker);
		this.fireTableDataChanged();
	}

	private void prepareHibernateDao() {
		this.clientDao = new HibernateDao<Worker, Integer>(Worker.class);
		this.workers = clientDao.findAll();
	}

	private void executeUpdate(String entityName, Object object) {
		HibernateUtil.beginTransaction();
		HibernateUtil.getSession().update(entityName, object);
		HibernateUtil.commitTransaction();
	}

	@Override
	public int getColumnCount() {
		return columns.length;
	}

	@Override
	public int getRowCount() {
		return workers.size();
	}

	@Override
	public Object getValueAt(int row, int col) {
		Worker worker = workers.get(row);
		switch (WorkerTableColumn.getByNumber(col)) {
		case COL_LOGIN:
			return worker.getLogin();
		case COL_E_MAIL:
			return worker.getEMail();
		case COL_NAME:
			return worker.getName();
		case COL_SURNAME:
			return worker.getSurname();
		case COL_ADDRESS:
			return worker.getAddress() != null ? worker.getAddress().getStreet() : null;
		case COL_CITY:
			return worker.getAddress() != null && worker.getAddress().getCity() != null
					? worker.getAddress().getCity().getName()
					: null;
		default:
			return null;
		}
	}

	@Override
	public void setValueAt(Object value, int row, int col) {
		Worker worker = workers.get(row);
		Worker workerShallowClone = (Worker) worker.clone();

		switch (WorkerTableColumn.getByNumber(col)) {
		case COL_LOGIN:
			workerShallowClone.setLogin(value.toString());
			break;
		case COL_E_MAIL:
			workerShallowClone.setEMail(value.toString());
			break;
		case COL_NAME:
			workerShallowClone.setName(value.toString());
			break;
		case COL_SURNAME:
			workerShallowClone.setSurname(value.toString());
			break;
		case COL_ADDRESS:
			if (workerShallowClone.getAddress() != null)
				workerShallowClone.getAddress().setStreet(value.toString());
			break;
		case COL_CITY:
			if (workerShallowClone.getAddress() != null)
				workerShallowClone.getAddress().setCity((City) value);
			break;
		default:
			break;
		}

		if (Validator.validateClientUpdate(workerShallowClone)) {
			executeUpdate(Worker.class.getName(), worker);
			workers.set(row, workerShallowClone);
		} else {
			LogGuiException.logWarning(
					SystemProperties.getInstance().getResourceBundle()
							.getString("clientTableModel.constraintViolationErrorTitle"),
					SystemProperties.getInstance().getResourceBundle()
							.getString("clientTableModel.constraintViolationError"));
		}

		this.fireTableRowsUpdated(row, row);
	}

	@Override
	public String getColumnName(int col) {
		return columns[col];
	}

	@Override
	public boolean isCellEditable(int rowIndex, int columnIndex) {
		return false;
	}

	public void reloadData() {
		prepareHibernateDao();
		fireTableDataChanged();
	}

	@Override
	public void fireTableChanged(TableModelEvent e) {
		super.fireTableChanged(e);
	}
}
