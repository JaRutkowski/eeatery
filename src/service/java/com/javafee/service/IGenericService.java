package com.javafee.service;

import java.io.Serializable;

public interface IGenericService<T, K extends Serializable> {

	public T save(T entity);

	T saveOrUpdate(T entity);

	T update(T entity);

	public void delete(T entity);

	public Iterable<T> findAll();

	public T findByPrimaryKey(Class<T> c, Integer id);

}
