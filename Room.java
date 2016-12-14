package logic;

/**
 * Created by Petar on 12/10/2016.
 */
public class Room {

    private String roomNumber;
    private String isLab;


    public Room(String roomNumber, String isLab) {
        this.roomNumber = roomNumber;
        this.isLab = isLab;
    }

    public String getRoomNumber() {
        return roomNumber;
    }

    public boolean getIsLab() {
        return (isLab.equalsIgnoreCase("Y"));
    }


}
