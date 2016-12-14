package logic;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.zip.DataFormatException;

/**
 * Created by Petar on 12/11/2016.
 */
public class OOADUtility {

    public static Date formatStringAsDate(String dateToFormat) {
        DateFormat df = new SimpleDateFormat("MM-dd-yyyy");
        Date date = null;
        try {
            date = df.parse(dateToFormat);
        } catch (ParseException e) {
            System.out.println(e.getMessage());
        }
        return date;
    }

    public static String formatDateToString(Date date) {
        return new SimpleDateFormat("mm/dd/yyyy").format(date);
    }

    public static Date formatStringAsTime(String timeToFormat) {
        DateFormat df = new SimpleDateFormat("H:mm");
        Date date = null;
        try {
            date = df.parse(timeToFormat);
        } catch (ParseException e) {
            System.out.println(e.getMessage());
        }
        return date;
    }

}
