package UseCases;

import Entities.*;
import Exceptions.*;
import sun.util.calendar.Gregorian;

import java.lang.reflect.Array;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.UUID;

/**
 * Representation of a roomManager, or a manager which stores all rooms
 * Each room in the list of rooms stores a schedule object which is a hashmap, where the keys are dates and the
 * values are an arraylist of talkIDs, where the talks are talks that have been successfully scheduled on that day.
 * @version 2.0
 */

public class RoomManager implements java.io.Serializable {
    private ArrayList<Room> rooms;

    public RoomManager(){
        rooms = new ArrayList<>();
    }

    /**
     * Creates a new room with a corresponding schedule and adds them to the list of rooms respectively
     * @param openHour - int, hour that the room opens
     * @param openMinute - int, minute that the room opens (e.x, if the room opens at 9:30am, openHour == 9, openMinute == 30
     * @param closingHour - int, hour that the room closes
     * @param closingMinute - int, minute that the room closes
     * @param capacity - int, maximum number of attendees that can be in the room
     */
    public void addRoom(int openHour, int openMinute, int closingHour, int closingMinute, int capacity){
        UUID id = UUID.randomUUID();
        String roomID = "Room " + id.toString();
        Room room = new Room(openHour, openMinute, closingHour, closingMinute, roomID, capacity);
        rooms.add(room);
    }

    /**
     * Creates a new room with a corresponding schedule and adds them to the list of rooms respectively
     * @param openHour - int, hour that the room opens
     * @param openMinute - int, minute that the room opens (e.x, if the room opens at 9:30am, openHour == 9, openMinute == 30
     * @param closingHour - int, hour that the room closes
     * @param closingMinute - int, minute that the room closes
     * @param capacity - int, maximum number of attendees that can be in the room
     * @param name - String, name of the room
     */
    public void addRoom(int openHour, int openMinute, int closingHour, int closingMinute, int capacity, String name){
        UUID id = UUID.randomUUID();
        String roomID = "Room " + id.toString();
        Room room = new Room(openHour, openMinute, closingHour, closingMinute, roomID, capacity, name);
        rooms.add(room);
    }

    /**
     * Creates a new room with a corresponding schedule and adds them to the list of rooms respectively, with the given roomID
     * @param openHour - int, hour that the room opens
     * @param openMinute - int, minute that the room opens (e.x, if the room opens at 9:30am, openHour == 9, openMinute == 30
     * @param closingHour - int, hour that the room closes
     * @param closingMinute - int, minute that the room closes
     * @param capacity - int, maximum number of attendees that can be in the room
     * @param roomID - String, RoomID of the form "Room xxx...xxx", where xxx,...xxx, is a universally unique identifier
     */
    public void addRoomByID(int openHour, int openMinute, int closingHour, int closingMinute, int capacity, String roomID){
        Room room = new Room(openHour, openMinute, closingHour, closingMinute, roomID, capacity);
        rooms.add(room);
    }

    /**
     * Takes in a room name and returns the corresponding room's ID if it exists
     * @param name String, name of the room whose ID to be found
     * @return the ID of the room with the given name, if it exists
     * @throws DoesNotExistException if there is no room with the inputted name
     */
    public String findRoomID(String name) throws DoesNotExistException {
        for (Room room: rooms){
            if (room.getName().equals(name)){
                return room.getRoomID();
            }
        }
        throw new DoesNotExistException("There is no room with this name");
    }

    /**
     * Returns a list of all the names of rooms stored in this RoomManager
     * @return ArrayList of Strings, the names of rooms in the RoomManager
     */
    public ArrayList<String> getRoomNames(){
        ArrayList<String> roomNames = new ArrayList<>();
        for (Room room: rooms){
            roomNames.add(room.getName());
        }
        return roomNames;
    }

    /**
     * Attempts to add attendee to list of registered attendees in talk, returns true iff successful
     * @param eventName - name of event to add attendee to
     * @param attendeeID -  ID of attendee to be added
     * @param um - UseCases.UserManager object storing attendees
     * @param tm - UseCases.TalkManager object storing talks
     * @return true - if and only if attendee was successfully added
     */

    public boolean addAttendee(String eventName, String attendeeID, UserManager um, TalkManager tm) throws DoesNotExistException{
        EventSearch es = new EventSearch();
        ArrayList<Event> events = es.getEventsByTitle(eventName, tm, this);
        if (events.size() > 1){
            System.out.println("This is not a unique title");
            return false;
        }
        Event event = events.get(0);
        String talkID = event.getEventID();
        AvailabilityChecker ac = new AvailabilityChecker();
        for (Room room: rooms){
            User attendee = um.findUser(attendeeID);
            GregorianCalendar date = event.getDate();
            LocalTime time = event.getStartTime();
            int duration = event.getDuration();
            if (room.isTalkScheduled(talkID) & event.getAttendees().size() < room.getCapacity() &
                    ac.isUserAvailable(date, time, duration, attendee, tm) & event.getNumberOfFreeSpots() >= 1){
                return event.addAttendee(attendeeID);
            }
        }
        return false;
    }


