package com.KPMS.UI;

import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;

import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JTextArea;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import com.KPMS.main.KPMSMain;

public class CellInspectPanel extends JPanel {

	File[] files;

	public CellInspectPanel() {
		GUIStuff();
		files = findAllFiles(new File("cells"));
		checkNeedInspect();
	}

	// GUI Stuff
	JTextArea cellsTxt;

	private void GUIStuff() {
		this.setBackground(Color.BLACK);
		this.setForeground(Color.WHITE);
		this.setLayout(new FlowLayout());

		cellsTxt = new JTextArea();
		cellsTxt.setEditable(false);
		cellsTxt.setFont(cellsTxt.getFont().deriveFont(24f));
		this.add(cellsTxt);
		JButton backBtn = new JButton("Back");
		backBtn.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				KPMSMain.window = new Window();
				
			}
			
		});
		this.add(backBtn);
	}

	private File[] findAllFiles(final File folder) {
		File[] files = folder.listFiles();
		return files;
	}

	private void checkNeedInspect() {
		ArrayList<File> cellThatNeedInspect = new ArrayList<>();

		JSONParser parser = new JSONParser();
		Object obj;

		JSONObject jsonObject;
		for (int i = 0; i < files.length; i++) {
			try {
				obj = parser.parse(new FileReader("cells/" + this.files[i].getName()));
				jsonObject = (JSONObject) obj;
				int lastCheck = Integer.parseInt(String.valueOf(jsonObject.get("Last_Check")));
				int nextCheck = Integer.parseInt(String.valueOf(jsonObject.get("Time_Until_Next_Check")));
				Calendar cal = Calendar.getInstance();
				int daysBetween = cal.get(Calendar.DAY_OF_YEAR) - lastCheck;
				System.out.println(daysBetween + " " + nextCheck);
				if (daysBetween == nextCheck) {
					cellThatNeedInspect.add(files[i]);
				}
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			} catch (ParseException e) {
				e.printStackTrace();
			}
		}
		String cellsMsg = "";
		for (int i = 0; i < cellThatNeedInspect.size(); i++) {
			cellsMsg += cellThatNeedInspect.get(i).getName();
		}
		if (!(cellsMsg.length() < 1)) {
			cellsTxt.setText(cellsMsg);
		}else {
			cellsTxt.setText("No Cells Need Inspecting Today!");
		}
	}
}
