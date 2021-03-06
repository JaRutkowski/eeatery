package com.javafee.startform;

import java.util.Date;

import com.javafee.common.Common;
import com.javafee.common.Constans.Role;
import com.javafee.common.Params;
import com.javafee.exception.RefusedLogInException;
import com.javafee.hibernate.dao.HibernateUtil;
import com.javafee.hibernate.dto.common.User;
import com.javafee.hibernate.dto.eeatery.Client;
import com.javafee.hibernate.dto.eeatery.Worker;

import lombok.Getter;

public final class LogInEvent {
	@Getter
	private static LogInEvent logInEvent = null;
	@Getter
	private static Role role;
	@Getter
	private static Client client;
	@Getter
	private static Worker worker;
	@Getter
	private static User loggedUser;
	@Getter
	private static Boolean isAdmin;
	@Getter
	private static Date logInDate;

	public enum LogInFailureCause {
		NOT_REGISTERED, NOT_HIRED, BAD_PASSWORD, NO_USER, UNIDENTIFIED
	}

	private LogInEvent() {
	}

	public static LogInEvent getInstance(String login, String password) throws RefusedLogInException {
		if (checkLogAndRole(login, password)) {
			logInEvent = new LogInEvent();
			logInDate = new Date();
		} else
			throw new RefusedLogInException("Cannot log in to the system");
		return logInEvent;
	}

	public Object getUser() {
		Object user = null;
		if (role == Role.CLIENT) {
			user = client;
		} else if (role == Role.WORKER) {
			user = worker;
		}

		return user;
	}

	private static boolean checkLogAndRole(String login, String password) {
		boolean result = false;

		worker = (Worker) HibernateUtil.getSession().getNamedQuery("Worker.checkIfWorkerLoginExist")
				.setParameter("login", login).uniqueResult();
		client = (Client) HibernateUtil.getSession().getNamedQuery("Client.checkIfClientLoginExist")
				.setParameter("login", login).uniqueResult();

		isAdmin = Common.isAdmin(login, password);

		if (worker != null || client != null) {
			if (worker != null) {
				role = Role.WORKER;
				loggedUser = worker;
			} else if (client != null) {
				role = Role.CLIENT;
				loggedUser = client;
			}
			if (checkLoginAndPassword(password)) {
				result = true;
			}

		} else if (worker == null && isAdmin) {
			role = Role.ADMIN;
			result = true;
		} else {
			Params.getInstance().add("NO_USER", LogInFailureCause.NO_USER);
		}

		return result;
	}

	private static boolean checkLoginAndPassword(String password) {
		boolean result = false;
		String md5 = Common.createMd5(password);

		if (client != null && md5.equals(client.getPassword()))
			result = true;
		else if (worker != null && md5.equals(worker.getPassword()))
			result = true;

		if (!result && worker != null)
			Params.getInstance().add("BAD_PASSWORD", LogInFailureCause.BAD_PASSWORD);
		if (!result && client != null)
			Params.getInstance().add("BAD_PASSWORD", LogInFailureCause.BAD_PASSWORD);

		return result;
	}

	public static void clearLogInData() {
		LogInEvent.role = null;
		LogInEvent.logInDate = null;
	}
}
