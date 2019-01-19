package com.javafee.mainform.frames;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.Toolkit;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;
import javax.swing.border.TitledBorder;

import com.javafee.common.Constans;
import com.javafee.common.SystemProperties;
import com.javafee.startform.StartForm;
import com.javafee.uniform.CockpitConfirmationPanel;

import lombok.Getter;
import javax.swing.JCheckBox;

public class DishAddFrame extends JFrame {

	private static final long serialVersionUID = -31457200324840715L;

	private JPanel contentPane;
	@Getter
	private JTextField textFieldName;
	@Getter
	private JTextField textFieldPrice;
	@Getter
	private JTextField textFieldMaxInvoiceNumber;
	@Getter
	private JTextField textFieldEngine;
	@Getter
	private CockpitConfirmationPanel cockpitConfirmationPanel;
	@Getter
	private JCheckBox chckbxNewCheckBox;

	public DishAddFrame() {
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setTitle(Constans.APPLICATION_NAME);
		setIconImage(Toolkit.getDefaultToolkit().getImage(StartForm.class.getResource("/images/eseller-ico.png")));
		setBounds(100, 100, 460, 258);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		GridBagLayout gbl_contentPane = new GridBagLayout();
		gbl_contentPane.columnWidths = new int[] { 0, 0 };
		gbl_contentPane.rowHeights = new int[] { 190, -148, 0 };
		gbl_contentPane.columnWeights = new double[] { 1.0, Double.MIN_VALUE };
		gbl_contentPane.rowWeights = new double[] { 0.0, 1.0, Double.MIN_VALUE };
		contentPane.setLayout(gbl_contentPane);

		JPanel panel = new JPanel();
		panel.setBorder(new TitledBorder(null,
				SystemProperties.getInstance().getResourceBundle().getString("invoiceAddFrame.borderTitle" + ""),
				TitledBorder.LEADING, TitledBorder.TOP, null, null));
		GridBagConstraints gbc_panel = new GridBagConstraints();
		gbc_panel.insets = new Insets(0, 0, 5, 0);
		gbc_panel.fill = GridBagConstraints.BOTH;
		gbc_panel.gridx = 0;
		gbc_panel.gridy = 0;
		contentPane.add(panel, gbc_panel);
		GridBagLayout gbl_panel = new GridBagLayout();
		gbl_panel.columnWidths = new int[] { 0, 0, 0 };
		gbl_panel.rowHeights = new int[] { 0, 0, 0, 0, 0, 0 };
		gbl_panel.columnWeights = new double[] { 0.0, 1.0, Double.MIN_VALUE };
		gbl_panel.rowWeights = new double[] { 0.0, 0.0, 0.0, 0.0, 0.0, Double.MIN_VALUE };
		panel.setLayout(gbl_panel);

		JLabel lblName = new JLabel(
				SystemProperties.getInstance().getResourceBundle().getString("dishTableModel.nameCol") + ":");
		GridBagConstraints gbc_lblManufacturer = new GridBagConstraints();
		gbc_lblManufacturer.anchor = GridBagConstraints.EAST;
		gbc_lblManufacturer.insets = new Insets(5, 0, 5, 5);
		gbc_lblManufacturer.gridx = 0;
		gbc_lblManufacturer.gridy = 0;
		panel.add(lblName, gbc_lblManufacturer);

		textFieldName = new JTextField();
		GridBagConstraints gbc_textFieldManufacturer = new GridBagConstraints();
		gbc_textFieldManufacturer.fill = GridBagConstraints.HORIZONTAL;
		gbc_textFieldManufacturer.insets = new Insets(0, 0, 5, 0);
		gbc_textFieldManufacturer.gridx = 1;
		gbc_textFieldManufacturer.gridy = 0;
		panel.add(textFieldName, gbc_textFieldManufacturer);
		textFieldName.setColumns(10);

		JLabel lblPrice = new JLabel(
				SystemProperties.getInstance().getResourceBundle().getString("dishTableModel.priceCol") + ":");
		GridBagConstraints gbc_lblModel = new GridBagConstraints();
		gbc_lblModel.anchor = GridBagConstraints.EAST;
		gbc_lblModel.insets = new Insets(0, 0, 5, 5);
		gbc_lblModel.gridx = 0;
		gbc_lblModel.gridy = 1;
		panel.add(lblPrice, gbc_lblModel);

		textFieldPrice = new JTextField();
		GridBagConstraints gbc_textFieldModel = new GridBagConstraints();
		gbc_textFieldModel.fill = GridBagConstraints.HORIZONTAL;
		gbc_textFieldModel.insets = new Insets(0, 0, 5, 0);
		gbc_textFieldModel.gridx = 1;
		gbc_textFieldModel.gridy = 1;
		panel.add(textFieldPrice, gbc_textFieldModel);
		textFieldPrice.setColumns(10);

		JLabel lblMaxInvoiceNumber = new JLabel(
				SystemProperties.getInstance().getResourceBundle().getString("dishTableModel.maxInvoiceNumberCol")
						+ ":");
		GridBagConstraints gbc_lblFuelKind = new GridBagConstraints();
		gbc_lblFuelKind.anchor = GridBagConstraints.EAST;
		gbc_lblFuelKind.insets = new Insets(0, 0, 5, 5);
		gbc_lblFuelKind.gridx = 0;
		gbc_lblFuelKind.gridy = 2;
		panel.add(lblMaxInvoiceNumber, gbc_lblFuelKind);

		textFieldMaxInvoiceNumber = new JTextField();
		GridBagConstraints gbc_textFieldFuelKind = new GridBagConstraints();
		gbc_textFieldFuelKind.fill = GridBagConstraints.HORIZONTAL;
		gbc_textFieldFuelKind.insets = new Insets(0, 0, 5, 0);
		gbc_textFieldFuelKind.gridx = 1;
		gbc_textFieldFuelKind.gridy = 2;
		panel.add(textFieldMaxInvoiceNumber, gbc_textFieldFuelKind);
		textFieldMaxInvoiceNumber.setColumns(10);

		chckbxNewCheckBox = new JCheckBox(
				SystemProperties.getInstance().getResourceBundle().getString("dishTableModel.vegetarianCol"));
		GridBagConstraints gbc_chckbxNewCheckBox = new GridBagConstraints();
		gbc_chckbxNewCheckBox.insets = new Insets(0, 0, 5, 0);
		gbc_chckbxNewCheckBox.gridx = 1;
		gbc_chckbxNewCheckBox.gridy = 3;
		panel.add(chckbxNewCheckBox, gbc_chckbxNewCheckBox);

		cockpitConfirmationPanel = new CockpitConfirmationPanel();
		GridBagConstraints gbc_panel_1 = new GridBagConstraints();
		gbc_panel_1.fill = GridBagConstraints.BOTH;
		gbc_panel_1.gridx = 0;
		gbc_panel_1.gridy = 1;
		contentPane.add(cockpitConfirmationPanel, gbc_panel_1);
	}

}
