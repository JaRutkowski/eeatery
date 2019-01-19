package com.javafee.test;

import java.math.BigDecimal;
import java.util.Date;

import javax.transaction.Transactional;

import org.junit.Assert;
import org.junit.Test;

import com.javafee.hibernate.dao.HibernateDao;
import com.javafee.hibernate.dto.eeatery.Dish;
import com.javafee.hibernate.dto.eeatery.Invoice;
import com.javafee.service.impl.InvoiceServiceImpl;

public class InvoiceServiceTest {

	private InvoiceServiceImpl invoiceService = new InvoiceServiceImpl(new HibernateDao<>(Invoice.class));

	private HibernateDao<Dish, Integer> dishDo = new HibernateDao<>(Dish.class);
	private HibernateDao<Invoice, Integer> invoiceDdao = new HibernateDao<>(Invoice.class);

	@Test
	@Transactional
	public void testAddDish() {
		Dish dishTest = new Dish();
		dishTest.setName("Test");
		dishTest.setPrice(new BigDecimal(10.0));
		dishDo.save(dishTest);

		Dish dishTest1 = new Dish();
		dishTest1.setName("Test1");
		dishTest1.setPrice(new BigDecimal(20.0));
		dishDo.save(dishTest1);

		Dish dishTest2 = new Dish();
		dishTest2.setName("Test2");
		dishTest2.setPrice(new BigDecimal(30.0));
		dishDo.save(dishTest2);

		Invoice invoice = new Invoice();
		invoice.setRealizationDate(new Date());
		invoice.getDish().add(dishTest);
		invoice.getDish().add(dishTest1);
		invoice.getDish().add(dishTest2);
		invoiceDdao.save(invoice);

		Assert.assertEquals(new Double(60.0),
				invoiceService.calculateInvoiceCost(Invoice.class, invoice.getIdInvoice()));
	}

}
