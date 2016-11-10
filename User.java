/**
 * Created by Petar on 10/19/2016.
 */
public class User {

    private int id;
    private String password;
    private UserType userType;

    enum UserType {STUDENT, INSTRUCTOR, ADMIN};

    /*
    * All methods return true temporarily
    * for the purpose of testing classes with the other teams (Database, Server, Web)
    *
    * */

    public boolean signIn(int id, String password) {
        return (db.loadUser(id).getPassword().equals(password));
    }

    public boolean changePassword(String oldPassword, String newPassword) {
        //methods from database team to:
        //get current password
        //change password, returns true if succeeded
        if (currentPassword.equals(database.getPassword(id)) && database.updatePassword(id, newPassword)) {
            password = newPassword;
            return true;
        } else {
            return false;
        }
    }

    /*
    *	Assuming DB team is providing a method to load a schedule based on the type of schedule requested.
	*	The old verison of this method contained a query param, has been removed as it's not needed. 
	*/
    public List<Exam> viewSchedule(ViewScheduleType viewScheduleType) {
        List<Exam> listOfExams;
        String query;
        switch (viewScheduleType) {
            case PROGRAM:
                //method from DBclass to retrieve list of exams with specified program
                query = "program";
                break;
            case ROOM:
                //method from DBclass to retrieve list of exams with specified room#
                query = "room";
                break;
            case WEEK:
            default:
                //method from DBclass to retrieve list of exams with specified week#
                query = "week";
                break;
            case TEACHER:
                //method from DBclass to retrieve list of exams with specified teacher
                query = "teacher";
                break;
        }


        listOfExams = db.loadSchedule(mQuery);

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
}