    /**
     * Attempts to add event to schedule
     * If event is a party, we check if the room is available and if so, we add event to schedule
     * If event is a talk, we also check if its speakers are available and if they all are, we add event to schedule
     * Call this method for any type of event
     * @param room - room to add talk to
     * @param eventID - ID of talk to be added
     * @param tm - UseCases.TalkManager object storing talks
     * @param um - UserManager object storing users
     * @return true - if and only if room was added to schedule successfully
     */


    public boolean addEventToSchedule(Room room, String eventID, TalkManager tm, UserManager um) throws DoesNotExistException {
        Event event = tm.findTalk(eventID);
        AvailabilityChecker ac = new AvailabilityChecker();
        if (!ac.isRoomAvailable(event, room, tm)){
            return false;
                }
        if (event instanceof Talk) {
            for (String speakerID : event.getSpeakers()) {
                if (!ac.isSpeakerAvailable((Talk) event, (Speaker) um.findUser(speakerID), tm)) {
                    return false;
                }
            }
        }
        room.addEvent(event);
        return true;
        }


    /**
     * Attempts to remove event from schedule, returns true iff successful
     * Call this method regardless of the type of event organizer wishes to remove
     * organizer must be in the events list of organizers in order to call this method - verify in controller
     * @param talkID - String, ID of talk to be removed
     * @param tm - UseCases.TalkManager object storing talks
     * @param um - UseCases.UserManager object storing attendees
     * @return true - if and only if talk was found in the schedule and successfully removed
     */

    public boolean removeEventFromSchedule(String talkID, TalkManager tm, UserManager um) throws DoesNotExistException {
        Event event = tm.findTalk(talkID);
        for (Room room : rooms) {
            if (room.isTalkScheduled(talkID)) {
                room.removeEvent(event);
                for (String attendeeID : event.getAttendees()) {
                    User user = um.findUser(attendeeID);
                    user.removeRegisteredEvent(event.getEventID());
                    if (event instanceof Talk) {
                        for (String speakerID : event.getSpeakers()) {
                            Speaker speaker = (Speaker) um.findUser(speakerID);
                            speaker.removeHostEvent(talkID);
                        }
                    }
                }
            }
            return true;
        }
        return false;
    }

    /**
     * Prints talks in a schedule with a given title
     * @param title - String, title to be checked
     * @param tm - UseCases.TalkManager object storing talks
     */
    public void displayTalksByTitle(String title, TalkManager tm) {
        EventSearch es = new EventSearch();
        ArrayList<Event> allTalksWithTitle = new ArrayList<>(es.getEventsByTitle(title, tm, this));
        for (Event talkWithTitle: allTalksWithTitle){
            System.out.println(talkWithTitle);
        }
    }

    /**
     * Prints talks in a schedule with a given Entities.Speaker
     * @param speakerID - String, the ID of the speaker whose talks to be found
     * @param tm - UseCases.TalkManager object storing talks
     */
    public void displayTalksBySpeaker(String speakerID, TalkManager tm, UserManager um) {
        EventSearch es = new EventSearch();
        ArrayList<Talk> allTalksBySpeaker = new ArrayList<>(es.getEventsBySpeaker(speakerID, tm, this));
        for (Talk talkBySpeaker: allTalksBySpeaker){
            System.out.println(talkBySpeaker);
        }
    }

    /**
     * Prints talks scheduled on a given date and time
     * @param date - Date object, date to be checked
     * @param time - LocalTime object, time to be checked
     * @param tm - UseCases.TalkManager object that stores all talks
     */

    public void displayTalksByTime(GregorianCalendar date, LocalTime time, TalkManager tm) {
        EventSearch es = new EventSearch();
        ArrayList<Event> allTalksAtTime = es.getEventsByTime(date, time, tm, this);
        for (Event talkAtTime: allTalksAtTime){
            System.out.println(talkAtTime);
        }
    }

    /**
     * Displays entire schedule
     * @param tm - UseCases.TalkManager object which stores talks
     */
    public String displaySchedule(TalkManager tm) throws DoesNotExistException {
        StringBuilder fullSchedule = new StringBuilder();
        for (Room room : rooms) {
            StringBuilder scheduleString = new StringBuilder("Schedule for " + room.getRoomID() + ": ");
            for (Map.Entry<GregorianCalendar, ArrayList<String>> talksOnDate : room.getSchedule().entrySet()) {
                StringBuilder talksString = new StringBuilder("{");
                for (String talkID : talksOnDate.getValue()) {
                    Event talk = tm.findTalk(talkID);
                    talksString.append(talk.toString()).append(", ");
                }
                talksString = new StringBuilder(talksString.substring(0, talksString.length() - 2));
                talksString.append("}");
                scheduleString.append(talksOnDate.getKey().toZonedDateTime().format(DateTimeFormatter.ofPattern("d MMM uuuu"))).append(": ").append(talksString).append("\n");
            }
            fullSchedule.append(scheduleString);
        }
        return fullSchedule.toString();
    }

