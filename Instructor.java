package logic;
/**
 * Created by Katrina
 */

import database.tables.DBExam;
import database.tables.DBRoom;

import java.sql.SQLException;

import java.util.ArrayList;
import java.util.Date;

public class Instructor extends User {

    public Instructor(int id, String pw, UserType ut) {
        super(id, pw, ut);
    }

    public Instructor(String userName, String pw, UserType ut) {
        super(userName, pw, ut);
    }

    public ReturnResult scheduleExam(String course, String instructor, Date date, String startTime,
                                     String endTime, String room, String program, boolean isLab) {

        ReturnResult result = checkAvail(room, startTime, endTime, date, isLab);

        if (result.isSuccess()) {
            //method from database to add new exam, returns exam# (PK) as int
            try {
                int id = new DBExam().addExam(course, instructor, OOADUtility.formatDateToString(date), startTime, endTime, room, program);
                if (id > 0) {
                result.setMessage("SUCCESS: " + course +" exam successfully booked!");
                } else {
                    result.setSuccess(false);
                    result.setMessage("FAIL: Could not book exam." );
                }
            } catch (SQLException e) {
                result.setSuccess(false);
                result.setMessage("ERROR: " + e.getMessage());
            }
        }

        return result;
    }

    private ReturnResult checkAvail(String room, String startTime, String endTime, Date date, boolean isLabRequired) {
        ReturnResult result = new ReturnResult(true, "SUCCESS: Exam time is available");
        Room roomDetails;

        Date start = OOADUtility.formatStringAsTime(startTime);
        Date end = OOADUtility.formatStringAsTime(endTime);

        //Checks if requested end time is set before requested start time
        if (end.compareTo(start) <= 0) {
            result.setSuccess(false);
            result.setMessage("ERROR: Exam end time must be set AFTER exam start time.");
        }

        if (result.isSuccess()) {
            //check if room exists
            try {
                roomDetails = new DBRoom().loadRoomDetails(room);
                //if the room doesn't exist return error
                if (roomDetails == null) {
                    result.setSuccess(false);
                    result.setMessage("ERROR: This room does not exist. Room: " + room);
                }

                //If client requested lab but the selected room is not a lab, notify them.
                if (result.isSuccess() && isLabRequired) {
                    //method from database team to check if room is a lab, returns boolean: true if lab
                    if (!roomDetails.getIsLab()) {
                        result.setSuccess(false);
                        result.setMessage("ERROR: You requested a lab. However, this room is not a lab. Room: " + room);
                    }
                }

                //load daily schedule for room and check requested start and end times against scheduled exams
                if (result.isSuccess()) {
                    ArrayList<Exam> examSchedule = new DBExam().loadExamScheduleByRoom(room, OOADUtility.formatDateToString(date));
                    if (examSchedule.size() == 0) {
                        //no exams schedulded return true
                        return result;
                    }

                    for (Exam e : examSchedule) {
                        Date scheduledStart = OOADUtility.formatStringAsTime(e.getStartTime());
                        Date scheduledEnd = OOADUtility.formatStringAsTime(e.getEndTime());

                        int compareStartToSStart = start.compareTo(scheduledStart);
                        int compareStartToSEnd = start.compareTo(scheduledEnd);
                        int compareEndToSEnd = end.compareTo(scheduledEnd);
                        int compareEndToSStart = end.compareTo(scheduledStart);
                        System.out.println(e.getStartTime());
                        if (compareStartToSStart != compareStartToSEnd && compareStartToSEnd != 0
                                || compareEndToSStart != compareEndToSEnd && compareEndToSStart > 0
                                || compareStartToSStart < compareEndToSStart - 1) {
                            result.setSuccess(false);
                            result.setMessage("FAIL: Exam time is not available");
                        }

                    }
                }
            } catch (SQLException e) {
                result.setSuccess(false);
                result.setMessage("ERROR: " + e.getMessage());
            }

        }

        return result;
    }
}
