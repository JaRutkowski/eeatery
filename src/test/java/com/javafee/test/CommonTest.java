package com.javafee.test;

import org.junit.Assert;
import org.junit.Test;

import com.javafee.common.Common;
import com.javafee.common.Constans;
import com.javafee.hibernate.dto.eeatery.Client;

public class CommonTest {

	@Test
	public void createMd5() {
		Common c = new Common();
		String pass = "AWPTO2019";
		@SuppressWarnings("static-access")
		String m = c.createMd5(pass);
		Assert.assertEquals("fd9291bb5765d3b5c77865fa620d1f88", m);
	}

	@Test
	public void generatePassword() {
		Common c = new Common();
		@SuppressWarnings("static-access")
		String pass = c.generatePassword();
		Assert.assertTrue(pass.length() == Constans.APPLICATION_GENERATE_PASSWORD_LENGTH);
	}

	@SuppressWarnings("static-access")
	@Test
	public void checkPasswordStrenght() {
		Common c = new Common();
		String strongPass = "Pi32434CCccmo#";
		@SuppressWarnings("static-access")
		boolean pass = c.checkPasswordStrenght(strongPass);
		Assert.assertTrue(pass);

		String weakPass = "awpto";
		pass = c.checkPasswordStrenght(weakPass);
		Assert.assertFalse(pass);
	}

	@Test
	public void isAdmin() {
		String login = "login";
		String password = "pass";
		Common c = new Common();
		@SuppressWarnings("static-access")
		boolean p = c.isAdmin(login, password);
		Assert.assertFalse(p);
	}

	@Test
	public void isAdmin1() {
		Client clien = new Client();
		Common c = new Common();
		@SuppressWarnings("static-access")
		boolean p = c.isAdmin(clien);
		Assert.assertFalse(p);
	}
}