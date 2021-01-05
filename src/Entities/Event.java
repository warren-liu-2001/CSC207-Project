package Entities;

import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.GregorianCalendar;
import java.util.List;

/**
 * Event entity class
 *
 * @version 2.0
 */

public abstract class Event implements java.io.Serializable{
    private String title;
    private GregorianCalendar date;
    private LocalTime startTime;
    private int duration;
    private ArrayList<String> attendeesId;
    private ArrayList<String> organizers;
    private int MAXLimit;
    private String EventID;

    /**
     *  Instantiates an event
     * @param title title
     * @param date date
     * @param startTime starting time
     * @param organizers organizer IDs
     * @param EventID event ID
     * @param duration duration of event
     * @param maxLimit max limit of event
     */

    public Event(String title, GregorianCalendar date, LocalTime startTime,
                         ArrayList<String> organizers, String EventID, int duration, int maxLimit){
        this.title = title;
        this.EventID = EventID;
        this.date = date;
        this.startTime = startTime;
        this.organizers = organizers;
        this.attendeesId = new ArrayList<>();
        this.MAXLimit = maxLimit;
        this.duration = duration;
    }

    /**
     *
     * @return title of the talk event
     */
    public String getTitle() {
        return this.title;
    }

    /**
     *
     * @param title set the title of the event
     */
    public void setTitle(String title) {
        this.title = title;
    }

    /**
     *
     * @return the date of the event, in calendar format
     */

    public GregorianCalendar getDate() {
        return this.date;
    }

    /**
     * set the date:
     * @param date set the date of the event.
     */

    public void setDate(GregorianCalendar date) {
        this.date = date;
    }

    /**
     *
     * @return start time of the event talk
     */
    public LocalTime getStartTime(){
        return this.startTime;
    }

    /**
     * sets start time:
     * @param startTime the start time of the event
     */
    public void setStartTime(LocalTime startTime) {
        this.startTime = startTime;
    }


    /**
     *
     * @return list of all attendees
     */
    public ArrayList<String> getAttendees() {
        return this.attendeesId;
    }

    /**
     *
     * @return how many attendees have signed up for the events
     */
    public int getNumbersOccupied() {return this.attendeesId.size();}

    /**
     *
     * @return the number of free spots left for this event
     */

    public int getNumberOfFreeSpots() {return this.MAXLimit - this.getNumbersOccupied();}

    /**
     *
     * @param user add an attendeeID to a talk
     * @return if user was added: if there is space, user is added, and if not, user is not.
     */
    public boolean addAttendee(String user){
        if (this.getNumberOfFreeSpots() > 0) {this.attendeesId.add(user);
            return true;}
        else {return false;}
    }

    /**
     *
     * @param user remove this Attendee with ID user from event
     */
    public void removeAttendee(String user) {
        this.attendeesId.remove(user);
    }


    /**
     *
     * @return the eventID of this Talk Event
     */
    public String getEventID() {
        return this.EventID;
    }

    /**
     *
     * @param eventID set the eventID of the talk
     */
    public void setEventID(String eventID) {
        this.EventID = eventID;
    }

    /**
     *
     * @return the maximum amount of attendees allowed into the event
     */
    public int getMAXLimit() {return this.MAXLimit;}

    /**
     *
     * @param newMAX the new maximum amount of attendees
     */
    public void setMAXLimit(int newMAX) {this.MAXLimit = newMAX;}

    /**
     *
     * @return how many minutes this thing lasts
     */
    public int getDuration() {
        return duration;
    }

    /**
     *
     * @param duration set how many minutes this event lasts
     */
    public void setDuration(int duration) {
        this.duration = duration;
    }

    /**
     *
     * @param addedTime add time to this event, while the event is running, rules to be enforced in use case.
     */
    public void addTime(int addedTime) {this.duration += addedTime;}

    /**
     *
     * @return the organizer list of this talk
     */
    public ArrayList<String> getOrganizers() {return this.organizers;}

    @Override
    public String toString() {
        // Talk: Orange, Date: Wed Nov 04 2020, Start time: 20:05:54.649, Attendees: [], Speakers: []
        return "Talk: " + title +
                ", Date: " + date.toZonedDateTime().format(DateTimeFormatter.ofPattern("d MMM uuuu"))+
                ", Start time: " + startTime +
                ", Organizers: " + organizers;
    }

    /**
     * Checks if this event is a Talk
     * @return if this event is a Talk
     */
    public Boolean isTalk() {
        return false;
    }

    /**
     * Checks if this event is a Panel
     * @return if this event is a Panel
     */
    public Boolean isPanel() {
        return false;
    }

    /**
     * Checks if this event is a Party
     * @return if this event is a Party
     */
    public Boolean isParty() {
        return false;
    }

    /**
     * Get the speakers at the Event. Subclasses with speakers should override
     * @return Empty ArrayList
     */
    public ArrayList<String> getSpeakers() {
        return new ArrayList<>();
    }
}
