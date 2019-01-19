package com.javafee.mainform;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Image;
import java.awt.Insets;
import java.awt.Toolkit;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import com.javafee.common.Constans;
import com.javafee.common.SystemProperties;
import com.javafee.startform.RegistrationPanel;
import com.javafee.startform.StartForm;
import com.javafee.uniform.CockpitEditionPanel;

import lombok.Getter;

public class MainForm extends JFrame {

	private static final long serialVersionUID = -3608994423760373741L;

	private JPanel contentPane;

	@Getter
	private CockpitEditionPanel actionPanel;
	@Getter
	private DishTablePanel dishTablePanel;
	@Getter
	private JLabel lblLogInInformation;
	@Getter
	private JButton btnLogOut;
	@Getter
	private JButton btnManageWorkers;

	public MainForm() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setTitle(Constans.APPLICATION_NAME);
		setIconImage(Toolkit.getDefaultToolkit().getImage(StartForm.class.getResource("/images/eseller-ico.png")));
		setBounds(100, 100, 1196, 724);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		GridBagLayout gbl_contentPane = new GridBagLayout();
		gbl_contentPane.columnWidths = new int[] { 0, 0, 0 };
		gbl_contentPane.rowHeights = new int[] { 0, 0, 510, 0, 0, 0 };
		gbl_contentPane.columnWeights = new double[] { 1.0, 0.0, Double.MIN_VALUE };
		gbl_contentPane.rowWeights = new double[] { 0.0, 0.0, 0.0, 1.0, 0.0, Double.MIN_VALUE };
		contentPane.setLayout(gbl_contentPane);

		lblLogInInformation = new JLabel(
				SystemProperties.getInstance().getResourceBundle().getString("tabbedForm.lblLogInInformation"));
		GridBagConstraints gbc_lblNewLabel = new GridBagConstraints();
		gbc_lblNewLabel.anchor = GridBagConstraints.WEST;
		gbc_lblNewLabel.insets = new Insets(0, 0, 5, 5);
		gbc_lblNewLabel.gridx = 0;
		gbc_lblNewLabel.gridy = 0;
		contentPane.add(lblLogInInformation, gbc_lblNewLabel);

		btnLogOut = new JButton(SystemProperties.getInstance().getResourceBundle().getString("tabbedForm.btnLogOut"));
		btnLogOut.setIcon(new ImageIcon(new ImageIcon(RegistrationPanel.class.getResource("/images/btnLogOut-ico.png"))
				.getImage().getScaledInstance(18, 18, Image.SCALE_SMOOTH)));
		GridBagConstraints gbc_btnNewButton = new GridBagConstraints();
		gbc_btnNewButton.insets = new Insets(0, 0, 5, 0);
		gbc_btnNewButton.gridx = 1;
		gbc_btnNewButton.gridy = 0;
		contentPane.add(btnLogOut, gbc_btnNewButton);

		dishTablePanel = new DishTablePanel();
		GridBagConstraints gbc_panel = new GridBagConstraints();
		gbc_panel.gridwidth = 2;
		gbc_panel.insets = new Insets(0, 0, 5, 0);
		gbc_panel.gridheight = 2;
		gbc_panel.fill = GridBagConstraints.BOTH;
		gbc_panel.gridx = 0;
		gbc_panel.gridy = 1;
		contentPane.add(dishTablePanel, gbc_panel);

		actionPanel = new CockpitEditionPanel();
		GridBagConstraints gbc_actionPanel = new GridBagConstraints();
		gbc_actionPanel.insets = new Insets(0, 0, 5, 0);
		gbc_actionPanel.gridwidth = 2;
		gbc_actionPanel.fill = GridBagConstraints.BOTH;
		gbc_actionPanel.gridx = 0;
		gbc_actionPanel.gridy = 3;
		contentPane.add(actionPanel, gbc_actionPanel);

		btnManageWorkers = new JButton(
				SystemProperties.getInstance().getResourceBundle().getString("cockpitEditionPanel.btnRegistration"));
		btnManageWorkers
				.setIcon(new ImageIcon(new ImageIcon(RegistrationPanel.class.getResource("/images/btnModify-ico.png"))
						.getImage().getScaledInstance(18, 18, Image.SCALE_SMOOTH)));
		GridBagConstraints gbc_btnManageWorkers = new GridBagConstraints();
		gbc_btnManageWorkers.gridwidth = 2;
		gbc_btnManageWorkers.fill = GridBagConstraints.BOTH;
		gbc_btnManageWorkers.insets = new Insets(0, 0, 0, 5);
		gbc_btnManageWorkers.gridx = 0;
		gbc_btnManageWorkers.gridy = 4;
		contentPane.add(btnManageWorkers, gbc_btnManageWorkers);
	}

}
