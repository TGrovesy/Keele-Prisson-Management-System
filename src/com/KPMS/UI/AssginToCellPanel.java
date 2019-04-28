package com.KPMS.UI;

import java.awt.Color;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.regex.Pattern;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JPanel;

import org.json.simple.JSONObject;

import com.KPMS.entites.Cell;
import com.KPMS.main.KPMSMain;

public class AssginToCellPanel extends JPanel {
	private static final long serialVersionUID = 1L;

	public AssginToCellPanel() {
		this.setLayout(new GridLayout(0, 1));
		SetupLayout();
		this.setVisible(true);
	}

	// buttons
	private JButton backBtn, assignBtn;
	
	private JComboBox prisonerComboBox, cellComboBox;

	public void SetupLayout() {

		this.setBackground(Color.BLACK);
		this.setForeground(Color.WHITE);

		backBtn = new JButton("Back");
		backBtn.setBackground(Color.DARK_GRAY);
		backBtn.setForeground(Color.WHITE);
		assignBtn = new JButton("Assgin!");
		assignBtn.setBackground(Color.DARK_GRAY);
		assignBtn.setForeground(Color.WHITE);
		
		String[] prisonerIDs = ScanPrisonersID();
		String[] cellIDs = ScanCellID();
		
		prisonerComboBox = new JComboBox<String>(prisonerIDs);
		cellComboBox = new JComboBox<String>(cellIDs);
		
		this.add(backBtn);
		this.add(prisonerComboBox);
		this.add(cellComboBox);
		this.add(assignBtn);

		backBtn.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {

				KPMSMain.window = new Window();
			}
		});
		
		assignBtn.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				AssignToCell() ;
				
			}
			
		});
	}

	private String[] ScanPrisonersID() {
		File[] files;
		File location = new File("prisoners");
		files = location.listFiles();
		String[] prisonerIDs = new String[files.length];
		for (int i = 0; i < files.length; i++) {
			prisonerIDs[i] = files[i].getName();
		}
		return prisonerIDs;
	}
	
	private String[] ScanCellID() {
		File[] files;
		File location = new File("cells");
		files = location.listFiles();
		String[] cellIDs = new String[files.length];
		for (int i = 0; i < files.length; i++) {
			cellIDs[i] = files[i].getName();
		}
		return cellIDs;
	}
	
	@SuppressWarnings("unchecked")
	private void AssignToCell() {
		String[] prisoner =  ((String) prisonerComboBox.getSelectedItem()).split(Pattern.quote("."));
		String[] cell = ((String) cellComboBox.getSelectedItem()).split(Pattern.quote("."));
		
		Cell cellToUpdate = new Cell(Integer.parseInt(cell[0].substring(4)));
		cellToUpdate.setPrisonerIDAndOccupy(Integer.parseInt(prisoner[0].substring(8)));
		
	}
}
