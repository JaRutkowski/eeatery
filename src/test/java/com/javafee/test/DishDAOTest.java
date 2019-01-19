package com.javafee.test;

import java.math.BigDecimal;

import javax.transaction.Transactional;

import org.junit.Assert;
import org.junit.Test;

import com.javafee.hibernate.dao.HibernateDao;
import com.javafee.hibernate.dto.eeatery.Dish;

public class DishDAOTest {

	private HibernateDao<Dish, Integer> dao = new HibernateDao<>(Dish.class);

	@Test
	@Transactional
	public void testAddDish() {
		Dish dish = new Dish();
		dish.setName("Test");
		dish.setPrice(new BigDecimal(10.0));
		dish.setMaxInvoiceNumber(10);
		dish.setVegetarian(true);

		dao.save(dish);

		Assert.assertEquals(dish.getName(), dao.findByPrimaryKey(dish.getIdDish()).getName());
	}

}
