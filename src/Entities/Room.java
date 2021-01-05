package Entities;

import java.util.*;
import java.time.LocalTime;

/**
 * The room entity class, represents a room within our program
 * Rooms stores a schedule object which is a hashmap, where the keys are dates and the
 * values are an arraylist of talkIDs, where the talks are talks that have been successfully scheduled on that day.
 *
 * @version 2.0
 */

public class Room implements java.io.Serializable {
    private int capacity;
    private LocalTime openTime;
    private LocalTime closingTime;
    private String roomID;
    private String name;
    private HashMap<GregorianCalendar, ArrayList<String>> schedule;

    /**
     * Create a room with specified attributes.
     * @param openHour - int, hour that the room opens
     * @param openMinute - int, minute that the room opens (e.x, if the room opens at 9:30am, openHour == 9, openMinute == 30
     * @param closingHour - int, hour that the room closes
     * @param closingMinute - int, minute that the room closes
     * @param capacity - int, capacity of this room
     */
    public Room(int openHour, int openMinute, int closingHour, int closingMinute, String roomID, int capacity) {
        this.capacity = capacity;
        openTime = java.time.LocalTime.of(openHour, openMinute);
        closingTime = java.time.LocalTime.of(closingHour, closingMinute);
        this.roomID = roomID;
        schedule = new HashMap<GregorianCalendar, ArrayList<String>>();
        // initializes name as the same as the room id
        this.name = this.roomID;
    }

    /**
     * Create a room with specified attributes
     * @param openHour - int, hour that the room opens
     * @param openMinute - int, minute that the room opens (e.x, if the room opens at 9:30am, openHour == 9, openMinute == 30
     * @param closingHour - int, hour that the room closes
     * @param closingMinute - int, minute that the room closes
     * @param capacity - int, capacity of this room
     * @param name - String, name of this room
     */
    public Room(int openHour, int openMinute, int closingHour, int closingMinute, String roomID, int capacity, String name) {
        this.capacity = capacity;
        openTime = java.time.LocalTime.of(openHour, openMinute);
        closingTime = java.time.LocalTime.of(closingHour, closingMinute);
        this.roomID = roomID;
        schedule = new HashMap<GregorianCalendar, ArrayList<String>>();
        this.name = name;
    }

    /**
     * Returns schedule of talks for this room
     * @return Schedule object
     */
    public HashMap<GregorianCalendar, ArrayList<String>> getSchedule(){
        return schedule;
    }

    /**
     * Return's capacity for this room
     * @return int representing capacity for this room
     */
    public int getCapacity(){
        return capacity;
    }

    /**
     * Sets room capacity equal to newCapacity
     * @param newCapacity - int capacity to set room to
     */
    public void setCapacity(int newCapacity){
        capacity = newCapacity;
    }

    /**
     * Gets the opening time of the room
     * @return LocalTime representing the time this room opens
     */
    public LocalTime getOpenTime(){
        return openTime;
    }

    /**
     * Gets the closing time of the room
     * @return LocalTime representing the time this room closes
     */
    public LocalTime getClosingTime() {return closingTime; }

    /**
     * Sets a new opening time for this room
     * @param openHour - int, new opening hour to be set for the room
     * @param openMinute - int, new opening minute to be set for the room
     */
    public void setOpenTime(int openHour, int openMinute) {
        LocalTime openTime = java.time.LocalTime.of(openHour, openMinute);
    }

    /**
     * Sets a new closing time for this room
     * @param closingHour - int, new closing hour to be set for the room
     * @param closingMinute - int, new closing minute to be set for the room
     */

    public void setClosingTime(int closingHour, int closingMinute){
        LocalTime closingTime = java.time.LocalTime.of(closingHour, closingMinute);
    }

    /**
     * Gets ID of room
     * @return int representing the room's ID
     */
    public String getRoomID(){
        return roomID;
    }

    /**
     * Gets name of room
     * @return the room name
     */
    public String getName() {
        return this.name;
    }

    /**
     * Sets the name of the room
     * @param newName new name of the room
     */
    public void setName(String newName) {
        this.name = newName;
    }
    /**
     * Returns list of talks that still have capacity
     * @param events - an ArrayList of talks to be checked for capacity
     * @return ArrayList</Entities.Talk> - subset of events containing those with capacity
     */
    public ArrayList<Talk> getEventsWithCapacity(ArrayList<Talk> events){
        ArrayList<Talk> eventsWithCapacity = new ArrayList<Talk>();
        for (Talk talk: events){
            if (talk.getAttendees().size() <= capacity){
                eventsWithCapacity.add(talk);
            }
        }
        return eventsWithCapacity;
    }


    /**
     * Add talk to talks in this schedule on the date stored in talk
     * @param talk - Entities.Talk to be added
     */
    public void addEvent(Event talk){
        if (schedule.containsKey(talk.getDate())){
            schedule.get(talk.getDate()).add(talk.getEventID());
        } else {
            schedule.put(talk.getDate(), new ArrayList<String>());
            schedule.get(talk.getDate()).add(talk.getEventID());
        }

    }

    /**
     * Attempt to remove talk from schedule
     * @param talk - Entities.Talk to be removed
     * @return true if and only if Entities.Talk was found in this Schedule and removed
     */
    public boolean removeEvent(Event talk){
        if (schedule.containsKey(talk.getDate()) & schedule.get(talk.getDate()).contains(talk.getEventID())){
            schedule.get(talk.getDate()).remove(talk.getEventID());
            return true;
        }
        return false;
    }

    /**
     * Check if talk is in schedule
     * @param talkID - ID of talk to be found
     * @return true if and only if talk is in this Schedule
     */
    public boolean isTalkScheduled(String talkID) {
        for (Map.Entry<GregorianCalendar, ArrayList<String>> talksOnDate : schedule.entrySet()) {
            for (String ID : talksOnDate.getValue()) {
                if (ID.equals(talkID)) {
                    return true;
                }
            }
        }
        return false;
    }



}