    /**
     * @param tm - TalkManager object that stores talks
     * @return a string of the occupied times of all rooms for any given day of the conference.
     * i.e. "Room <uuid> opens at <time> and closes at <time>. The capacity is <maxLimit>.
     *      <date>: The room is currently booked from <time> to <time>, <time> to <time>, ...
     *      <date>: ...
     *
     *      Room <uuid> ...
     *      ...
     *      "
     */
    public String displayOccupiedTimes(TalkManager tm) throws DoesNotExistException {
        StringBuilder occupied = new StringBuilder();
        for (Room room : rooms) {
            occupied.append(room.getRoomID()).append(" opens at ").append(room.getOpenTime().toString()).append(" and closes at ").append(room.getClosingTime().toString()).append(". The capacity is ").append(room.getCapacity()).append(". \n");
            for (Map.Entry<GregorianCalendar, ArrayList<String>> talksOnDate : room.getSchedule().entrySet()) {
                GregorianCalendar date = talksOnDate.getKey();
                ArrayList<LocalTime> occupiedTimes = new ArrayList<>();
                HashMap<LocalTime, Integer> startAndDuration = new HashMap<>();
                for (String talkID : talksOnDate.getValue()) {
                    Event event = tm.findTalk(talkID);
                    startAndDuration.put(event.getStartTime(), event.getDuration());
                    orderTimesHelper(occupiedTimes, event);
                }
                occupied.append(occupiedAtHelper(occupiedTimes, date, room, startAndDuration)).append("\n");
            }
        }
        if (occupied.toString().equals("")) {
            return "There is no room open at the conference yet.\n";
        } else {
            return occupied.toString();
        }
    }

    /**
     * A helper function for displayOccupiedTimes
     * @param occupiedTimes Times it is occupied
     * @param event the event in question
     */

    private void orderTimesHelper(ArrayList<LocalTime> occupiedTimes, Event event) {
        LocalTime eventStartTime = event.getStartTime();
        int index = 0;
        boolean searching = true;
        while (index < occupiedTimes.size() && searching) {
            LocalTime timeToCompare = occupiedTimes.get(index);
            if (eventStartTime.compareTo(timeToCompare) < 0) {
                searching = false;
            }
            if (searching) {
                index++;
            }
        }
        occupiedTimes.add(index, eventStartTime);
    }

    /**
     * A helper for displayedOccupiedTimes
     * @param occupiedTimes times at which it is occupied
     * @param date the date
     * @param room the room of the event
     * @param startToDuration the start to duration timespan
     * @return When room is occupied at
     */
    private String occupiedAtHelper(ArrayList<LocalTime> occupiedTimes, GregorianCalendar date, Room room,
                                    HashMap<LocalTime, Integer> startToDuration) {
        StringBuilder occupiedAt = new StringBuilder(date.toZonedDateTime().format(DateTimeFormatter.ofPattern("d MMM uuuu")) + ": ");
        occupiedAt.append("The room is currently booked from ");
        for (LocalTime time : occupiedTimes) {
            LocalTime startPlusDuration = time.plusMinutes(startToDuration.get(time));
            occupiedAt.append(time.toString()).append(" to ").append(startPlusDuration.toString());
            LocalTime lastTime = occupiedTimes.get(occupiedTimes.size() - 1);
            if (time.equals(lastTime)) {
                occupiedAt.append(". \n");
            } else {
                occupiedAt.append(", ");
            }
        }
        return occupiedAt.toString();
    }

    /**
     *
     * @return list of all rooms
     */
    public ArrayList<Room> getRooms(){return rooms;}

    /**
     *
     * @return all of the room IDs in the system
     */
    public ArrayList<String> getAllRooms() {
        ArrayList<String> ts = new ArrayList<>();
        for (Room t : this.rooms){
            ts.add(t.getRoomID());
        }
        return ts;
    }

    /**
     * Find and return the room with ID given, if it exists
     * @param ID - String, unique ID of room we wish to find
     * @return Room object, room with identifier ID
     * @throws DoesNotExistException - throws DoesNotExist exception if there is no Room object with given ID
     */
    public Room findRoom(String ID) throws DoesNotExistException {
        for (Room room : rooms) {
            if (room.getRoomID().equals(ID)) {
                return room;
            }
        }
        throw new DoesNotExistException("This ID does not exist");
    }

    /**
     * Returns the capaciy of a room
     * @param id String, id of the room to get the capacity of
     * @return that Room's capacity
     * @throws DoesNotExistException if there is no Room object with given ID
     */
    public int getRoomCapacity(String id) throws DoesNotExistException {
        return findRoom(id).getCapacity();
    }
}
