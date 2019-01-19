package com.javafee.common;

import java.util.List;

import com.javafee.hibernate.dao.HibernateUtil;
import com.javafee.hibernate.dto.eeatery.Client;
import com.javafee.hibernate.dto.eeatery.Worker;

public final class Validator {
	@SuppressWarnings("unchecked")
	public static boolean validateClientUpdate(Client client) {
		boolean result;

		Client existingLoginClient = (Client) HibernateUtil.getSession()
				.getNamedQuery("Client.checkWithComparingIdIfClientLoginExist").setParameter("login", client.getLogin())
				.setParameter("id", client.getIdUsers()).uniqueResult();
		List<Client> ud = HibernateUtil.getSession().createQuery("from UserData").list();
		for (Client u : ud) {
			if (u.getLogin().equals(client.getLogin()) && u.getIdUsers() != client.getIdUsers())
				return false;
		}

		if (existingLoginClient == null) {
			result = true;
		} else {
			result = false;
		}

		return result;
	}

	public static boolean validateClientPesel(String pesel) {
		boolean result = true;
		if (!"".equals(pesel)) {
			Client existingPeselClient = (Client) HibernateUtil.getSession()
					.getNamedQuery("UserData.checkIfUserDataPeselExist").setParameter("peselNumber", pesel)
					.uniqueResult();
			result = existingPeselClient != null ? false : true;
		}
		return result;
	}

	public static boolean validateWorkerPesel(String pesel) {
		boolean result = true;
		if (!"".equals(pesel)) {
			Worker existingPeselClient = (Worker) HibernateUtil.getSession()
					.getNamedQuery("UserData.checkIfUserDataPeselExist").setParameter("peselNumber", pesel)
					.uniqueResult();
			result = existingPeselClient != null ? false : true;
		}
		return result;
	}

	@SuppressWarnings("unchecked")
	public static boolean validateClientUpdate(Worker client) {
		boolean result;

		Worker existingLoginClient = (Worker) HibernateUtil.getSession()
				.getNamedQuery("Worker.checkWithComparingIdIfClientLoginExist").setParameter("login", client.getLogin())
				.setParameter("id", client.getIdUsers()).uniqueResult();

		List<Worker> ud = HibernateUtil.getSession().createQuery("from UserData").list();
		for (Worker u : ud) {
			if (u.getLogin().equals(client.getLogin()) && u.getIdUsers() != client.getIdUsers())
				return false;
		}

		if (existingLoginClient == null) {
			result = true;
		} else {
			result = false;
		}

		return result;
	}
}
