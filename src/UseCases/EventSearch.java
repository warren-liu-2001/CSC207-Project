package UseCases;

import Entities.Event;
import Entities.Room;
import Entities.Talk;
import Exceptions.DoesNotExistException;

import java.lang.reflect.Array;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.GregorianCalendar;
import java.util.Map;

/**
 * This is a helper class to RoomManager
 *
 * @version 2.0
 */
public class EventSearch implements java.io.Serializable {
    /**
     * Use this class to find the events by a given metric : title, speaker name, or date and time
     * Helper class to RoomManager - create a new instance with empty constructor to use these methods
     */
    public EventSearch(){}

    /**
     * Gets talks with given title
     * @param title - String, title of talks to be found in schedule
     * @param tm - UseCases.TalkManager object storing talks
     * @return ArrayList</Entities.Talk> containing talks with given title
     */
    public ArrayList<Event> getEventsByTitle(String title, TalkManager tm, RoomManager rm) {
        ArrayList<Event> eventsWithTitle = new ArrayList<>();
        try {
            for (Room room : rm.getRooms()) {
                for (Map.Entry<GregorianCalendar, ArrayList<String>> eventsOnDate : room.getSchedule().entrySet()) {
                    for (String talkID : eventsOnDate.getValue()) {
                        if (tm.findTalk(talkID).getTitle().equals(title)) {
                            eventsWithTitle.add(tm.findTalk(talkID));
                        }
                    }
                }
            }
            return eventsWithTitle;
        } catch (DoesNotExistException e) {
            // return the partial list of events
            return eventsWithTitle;
        }
    }

    /**
     * Gets talks with given speaker
     * @param speakerID - String which is the ID of the Entities.Speaker whose talks to be found
     * @param tm - UseCases.TalkManager object storing talks
     * @return ArrayList</Entities.Talk> containing talks given by speaker
     */
    public ArrayList<Talk> getEventsBySpeaker(String speakerID, TalkManager tm, RoomManager rm) {
        ArrayList<Talk> eventsBySpeaker = new ArrayList<>();
        try {
            for (Room room: rm.getRooms()){
                for  (Map.Entry<GregorianCalendar, ArrayList<String>> eventsOnDate: room.getSchedule().entrySet()) {
                    for (String talkID : eventsOnDate.getValue()) {
                        Talk talk = (Talk) tm.findTalk(talkID);
                        String talkSpeakerID = talk.getSpeakers().get(0);
                        if (talkSpeakerID.equals(speakerID)) {
                            eventsBySpeaker.add((Talk) tm.findTalk(talkID));
                        }
                    }
                }
            }
            return eventsBySpeaker;
        } catch (DoesNotExistException e) {
            // return the partial list of events
            return eventsBySpeaker;
        }
    }

    /**
     * Return list of talks scheduled on given date and time
     * @param date - GregorianCalendar object, date to be checked
     * @param time - LocalTime object, time to be checked
     * @param tm - UseCases.TalkManager object storing talks
     * @return ArrayList</Entities.Talk>, list of events scheduled on date at time
     */
    public ArrayList<Event> getEventsByTime(GregorianCalendar date, LocalTime time, TalkManager tm, RoomManager rm) {
        ArrayList<Event> eventsOnDate = new ArrayList<>();
        try {
            for (Room room: rm.getRooms()){
                if (room.getSchedule().containsKey(date)){
                    for (String talkID: room.getSchedule().get(date)){
                        if (tm.findTalk(talkID).getStartTime().equals(time)){
                            eventsOnDate.add(tm.findTalk(talkID));
                        }
                    }
                }
            }
            return eventsOnDate;
        } catch (DoesNotExistException e) {
            // return the partial list of events
            return eventsOnDate;
        }
    }

    /**
     *
     * @param tm talk manager
     * @param rm room manager
     * @return list of all events with no attendees
     */
    public ArrayList<String> getEmptyEvents(TalkManager tm, RoomManager rm) {
        ArrayList<String> eventNames = new ArrayList<>();
        try {
            for (Room room: rm.getRooms()) {
                for (Map.Entry<GregorianCalendar, ArrayList<String>> eventsOnDate : room.getSchedule().entrySet()) {
                    for (String talkID : eventsOnDate.getValue()) {
                        if (tm.findTalk(talkID).getAttendees().size() == 0) {
                            eventNames.add(tm.findTalk(talkID).getTitle());
                        }
                    }
                }
            }

            return eventNames;
        } catch (DoesNotExistException e) {
            // return the partial list of events
            return eventNames;
        }
    }

    /**
     * get a list of all of the events
     * @param tm talkmanager
     * @param rm room manager
     * @return all of the events in the calendar
     */

    public ArrayList<Event> getAllEvents(TalkManager tm, RoomManager rm) {
        ArrayList<Event> events = new ArrayList<>();
        try {
            for (Room room: rm.getRooms()) {
                for (Map.Entry<GregorianCalendar, ArrayList<String>> eventsOnDate : room.getSchedule().entrySet()) {
                    for (String talkID : eventsOnDate.getValue()) {
                            events.add(tm.findTalk(talkID));
                    }
                }
            }
            return events;
        } catch (DoesNotExistException e) {
            // return the partial list of events
            return events;
        }
    }

}
