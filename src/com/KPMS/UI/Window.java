package com.KPMS.UI;

import static com.KPMS.entites.scheduleFile.writeSchedule;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.regex.Pattern;

import javax.swing.Box;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.border.EtchedBorder;
import javax.swing.border.TitledBorder;

import com.KPMS.entites.Prisoner;
import com.KPMS.entites.ScheduleFileJson;
import com.KPMS.entites.VisitCounter;
import com.KPMS.main.*;

import static com.KPMS.entites.History.sendPrisonerHistory;

public class Window extends JFrame {

	private static final long serialVersionUID = 1L;

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

	private static JPanel selection, inputPanel, display, prisonerAddInfoPanel, schedulePage;
	private static JTextArea historyInput, historyDisplay;
	private static JLabel dateTag, dateLabel, prisonerLabel, inputLabel;
	private static JButton historySubmit;
	private static Date date;
	private JPanel prisonerInfoPanel;
	private JPanel prisonerPanelTop, prisonerPanelBottom;
	private JTextField prisonerScheduleSelector;
	private JComboBox prisonerInputID;
	private JLabel prisonerIDLbl, timeSep;
	private JButton prisonerGetBtn, getVisitTime, addVisitTime;
	private Prisoner prisoner;
	private JTextArea prisonerInfoTextArea, prisonerScheduleDisplay;
	private ScheduleFileJson scheduleJson;
	private VisitCounter vcounter;
	private JComboBox prisonerCombo, hourCombo, minuteCombo;
	boolean reset = false;
	int hourN;
	int minuteN;
	int fileNum = 1;

	
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
	
	// Gui Stuff for adding prisoner
	private JButton addPrisonerBtn, scheduleButton, backBtn;

