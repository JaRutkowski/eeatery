package com.javafee.mainform;

import java.math.BigDecimal;

import javax.swing.JOptionPane;

import com.javafee.common.SystemProperties;
import com.javafee.common.Utils;
import com.javafee.exception.LogGuiException;
import com.javafee.hibernate.dao.HibernateUtil;
import com.javafee.hibernate.dto.eeatery.Dish;
import com.javafee.mainform.frames.DishAddFrame;
import com.javafee.model.DishTableModel;

public class DishAddEvent {

	private DishAddFrame dishAddFrame;
	private DishTableModel dishTableModel;

	public void control(DishTableModel dishTableModel) {
		this.dishTableModel = dishTableModel;
		openDishAddFrame();

		dishAddFrame.getCockpitConfirmationPanel().getBtnAccept().addActionListener(e -> onClickBtnAccept());
	}

	private void openDishAddFrame() {
		if (dishAddFrame == null || (dishAddFrame != null && !dishAddFrame.isDisplayable())) {
			dishAddFrame = new DishAddFrame();
			dishAddFrame.setVisible(true);
		} else {
			dishAddFrame.toFront();
		}
	}

	private void onClickBtnAccept() {
		try {
			Dish dish = new Dish();
			dish.setName(dishAddFrame.getTextFieldName().getText());
			dish.setPrice(new BigDecimal(dishAddFrame.getTextFieldPrice().getText()));
			dish.setMaxInvoiceNumber(new Integer(dishAddFrame.getTextFieldMaxInvoiceNumber().getText()));
			dish.setVegetarian(dishAddFrame.getChckbxNewCheckBox().isSelected());

			HibernateUtil.beginTransaction();
			HibernateUtil.getSession().save(dish);
			HibernateUtil.commitTransaction();

			dishTableModel.add(dish);

			Utils.displayOptionPane(
					SystemProperties.getInstance().getResourceBundle().getString("dishAddEvent.dishAdditionSuccess"),
					SystemProperties.getInstance().getResourceBundle()
							.getString("dishAddEvent.dishAdditionSuccessTitle"),
					JOptionPane.INFORMATION_MESSAGE);

			dishTableModel.reloadData();
			dishAddFrame.dispose();
		} catch (NumberFormatException e) {
			LogGuiException.logError(
					SystemProperties.getInstance().getResourceBundle().getString("carAddEvent.dataParseErrorTitle"),
					SystemProperties.getInstance().getResourceBundle().getString("carAddEvent.dataParseError"), e);
		}
	}

}
