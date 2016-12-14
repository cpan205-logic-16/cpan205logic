//package logic;
//
//public class Admin extends Instructor {
//
//    public Admin(){
//    }
//
//    /**
//     *
//     * @param userId user to change password for.
//     * @param newPassword specify the new password for user
//     * @return if password change was successful or not.
//     */
//    public ReturnResult changePassword(int userId, String newPassword){
//        ReturnResult result = new ReturnResult(true, "Success: Password successfully changed for user ID: " + userId);
//        if (db.changePassword(int userId, String newPassword)) {
//            return result;
//        } else {
//            result.setSuccess(false);
//            result.setMessage("Error: Could not change password for user ID: " + userId);
//            return result;
//        }
//    }
//
//    /**
//     *
//     * @param typeOfUser the type of user to add
//     * @param password password to set for user
//     * @return int value representing user Id, if return > 0 the user was added successfully.
//     * else if return < 0 the user was unable to be added.
//     */
//    public int addUser (UserType typeOfUser, String password){
//        return db.addUser(typeOfUser, String password);
//    }
//
//    /**
//     * Used to be modifyUser(int userId, UserType typeOfUser)
//     *
//     * @param userId user to change
//     * @param typeOfUser type of user to change to
//     * @return if change was successful or not.
//     */
//    public ReturnResult changeUserType(int userId, UserType typeOfUser){
//        ReturnResult result = new ReturnResult(true, "Success: DBUser type changed");
//        //dependant on db team method
//        if (!db.changeUserType(userId, typeOfUser)) {
//            result.setSuccess(false);
//            result.setMessage("Error: Could not change user type.");
//        }
//        return result;
//    }
//
//    /**
//     *
//     * @param userId user to deleted
//     * @return if user has been deleted successfully or not.
//     */
//	public ReturnResult deleteUser(int userId){
//		ReturnResult result = new ReturnResult(true, "Success: user " + userId + " has been deleted.");
//        if (!db.deleteUser(userId)) {
//            result.setSuccess(false);
//            result.setMessage("Error: user " + userId + " could not be deleted.");
//        }
//        return result;
//	}
//
//	public ReturnResult modifyExam() {
//
//    }
//
//
//    public int generateId(){
//        return 0;
//    }
//
//}