	private void SetupGetPrisonerInfoPanel() {
		prisonerInfoPanel = new JPanel();
		prisonerInfoPanel.setBackground(Color.BLACK);
		prisonerInfoPanel.setForeground(Color.WHITE);
		prisonerInfoPanel.setLayout(new FlowLayout());

		// top prisoner panel
		prisonerPanelTop = new JPanel();
		prisonerPanelTop.setBackground(Color.BLACK);
		prisonerPanelTop.setForeground(Color.WHITE);
		prisonerPanelTop.setPreferredSize(new Dimension(getWidth() - 20, 100));
		prisonerPanelTop.setLayout(new GridLayout(3, 3));

		prisonerInputID = new JComboBox(ScanPrisonersID());
		prisonerIDLbl = new JLabel("Prisoner ID: ");
		prisonerGetBtn = new JButton("Get");
		addPrisonerBtn = new JButton("Add");

		backBtn = new JButton("Back");
		backBtn.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				KPMSMain.window = new Window();

			}
		});

		prisonerInputID.setSize(100, 80);
		prisonerIDLbl.setForeground(Color.WHITE);
		prisonerGetBtn.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				try {
					int ID =Integer.parseInt(((String)prisonerInputID.getSelectedItem()).split(Pattern.quote("."))[0].substring(8));
					prisoner = new Prisoner(ID);
					DisplayPrisonerInfo();
				} catch (NumberFormatException exception) {
					System.out.println("Not a number");
				}
			}
		});
		prisonerPanelTop.add(prisonerIDLbl);
		prisonerPanelTop.add(prisonerInputID);
		prisonerPanelTop.add(prisonerGetBtn);

		// bottom prisoner panel
		prisonerPanelBottom = new JPanel();
		prisonerPanelBottom.setBackground(Color.BLACK);
		prisonerPanelBottom.setPreferredSize(new Dimension(getWidth() - 10, (int) (getHeight() * .8)));

		prisonerInfoTextArea = new JTextArea();
		prisonerInfoTextArea.setForeground(Color.BLACK);
		prisonerInfoTextArea.setEditable(false);

		prisonerPanelBottom.add(prisonerInfoTextArea);

		prisonerPanelBottom.add(addPrisonerBtn);

		prisonerPanelBottom.add(backBtn);
		addPrisonerBtn.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				new AddPrisonerPopupWindow();
			}
		});
		prisonerInfoPanel.add(prisonerPanelTop);

		prisonerInfoPanel.add(prisonerPanelBottom);

		this.add(prisonerInfoPanel);

	}

	private void PrisonerInfo() {

		prisonerAddInfoPanel = new JPanel();

		// Array for combobox
		String[] prisonNumber = { "Select a prisoner", "Prisoner1", "Prisoner2", "Prisoner3", "Prisoner4", "Prisoner5" };
		// Date
		DateFormat currentDate = new SimpleDateFormat("yyyy/MM/dd");
		date = new Date();

		// Panel for selection
		selection = new JPanel();
		selection.setPreferredSize(new Dimension(200, 300));
		selection.setBorder(new TitledBorder(new EtchedBorder(), "Select"));

		dateTag = new JLabel("Current Date:");// date tag
		selection.add(dateTag);
		dateLabel = new JLabel(currentDate.format(date));// get date in format of yyyy/mm/dd
		selection.add(dateLabel);

		selection.add(Box.createVerticalStrut(60));// add spacing

		prisonerLabel = new JLabel("Select prisoner:");
		selection.add(prisonerLabel);
		prisonerCombo = new JComboBox(prisonNumber); // combo box for prisoner selection with array of prisoner name
		prisonerFileSelection selectFile = new prisonerFileSelection();
		prisonerCombo.addActionListener(selectFile);
		prisonerCombo.setPreferredSize(new Dimension(150, 20));
		selection.add(prisonerCombo, BorderLayout.PAGE_END);

		prisonerAddInfoPanel.add(selection, BorderLayout.LINE_START);

		// input panel for info
		inputPanel = new JPanel();
		inputPanel.setPreferredSize(new Dimension(900, 300));
		inputPanel.setLayout(new BorderLayout());
		inputPanel.setBorder(new TitledBorder(new EtchedBorder(), "Input"));

		inputLabel = new JLabel("Enter Text: ");
		inputPanel.add(inputLabel, BorderLayout.PAGE_START);

		historyInput = new JTextArea(); // area to input history of the selected prisoner
		historyInput.setPreferredSize(new Dimension(800, 100));
		inputPanel.add(historyInput, BorderLayout.CENTER);

		historySubmit = new JButton("Submit");
		historySubmit.setMaximumSize(new Dimension(100, 30));
		sendHistory submitHistory = new sendHistory();
		historySubmit.addActionListener(submitHistory);
		inputPanel.add(historySubmit, BorderLayout.PAGE_END);

		prisonerAddInfoPanel.add(inputPanel, BorderLayout.CENTER);

		// button to scheduel
		scheduleButton = new JButton("C");
		scheduleButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// remove main panel
				remove(prisonerAddInfoPanel);
				scheduleSetUp();
				UpdateFrame();
			}
		});

		prisonerAddInfoPanel.add(scheduleButton);

		// Display panel
		display = new JPanel();
		display.setPreferredSize(new Dimension(900, 900));
		display.setBorder(new TitledBorder(new EtchedBorder(), "display"));

		historyDisplay = new JTextArea();
		historyDisplay.setEditable(false);
		historyDisplay.setPreferredSize(new Dimension(800, 800));

		display.add(historyDisplay);
		prisonerAddInfoPanel.add(display, BorderLayout.PAGE_END);

		this.add(prisonerAddInfoPanel);
	}

	

	// Buttons To Navigate
	private JButton prisonerInfoPageBtn;
	private JButton assginCellBtn;

	
	private void SetupAssginToCellPanel() {
		
	}

	///////////////////////////
	private JButton prisonerAddInfoBtn;

	private void addInfoButtons() {
		prisonerAddInfoBtn = new JButton("Add and view Prisoner/Cell History");
		prisonerAddInfoBtn.setBackground(Color.DARK_GRAY);
		prisonerAddInfoBtn.setForeground(Color.WHITE);
		prisonerAddInfoBtn.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// remove main panel
				remove(mainPageControlPanel);
				PrisonerInfo();
				UpdateFrame();
			}

		});
		mainPageControlPanel.add(prisonerAddInfoBtn);

	}

	public class sendHistory implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			String prionserHistoryString;
			String combobSelection;
			String fileName = prisonerCombo.getSelectedItem().toString() + ".txt";
			String defaultSelection = "Select a prisoner";
			String lines;

			date = new Date();
			combobSelection = prisonerCombo.getSelectedItem().toString();

			if ((!historyInput.getText().equals(""))) {// check if required columns filled or not
				prionserHistoryString = date + " -- " + combobSelection + ": " + historyInput.getText() + System.lineSeparator();// input string
				// create file if file not created
				sendPrisonerHistory(prionserHistoryString, fileName);

				System.out.println(prionserHistoryString);

				historyInput.setText(null);

				// get Buffered Reader
				historyDisplay.setText(null);
				try {
					FileReader fileReader = new FileReader(fileName);
					BufferedReader br = new BufferedReader(fileReader);

					while ((lines = br.readLine()) != null) {
						historyDisplay.append(lines + "\n");
					}

				} catch (FileNotFoundException e1) {
					e1.printStackTrace();
				} catch (IOException e1) {
					e1.printStackTrace();
				}

			} else {
				System.out.println("Please select a valid option");

			}

		}
	}

	private void SetupMainPanel() {
		mainPageControlPanel = new JPanel();
		mainPageControlPanel.setBackground(Color.BLACK);
		mainPageControlPanel.setLayout(new GridLayout(3, 3));
		SetupButtons();
		add(mainPageControlPanel);
	}

	// Displays prisoner Info for the prisoner info panel!
	private void DisplayPrisonerInfo() {
		String prisonerInfo = "";
		prisonerInfo += "Name: " + prisoner.GetName() + "\n";
		prisonerInfo += "Sentence Length: " + prisoner.GetSentenceLength() + "\n";
		prisonerInfo += "Release Date: " + prisoner.GetReleaseDate() + "\n";
		prisonerInfo += "Job: " + prisoner.GetJob() + "\n";
		prisonerInfo += "Security Level: " + prisoner.GetSecurityLevel() + "\n";
		prisonerInfo += "Medication: ";

		for (int i = 0; i < prisoner.GetMedication().size(); i++) {
			prisonerInfo += prisoner.GetMedication().get(i) + ", ";
		}

		prisonerInfoTextArea.setText(prisonerInfo);
	}

	// Buttons To Navigate
	private JButton cellsThatNeedInspectBtn;

	private void SetupButtons() {
		prisonerInfoPageBtn = new JButton("Get Or Add Prisoner");
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

		assginCellBtn = new JButton("Assgin To Cell");
		assginCellBtn.setBackground(Color.DARK_GRAY);
		assginCellBtn.setForeground(Color.WHITE);
		assginCellBtn.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// remove main panel

				remove(mainPageControlPanel);
				add(new AssginToCellPanel());
				UpdateFrame();
			}
		});

		cellsThatNeedInspectBtn = new JButton("View Cells That Need Inspecting");
		cellsThatNeedInspectBtn.setBackground(Color.DARK_GRAY);
		cellsThatNeedInspectBtn.setForeground(Color.WHITE);
		cellsThatNeedInspectBtn.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// remove main panel
				remove(mainPageControlPanel);
				add(new CellInspectPanel());
				UpdateFrame();
			}
		});

		addInfoButtons();
		mainPageControlPanel.add(assginCellBtn);
		mainPageControlPanel.add(prisonerInfoPageBtn);
		mainPageControlPanel.add(cellsThatNeedInspectBtn);
	}

	int minutecounter = 0;
	int hourcounter = 1;
	String scheduleString, hourval, minuteval;
	String scheduleFileName;

	public void scheduleSetUp() {
		schedulePage = new JPanel();
		// Create file for schedule visit
		DateFormat currentDate = new SimpleDateFormat("yyyy-MM-dd");
		date = new Date();
		String scheduleFileName = currentDate.format(date).toString() + ".txt";

		// Assume visit time is from 9:00am to 11:00am and 3:00am to 5:00am, each slot
		// is 20 minutes long.
		getVisitTime = new JButton("get visit time");
		prisonerScheduleSelector = new JTextField();
		prisonerScheduleSelector.setPreferredSize(new Dimension(200, 20));
		schedulePage.add(prisonerScheduleSelector);

		getVisitTime.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {

				if (minutecounter < 3) {
					minutecounter += 1;
				} else if (minutecounter == 3) {
					minutecounter = 1;
					reset = true;

				}
				if (reset == true) {

					hourcounter += 1;
					reset = false;
				} else if (hourcounter == 4) {
					minutecounter = 5;
					hourcounter = 5;
				}

				vcounter = new VisitCounter();
				hourval = vcounter.hourCounter(hourcounter);
				minuteval = vcounter.minuteCounter(minutecounter);
				System.out.println(hourval + minuteval);

				scheduleString = "Prisoner Number: " + prisonerScheduleSelector.getText() + ", Visit Time Schedule at: " + hourval + ":" + minuteval;

				writeSchedule(scheduleString,scheduleFileName);

			}
		});

		schedulePage.add(getVisitTime);
		add(schedulePage);

	}

	private void UpdateFrame() {

		invalidate();
		validate();
		repaint();
	}

	public class prisonerFileSelection implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			String origianlLines;
			String fileName = prisonerCombo.getSelectedItem().toString() + ".txt";
			File dir = new File(fileName);

			if (dir.exists()) {
				try {

					FileReader fileReader = new FileReader(fileName);
					BufferedReader br = new BufferedReader(fileReader);

					historyDisplay.setText(null);
					while ((origianlLines = br.readLine()) != null) {
						historyDisplay.append(origianlLines + "\n");
					}

				} catch (IOException e1) {
					e1.printStackTrace();
				}
			} else if (!dir.exists()) {
				System.out.println("No file found");
			}

		}

		long minutecounter = 0;
		long hourcounter = 1;
		String scheduleString, hourval, minuteval;
		String scheduleFileName;

		public void scheduleSetUp() {
			schedulePage = new JPanel();
			// Create file for schedule visit
			DateFormat currentDate = new SimpleDateFormat("yyyy-MM-dd");
			date = new Date();
			String scheduleFileName = currentDate.format(date).toString() + ".txt";
			String scheduleFileNamex = currentDate.format(date).toString();

			// Assume visit time is from 9:00am to 11:00am and 3:00am to 5:00am, each slot
			// is 20 minutes long.
			addVisitTime = new JButton("add visit time");
			getVisitTime = new JButton("get visit time");
			prisonerScheduleSelector = new JTextField();
			prisonerScheduleSelector.setPreferredSize(new Dimension(200, 20));
			schedulePage.add(prisonerScheduleSelector);
			Integer[] hourSelection = { null, 1, 2, 3, 4, 5 };
			Integer[] minuteSelection = { null, 00, 20, 40 };
			hourCombo = new JComboBox(hourSelection);
			minuteCombo = new JComboBox(minuteSelection);
			timeSep = new JLabel(":");
			prisonerScheduleDisplay = new JTextArea();
			prisonerScheduleDisplay.setPreferredSize(new Dimension(getWidth() - 100, (int) (getHeight() * .5)));
			prisonerScheduleDisplay.setEditable(false);

			addVisitTime.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {

					long scheduleID = Long.parseLong(prisonerScheduleSelector.getText());
					long hourSelected = hourCombo.getSelectedIndex();
					long minuteSelected = minuteCombo.getSelectedIndex();
					new ScheduleFileJson(hourSelected, minuteSelected, scheduleID);
				}

			});

			getVisitTime.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					long scheduleID = Long.parseLong(prisonerScheduleSelector.getText());
					scheduleJson = new ScheduleFileJson(scheduleID);
					long outHour = scheduleJson.getHour();
					long outMinute = scheduleJson.getMinute();

					vcounter = new VisitCounter();
					hourval = vcounter.hourCounter((int) outHour);
					minuteval = vcounter.minuteCounter((int) outMinute);
					System.out.println(hourval + ":" + minuteval);
				}
			});

			schedulePage.add(hourCombo);
			schedulePage.add(timeSep);
			schedulePage.add(minuteCombo);
			schedulePage.add(getVisitTime);
			schedulePage.add(addVisitTime);
			schedulePage.add(prisonerScheduleDisplay);
			add(schedulePage);

		}
	}
}