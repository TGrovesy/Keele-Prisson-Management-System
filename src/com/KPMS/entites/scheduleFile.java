/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.KPMS.entites;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

/**
 *
 * @author heyco
 */
public class scheduleFile {
    public void scheduleFile(){    
    }
    public static void writeSchedule(String date,String inputfileName){
        File scheduleFile = new File(inputfileName);
        try{
            scheduleFile.createNewFile();
        }catch(IOException el){
            System.out.println(el.toString());
        }
        appendToFile(inputfileName, date);
        
        
        System.out.println(date);
        
        
        
    }

    private static void appendToFile(String fileName, String inputDate) {
        try{
            BufferedWriter out=new BufferedWriter(
                new FileWriter(fileName, true));
            out.write(inputDate);
            out.close();
        }catch(IOException e){
            System.out.println("exception occured"+e.toString());
        }
    }
}
