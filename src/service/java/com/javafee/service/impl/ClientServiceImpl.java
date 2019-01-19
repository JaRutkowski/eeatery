package com.javafee.service.impl;

import com.javafee.hibernate.dao.GenericDao;
import com.javafee.hibernate.dto.common.Address;
import com.javafee.hibernate.dto.eeatery.Client;
import com.javafee.service.common.Common;

public class ClientServiceImpl extends GenericServiceImpl<Client, Integer> {

	public ClientServiceImpl(GenericDao<Client, Integer> genericDao) {
		super(genericDao);
	}

	public Client changePassword(Client client, String password) {
		client.setPassword(Common.createMd5(password));
		return this.update(client);
	}

	public Client changeAddress(Client client, Address address) {
		client.setAddress(address);
		return this.update(client);
	}

	public Client findClientById(Class<Client> c, Integer id) {
		return this.findByPrimaryKey(c, id);
	}

}
