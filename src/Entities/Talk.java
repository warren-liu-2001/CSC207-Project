package Entities;

import java.sql.Time;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.time.LocalTime;
import java.util.GregorianCalendar;

/**
 * A representation of a talk, which is an event within our system
 *
 * @version 2.0
 */

public class Talk extends Event implements java.io.Serializable {
    private String title;
    private GregorianCalendar date;
    private LocalTime startTime;
    private int duration;
    private ArrayList<String> attendeesId;
    private ArrayList<String> organizers;
    private int MAXLimit;
    private ArrayList<String> speakers;
    private String EventID;

    /**
     * Initializes a Talk object
     * @param title the title of the talk
     * @param date the date of the talk
     * @param startTime when the talk starts
     * @param organizers the speakers in the talk
     * @param EventID the eventID of the talk
     * @param duration length of the talk
     */

    public Talk(String title, GregorianCalendar date, LocalTime startTime,
                ArrayList<String> organizers, String EventID, int duration, String speakerID, int maxLimit) {
        super(title, date, startTime, organizers, EventID, duration, maxLimit);
        this.speakers = new ArrayList<>();
        this.speakers.add(speakerID);
        // By default, lets set the max limit to 100.
    }

    /**
     * Gets a string representation of the talk
     * @return string representation of the talk
     */
    @Override
    public String toString() {
        // Talk: Orange, Date: Wed Nov 04 2020, Start time: 20:05:54.649, Attendees: [], Speakers: []
        return "Talk: " + title +
                ", Date: " + date.toZonedDateTime().format(DateTimeFormatter.ofPattern("d MMM uuuu"))+
                ", Start time: " + startTime +
                ", Organizers: " + organizers +
                ", Attendees: " + attendeesId +
                ", Speakers: " + speakers;
    }

    /**
     * Gets the speaker at this talk
     * @return the talk event speaker's ID
     */
    public ArrayList<String> getSpeakers() {
        return this.speakers;
    }

    /**
     * Switches the speaker speaking at the talk
     * @param newSpeakerID new speaker for this talk
     */
    public void switchSpeaker(String newSpeakerID){
        speakers.remove(0);
        speakers.add(newSpeakerID);
    }

    /**
     * Remove all speakers from this talk
     */
    public void removeAllSpeaker(){
        speakers.clear();
    }

    /**
     * Sets the speakers at the talk
     * @param newbies ArrayList of new speakers
     */
    public void setSpeakers(ArrayList<String> newbies){
        speakers = newbies;
    }

    /**
     * Gets if this event if a talk or not
     * @return if this event is a talk
     */
    public Boolean isTalk() {
        return true;
    }
}