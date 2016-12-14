package logic;

import database.tables.DBUser;

import java.sql.SQLException;
import java.util.List;

/**
 * Created by Petar on 10/19/2016.
 */
public class User {

    private int id;
    private String userName;
    private String password;
    private UserType userType;
    private User validatedUser;

    public enum UserType {STUDENT, INSTRUCTOR, ADMIN};

    public User(String userName, String password, UserType userType) {
        this.userName = userName;
        this.password = password;
        this.userType = userType;
    }

    public User(int id, String password, UserType userType) {
        this.id = id;
        this.password = password;
        this.userType = userType;
    }

    public User(int id, String password) {
        this.id = id;
        this.password = password;
    }

    public User(int id) {
        this.id = id;

    }

    public User() {}

    /**
     * Provides user validation. Perhaps this should return a User object instead?
     * @param password password to verify with
     * @return true/false if the user was validated
     */
    public ReturnResult signIn(String password) {
        ReturnResult result = new ReturnResult(false, "ERROR: Invalid password.");
        try {
            //User user = DBUser.loadUser(id);
            User user = DBUser.loadUser(userName);
            if (user.getPassword().equals(password)) {
                setValidatedUser(user);
                result.setSuccess(true);
                result.setMessage("SUCCESS: User validated.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
            result.setMessage("ERROR: Database error(" + e.getMessage()+")");
        }
        return result;
    }

    public User loadValidatedUser() {
        return validatedUser;
    }

    private void setValidatedUser(User user) {
        this.validatedUser = user;
    }

//    public boolean signIn(int id, String password) {
//        //return (db.loadUser(id).getPassword().equals(password));
//    }

    public ReturnResult changePassword(String oldPassword, String newPassword) {
        ReturnResult result = new ReturnResult(false, "ERROR: Old password did not match");
        DBUser dbUser = new DBUser();
        try {
            if (oldPassword.equals(getPassword()) && dbUser.updatePassword(getId(), newPassword) > 0) {
                setPassword(newPassword);
                result.setSuccess(true);
                result.setMessage("SUCCESS: Password changed successfully");
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            result.setMessage("ERROR: Database error(" + e.getMessage()+")");
        }
        return result;
    }

    /*
    *	Assuming DB team is providing a method to load a schedule based on the type of schedule requested.
	*	The old verison of this method contained a query param, has been removed as it's not needed. 
	*/
    public List<Exam> viewSchedule(ViewScheduleType viewScheduleType, String search){
        List<Exam> listOfExams = null;
        String query;
        switch (viewScheduleType) {
            case PROGRAM:
                query = "PROGRAM";
                break;
            case ROOM:
                query = "ROOM_NO";
                break;
            case WEEK:
            default:
                query = "week";
                break;
            case TEACHER:
                query = "INSTRUCTOR";
                break;
        }

        DBUser dbUser = new DBUser();
        try {
            listOfExams = dbUser.viewSchedule(query, search);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

        return listOfExams;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public UserType getUserType() {
        return userType;
    }

    public void setUserType(UserType userType) {
        this.userType = userType;
    }

    public void setUserName(String userName) {this.userName = userName;}

    public String getUserName() {return userName;}

    public void setUserTypeAsStr(String userType) {
        setUserType(UserType.valueOf(userType.toUpperCase()));
    }
}
