package com.javafee.service.impl;

import com.javafee.hibernate.dao.GenericDao;
import com.javafee.hibernate.dto.eeatery.Dish;

public class DishServiceImpl extends GenericServiceImpl<Dish, Integer> {

	public DishServiceImpl(GenericDao<Dish, Integer> genericDao) {
		super(genericDao);
	}

}
