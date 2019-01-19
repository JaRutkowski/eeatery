package com.javafee.model;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import javax.swing.event.TableModelEvent;
import javax.swing.table.AbstractTableModel;

import com.javafee.common.Constans.DishTableColumn;
import com.javafee.common.SystemProperties;
import com.javafee.hibernate.dao.HibernateDao;
import com.javafee.hibernate.dao.HibernateUtil;
import com.javafee.hibernate.dto.eeatery.Dish;
import com.javafee.service.impl.DishServiceImpl;

public class DishTableModel extends AbstractTableModel {
	private static final long serialVersionUID = 1L;

	protected List<Dish> dishes = null;
	private String[] columns;

	protected DishServiceImpl dishServiceImpl = new DishServiceImpl(new HibernateDao<>(Dish.class));

	public DishTableModel() {
		super();
		this.prepareDishList();
		this.columns = new String[] {
				SystemProperties.getInstance().getResourceBundle().getString("dishTableModel.nameCol"),
				SystemProperties.getInstance().getResourceBundle().getString("dishTableModel.priceCol"),
				SystemProperties.getInstance().getResourceBundle().getString("dishTableModel.maxInvoiceNumberCol"),
				SystemProperties.getInstance().getResourceBundle().getString("dishTableModel.vegetarianCol") };
	}

	public Dish getDish(int index) {
		return dishes.get(index);
	}

	public void setDish(int index, Dish dish) {
		dishes.set(index, dish);
	}

	public void add(Dish dish) {
		dishes.add(dish);
		this.fireTableDataChanged();
	}

	public void remove(Dish dish) {
		dishes.remove(dish);
		this.fireTableDataChanged();
	}

	protected void prepareDishList() {
		dishes = new ArrayList<>();
		this.dishServiceImpl.findAll().forEach(e -> dishes.add(e));
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
		return dishes.size();
	}

	@Override
	public Object getValueAt(int row, int col) {
		Dish dish = dishes.get(row);
		switch (DishTableColumn.getByNumber(col)) {
		case COL_NAME:
			return dish.getName();
		case COL_PRICE:
			return dish.getPrice();
		case COL_MAX_INVOICE_NUMBER:
			return dish.getMaxInvoiceNumber();
		case COL_VEGETARIAN:
			return dish.getVegetarian()
					? SystemProperties.getInstance().getResourceBundle().getString("clientTableModel.registeredTrueVal")
					: SystemProperties.getInstance().getResourceBundle()
							.getString("clientTableModel.registeredFalseVal");
		default:
			return null;
		}
	}

	@Override
	public void setValueAt(Object value, int row, int col) {
		Dish dish = dishes.get(row);
		Dish dishShallowClone = (Dish) dish.clone();
		switch (DishTableColumn.getByNumber(col)) {
		case COL_NAME:
			dishShallowClone.setName(value.toString());
			break;
		case COL_PRICE:
			dishShallowClone.setPrice(new BigDecimal((Double) value));
			break;
		case COL_MAX_INVOICE_NUMBER:
			dishShallowClone.setMaxInvoiceNumber((Integer) value);
			break;
		case COL_VEGETARIAN:
			dishShallowClone.setVegetarian((Boolean) value);
			break;
		}

		executeUpdate(Dish.class.getName(), dish);
		dishes.set(row, dishShallowClone);

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
		prepareDishList();
		fireTableDataChanged();
	}

	@Override
	public void fireTableChanged(TableModelEvent e) {
		super.fireTableChanged(e);
	}

}
