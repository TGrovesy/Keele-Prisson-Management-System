/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.KPMS.UI;


import static com.KPMS.entites.History.sendPrisonerHistory;
import com.KPMS.entites.Prisoner;
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

import java.awt.BorderLayout;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import javax.swing.Box;
import javax.swing.JComboBox;
import static javax.swing.JFrame.EXIT_ON_CLOSE;
import javax.swing.JOptionPane;
import javax.swing.border.EtchedBorder;
import javax.swing.border.TitledBorder;

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
                addInfoButtons();
		this.add(mainPageControlPanel);
	}
        
      
        private static JPanel selection, inputPanel, display, prisonerAddInfoPanel;
        private static JTextArea historyInput,historyDisplay;
        private static JLabel dateTag, dateLabel, prisonerLabel, inputLabel;
        private static JButton historySubmit;
        private static Date date;
        private static JComboBox prisonerCombo;
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
        
        private void PrisonerInfo() {
          
        prisonerAddInfoPanel = new JPanel();
         
        //Array for combobox
        String[] prisonNumber={"Select a prisoner", "Prisoner1", "Prisoner2", "Prisoner3", "Prisoner4", "Prisoner5"};
        //Date
        DateFormat currentDate = new SimpleDateFormat("yyyy/MM/dd");
        date = new Date();

        //Panel for selection
        selection=new JPanel();
        selection.setPreferredSize(new Dimension(200,300));
        selection.setBorder(new TitledBorder(new EtchedBorder(),"Select"));

        dateTag = new JLabel("Current Date:");//date tag
        selection.add(dateTag);
        dateLabel = new JLabel(currentDate.format(date));//get date in format of yyyy/mm/dd
        selection.add(dateLabel);

        selection.add(Box.createVerticalStrut(60));//add spacing


        prisonerLabel = new JLabel("Select prisoner:");
        selection.add(prisonerLabel);
        prisonerCombo = new JComboBox(prisonNumber); //combo box for prisoner selection with array of prisoner name
        prisonerFileSelection selectFile = new prisonerFileSelection();
        prisonerCombo.addActionListener(selectFile);
        prisonerCombo.setPreferredSize(new Dimension(150,20));
        selection.add(prisonerCombo, BorderLayout.PAGE_END);

        prisonerAddInfoPanel.add(selection,BorderLayout.LINE_START);


        //input panel for info
        inputPanel=new JPanel();
        inputPanel.setPreferredSize(new Dimension(900,300));
        inputPanel.setLayout(new BorderLayout());
        inputPanel.setBorder(new TitledBorder(new EtchedBorder(),"Input"));

        inputLabel = new JLabel("Enter Text: ");
        inputPanel.add(inputLabel, BorderLayout.PAGE_START);

        historyInput= new JTextArea(); //area to input history of the selected prisoner
        historyInput.setPreferredSize(new Dimension(800,100));
        inputPanel.add(historyInput, BorderLayout.CENTER);

        historySubmit = new JButton("Submit");
        historySubmit.setMaximumSize(new Dimension(100,30));
        sendHistory submitHistory = new sendHistory();
        historySubmit.addActionListener(submitHistory);
        inputPanel.add(historySubmit,BorderLayout.PAGE_END);

        prisonerAddInfoPanel.add(inputPanel,BorderLayout.CENTER);


        //Display panel

        display = new JPanel();
        display.setPreferredSize(new Dimension(900,900));
        display.setBorder(new TitledBorder(new EtchedBorder(),"display"));

        historyDisplay = new JTextArea();
        historyDisplay.setEditable(false);
        historyDisplay.setPreferredSize(new Dimension(800,800));

        display.add(historyDisplay);
        prisonerAddInfoPanel.add(display,BorderLayout.PAGE_END);

        this.add(prisonerAddInfoPanel);
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
        ///////////////////////////
        private JButton prisonerAddInfoBtn;
        
        private void addInfoButtons() {
            prisonerAddInfoBtn = new JButton("Add Prisoner Button");
            prisonerAddInfoBtn.setBackground(Color.DARK_GRAY);
            prisonerAddInfoBtn.setForeground(Color.WHITE);
            prisonerAddInfoBtn.addActionListener(new ActionListener() {

                @Override
                public void actionPerformed(ActionEvent e) {
                    //remove main panel
                    remove(mainPageControlPanel);
                    PrisonerInfo();
                    UpdateFrame();
                }
                
            });
            mainPageControlPanel.add(prisonerAddInfoBtn);
            
        }
          public static class sendHistory implements ActionListener{

        @Override
        public void actionPerformed(ActionEvent e){
            String prionserHistoryString;
            String combobSelection;
            String fileName = prisonerCombo.getSelectedItem().toString()+".txt";
            String defaultSelection ="Select a prisoner";
            String lines;


            date = new Date();
            combobSelection=prisonerCombo.getSelectedItem().toString();


            if((!historyInput.getText().equals(""))) {//check if required columns filled or not

                prionserHistoryString = date + " -- " + combobSelection + ": " + historyInput.getText() + System.lineSeparator();//input string

                //create file if file not created
                sendPrisonerHistory(prionserHistoryString,fileName);

                historyInput.setText(null);

                //get Buffered Reader
                historyDisplay.setText(null);
                try {
                    FileReader fileReader = new FileReader(fileName);
                    BufferedReader br = new BufferedReader(fileReader);

                    while((lines = br.readLine())!=null){
                        historyDisplay.append(lines + "\n");
                    }

                } catch (FileNotFoundException e1) {
                    e1.printStackTrace();
                } catch (IOException e1) {
                    e1.printStackTrace();
                }

            }
            else {
                System.out.println("Please select a valid option");


            }

        }

    }
    public static class prisonerFileSelection implements ActionListener{


        @Override
        public void actionPerformed(ActionEvent e) {
            String origianlLines;
            String fileName = prisonerCombo.getSelectedItem().toString()+".txt";
            File dir = new File(fileName);

            if(dir.exists()){
                try {

                    FileReader fileReader = new FileReader(fileName);
                    BufferedReader br = new BufferedReader(fileReader);

                    historyDisplay.setText(null);
                    while((origianlLines = br.readLine())!=null){
                        historyDisplay.append(origianlLines + "\n");
                    }

                } catch (IOException e1) {
                    e1.printStackTrace();
                }
            }
            else if(!dir.exists()){
                System.out.println("No file found");
            }


        }
    }
	private void UpdateFrame() {

		invalidate();
		validate();
		repaint();
	}

}
