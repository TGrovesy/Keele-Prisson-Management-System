package com.KPMS.UI;

import java.awt.Color;

import javax.swing.JFrame;
import javax.swing.JPanel;

public class Window extends JFrame{

	public Window() {
		setSize(1440, 900); //16:10 Aspect Ratio
		setTitle("Keele Prison Management System!");
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		
		//Do stuff to our window
		SetupMainPanel();
		
		setLocationRelativeTo(null);
		setVisible(true);
	}
	
	private JPanel mainPageControlPanel;
	
	private void SetupMainPanel() {
		mainPageControlPanel = new JPanel();
		mainPageControlPanel.setBackground(Color.BLACK);
		this.add(mainPageControlPanel);
	}
	
}
