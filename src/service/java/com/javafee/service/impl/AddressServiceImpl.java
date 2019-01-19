package com.javafee.service.impl;

import com.javafee.hibernate.dao.GenericDao;
import com.javafee.hibernate.dto.common.Address;

public class AddressServiceImpl extends GenericServiceImpl<Address, Integer> {

	public AddressServiceImpl(GenericDao<Address, Integer> genericDao) {
		super(genericDao);
	}

}
