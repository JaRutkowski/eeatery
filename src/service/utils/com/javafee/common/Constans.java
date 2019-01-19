package com.javafee.common;

import java.awt.Dimension;
import java.text.SimpleDateFormat;
import java.util.stream.Stream;

public final class Constans {

	public enum DishTableColumn {
		COL_NAME(0), COL_PRICE(1), COL_MAX_INVOICE_NUMBER(2), COL_VEGETARIAN(3);

		private final Integer value;

		DishTableColumn(final int newValue) {
			value = newValue;
		}

		public Integer getValue() {
			return value;
		}

		public static DishTableColumn getByNumber(int dishTableSelectedIndex) {
			return Stream.of(DishTableColumn.values()).filter(item -> item.getValue().equals(dishTableSelectedIndex))
					.findFirst().get();
		}
	}

	public enum WorkerTableColumn {
		COL_PESEL_NUMBER(0), COL_DOCUMENT_NUMBER(1), COL_LOGIN(2), COL_E_MAIL(3), COL_NAME(4), COL_SURNAME(5),
		COL_ADDRESS(6), COL_CITY(7), COL_SEX(8), COL_BIRTH_DATE(9), COL_REGISTERED(10);

		private final Integer value;

		WorkerTableColumn(final int newValue) {
			value = newValue;
		}

		public Integer getValue() {
			return value;
		}

		public static WorkerTableColumn getByNumber(int clientTableSelectedIndex) {
			return Stream.of(WorkerTableColumn.values())
					.filter(item -> item.getValue().equals(clientTableSelectedIndex)).findFirst().get();
		}
	}

	public enum Role {
		ADMIN, WORKER, CLIENT;
	}

	public enum Context {
		ADDITION, MODIFICATION, CANCELED;
	}

	public static final String APPLICATION_NAME = "e-eatery";
	public static final String APPLICATION_LANGUAGE = "pl";
	public static final String APPLICATION_LANGUAGE_PL = "pl";
	public static final String APPLICATION_LANGUAGE_EN = "en";
	public static final Object APPLICATION_COMBO_BOX_BLANK_OBJECT = null;
	public static final SimpleDateFormat APPLICATION_DATE_FORMAT = new SimpleDateFormat("dd.MM.yyyy");
	public static final SimpleDateFormat APPLICATION_DATE_TIME_FORMAT = new SimpleDateFormat("dd.MM.yyyy HH:mm:ss");
	public static final String APPLICATION_CURRENCY = "PLN";
	public static final Integer APPLICATION_MIN_PASSWORD_LENGTH = 8;
	public static final Integer APPLICATION_MAX_PASSWORD_LENGTH = 16;
	public static final Integer APPLICATION_GENERATE_PASSWORD_LENGTH = 16;
	public static final String APPLICATION_EMAIL = "nreply.eeatery@gmail.com";
	public static final String APPLICATION_EMAIL_PASSWORD = "Admin95!";
	public static final String APPLICATION_TEMPLATE_ENCODING = "UTF-8";

	public static final Dimension START_FORM_MINIMUM_SIZE = new Dimension(300, 200);
	public static final Dimension EMAIL_FORM_MINIMUM_SIZE = new Dimension(800, 700);

	public static final String ROLE_ADMIN = SystemProperties.getInstance().getResourceBundle()
			.getString("constans.ROLE_ADMIN");
	public static final String WORKER_ACCOUNTANT = SystemProperties.getInstance().getResourceBundle()
			.getString("constans.WORKER_ACCOUNTANT");
	public static final String WORKER_LIBRARIAN = SystemProperties.getInstance().getResourceBundle()
			.getString("constans.WORKER_LIBRARIAN");
	public static final String ROLE_WORKER = SystemProperties.getInstance().getResourceBundle()
			.getString("constans.WORKER");
	public static final String ROLE_CLIENT = SystemProperties.getInstance().getResourceBundle()
			.getString("constans.CLIENT");

	public static final String LANGUAGE_RESOURCE_BUNDLE = "messages";

	public static final Character DATA_BASE_MALE_SIGN = 'M';
	public static final Character DATA_BASE_FEMALE_SIGN = 'F';
	public static final Boolean DATA_BASE_REGISTER_DEFAULT_FLAG = false;
	public static final String DATA_BASE_ADMIN_LOGIN = "admin";
	public static final String DATA_BASE_ADMIN_PASSWORD = "192023a7bbd73250516f069df18b500";
	public static final String DATA_BASE_RESET_PASSWORD = "9ed62c5de38c30878855eb50eec488ad";
	public static final Integer DATA_BASE_INVENTORY_NUMBER_LENGTH = 13;
	public static final Integer DATA_BASE_ISBN_NUMBER_LENGTH = 13;
	public static final Integer DATA_BASE_PESEL_NUMBER_LENGHT = 11;
	public static final String DATA_BASE_MESSAGE_TYPE_USR_MESSAGE = "usr_message";
	public static final String DATA_BASE_MESSAGE_TYPE_SYS_MESSAGE = "sys_message";
	public static final String DATA_BASE_MESSAGE_TYPE_SYS_NOTIFICATION = "sys_notifiaction";

}
