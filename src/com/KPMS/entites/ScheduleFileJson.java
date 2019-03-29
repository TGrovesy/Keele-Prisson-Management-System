package com.KPMS.entites;

import static com.sun.org.apache.xalan.internal.lib.ExsltDatetime.date;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

public class ScheduleFileJson {

    long hourCounter;
    long minuteCounter;
    long k;

    public ScheduleFileJson(long fileName) {
        LoadscheduleFile(fileName);
    }

    public ScheduleFileJson(long hourCounter, long minuteCounter, long k) {
        this.hourCounter = hourCounter;
        this.minuteCounter = minuteCounter;
        this.k = k;
        CreateJSONScheduleFile();
    }

    private void CreateJSONScheduleFile() {
        JSONObject obj = new JSONObject();
        obj.put("hour", this.hourCounter);
        obj.put("minute", this.minuteCounter);
        obj.put("fNum", this.k);
        DateFormat currentDate = new SimpleDateFormat("yyyy-MM-dd");
        Date date = new Date();
        String scheduleFileName = currentDate.format(date).toString();
        try (FileWriter file = new FileWriter(scheduleFileName + "-" + k + ".json")) {
            file.write(obj.toJSONString());
            file.flush();

        } catch (IOException ex) {
            System.out.println(ex.toString());
        }

    }

    private void LoadscheduleFile(long fileName) {
        try {
            JSONParser parser = new JSONParser();
            DateFormat currentDate = new SimpleDateFormat("yyyy-MM-dd");
            Date date = new Date();
            String scheduleFileNamex = currentDate.format(date).toString();
            
            
            Object obj = parser.parse(new FileReader(scheduleFileNamex + "-"+fileName + ".json"));
            JSONObject jObj = (JSONObject) obj;

            long hourCounter = (long) jObj.get("hour");
            this.hourCounter = hourCounter;
            long minuteCounter = (long) jObj.get("minute");
            this.minuteCounter = minuteCounter;

            long k = (long) jObj.get("fNum");
            this.k = k;

        } catch (FileNotFoundException ex) {
            Logger.getLogger(scheduleFile.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ParseException ex) {
            Logger.getLogger(scheduleFile.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(scheduleFile.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public long getHour() {
        return this.hourCounter;
    }

    public long getMinute() {
        System.out.println(this.minuteCounter);
        return this.minuteCounter;
    }

    public long getFileNumber() {
        return this.k;
    }
}
