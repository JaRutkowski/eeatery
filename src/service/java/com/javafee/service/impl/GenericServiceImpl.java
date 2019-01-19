package com.javafee.service.impl;

import java.io.Serializable;

import com.javafee.hibernate.dao.GenericDao;
import com.javafee.hibernate.dao.HibernateUtil;
import com.javafee.service.IGenericService;

public abstract class GenericServiceImpl<T, K extends Serializable> implements IGenericService<T, Serializable> {

	protected GenericDao<T, K> repository;

	@SuppressWarnings("unchecked")
	public GenericServiceImpl(@SuppressWarnings("rawtypes") GenericDao genericDao) {
		this.repository = genericDao;
	}

	public T save(T entity) {
		return repository.save(entity);
	}

	public T saveOrUpdate(T entity) {
		return repository.saveOrUpdate(entity);
	}

	public T update(T entity) {
		return repository.update(entity);
	}

	public void delete(T entity) {
		repository.delete(entity);
	}

	public Iterable<T> findAll() {
		return repository.findAll();
	}

	public T findByPrimaryKey(Class<T> c, Integer id) {
		return HibernateUtil.getSession().load(c, id);
	}

}
