package logic;
/**
 * Created by Katrina
 */

import database.tables.DBExam;
import database.tables.DBRoom;
import logic.ReturnResult;
import logic.Room;
import logic.User;

import java.sql.SQLException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class Instructor extends User {

    public Instructor(int id, String pw, UserType ut) {
        super(id, pw, ut);
    }

    public int scheduleExam(String room, String startTime, String endTime, String course, boolean isLab, String instructor, Date date){
        ReturnResult result = checkAvail(room, startTime, endTime, date, isLab);
        System.out.println(result.getMessage());
        if(result.isSuccess()){
            //method from database to add new exam, returns exam# (PK) as int
            //int x = database.addExam(room, startTime, endTime, course, instructor, date);
            return 1;
        }
        else{
            return 0;
        }
    }

    public ReturnResult checkAvail(String room, String startTime, String endTime, Date date, boolean isLabRequired){
        ReturnResult result = new ReturnResult(true, "SUCCESS: Room is available");
        Room roomDetails = null;
        //check if room exists

        try {
            roomDetails = new DBRoom().loadRoomDetails(room);
            if (roomDetails == null) {
                result.setSuccess(false);
                result.setMessage("ERROR: This room does not exist. Room: " + room);
            }
        } catch (SQLException e) {
            result.setSuccess(false);
            result.setMessage("ERROR: " + e.getMessage());
        }
        //If lab is req'd
        if(result.isSuccess() && isLabRequired){
            //method from database team to check if room is a lab, returns boolean: true if lab
            if (!roomDetails.getIsLab()) {
                result.setSuccess(false);
                result.setMessage("ERROR: You requested a lab. However, this room is not a lab. Room: " + room);
            }
        }
        //if room is free

       try {
           ArrayList<Exam> examSchedule = new DBExam().loadExamScheduleByRoom(room, OOADUtility.formatDateToString(date));
           if (examSchedule.size() == 0) return result;
           Date start = OOADUtility.formatStringAsTime(startTime);
           Date end = OOADUtility.formatStringAsTime(endTime);

           for (Exam e : examSchedule) {
               Date scheduledStart = OOADUtility.formatStringAsTime(e.getStartTime());
               Date scheduledEnd = OOADUtility.formatStringAsTime(e.getEndTime());
              
               int compareStartResult = start.compareTo(scheduledStart);
               int compareEndResult = end.compareTo(scheduledEnd);
             if (compareStartResult == 0) {
                 result.setMessage("ERROR: Exam time is not available. Another exam is already scheduled for that time.");
                 result.setSuccess(false);
                // return result;
             } else if (compareStartResult == 1 && compareEndResult < 0 || end.compareTo(scheduledStart) == 1) {
                result.setMessage("ERROR: Exam time is not available. Requested exam time overlaps existing exam.");
                result.setSuccess(false);
             }

           }
       } catch (SQLException e){
           result.setSuccess(false);
           result.setMessage("ERROR: " + e.getMessage());
       }
        //method from database team to check scheduled exams ??returns 2d array with with start/end times of exams on DATE in ROOM??
//        int[][] exams = database.getRoomInfo(date, room);
//        for(int[] exam : exams){
//            //Check if start time of new exam overlaps with existing exam
//            if(startTime >= exam[0] && startTime <= exam[1]){
//                return false;
//            }
//            //Check if end time of new exam overlaps with existing exam
//            if(endTime >= exam[0] && endTime <= exam[1]){
//                return false;
//            }
//        }

        return result;
    }
}
