package Entities;

import java.time.LocalTime;
import java.util.ArrayList;
import java.util.GregorianCalendar;

/**
 * Party Class
 *
 * @version 2.0
 */

public class Party extends Event implements java.io.Serializable{
    private String title;
    private GregorianCalendar date;
    private LocalTime startTime;
    private int duration;
    private ArrayList<String> attendeesId;
    private ArrayList<String> organizers;
    private int MAXLimit;
    private String EventID;

    /**
     *
     * @param title title of party
     * @param date date of party
     * @param startTime start time of party
     * @param organizers organizers
     * @param EventID eventID
     * @param duration duration
     * @param maxLimit maximum limit
     */
    public Party(String title, java.util.GregorianCalendar date, LocalTime startTime,
                 ArrayList<String> organizers, String EventID, int duration, int maxLimit) {
        super(title, date, startTime, organizers, EventID, duration, maxLimit);
    }

    /**
     *
     * @return the list of speakers
     */
    @Override
    public ArrayList<String> getSpeakers() {
        return new ArrayList<String>();
    }

    /**
     * Gets if this Event is a Party or not
     * @return if this Event if a Party
     */
    public Boolean isParty() {
        return true;
    }
}
