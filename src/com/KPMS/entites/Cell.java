package com.KPMS.entites;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Calendar;
import java.util.Date;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

public class Cell {

	private boolean occupied;
	private String securityLevel;
	private String cellNumber;
	private String block;

	private int capacityTotal;
	private int capactiyUsed;

	private int prisonerID;

	private String lastCheck;
	private int nextCheck;

	public Cell(int cellNumber, String block, String securityLevel, int prisonerID, int timeUntilNextCheck) {
		this.cellNumber = String.valueOf(cellNumber);
		this.block = block;
		this.securityLevel = securityLevel;
		this.prisonerID = prisonerID;
		this.nextCheck = timeUntilNextCheck;

		Calendar calendar =  Calendar.getInstance();
		this.lastCheck = String.valueOf(calendar.get(Calendar.DAY_OF_YEAR));
		CreateJSONCellFile();
	}

	public Cell(int cellNumber) {
		LoadCellFile(cellNumber);
	}

	private void RequestCheck() {
		// TODO heyco add the get stuff here to read your files
	}

	// Depeding on which constructor is used it either creates an entirely new
	// prisoner JSON
	@SuppressWarnings("unchecked")
	private void CreateJSONCellFile() {
		JSONObject obj = new JSONObject();
		obj.put("Cell_Number", this.cellNumber);
		obj.put("Security_Level", this.securityLevel);
		obj.put("ocupied", this.occupied);
		obj.put("block", this.block);
		obj.put("capacityTotal", this.capacityTotal);
		obj.put("capacityUsed", this.capactiyUsed);

		obj.put("Prisoner_ID", this.prisonerID);

		obj.put("Last_Check", this.lastCheck);
		obj.put("Time_Until_Next_Check", this.nextCheck);

		try (FileWriter file = new FileWriter("cells/cell" + this.cellNumber + ".json")) {

			file.write(obj.toJSONString());
			file.flush();

		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	// Depeding on which constructor is used it loads in a prissoner file based on
	// the ID
	private void LoadCellFile(int prisonerID) {
		JSONParser parser = new JSONParser();

		try {

			Object obj = parser.parse(new FileReader("cells/cell" + prisonerID + ".json"));

			JSONObject jsonObject = (JSONObject) obj;

			this.cellNumber = (String) jsonObject.get("Cell_Number");
			this.securityLevel = (String) jsonObject.get("Security_Level");
			this.occupied = (boolean) jsonObject.get("occupied");
			this.block = (String) jsonObject.get("block");
			this.capactiyUsed = (int) jsonObject.get("capactiyUsed");
			this.capacityTotal = (int) jsonObject.get("capacityTotal");

			this.prisonerID = (int) jsonObject.get("Prisoner_ID");
			this.lastCheck = (String) jsonObject.get("Last_Check");
			this.nextCheck = (int) jsonObject.get("Time_Until_Next_Check");

		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (org.json.simple.parser.ParseException e) {
			e.printStackTrace();
		}
	}

}
