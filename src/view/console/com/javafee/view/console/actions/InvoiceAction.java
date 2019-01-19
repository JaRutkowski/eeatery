package com.javafee.view.console.actions;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Scanner;

import com.javafee.hibernate.dao.HibernateDao;
import com.javafee.hibernate.dto.eeatery.Client;
import com.javafee.hibernate.dto.eeatery.Dish;
import com.javafee.hibernate.dto.eeatery.Invoice;
import com.javafee.service.impl.ClientServiceImpl;
import com.javafee.service.impl.DishServiceImpl;
import com.javafee.service.impl.InvoiceServiceImpl;

public class InvoiceAction implements IChoiceAction<Invoice> {

	@Override
	public void displayDescription(String description) {
		System.out.println(description);
	}

	@Override
	public Invoice create() {
		@SuppressWarnings("resource")
		Scanner scanner = new Scanner(System.in);
		Invoice invoice = new Invoice();

		System.out.print("realization date [dd.MM.yyyy]: ");
		try {
			invoice.setRealizationDate(new SimpleDateFormat("dd.MM.yyyy").parse(scanner.nextLine()));
		} catch (ParseException e) {
			e.printStackTrace();
		}
		System.out.print("dish id: ");
		Integer dishId = scanner.nextInt();
		DishServiceImpl dishServiceImpl = new DishServiceImpl(new HibernateDao<Dish, Integer>(Dish.class));
		invoice.getDish().add(dishServiceImpl.findByPrimaryKey(Dish.class, dishId));
		System.out.print("client id: ");
		Integer clientId = scanner.nextInt();
		ClientServiceImpl userServiceImpl = new ClientServiceImpl(new HibernateDao<Client, Integer>(Client.class));
		invoice.setClient(userServiceImpl.findByPrimaryKey(Client.class, clientId));

		return invoice;
	}

	@Override
	public Invoice delete() {
		@SuppressWarnings("resource")
		Scanner scanner = new Scanner(System.in);

		System.out.print("invoice id: ");
		Integer dishId = scanner.nextInt();
		InvoiceServiceImpl invoiceServiceImpl = new InvoiceServiceImpl(
				new HibernateDao<Invoice, Integer>(Invoice.class));
		return invoiceServiceImpl.findByPrimaryKey(Invoice.class, dishId);
	}

	@Override
	public void printAll(Iterable<Invoice> iterable) {
		iterable.forEach(e -> System.out.println(e));
	}

}
