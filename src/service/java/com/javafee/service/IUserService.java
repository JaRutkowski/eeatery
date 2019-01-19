package com.javafee.service;

import com.javafee.hibernate.dto.common.User;

public interface IUserService extends IGenericService<User, Integer> {

	public void changePassword(User user);

	public void changeAddress(User user);

}
