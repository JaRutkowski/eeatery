package com.javafee.view.console.actions;

import java.util.Date;
import java.util.Scanner;

import com.javafee.hibernate.dao.HibernateDao;
import com.javafee.hibernate.dto.eeatery.Client;
import com.javafee.service.common.Common;
import com.javafee.service.impl.ClientServiceImpl;

public class ClientAction implements IChoiceAction<Client> {

	@Override
	public void displayDescription(String description) {
		System.out.println(description);
	}

	@Override
	public Client create() {
		@SuppressWarnings("resource")
		Scanner scanner = new Scanner(System.in);
		Client client = new Client();

		System.out.print("login: ");
		client.setLogin(scanner.nextLine());
		System.out.print("password: ");
		client.setPassword(Common.createMd5(scanner.nextLine()));
		System.out.print("name: ");
		client.setName(scanner.nextLine());
		System.out.print("surname: ");
		client.setSurname(scanner.nextLine());
		System.out.print("email: ");
		client.setEMail(scanner.nextLine());
		System.out.print("phone number: ");
		client.setPhoneNumber(scanner.nextLine());

		client.setRegisterDate(new Date());

		return client;
	}

	public Client updatePassword() {
		@SuppressWarnings("resource")
		Scanner scanner = new Scanner(System.in);
		Client client = new Client();

		System.out.print("type user id: ");
		client.setLogin(scanner.nextLine());

		return client;
	}

	@Override
	public Client delete() {
		@SuppressWarnings("resource")
		Scanner scanner = new Scanner(System.in);

		System.out.print("type client id: ");
		Integer clientId = scanner.nextInt();
		ClientServiceImpl userServiceImpl = new ClientServiceImpl(new HibernateDao<Client, Integer>(Client.class));
		return userServiceImpl.findByPrimaryKey(Client.class, clientId);
	}

	@Override
	public void printAll(Iterable<Client> iterable) {
		iterable.forEach(e -> System.out.println(e));
	}

}
