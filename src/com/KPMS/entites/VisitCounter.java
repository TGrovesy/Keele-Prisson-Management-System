/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.KPMS.entites;

/**
 *
 * @author heyco
 */
public class VisitCounter {
    public VisitCounter(){
    }
    String minutePhase;
    String hourPhase;
    public String hourCounter (int hourValue){
        switch(hourValue){
            case 1:
                hourPhase = "1";
                break;
            case 2:
                hourPhase = "2";
                break;
            case 3:
                hourPhase = "3";
                break;
            case 4:
                hourPhase ="4";
                break;
            case 5:
                hourPhase ="no";
                break;
          
        }
        return hourPhase;
    }
    public String minuteCounter(int minuteValue){
        switch(minuteValue){
            case 1:
                minutePhase = ":00";
                break;
            case 2:
                minutePhase = ":20";
                break;
            case 3:
                minutePhase = ":40";
                break;
            case 5:
                minutePhase =" more";
                break;
        }
        return minutePhase;
    }
    
}
