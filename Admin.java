package logic;

import database.tables.DBAdmin;
import database.tables.DBExam;
import database.tables.DBUser;

import java.sql.SQLException;

public class Admin extends Instructor {

    DBAdmin dba;

    public Admin(int id, String password){
        super(id, password, UserType.ADMIN);
        dba = new DBAdmin();
    }

    public Admin(String userName, String password) {
        super(userName, password, UserType.ADMIN);
        dba = new DBAdmin();
    }

    /**
     *
     * @param userName user to change password for.
     * @param newPassword specify the new password for user
     * @return if password change was successful or not.
     */
    public ReturnResult changePasswordForUser(String userName, String newPassword){
        ReturnResult result = new ReturnResult(true, "Success: Password successfully changed for: " + userName);
        try {
            if (dba.updatePassword(userName, newPassword)) {
                return result;
            } else {
                result.setSuccess(false);
                result.setMessage("FAIL: Could not change password for user: " + userName);
                return result;
            }
        } catch (SQLException e) {
            result.setSuccess(false);
            result.setMessage("SQL-ERROR: " + e.getMessage());
        }
        return result;
    }

    /**
     *
     * @param typeOfUser the type of user to add
     * @param password password to set for user
     * @return int value representing user Id, if return > 0 the user was added successfully.
     * else if return < 0 the user was unable to be added.
     */
    public ReturnResult addUser (UserType typeOfUser, String userName, String password){

        ReturnResult result = new ReturnResult(true, "SUCCESS: User added successfully");
        try {
            if (!dba.addUser(userName, password, String.valueOf(typeOfUser))) {
                result.setSuccess(false);
                result.setMessage("FAIL: Unable to add user: " + userName);
            }
        } catch (SQLException e) {
            result.setSuccess(false);
            result.setMessage("SQL-ERROR: " + e.getMessage());
        }
        return result;
    }

    /**
     * Used to be modifyUser(int userId, UserType typeOfUser)
     *
     * @param userName user to change
     * @param typeOfUser type of user to change to
     * @return if change was successful or not.
     */
    public ReturnResult changeUserType(String userName, UserType typeOfUser){
        ReturnResult result = new ReturnResult(true, "Success: " + userName + " is now an " + String.valueOf(typeOfUser));
        try {
            if (!dba.updateUser(userName, String.valueOf(typeOfUser))) {
                result.setSuccess(false);
                result.setMessage("FAIL: Unable to change user type for: " + userName);
            }
        } catch (SQLException e) {
            result.setSuccess(false);
            result.setMessage("SQL-ERROR: " + e.getMessage());
        }
        return result;
    }

    /**
     *
     * @param userName user to deleted
     * @return if user has been deleted successfully or not.
     */
	public ReturnResult deleteUser(String userName){
		ReturnResult result = new ReturnResult(true, "Success: user " + userName + " has been deleted.");
        try {
            if (!dba.deleteUser(userName)) {
                result.setSuccess(false);
                result.setMessage("FAIL : Unable to delete user: " + userName);
            }
        } catch (SQLException e) {
            result.setSuccess(false);
            result.setMessage("SQL-ERROR: " + e.getMessage());
        }
        return result;
	}

	/*
	* String course, String instructor, String date, String startTime,
                                     String endTime, String room, String program, boolean isLab
	*
	* */

	public ReturnResult modifyExam(int examId, String course, String instructor, String date, String startTime,
                                   String endTime, String room, String program, boolean isLab) {
        ReturnResult result = super.checkAvail(room, startTime, endTime, date, isLab);
        if (result.isSuccess()) {
            DBExam dbExam = new DBExam();
            try {
                if (dbExam.updateExam(String.valueOf(examId), course, instructor, date, startTime, endTime, room, program) > 0) {
                    result.setSuccess(true);
                    result.setMessage("SUCCESS: Exam modified successfully! Exam ID: " + examId);
                } else {
                    result.setSuccess(false);
                    result.setMessage("FAIL: Unable to modify exam. Exam ID: " + examId);
                }
            } catch (SQLException e) {
                result.setSuccess(false);
                result.setMessage("SQL-ERROR: " + e.getMessage());
            }
        }

        return result;
    }




}
