package com.javafee.service.impl;

import com.javafee.hibernate.dao.GenericDao;
import com.javafee.hibernate.dto.association.City;
import com.javafee.hibernate.dto.common.User;

public class CityServiceImpl extends GenericServiceImpl<City, Integer> {

	public CityServiceImpl(GenericDao<User, Integer> genericDao) {
		super(genericDao);
	}

}
