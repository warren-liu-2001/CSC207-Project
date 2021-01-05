package UseCases;

import Entities.*;
import Exceptions.*;

import java.time.LocalTime;
import java.util.ArrayList;
import java.util.GregorianCalendar;
import java.util.UUID;

/**
 * Stores all the talks in the system
 *
 * @version 2.0
 */

public class TalkManager implements java.io.Serializable {
    /**
     * Stores entire list of Events
     * Can initialize an event and add it to the list of talks
     * To create an event, just call createEvent
     */

    private ArrayList<Event> talks;
    private ArrayList<String> ids;

    /**
     * Initializes a talk manager
     */
    public TalkManager() {
        this.talks = new ArrayList<>();
    }

    /**
     * create a Talk object with following parameters
     * @param title String title of talk
     * @param date Gregorian Calendar date of talk
     * @param startTime LocalTime start time of talk
     * @param organizers ArrayList</String>, list of organizerIDs of talk
     * @param duration int duration of talk in minutes
     * @param speakerID ID of speaker of talk
     */
    //To set MAXLimit to be different from 100, call setMAXLimit() in Event
    public void createTalk(String title, GregorianCalendar date, LocalTime startTime,
                            ArrayList<String> organizers, int duration, String speakerID, int maxLimit){
        UUID id = UUID.randomUUID();
        String ID = "Talk " + id.toString();
        Talk talk = new Talk(title, date, startTime, organizers, ID, duration, speakerID, maxLimit);
        this.talks.add(talk);
    }
    /**
     * create a Party object with following parameters
     * @param title String title of talk
     * @param date Gregorian Calendar date of talk
     * @param startTime LocalTime start time of talk
     * @param organizers ArrayList</String>, list of organizerIDs of talk
     * @param duration int duration of talk in minutes
     */
    //To set MAXLimit to be different from 100, call setMAXLimit() in Event
    public void createParty(String title, java.util.GregorianCalendar date, LocalTime startTime,
                            ArrayList<String> organizers, int duration, int maxLimit){
        UUID id = UUID.randomUUID();
        String ID = "Party " + id.toString();
        Party party = new Party(title, date, startTime, organizers, ID, duration, maxLimit);
        talks.add(party);
    }

    /**
     * create a Panel object with following parameters
     * @param title String title of talk
     * @param date Gregorian Calendar date of talk
     * @param startTime LocalTime start time of talk
     * @param organizers ArrayList</String>, list of organizerIDs of talk
     * @param duration int duration of talk in minutes
     * @param speakers ArrayList of Strings containing IDs of speakers hosting panel
     */
    //To set MAXLimit to be different from 100, call setMAXLimit() in Event
    public void createPanel(String title, GregorianCalendar date, LocalTime startTime,
                            ArrayList<String> organizers, int duration, ArrayList<String> speakers, int maxLimit){
        UUID id = UUID.randomUUID();
        String ID = "Panel " + id.toString();
        Panel panel = new Panel(title, date, startTime, organizers, ID, duration, speakers, maxLimit);
        talks.add(panel);
    }

    /**
     *Switches the old speaker to the new speaker if the newSpeaker is available, returns true iff successful
     * @param talk talk object in question
     * @param speakerID the new speaker's ID
     * @param um - UserManager
     */
    public boolean replaceSpeaker(Talk talk, String speakerID, UserManager um) throws DoesNotExistException {
        AvailabilityChecker ac = new AvailabilityChecker();
        Speaker speaker = um.findSpeaker(speakerID);
        if (ac.isSpeakerAvailable(talk, speaker, this)){
            Speaker oldSpeaker = um.findSpeaker(talk.getSpeakers().get(0));
            talk.switchSpeaker(speakerID);
            speaker.addHostEvent(talk.getEventID());
            oldSpeaker.removeHostEvent(talk.getEventID());
            return true;
        }
        return false;
    }

