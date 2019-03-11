package com.KPMS.entites;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

public class Prisoner {

	private String prisonerName;
	private int prisonerID;
	private int sentenceLength;

	// release date
	private int releaseDay, releaseMonth, releaseYear;

	private ArrayList<String> medication = new ArrayList<>();

	private String job;

	private String securityLevel;

	public Prisoner(int prisonerFilePath) {
		
		LoadPrisonerFile(prisonerFilePath);
	}

	public Prisoner(int prisonerID, String prisonerName, int sentenceLength, int releaseDay, int releaseMonth, int releaseYear, ArrayList<String> medication, String job, String securityLevel) {
		this.prisonerID = prisonerID;
		this.prisonerName = prisonerName;
		this.sentenceLength = sentenceLength;
		this.releaseDay = releaseDay;
		this.releaseMonth = releaseMonth;
		this.releaseYear = releaseYear;
		this.medication = medication;
		this.job = job;
		this.securityLevel = securityLevel;
		CreateJSONPrisonerFile();
	}

	//Depeding on which constructor is used it either creates an entirely new prisoner JSON
	@SuppressWarnings("unchecked")
	private void CreateJSONPrisonerFile() {
		JSONObject obj = new JSONObject();
		obj.put("ID", this.prisonerID);
		obj.put("name", this.prisonerName);
		obj.put("sentenceLength", this.sentenceLength);
		DateFormat dateFormat = new SimpleDateFormat("dd-mm-yyyy");
		Date date;
		try {
			date = dateFormat.parse(this.releaseDay + "-" + this.releaseMonth + "-" + this.releaseYear);
			String dateF = dateFormat.format(date);
			obj.put("releaseDate", dateF);
		} catch (ParseException e1) {
			System.out.println("Failed To parse date");
		}
		obj.put("job", this.job);
		obj.put("securityLevel", this.securityLevel);

		JSONArray medList = new JSONArray();
		for(int i = 0; i < this.medication.size(); i++) {
			medList.add(medication.get(i));
		}

		obj.put("medication", medList);

		try (FileWriter file = new FileWriter("prisoners/prisoner" + this.prisonerID +".json")) {

			file.write(obj.toJSONString());
			file.flush();

		} catch (IOException e) {
			e.printStackTrace();
		}

	}
	
	//Depeding on which constructor is used it loads in a prissoner file based on the ID
	private void LoadPrisonerFile(int prisonerID) {
		JSONParser parser = new JSONParser();

        try {

            Object obj = parser.parse(new FileReader("prisoners/prisoner"+ prisonerID + ".json"));

            JSONObject jsonObject = (JSONObject) obj;

            String name = (String) jsonObject.get("name");
            this.prisonerName = name;
            
            String ID = (String)  String.valueOf(jsonObject.get("ID"));
            this.prisonerID = Integer.parseInt(ID);
            
            String securityLevel =  (String) jsonObject.get("securityLevel");
            this.securityLevel = securityLevel;

            String releaseDate =  (String) jsonObject.get("releaseDate");
            String[] dayMonthYear = releaseDate.split("-");
            this.releaseDay = Integer.parseInt(dayMonthYear[0]);
            this.releaseMonth = Integer.parseInt(dayMonthYear[1]);
            this.releaseYear = Integer.parseInt(dayMonthYear[2]);
            
            String job =  (String) jsonObject.get("job");
            this.job = job;
            
            String sentenceLength =  (String) String.valueOf(jsonObject.get("sentenceLength"));
            this.sentenceLength = Integer.parseInt(sentenceLength);
            
            // loop array
            JSONArray msg = (JSONArray) jsonObject.get("medication");
            Iterator<String> iterator = msg.iterator();
            while (iterator.hasNext()) {
            	this.medication.add(iterator.next());
            }

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (org.json.simple.parser.ParseException e) {
            e.printStackTrace();
        }
	}

	public String GetName() {
		return this.prisonerName;
	}
	
	public int GetSentenceLength() {
		return this.sentenceLength;
	}
	
	public String GetReleaseDate() {
		return this.releaseDay + "/" + this.releaseMonth + "/" + this.releaseYear;
	}
	
	public String GetSecurityLevel() {
		return this.securityLevel;
	}
	
	public String GetJob() {
		return this.job;
	}
	
	public ArrayList<String> GetMedication() {
		return this.medication;
	}
	
	public void DeletePrisoner() {

	}

	public void GetBehaviorRecords() {

	}
}
