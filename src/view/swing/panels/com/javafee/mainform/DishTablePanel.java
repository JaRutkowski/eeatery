package com.javafee.mainform;

import javax.swing.ListSelectionModel;

import com.javafee.model.DishTableModel;

public class DishTablePanel extends TablePanel {

	private static final long serialVersionUID = -6644685896316330046L;

	public DishTablePanel() {
		super(new DishTableModel());
		getTable().setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
	}

}
