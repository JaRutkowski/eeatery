package com.javafee.service.impl;

import com.javafee.hibernate.dao.GenericDao;
import com.javafee.hibernate.dto.common.Address;
import com.javafee.hibernate.dto.common.User;
import com.javafee.service.common.Common;

public class UserServiceImpl extends GenericServiceImpl<User, Integer> {

	public UserServiceImpl(GenericDao<User, Integer> genericDao) {
		super(genericDao);
	}

	public User changePassword(User user, String password) {
		user.setPassword(Common.createMd5(password));
		return this.update(user);
	}

	public User changeAddress(User user, Address address) {
		user.setAddress(address);
		return this.update(user);
	}

	public User findUserById(Class<User> c, Integer id) {
		return this.findByPrimaryKey(c, id);
	}

}