    /**
     * Switches the old speakers to the new speakers if the newSpeaker is available, returns true iff successful
     * @param ID of talk object in question
     * @param speakerIDs the new speakers' IDs
     * @param um - UserManager
     */
    //Might Not Work
    public boolean replaceSpeakers(String ID, ArrayList<String> speakerIDs, UserManager um) throws DoesNotExistException {
        Event event = findTalk(ID);
        if (event.isTalk()) {
            // event is a talk, so casting is safe
            Talk talk = (Talk) event;

            AvailabilityChecker ac = new AvailabilityChecker();
            //set up availability
            ArrayList<String> sIDs = new ArrayList<>();
            for(String speakerID: speakerIDs){
                Speaker speaker = um.findSpeaker(speakerID);
                if (ac.isSpeakerAvailable(talk, speaker, this)){
                    sIDs.add(speakerID);
                }
                else {
                    return false;
                }
            }
            talk.removeAllSpeaker();
            talk.setSpeakers(sIDs);
            for(String speakerID: sIDs) {
                Speaker speaker = um.findSpeaker(speakerID);
                speaker.addHostEvent(talk.getEventID());
            }
            return talk.getSpeakers() == speakerIDs;
        } else {
            // not a talk, can't do this
            return false;
        }
    }

    /**
     * Add a speaker to a Panel.
     * @param talk Panel to add a speaker to
     * @param speakerID ID of the speaker to add
     * @param um UserManager containing this speaker's information
     * @return If the addition was successful or not
     * @throws DoesNotExistException If the speaker does not exist
     */
    public boolean addSpeaker(Panel talk, String speakerID, UserManager um) throws DoesNotExistException {
        AvailabilityChecker ac = new AvailabilityChecker();
        Speaker speaker = um.findSpeaker(speakerID);
        if (ac.isSpeakerAvailable(talk, speaker, this)){
            talk.getSpeakers().add(speakerID);
            speaker.addHostEvent(speakerID);
            return true;
        }
        return false;
    }

    /**
     * Add a speaker to a Panel.
     * @param eventID ID of Event to add a speaker to
     * @param speakerID ID of the speaker to add
     * @param um UserManager containing this speaker's information
     * @return If the addition was successful or not
     * @throws DoesNotExistException If the speaker does not exist
     */
    public boolean addSpeaker(String eventID, String speakerID, UserManager um) throws DoesNotExistException {
        AvailabilityChecker ac = new AvailabilityChecker();
        Speaker speaker = um.findSpeaker(speakerID);
        Event event = findTalk(eventID);
        if (event.isPanel()) {
            // safe to cast as panel
            Panel panel = (Panel) event;
            if (ac.isSpeakerAvailable(panel, speaker, this)){
                panel.getSpeakers().add(speakerID);
                speaker.addHostEvent(speakerID);
                return true;
            }
        }
        return false;
    }

    /**
     * Gets if the event associated with the given id exists
     * @param ID ID of the event to find
     * @return if this event with ID is stored within the TalkManager
     */
    public boolean isEvent(String ID){
        for (Event talk : talks) {
            if (talk.getEventID().equals(ID)) {
                return true;
            }
        }
        return false;
    }

    /**
     * Gets if a Event is a Talk or not
     * @param id ID of Event to check
     * @return If the Event is a Talk or not
     * @throws DoesNotExistException if no such Event exists
     */
    public Boolean isTalk(String id) throws DoesNotExistException {
        return findTalk(id).isTalk();
    }

    /**
     * Gets if a Event is a Panel or not
     * @param id ID of Event to check
     * @return If the Event is a Panel or not
     * @throws DoesNotExistException if no such Event exists
     */
    public Boolean isPanel(String id) throws DoesNotExistException {
        return findTalk(id).isPanel();
    }

    /**
     * Gets if a Event is a Party or not
     * @param id ID of Event to check
     * @return If the Event is a Party or not
     * @throws DoesNotExistException if no such Event exists
     */
    public Boolean isParty(String id) throws DoesNotExistException {
        return findTalk(id).isParty();
    }


    /**
     *
     * @return all of the talk IDs in the system
     */
    public ArrayList<String> getAllTalks() {
        ArrayList<String> ts = new ArrayList<>();
        for (Event t : this.talks){
            ts.add(t.getEventID());
        }
        return ts;
    }

    /**
     *
     * @param ID ID of the talk
     * @return the talk with the above ID's string representation
     * @throws DoesNotExistException if talk DNE
     */
    public String displayTalk(String ID) throws DoesNotExistException {
        // Talk: Orange, Date: Wed Nov 04 2020, Start time: 20:05:54.649, Attendees: [], Speakers: []
        return findTalk(ID).toString();
    }

    /**
     *
     * @param ID Id of the talk
     * @return title of the talk with above ID
     * @throws DoesNotExistException if talk does not exist
     */
    public String getTalkTitle(String ID) throws DoesNotExistException {
        return findTalk(ID).getTitle();
    }

