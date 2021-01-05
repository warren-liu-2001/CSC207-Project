package Controllers;

import Entities.*;
import Exceptions.*;
import UseCases.*;
import org.w3c.dom.Attr;

import java.lang.reflect.Array;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.GregorianCalendar;
import java.util.Map;

/**
 * Handles talk organizing functionality for the PresenterMain access point.
 *
 * @version 2.0
 */
public class TalkOrganizer implements java.io.Serializable{

    private UserAccount ua;
    private TalkManager tm;
    private RoomManager rm;
    private UserManager um;

    /**
     *
     * @param useraccount useraccount
     * @param usecasestorage use case storage
     */
    public TalkOrganizer(UserAccount useraccount, UseCaseStorage usecasestorage) {
        this.ua = useraccount;
        this.tm = usecasestorage.getTalkManager();
        this.rm = usecasestorage.getRoomManager();
        this.um = usecasestorage.getUserManager();

    }

    /**
     * Add a talk event to the conference.
     * @param title - the title of the talk.
     * @param date - the date of the talk.
     * @param startTime - the time the talk starts.
     * @param speaker - the speaker of the talk.
     * @param duration - the duration of the talk.
     * @param organizerIDs - the list of organizer IDs responsible for the talk.
     * @param roomID - the ID of the room where the talk takes place.
     * @param maxLimit - maximum capacity for the event
     * @return if the event was created successfully or not
     */
    public boolean addTalk(String title, GregorianCalendar date, LocalTime startTime, String speaker, int duration,
                        ArrayList<String> organizerIDs, String roomID, int maxLimit) throws DoesNotExistException {
        if (ua.isOrganizer()) {

            // check if event capacity is greater than room capacity
            if (rm.getRoomCapacity(roomID) < maxLimit) {
                return false;
            }
            // check if speaker is available for the talk
            if (!(isSpeakerAvailable(speaker, date, startTime, duration))) {
                return false;
            }
            // check if room is available for the talk, talk time is within room's open hours
            if (!(isRoomAvailable(roomID, date, startTime, duration))) {
                return false;
            }

            this.tm.createTalk(title, date, startTime, organizerIDs, duration, speaker, maxLimit);
            Event found = this.tm.findTalkByTitle(title);
            String eventID = found.getEventID();
            rm.addEventToSchedule(rm.findRoom(roomID), eventID, tm, um);
            return true;
        } else {
            return false;
        }
    }

    /**
     * Add a panel event to the conference.
     * @param title - the title of the panel.
     * @param date - the date of the panel.
     * @param startTime - the time the panel starts.
     * @param speakers - the speakers of the panel.
     * @param duration - the duration of the panel.
     * @param organizerIDs - the list of organizer IDs responsible for the panel.
     * @param roomID - the ID of the room where the panel takes place.
     * @param maxLimit - the maximum capacity for the event
     * @return if the event was created successfully or not
     */
    public Boolean addPanel(String title, GregorianCalendar date, LocalTime startTime, ArrayList<String> speakers,
                         int duration, ArrayList<String> organizerIDs, String roomID, int maxLimit) throws DoesNotExistException {
        if (ua.isOrganizer()) {

            // check if event capacity is greater than room capacity
            if (rm.getRoomCapacity(roomID) < maxLimit) {
                return false;
            }
            // check if each speaker is available for the panel
            for (String speakerID : speakers) {
                if (!(isSpeakerAvailable(speakerID, date, startTime, duration))) {
                    return false;
                }
            }
            // check if room is available for the talk, talk time is within room's open hours
            if (!(isRoomAvailable(roomID, date, startTime, duration))) {
                return false;
            }

            this.tm.createPanel(title, date, startTime, organizerIDs, duration, speakers, maxLimit);
            Event found = this.tm.findTalkByTitle(title);
            String eventID = found.getEventID();
            rm.addEventToSchedule(rm.findRoom(roomID), eventID, tm, um);
            return true;
        } else {
            return false;
        }
    }

    /**
     * Add a party event to the conference.
     * @param title - the title of the party.
     * @param date - the date of the party.
     * @param startTime - the time the party starts.
     * @param duration - the duration of the party.
     * @param organizerIDs - the list of organizer IDs responsible for the party.
     * @param roomID - the ID of the room where the party takes place.
     * @param maxLimit - the maximum capacity for the event
     * @return if the event was created successfully or not
     */
    public Boolean addParty(String title, GregorianCalendar date, LocalTime startTime, int duration,
                         ArrayList<String> organizerIDs, String roomID, int maxLimit) throws DoesNotExistException {
        if (ua.isOrganizer()) {

            // check if event capacity is greater than room capacity
            if (rm.getRoomCapacity(roomID) < maxLimit) {
                return false;
            }
            // check if room is available for the talk, talk time is within room's open hours
            if (!(isRoomAvailable(roomID, date, startTime, duration))) {
                return false;
            }

            this.tm.createParty(title, date, startTime, organizerIDs, duration, maxLimit);
            Event found = this.tm.findTalkByTitle(title);
            String eventID = found.getEventID();
            rm.addEventToSchedule(rm.findRoom(roomID), eventID, tm, um);
            return true;
        } else {
            return false;
        }
    }

    /**
     * Checks if a speaker is available on a given date for a given period of time.
     * @param speakerID - the ID of the speaker.
     * @param date - the date in question
     * @param startTime - the start of the time in question.
     * @param duration - the length of time in which the speaker's availability is asked.
     * @return true if and only if the speaker is available on this date for the desired length of time.
     */
    public boolean isSpeakerAvailable(String speakerID, GregorianCalendar date, LocalTime startTime, int duration)
            throws DoesNotExistException {
        AvailabilityChecker availChecker = new AvailabilityChecker();
        Speaker speaker = (Speaker) um.findUser(speakerID);
        return availChecker.isSpeakerAvailable(date, startTime, duration, speaker, tm);
    }

    /**
     * Checks if room is available at the given date and time for the given duration
     * @param roomID - the ID of the room object, whose availability we wish to check
     * @param date Gregorian Calendar object representing date to check room's availability
     * @param startTime  LocalTime object - time at which to check room's availability
     * @param duration - number of minutes we want room to be free
     * @return true iff room is available
     */
    public boolean isRoomAvailable(String roomID, GregorianCalendar date, LocalTime startTime, int duration) throws DoesNotExistException {
        AvailabilityChecker availChecker = new AvailabilityChecker();
        Room room = rm.findRoom(roomID);
        return availChecker.isRoomAvailable(date, startTime, duration, room, tm);
    }

    /**
     *
     * @param EventID the ID of the event that is to be removed.
     * @return if the event was successfully removed or not
     */

    public boolean removeEvent(String EventID){

        try{
            // Check firstly if the organizer is hosting the event. If not, its none of their business
            if (ua.isOrganizer()) {
                ArrayList<String> Hosts = this.tm.getOrganizerIDs(EventID);
                boolean isHost = Hosts.contains(ua.getUserID());
                if (isHost) {
                    this.rm.removeEventFromSchedule(EventID, this.tm, this.um);
                    return true;
                } else {return false;}

            }
            // Being admin overrides the need for checking if the organizer is hosting the event.
            // Admins can do whatever they want
            else if (ua.isAdmin()) {
                this.rm.removeEventFromSchedule(EventID, this.tm, this.um);
                return true;
            }
            // If it isn't an organizer, or an admin, then they can GTFO
            else {return false;}
        } catch(DoesNotExistException e) {
            return false;
        }
    }
}
