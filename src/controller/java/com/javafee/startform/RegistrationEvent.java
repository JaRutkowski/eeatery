package com.javafee.startform;

import java.util.Date;

import com.javafee.common.Common;
import com.javafee.common.Constans.Role;
import com.javafee.common.Params;
import com.javafee.exception.RefusedRegistrationException;
import com.javafee.hibernate.dao.HibernateUtil;
import com.javafee.hibernate.dto.association.City;
import com.javafee.hibernate.dto.common.Address;
import com.javafee.hibernate.dto.common.User;
import com.javafee.hibernate.dto.eeatery.Client;
import com.javafee.hibernate.dto.eeatery.Worker;

import lombok.Getter;

public class RegistrationEvent {
	private static RegistrationEvent registrationEvent = null;
	@Getter
	private static Date registrationDate;

	public static com.javafee.hibernate.dto.common.User user = null;

	public enum RegistrationFailureCause {
		ALREADY_REGISTERED, PARAMETERS_ERROR, WEAK_PASSWORD, INCORRECT_BIRTH_DATE, UNIDENTIFIED
	}

	private RegistrationEvent() {
	}

	public static RegistrationEvent getInstance(String name, String surname, String address, City city, String login,
			String eMail, String password, Role role) throws RefusedRegistrationException {
		if (checkRegistration(login, password, role)) {
			registrationEvent = new RegistrationEvent();
			registrationDate = new Date();
			RegistrationEvent.user = createUser(name, surname, address, city, login, eMail, password, role);
		} else
			throw new RefusedRegistrationException("Cannot register to the system");
		return registrationEvent;
	}

	public static void forceClearRegistrationEvenet() {
		registrationEvent = null;
	}

	private static boolean checkRegistration(String login, String password, Role role) {
		boolean result = false;
		Worker worker = null;
		switch (role) {
		case WORKER:
			worker = (Worker) HibernateUtil.getSession().getNamedQuery("Worker.checkIfWorkerLoginExist")
					.setParameter("login", login).uniqueResult();

			if (worker != null)
				Params.getInstance().add("ALREADY_REGISTERED", RegistrationFailureCause.ALREADY_REGISTERED);
			else {
				if (Common.checkPasswordStrenght(password))
					result = true;
				else
					Params.getInstance().add("WEAK_PASSWORD", RegistrationFailureCause.WEAK_PASSWORD);
			}
		case ADMIN:
			worker = (Worker) HibernateUtil.getSession().getNamedQuery("Worker.checkIfWorkerLoginExist")
					.setParameter("login", login).uniqueResult();

			if (worker != null)
				Params.getInstance().add("ALREADY_REGISTERED", RegistrationFailureCause.ALREADY_REGISTERED);
			else {
				if (Common.checkPasswordStrenght(password))
					result = true;
				else
					Params.getInstance().add("WEAK_PASSWORD", RegistrationFailureCause.WEAK_PASSWORD);
			}
			break;
		case CLIENT:
			Client client = (Client) HibernateUtil.getSession().getNamedQuery("Client.checkIfClientLoginExist")
					.setParameter("login", login).uniqueResult();
			// Client existingPeselClient = (Client)
			// HibernateUtil.getSession().getNamedQuery("User.checkIfUserPeselExist")
			// .setParameter("peselNumber", peselNumber).uniqueResult();
			if (client != null)
				Params.getInstance().add("ALREADY_REGISTERED", RegistrationFailureCause.ALREADY_REGISTERED);
			// if (!"".equals(peselNumber) && (client != null || existingPeselClient !=
			// null))
			// Params.getInstance().add("ALREADY_REGISTERED",
			// RegistrationFailureCause.ALREADY_REGISTERED);
			else {
				if (Common.checkPasswordStrenght(password))
					result = true;
				else
					Params.getInstance().add("WEAK_PASSWORD", RegistrationFailureCause.WEAK_PASSWORD);
			}
			break;
		default:
			break;
		}

		return result;
	}

	private static User createUser(String name, String surname, String address, City city, String login, String eMail,
			String password, Role role) {
		HibernateUtil.beginTransaction();
		Address addressObj = new Address();
		addressObj.setCity(city);
		addressObj.setStreet(address);
		HibernateUtil.getSession().save(addressObj);
		HibernateUtil.commitTransaction();

		HibernateUtil.beginTransaction();
		User resultUser = null;
		Worker worker = null;
		switch (role) {
		case WORKER:
			worker = new Worker();
			worker.setName(name);
			worker.setSurname(surname);
			worker.setLogin(login);
			worker.setEMail(eMail);
			worker.setPassword(Common.createMd5(password));
			worker.setAddress(addressObj);
			HibernateUtil.getSession().save(worker);
			HibernateUtil.commitTransaction();

			HibernateUtil.beginTransaction();
			resultUser = worker;
			break;
		case ADMIN:
			worker = new Worker();
			worker.setName(name);
			worker.setSurname(surname);
			worker.setLogin(login);
			worker.setEMail(eMail);
			worker.setPassword(Common.createMd5(password));
			HibernateUtil.getSession().save(worker);
			HibernateUtil.commitTransaction();

			HibernateUtil.beginTransaction();
			resultUser = worker;
			break;
		case CLIENT:
			Client client = new Client();
			client.setName(name);
			client.setSurname(surname);
			client.setLogin(login);
			client.setEMail(eMail);
			client.setPassword(Common.createMd5(password));
			client.setAddress(addressObj);
			HibernateUtil.getSession().save(client);
			resultUser = client;
			break;
		default:
			break;
		}

		HibernateUtil.commitTransaction();
		return resultUser;
	}

	@SuppressWarnings("unused")
	private static boolean checkParameters(String peselNumber) {
		boolean result = false;
		return result;
	}
}
