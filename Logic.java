/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package javaapplication49;

import java.util.Date;

/**
 *
 * @author John
 */
public class Logic {
    
    public int scheduleExam(String room, int startTime, int endTime, String course, boolean isLab, String instructor, Date date){
        
        if(checkAvail(room, startTime, endTime, date, isLab)){
            //method from database to add new exam, returns exam# (PK) as int
            int x = database.addExam(room, startTime, endTime, course, instructor, date);
            return x;
        }
        else{
            return 0;
        }
    }
    
    public  boolean checkAvail(String room, int startTime, int endTime, Date date, boolean isLab){
        //If lab is req'd
        if(isLab){
            //method from database team to check if room is a lab, returns boolean: true if lab
            if(! database.isLab()){
                return false;
            }
        }
        //if room is free
        //method from database team to check scheduled exams ??returns 2d array with with start/end times of exams on DATE in ROOM??
        int[][] exams = datebase.getRoomInfo(date, room);
        for(int[] exam : exams){
            //Check if start time of new exam is overlaps with existing exam
            if(startTime >= exam[0] && startTime <= exam[1]){
                return false;
            }
            //Check if end time of new exam is overlaps with existing exam
            if(endTime >= exam[0] && endTime <= exam[1]){
                return false;
            }
        }
        
        return true;
    }
        
    
}
