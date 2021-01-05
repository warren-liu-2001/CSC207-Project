package Presenters;

import Controllers.UseCaseStorage;
import Controllers.UserAccount;
import Controllers.UserAccountSystem;
import Entities.Event;
import Entities.Talk;
import Entities.User;
import Exceptions.DoesNotExistException;
import Exceptions.ObjectInvalidException;
import UseCases.EventSearch;
import UseCases.RoomManager;
import UseCases.TalkManager;

import java.time.LocalTime;
import java.util.GregorianCalendar;
import java.util.ArrayList;
import java.util.Map;

/**
 * Handles Viewing of schedules
 *
 * @version 1.1
 */

public class ScheduleViewer {
    private UserAccount userAccount;
    private UseCaseStorage ucs;

    public ScheduleViewer(UserAccount userAccount, UseCaseStorage ucs) {
        this.userAccount = userAccount;
        this.ucs = ucs;
    }

    /**
     * Returns representation of the entire schedule
     * @throws DoesNotExistException
     */
    public void viewEntireSchedule() throws DoesNotExistException {
        System.out.println("The schedule: ");
        System.out.println(ucs.getRoomManager().displaySchedule(ucs.getTalkManager()));
    }

    /**
     * Displays a list of events with the given title or speaker name
     * @param searchType- Must be either "Title" or "Speaker Name", whichever type the user wants to search events by
     * @param searchValue- the title of an event, if searchType is "Title", or the name of a speaker, if searchType is "Speaker name"
     * @throws DoesNotExistException
     */
    public void viewEvents(String searchType, String searchValue) throws DoesNotExistException {
        if (searchType.equals("Title")) {
            ucs.getRoomManager().displayTalksByTitle(searchValue, ucs.getTalkManager());
        }
        if (searchType.equals("Speaker name")) {

            ucs.getRoomManager().displayTalksBySpeaker(ucs.getUserManager().findUser(searchValue).getUserID(),
                    ucs.getTalkManager(), ucs.getUserManager());
        }
    }

    /**
     * Displays a list of events on the given date at the given time
     * @param month - integer, searching for events in this month
     * @param day - integer, searching for events on this day
     * @param year - integer, searching for events on this year
     * @param hour - integer, searching for events at this hour
     * @param minute - integer, searching for events on this minute
     */
    public void viewEvents(int month, int day, int year, int hour, int minute) {
        GregorianCalendar date = new GregorianCalendar(year, month, day);
        LocalTime time = java.time.LocalTime.of(hour, minute);
        ucs.getRoomManager().displayTalksByTime(date, time, ucs.getTalkManager());
    }

    /**
     * Displays events that this user is registered for
     * @param ucs - UseCaseStorage
     * @throws DoesNotExistException
     * @return the list of events for an attendee
     */
    public ArrayList<String> viewMyEvents(UseCaseStorage ucs) throws DoesNotExistException, ObjectInvalidException {
        User user = ucs.getUserManager().findUser(userAccount.getUserID());
        for (String talkID : ucs.getAttributeChanger().getRegisteredTalks(userAccount.getUserID(), ucs.getUserManager())) {
            System.out.println(ucs.getTalkManager().findTalk(talkID));
        }
        return ucs.getAttributeChanger().getRegisteredTalks(userAccount.getUserID(), ucs.getUserManager());
    }

    /**
     * Displays events that are available for this user to register for
     * @throws DoesNotExistException
     * @return the available events in the schedule
     */
    public ArrayList<String> viewAvailableEvents() throws DoesNotExistException, ObjectInvalidException {
        ArrayList<Event> availableEvents = new EventSearch().getAllEvents(ucs.getTalkManager(),ucs.getRoomManager());
//        ArrayList<String> availableEvents = new ArrayList<String>();
        ArrayList<String> result = new ArrayList<>();
//        ArrayList<Entities.Room> rooms = ucs.getRoomManager().getRooms();
//        for (Entities.Room room : rooms) {
//            for (Map.Entry<GregorianCalendar, ArrayList<String>> eventsOnDate : room.getSchedule().entrySet()) {
//                for (String talkID : eventsOnDate.getValue()) {
//                    if (ucs.getRoomManager().addAttendee(talkID, userAccount.getUserID(), ucs.getUserManager(), ucs.getTalkManager())) {
//                        availableEvents.add(talkID);
//                    }
//                }
//            }
//        }
        for (Event talk : availableEvents) {
            result.add(talk.getTitle() + " - " +talk.getEventID());
        }
        return result;

    }
    /**
     * Return events that this speaker is giving
     * @param uas - UserAccountSystem
     * @return A list of events the user is speaking at
     * @throws DoesNotExistException
     */
    public ArrayList<String> getHostingTalks(UserAccountSystem uas) throws DoesNotExistException {
        EventSearch e = new EventSearch();
        ArrayList<Talk> hosting = e.getEventsBySpeaker(uas.getUserAccount().getUserID(),uas.getUseCaseStorage().getTalkManager(),uas.getUseCaseStorage().getRoomManager());
        ArrayList<String> result = new ArrayList<>();
        for (Talk t : hosting) {
            result.add(t.toString());
        }
        return result;
    }

    /**
     * Get a list of event names with no attendees
     * @return ArrayList containing the names of the found events
     */
     public ArrayList<String> getEmptyEventNames() {
        EventSearch es = new EventSearch();
        ArrayList<String> eventNames = es.getEmptyEvents(ucs.getTalkManager(), ucs.getRoomManager());
        return eventNames;
     }
}
