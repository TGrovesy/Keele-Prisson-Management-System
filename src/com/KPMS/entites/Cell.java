package com.KPMS.entites;

import java.io.FileWriter;
import java.io.IOException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

public class Cell {
	
	private boolean occupied;
	private String securityLevel;
	private String cellNumber;
	private String block;
	
	private int capacityTotal;
	private int capactiyUsed;
	
	private int prisonerID;
	
	private int lastCheck;
	private int nextCheck;
	
	public Cell() {
		
	}
	
	private void RequestCheck() {
		//TODO heyco add the get stuff here to read your files
	}
	
	//Depeding on which constructor is used it either creates an entirely new prisoner JSON
		@SuppressWarnings("unchecked")
		private void CreateJSONCellFile() {
			JSONObject obj = new JSONObject();
			obj.put("Cell_Number", this.cellNumber);
			obj.put("Security_Level", this.securityLevel);
			obj.put("ocupied", this.occupied);
			
			obj.put("Prisoner_ID", this.prisonerID);
			obj.put("securityLevel", this.securityLevel);

			obj.put("Last_Check", this.lastCheck);
			obj.put("Next_Check", this.lastCheck);
			

			try (FileWriter file = new FileWriter("prisoners/prisoner" + this.prisonerID +".json")) {

				file.write(obj.toJSONString());
				file.flush();

			} catch (IOException e) {
				e.printStackTrace();
			}

		}

}
