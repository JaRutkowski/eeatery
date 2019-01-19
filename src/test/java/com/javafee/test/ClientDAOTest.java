package com.javafee.test;

import java.util.Date;

import javax.transaction.Transactional;

import org.junit.Assert;
import org.junit.Test;

import com.javafee.hibernate.dao.HibernateDao;
import com.javafee.hibernate.dto.eeatery.Client;

public class ClientDAOTest {

	private HibernateDao<Client, Integer> dao = new HibernateDao<>(Client.class);

	@Test
	@Transactional
	public void testAddDish() {
		Client client = new Client();
		client.setName("Test");
		client.setSurname("Test");
		client.setLogin("Test");
		client.setRegisterDate(new Date());
		client.setEMail("test@test.pl");

		dao.save(client);

		Assert.assertEquals(client.getName(), dao.findByPrimaryKey(client.getIdUsers()).getName());
	}

}
