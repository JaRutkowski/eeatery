package com.javafee.uniform;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Image;
import java.awt.Insets;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JPanel;

import com.javafee.common.SystemProperties;
import com.javafee.common.Utils;
import com.javafee.startform.RegistrationPanel;

import lombok.Getter;

public class CockpitEditionPanel extends JPanel {
	private static final long serialVersionUID = 1L;

	@Getter
	private JButton btnAdd;
	@Getter
	private JButton btnDelete;
	@Getter
	private JButton btnInvoice;

	public CockpitEditionPanel() {
		setBackground(Utils.getApplicationColor());
		GridBagLayout gridBagLayout = new GridBagLayout();
		gridBagLayout.columnWidths = new int[] { 0, 0, 0, 0, 0 };
		gridBagLayout.rowHeights = new int[] { 0, 0, 0 };
		gridBagLayout.columnWeights = new double[] { 1.0, 1.0, 1.0, 1.0, Double.MIN_VALUE };
		gridBagLayout.rowWeights = new double[] { 0.0, 0.0, Double.MIN_VALUE };
		setLayout(gridBagLayout);

		btnAdd = new JButton(
				SystemProperties.getInstance().getResourceBundle().getString("cockpitEditionPanel.btnAdd"));
		btnAdd.setIcon(new ImageIcon(new ImageIcon(RegistrationPanel.class.getResource("/images/btnAddToList-ico.png"))
				.getImage().getScaledInstance(18, 18, Image.SCALE_SMOOTH)));
		GridBagConstraints gbc_btnAdd = new GridBagConstraints();
		gbc_btnAdd.gridwidth = 2;
		gbc_btnAdd.anchor = GridBagConstraints.NORTH;
		gbc_btnAdd.fill = GridBagConstraints.HORIZONTAL;
		gbc_btnAdd.insets = new Insets(0, 0, 5, 5);
		gbc_btnAdd.gridx = 0;
		gbc_btnAdd.gridy = 0;
		add(btnAdd, gbc_btnAdd);

		btnDelete = new JButton(
				SystemProperties.getInstance().getResourceBundle().getString("cockpitEditionPanel.btnRemove"));
		btnDelete.setIcon(new ImageIcon(new ImageIcon(RegistrationPanel.class.getResource("/images/btnRemove-ico.png"))
				.getImage().getScaledInstance(18, 18, Image.SCALE_SMOOTH)));
		GridBagConstraints gbc_btnDelete = new GridBagConstraints();
		gbc_btnDelete.gridwidth = 2;
		gbc_btnDelete.anchor = GridBagConstraints.NORTH;
		gbc_btnDelete.fill = GridBagConstraints.HORIZONTAL;
		gbc_btnDelete.insets = new Insets(0, 0, 5, 0);
		gbc_btnDelete.gridx = 2;
		gbc_btnDelete.gridy = 0;
		add(btnDelete, gbc_btnDelete);

		btnInvoice = new JButton(
				SystemProperties.getInstance().getResourceBundle().getString("cockpitEditionPanel.btnInvoice"));
		btnInvoice.setIcon(
				new ImageIcon(new ImageIcon(RegistrationPanel.class.getResource("/images/btnSaveAsDraft-ico.png"))
						.getImage().getScaledInstance(18, 18, Image.SCALE_SMOOTH)));
		GridBagConstraints gbc_btnInvoice = new GridBagConstraints();
		gbc_btnInvoice.gridwidth = 4;
		gbc_btnInvoice.fill = GridBagConstraints.BOTH;
		gbc_btnInvoice.gridx = 0;
		gbc_btnInvoice.gridy = 1;
		add(btnInvoice, gbc_btnInvoice);
	}
}
