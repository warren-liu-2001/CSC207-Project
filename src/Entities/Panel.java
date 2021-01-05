package Entities;

import sun.awt.HKSCS;
import java.util.ArrayList;
import java.time.LocalTime;
import java.util.GregorianCalendar;

public class Panel extends Talk implements java.io.Serializable{
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
     * Initializes a Panel object
     *
     * @param title the title of the panel
     * @param date the date of the panel
     * @param startTime when the panel starts
     * @param organizers the speakers in the panel
     * @param EventID the eventID of the panel
     * @param duration length of the panel, in minutes
     * @param maxLimit capacity of the panel
     * @param speakers speakers at this panel
     */

    public Panel(String title, GregorianCalendar date, LocalTime startTime,
                 ArrayList<String> organizers, String EventID, int duration, ArrayList<String> speakers, int maxLimit) {
        super(title, date, startTime, organizers, EventID, duration, speakers.get(0), maxLimit);
        super.setSpeakers(speakers);

    }

    /**
     * Adds a speaker to this Panel
     * @param speakerId id of the speaker to add
     */
    public void addSpeaker(String speakerId) {
        getSpeakers().add(speakerId);
    }

    /**
     * Removes a speaker from this Panel
     * @param speakerId id of the speaker to remove
     * @return If the speaker was removed successfully
     */
    public boolean removeSpeaker(String speakerId) {
        if (this.getSpeakers().contains(speakerId) && this.getSpeakers().size() > 1) {
            this.getSpeakers().remove(speakerId);
            return true;
        }
        else {return false;}
    }

    /**
     * Gets if this event is a panel or not
     * @return if this event is a panel
     */
    public Boolean isPanel() {
        return true;
    }
}