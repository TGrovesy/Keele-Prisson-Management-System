package com.KPMS.UI;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextField;

import com.KPMS.entites.Prisoner;

public class Window extends JFrame {

	public Window() {
		setSize(1440, 900); // 16:10 Aspect Ratio
		setTitle("Keele Prison Management System!");
		setDefaultCloseOperation(EXIT_ON_CLOSE);

		// Do stuff to our window
		SetupMainPanel();

		setLocationRelativeTo(null);
		setVisible(true);
	}

	private JPanel mainPageControlPanel;

	private void SetupMainPanel() {
		mainPageControlPanel = new JPanel();
		mainPageControlPanel.setBackground(Color.BLACK);
		mainPageControlPanel.setLayout(new GridLayout(3, 3));
		SetupButtons();
		this.add(mainPageControlPanel);
	}

	private JPanel prisonerInfoPanel;
	private JPanel prisonerPanelTop, prisonerPanelBottom;
	private JTextField prisonerInputID;
	private JLabel prisonerIDLbl;
	private JButton prisonerGetBtn;
	private Prisoner prisoner;
	private JTextArea prisonerInfoTextArea;

	private void SetupGetPrisonerInfoPanel() {
		prisonerInfoPanel = new JPanel();
		prisonerInfoPanel.setBackground(Color.BLACK);
		prisonerInfoPanel.setForeground(Color.WHITE);
		prisonerInfoPanel.setLayout(new FlowLayout());

		//top prisoner panel
		prisonerPanelTop = new JPanel();
		prisonerPanelTop.setBackground(Color.BLACK);
		prisonerPanelTop.setForeground(Color.WHITE);
		prisonerPanelTop.setPreferredSize(new Dimension(getWidth() -20, 100));
		prisonerPanelTop.setLayout(new GridLayout(3, 3));
		
		

		prisonerInputID = new JTextField();
		prisonerIDLbl = new JLabel("Prisoner ID: ");
		prisonerGetBtn = new JButton("Get");
		
		prisonerInputID.setSize(100, 80);
		prisonerIDLbl.setForeground(Color.WHITE);
		prisonerGetBtn.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				try {
					int ID = Integer.parseInt(prisonerInputID.getText());
					prisoner = new Prisoner(ID);
					DisplayPrisonerInfo();
				}catch(NumberFormatException exception) {
					System.out.println("Not a number");
				}
			}
		});
		
		prisonerPanelTop.add(prisonerIDLbl);
		prisonerPanelTop.add(prisonerInputID);
		prisonerPanelTop.add(prisonerGetBtn);

		//bottom prisoner panel
		prisonerPanelBottom = new JPanel();
		prisonerPanelBottom.setBackground(Color.BLACK);
		prisonerPanelBottom.setPreferredSize(new Dimension(getWidth() -10, (int) (getHeight()*.8)));
		
		prisonerInfoTextArea = new JTextArea();
		prisonerInfoTextArea.setForeground(Color.BLACK);
		prisonerInfoTextArea.setEditable(false);

		prisonerPanelBottom.add(prisonerInfoTextArea);
		
		prisonerInfoPanel.add(prisonerPanelTop);
		prisonerInfoPanel.add(prisonerPanelBottom);

		this.add(prisonerInfoPanel);

	}
	
	//Displays prisoner Info for the prisoner info panel!
	private void DisplayPrisonerInfo() {
		String prisonerInfo = "";
		prisonerInfo += "Name: " + prisoner.GetName() + "\n";
		prisonerInfo += "Sentence Length: " + prisoner.GetSentenceLength() + "\n";
		prisonerInfo += "Release Date: " + prisoner.GetReleaseDate() + "\n";
		prisonerInfo += "Job: " + prisoner.GetJob() + "\n";
		prisonerInfo += "Security Level: " + prisoner.GetSecurityLevel() + "\n";
		prisonerInfo += "Medication: ";
		
		for(int i = 0; i < prisoner.GetMedication().size(); i++) {
			prisonerInfo += prisoner.GetMedication().get(i) + ", ";
		}
		
		prisonerInfoTextArea.setText(prisonerInfo);
	}

	// Buttons To Navigate
	private JButton prisonerInfoPageBtn;

	private void SetupButtons() {
		prisonerInfoPageBtn = new JButton("Prisoner Info");
		prisonerInfoPageBtn.setBackground(Color.DARK_GRAY);
		prisonerInfoPageBtn.setForeground(Color.WHITE);
		prisonerInfoPageBtn.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// remove main panel
				remove(mainPageControlPanel);
				SetupGetPrisonerInfoPanel();
				UpdateFrame();
			}
		});
		mainPageControlPanel.add(prisonerInfoPageBtn);
	}

	private void UpdateFrame() {

		invalidate();
		validate();
		repaint();
	}

}
