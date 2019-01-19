package com.javafee.view.console;

import java.util.Scanner;

import com.javafee.hibernate.dao.HibernateDao;
import com.javafee.hibernate.dao.HibernateUtil;
import com.javafee.hibernate.dto.eeatery.Client;
import com.javafee.hibernate.dto.eeatery.Dish;
import com.javafee.hibernate.dto.eeatery.Invoice;
import com.javafee.service.impl.ClientServiceImpl;
import com.javafee.service.impl.DishServiceImpl;
import com.javafee.service.impl.GenericServiceImpl;
import com.javafee.service.impl.InvoiceServiceImpl;
import com.javafee.view.console.actions.ClientAction;
import com.javafee.view.console.actions.DishAction;
import com.javafee.view.console.actions.IChoiceAction;
import com.javafee.view.console.actions.InvoiceAction;

public class Actions {

	public void control() {
		this.initialize();
	}

	private void initialize() {
		System.out.print("Type your choice or quit [q]: ");
		@SuppressWarnings("resource")
		Scanner scanner = new Scanner(System.in);
		String choice = scanner.nextLine();
		while (!"q".equals(choice)) {
			this.processChoice(choice);
			System.out.print("Type your choice or quit [q]: ");
			choice = scanner.nextLine();
		}
	}

	private void processChoice(String choice) {
		switch (choice) {
		case "create-user":
		case "cu":
			this.createUser();
			break;
		case "update-user-password":
		case "uup":
			this.updateUserPassword();
			break;
		case "delete-user":
		case "du":
			this.deleteUser();
			break;
		case "print-users":
		case "pus":
			this.printUsers();
			break;
		case "create-dish":
		case "cd":
			this.createDish();
			break;
		case "delete-dish":
		case "dd":
			this.deleteDish();
			break;
		case "print-dished":
		case "pds":
			this.printDishes();
			break;
		case "create-invoice":
		case "ci":
			this.createInvoice();
			break;
		case "delete-invoice":
		case "di":
			this.deleteInvoice();
			break;
		case "print-invoices":
		case "pis":
			this.printInvoices();
			break;
		case "quit":
		case "q":
			return;

		default:
			break;
		}
	}

	private <T> void delete(IChoiceAction<T> choiceAction, GenericServiceImpl<T, Integer> service, String description) {
		choiceAction.displayDescription("-- deleting " + description + "...");

		HibernateUtil.beginTransaction();
		service.delete(choiceAction.delete());
		HibernateUtil.commitTransaction();
	}

	private <T> void create(IChoiceAction<T> choiceAction, GenericServiceImpl<T, Integer> service, String description) {
		choiceAction.displayDescription("-- creating " + description + "...");

		HibernateUtil.beginTransaction();
		service.save(choiceAction.create());
		HibernateUtil.commitTransaction();
	}

	private <T> void printAll(IChoiceAction<T> choiceAction, GenericServiceImpl<T, Integer> service,
			String description) {
		choiceAction.displayDescription("-- printing all " + description + "...");

		HibernateUtil.beginTransaction();
		choiceAction.printAll(service.findAll());
		HibernateUtil.commitTransaction();
	}

	private void createUser() {
		ClientAction userAction = new ClientAction();
		ClientServiceImpl userServiceImpl = new ClientServiceImpl(new HibernateDao<Client, Integer>(Client.class));

		this.create(userAction, userServiceImpl, "client");
	}

	private void updateUserPassword() {
		ClientAction userAction = new ClientAction();
		ClientServiceImpl userServiceImpl = new ClientServiceImpl(new HibernateDao<Client, Integer>(Client.class));
		@SuppressWarnings("resource")
		Scanner scanner = new Scanner(System.in);
		String password = scanner.nextLine();

		HibernateUtil.beginTransaction();
		userServiceImpl.changePassword(userAction.updatePassword(), password);
		HibernateUtil.commitTransaction();
	}

	private void deleteUser() {
		ClientAction userAction = new ClientAction();
		ClientServiceImpl userServiceImpl = new ClientServiceImpl(new HibernateDao<Client, Integer>(Client.class));

		this.delete(userAction, userServiceImpl, "user");
	}

	private void printUsers() {
		ClientAction userAction = new ClientAction();
		ClientServiceImpl userServiceImpl = new ClientServiceImpl(new HibernateDao<Client, Integer>(Client.class));

		this.printAll(userAction, userServiceImpl, "users");
	}

	private void createDish() {
		DishAction dishAction = new DishAction();
		DishServiceImpl dishServiceImpl = new DishServiceImpl(new HibernateDao<Dish, Integer>(Dish.class));

		this.create(dishAction, dishServiceImpl, "dish");
	}

	private void deleteDish() {
		DishAction dishAction = new DishAction();
		DishServiceImpl dishServiceImpl = new DishServiceImpl(new HibernateDao<Dish, Integer>(Dish.class));

		this.delete(dishAction, dishServiceImpl, "dish");
	}

	private void printDishes() {
		DishAction dishAction = new DishAction();
		DishServiceImpl dishServiceImpl = new DishServiceImpl(new HibernateDao<Dish, Integer>(Dish.class));

		this.printAll(dishAction, dishServiceImpl, "dishes");
	}

	private void createInvoice() {
		InvoiceAction invoiceAction = new InvoiceAction();
		InvoiceServiceImpl invoiceServiceImpl = new InvoiceServiceImpl(
				new HibernateDao<Invoice, Integer>(Invoice.class));

		this.create(invoiceAction, invoiceServiceImpl, "invoice");
	}

	private void deleteInvoice() {
		InvoiceAction invoiceAction = new InvoiceAction();
		InvoiceServiceImpl invoiceServiceImpl = new InvoiceServiceImpl(
				new HibernateDao<Invoice, Integer>(Invoice.class));

		this.delete(invoiceAction, invoiceServiceImpl, "invoice");
	}

	private void printInvoices() {
		InvoiceAction invoiceAction = new InvoiceAction();
		InvoiceServiceImpl invoiceServiceImpl = new InvoiceServiceImpl(
				new HibernateDao<Invoice, Integer>(Invoice.class));

		this.printAll(invoiceAction, invoiceServiceImpl, "invoices");
	}

}
