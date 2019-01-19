package com.javafee.view.console.actions;

import java.util.Scanner;

import com.javafee.hibernate.dao.HibernateDao;
import com.javafee.hibernate.dto.eeatery.Dish;
import com.javafee.service.impl.DishServiceImpl;

public class DishAction implements IChoiceAction<Dish> {

	@Override
	public void displayDescription(String description) {
		System.out.println(description);
	}

	@Override
	public Dish create() {
		@SuppressWarnings("resource")
		Scanner scanner = new Scanner(System.in);
		Dish dish = new Dish();

		System.out.print("name: ");
		dish.setName(scanner.nextLine());
		System.out.print("price: ");
		dish.setPrice(scanner.nextBigDecimal());
		System.out.print("max invoice number: ");
		dish.setMaxInvoiceNumber(scanner.nextInt());
		System.out.print("is vegetarian [True/False]: ");
		dish.setVegetarian(scanner.nextBoolean());

		return dish;
	}

	@Override
	public Dish delete() {
		@SuppressWarnings("resource")
		Scanner scanner = new Scanner(System.in);

		System.out.print("dish id: ");
		Integer dishId = scanner.nextInt();
		DishServiceImpl dishServiceImpl = new DishServiceImpl(new HibernateDao<Dish, Integer>(Dish.class));
		return dishServiceImpl.findByPrimaryKey(Dish.class, dishId);
	}

	@Override
	public void printAll(Iterable<Dish> iterable) {
		iterable.forEach(e -> System.out.println(e));
	}

}
