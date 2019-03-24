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
public class History {
    public void History(){
    }
    public static void sendPrisonerHistory(String prisonerSelectionString, String prisonerFile){
        File prisonerfile = new File(prisonerFile);
        try {
            prisonerfile.createNewFile();
        } catch (IOException e1) {
            System.out.println(e1.toString());
        }
        appendToFile(prisonerFile, prisonerSelectionString); //add string into file
        
        
        System.out.println(prisonerSelectionString);
        
       
        
    }
    public static void appendToFile(String fileName, String history){
        try {

            // Open given file in append mode.
            BufferedWriter out = new BufferedWriter(
                    new FileWriter(fileName, true));
            out.write(history);
            out.close();
        }catch (IOException e) {
            System.out.println("exception occoured" + e);
        }

    }
        
    
}
