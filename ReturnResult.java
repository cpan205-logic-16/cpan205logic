package logic;
/**
 * Created by petar on 28/11/16.
 */
public class ReturnResult {

    private boolean isSuccess;
    private String message;

    /**
     *
     * @param isSuccess set the return value
     * @param message set a descriptive message if isSuccess is set to false.
     *                Use an empty string or success message is isSuccess is set to true.
     */
    public ReturnResult(boolean isSuccess, String message) {
        this.isSuccess = isSuccess;
        this.message = message;
    }

    /**
     *
     * @return if result is true or false.
     */
    public boolean isSuccess() {
        return isSuccess;
    }

    public void setSuccess(boolean success) {
        isSuccess = success;
    }

    /**
     *
     * @return gets the reason for a failed result.
     */
    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
