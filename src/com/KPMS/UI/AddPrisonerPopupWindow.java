package com.KPMS.UI;

import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextField;

import com.KPMS.entites.Prisoner;

public class AddPrisonerPopupWindow extends JFrame{

	private static final long serialVersionUID = 1L;


	public AddPrisonerPopupWindow() {
		setSize(1440, 900); // 16:10 Aspect Ratio
		setTitle("Add Prisoner!");
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setLayout(new GridLayout(0, 1));
		
		CreateLayout();

		setLocationRelativeTo(null);
		setVisible(true);
	}
	
	private JButton addBtn, cancelBtn;
	
	private JTextField prisonerIDTxt, prisonerNameTxt, sentenceLengthTxt, releaseDayTxt, releaseMonthTxt, releaseYearTxt, meidcationTxt, jobTxt, securityLevelTxt;
	
	private JLabel prisonerIDLbl, prisonerNameLbl, sentenceLengthLbl, releaseDayLbl, releaseMonthLbl, releaseYearLbl, meidcationLbl, jobLbl, securityLevelLbl;
	
	
	private void CreateLayout() {
		
		prisonerIDLbl = new JLabel("Prisoner ID:");
		prisonerIDTxt = new  JTextField();
		this.add(prisonerIDLbl);
		this.add(prisonerIDTxt);
		
		prisonerNameLbl = new JLabel("Prisoner Name:");
		prisonerNameTxt = new  JTextField();
		this.add(prisonerNameLbl);
		this.add(prisonerNameTxt);

		sentenceLengthLbl = new JLabel("Sentence Length:");
		sentenceLengthTxt = new  JTextField();
		this.add(sentenceLengthLbl);
		this.add(sentenceLengthTxt);

		releaseDayLbl = new JLabel("Release Day:");
		releaseDayTxt = new  JTextField();
		this.add(releaseDayLbl);
		this.add(releaseDayTxt);

		releaseMonthLbl = new JLabel("Release Month:");
		releaseMonthTxt = new  JTextField();
		this.add(releaseMonthLbl);
		this.add(releaseMonthTxt);
		
		releaseYearLbl = new JLabel("Release Year:");
		releaseYearTxt = new  JTextField();
		this.add(releaseYearLbl);
		this.add(releaseYearTxt);

		meidcationLbl = new JLabel("Medication:");
		meidcationTxt = new  JTextField();
		this.add(meidcationLbl);
		this.add(meidcationTxt);

		jobLbl = new JLabel("Job:");
		jobTxt = new  JTextField();
		this.add(jobLbl);
		this.add(jobTxt);

		securityLevelLbl = new JLabel("Security Level:");
		securityLevelTxt = new  JTextField();
		this.add(securityLevelLbl);
		this.add(securityLevelTxt);
		
		cancelBtn = new JButton("Cancel");
		cancelBtn.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				dispose();
				
			}
		});
		this.add(cancelBtn);
		
		addBtn = new JButton("Add");
		addBtn.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				int prisonerID = Integer.parseInt(prisonerIDTxt.getText());
				int sentenceLength = Integer.parseInt(sentenceLengthTxt.getText());
				int releaseDay = Integer.parseInt(releaseDayTxt.getText());
				int releaseMonth = Integer.parseInt(releaseMonthTxt.getText());
				int releaseYear= Integer.parseInt(releaseYearTxt.getText());
				ArrayList<String> meds = new ArrayList<>();
				String[] medSplit = meidcationTxt.getText().split(",");
				for(int i = 0; i < medSplit.length; i++) {
					meds.add(medSplit[i]);
				}
				
				new Prisoner(prisonerID, prisonerNameTxt.getText(), sentenceLength, releaseDay, releaseMonth, releaseYear, meds, jobTxt.getText(), securityLevelTxt.getText());
				dispose();
			}
		});
		this.add(addBtn);
		this.pack();
		
	}

}