    /**
     *
     * @param ID talk ID
     * @param title new title of the talk
     * @throws DoesNotExistException if talk DNE
     */
    public void setTalkTitle(String ID, String title) throws DoesNotExistException {
        findTalk(ID).setTitle(title);
    }

    /**
     *
     * @param ID talk ID
     * @return date of the talk with ID
     * @throws DoesNotExistException if talk does not exist
     */
    public GregorianCalendar getTalkDate(String ID) throws DoesNotExistException {
        return findTalk(ID).getDate();
    }

    /**
     * sets new date for the talk
     * @param ID ID of the talk Object
     * @param date the new date of the talk
     * @throws DoesNotExistException if the date does not exist
     */

    public void setTalkDate(String ID, GregorianCalendar date) throws DoesNotExistException {
        findTalk(ID).setDate(date);
    }

    /**
     *
     * @param ID the id of the talk
     * @return the starttime of the talk
     * @throws DoesNotExistException if talk DNE
     */
    public LocalTime getTalkStartTime(String ID) throws DoesNotExistException {
        return findTalk(ID).getStartTime();
    }

    /**
     *
     * @param ID ID of the talk
     * @param startTime the new starttime of the talk to be set
     * @throws DoesNotExistException if talk with ID dne
     */
    public void setTalkStartTime(String ID, LocalTime startTime) throws DoesNotExistException {
        findTalk(ID).setStartTime(startTime);
    }

    /**
     *
     * @param ID ID of the talk
     * @return list of attendees of the talk with above ID
     * @throws DoesNotExistException if talk DNE
     */
    public ArrayList<String> getTalkAttendees(String ID) throws DoesNotExistException {
        return findTalk(ID).getAttendees();
    }

    /**
     *
     * @param ID ID of the talk
     * @param user AttendeeID to be added
     * @throws DoesNotExistException if talk with ID ID DNE
     */
    public void addTalkAttendee(String ID, String user) throws DoesNotExistException {
        findTalk(ID).addAttendee(user);
    }

    /**
     *
     * @param ID ID of the talk
     * @param user UserID to be removed from talk with ID ID
     * @throws DoesNotExistException if talk with ID ID does not exist
     */

    public void removeTalkAttendee(String ID, String user) throws DoesNotExistException {
        findTalk(ID).removeAttendee(user);
    }

    /**
     *
     * @param ID ID of the talk
     * @return the speaker ID of the speaker of this talk
     * @throws DoesNotExistException if the talk with ID ID is not stored within this manager
     */

    public ArrayList<String> getTalkSpeaker(String ID) throws DoesNotExistException {
        Event talk = findTalk(ID);
        return talk.getSpeakers();
    }

    /**
     * Gets the organizers associated with this event
     * @param ID ID of the event
     * @return An ArrayList of organizer IDs
     * @throws DoesNotExistException if the event does not exist
     */
    public ArrayList<String> getOrganizerIDs(String ID) throws DoesNotExistException {
        Event talk = this.findTalk(ID);
        return talk.getOrganizers();
    }

    /**
     * Gets all of the talks
     * @return an ArrayList of all the talks
     */
    public ArrayList<Event> getTalks() {
        return talks;
    }

    /**
     * Finds the event with specified ID
     * @param ID ID of the event to find
     * @return the event with the specified ID
     * @throws DoesNotExistException if the ID doesn't belong to any talk
     */
    public Event findTalk(String ID) throws DoesNotExistException {
        for (Event event : talks) {
            if (event.getEventID().equals(ID)) {
                return event;
            }
        }
        throw new DoesNotExistException("This Talk with ID does not exist");
    }
    /**
     * Change Capacity of Talks
     * @param ID ID of the event to find
     * @param newLimit the new capcity
     * @return the event with the specified ID
     * @throws DoesNotExistException if the ID doesn't belong to any talk
     */
    public boolean changeCapacity(String ID, int newLimit) throws DoesNotExistException {
        findTalk(ID).setMAXLimit(newLimit);
        return newLimit == findTalk(ID).getMAXLimit();
    }

    /**
     * Finds the event with specified title
     * @param title title of the talk
     * @return the event with specified title
     * @throws DoesNotExistException if title is not the title of any talk
     */
    public Event findTalkByTitle(String title) throws DoesNotExistException {
        for (Event event : talks) {
            if (event.getTitle().equals(title)) {
                return event;
            }
        }
        throw new DoesNotExistException("This Talk with ID does not exist");
    }
}
