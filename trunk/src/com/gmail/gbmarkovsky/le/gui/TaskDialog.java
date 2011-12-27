package com.gmail.gbmarkovsky.le.gui;

import java.awt.Component;
import java.awt.Frame;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.DefaultListCellRenderer;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

import com.gmail.gbmarkovsky.le.elements.GateType;

public class TaskDialog extends JDialog {
	private static final long serialVersionUID = 7680229364334703049L;
	private JTextArea taskTerms;

	public TaskDialog(Frame owner) {
		super(owner, "Условие задачи", true);
		initComponents();
		
		pack();
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setLocationRelativeTo(getOwner());
	}

	private void initComponents() {
		setLayout(new GridBagLayout());
		add(createAvailableGatesPanel(), new GridBagConstraints(0, 0, 1, 1, 1.0, 0.0,
				GridBagConstraints.CENTER, GridBagConstraints.HORIZONTAL,
				new Insets(0, 0, 0, 0), 0, 0));
		
		add(createTermsPanel(), new GridBagConstraints(0, 1, 1, 1, 1.0, 1.0,
				GridBagConstraints.CENTER, GridBagConstraints.BOTH,
				new Insets(0, 0, 0, 0), 0, 0));
		
	}

	private JPanel createTermsPanel() {
		JPanel panel = new JPanel(new GridBagLayout());
		panel.setBorder(BorderFactory.createTitledBorder(
				BorderFactory.createEtchedBorder(), "Текст задачи"));
		
		taskTerms = new JTextArea(8, 40);
		panel.add(new JScrollPane(taskTerms), new GridBagConstraints(0, 0, 1, 1, 1.0, 1.0,
				GridBagConstraints.CENTER, GridBagConstraints.BOTH,
				new Insets(0, 0, 0, 0), 0, 0));
		
		return panel;
	}
	
	public JPanel createAvailableGatesPanel() {
		JPanel panel = new JPanel(new GridBagLayout());
		panel.setBorder(BorderFactory.createTitledBorder(
				BorderFactory.createEtchedBorder(), "Доступные элементы"));
		DefaultListModel allModel = new DefaultListModel();
		for (int i = 0; i < GateType.values().length; i++) {
			allModel.addElement(GateType.values()[i]);
		}
		final JList allGateTypes = new JList(allModel);
		allGateTypes.setCellRenderer(new GateListRenderer());
		panel.add(new JScrollPane(allGateTypes), new GridBagConstraints(0, 0, 1, 1, 1.0, 1.0,
				GridBagConstraints.CENTER, GridBagConstraints.BOTH,
				new Insets(0, 0, 0, 0), 0, 0));
		
		JPanel buttons = new JPanel();
		
		JButton move = new JButton(">");
		buttons.add(move);
		
		
		panel.add(buttons, new GridBagConstraints(1, 0, 1, 1, 0.0, 1.0,
				GridBagConstraints.CENTER, GridBagConstraints.BOTH,
				new Insets(0, 0, 0, 0), 0, 0));
		
		final JList selectedGateTypes = new JList(new DefaultListModel());
		selectedGateTypes.setCellRenderer(new GateListRenderer());
		panel.add(new JScrollPane(selectedGateTypes), new GridBagConstraints(2, 0, 1, 1, 1.0, 1.0,
				GridBagConstraints.CENTER, GridBagConstraints.BOTH,
				new Insets(0, 0, 0, 0), 0, 0));
		
		move.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				Object selectedValue = allGateTypes.getSelectedValue();
				DefaultListModel defaultListModel = (DefaultListModel) selectedGateTypes.getModel();
				defaultListModel.addElement(selectedValue);
				DefaultListModel allListModel = (DefaultListModel)allGateTypes.getModel();
				allListModel.removeElement(selectedValue);
			}
		});
		
		return panel; 
	}
	
	class GateListRenderer extends DefaultListCellRenderer {
		private static final long serialVersionUID = 1414169066445912158L;

		@Override
		public Component getListCellRendererComponent(JList list, Object value,
				int index, boolean isSelected, boolean cellHasFocus) {
			super.getListCellRendererComponent(list, value, index, isSelected,
					cellHasFocus);
				
				GateType gateType = (GateType) value;
				setText(gateType.getName());
			return this;
		}
		
	}
	
	public static void main(String[] args) {
		new TaskDialog(null).setVisible(true);
	}
}
