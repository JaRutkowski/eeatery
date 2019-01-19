package com.javafee.service.impl;

import com.javafee.hibernate.dao.GenericDao;
import com.javafee.hibernate.dto.eeatery.Dish;
import com.javafee.hibernate.dto.eeatery.Invoice;

public class InvoiceServiceImpl extends GenericServiceImpl<Invoice, Integer> {

	public InvoiceServiceImpl(GenericDao<Invoice, Integer> genericDao) {
		super(genericDao);
	}

	public Invoice findUserById(Class<Invoice> c, Integer id) {
		return this.findByPrimaryKey(c, id);
	}

	public Double calculateInvoiceCost(Class<Invoice> c, Integer id) {
		Double result = 0.0;
		for (Dish dish : findByPrimaryKey(c, id).getDish()) {
			result += dish.getPrice().doubleValue();
		}
		return result;
	}

}
