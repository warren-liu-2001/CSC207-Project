package Presenters;

import Controllers.UseCaseStorage;
import Entities.User;
import Exceptions.DoesNotExistException;
import Exceptions.ObjectInvalidException;
import UseCases.*;
import org.w3c.dom.Attr;

import java.util.ArrayList;

public class DisplayInfo {

    /**
     * Format a list of user information formatted as "name - email"
     * @param names list of names to format
     * @param emails list of emails to format
     * @return ArrayList of formatted user info
     */
    public ArrayList<String> combineUsersInfo(ArrayList<String> names, ArrayList<String> emails) {
        ArrayList<String> result= new ArrayList<>();
        for(int i=0; i< names.size(); i++){
            result.add(names.get(i) + " - " + emails.get(i));
        }
        return result;
    }

    /**
     * Print value of given attribute.
     * @param attribute Name of attribute to print
     * @param value Value to print
     */
    public void printUserAttribute(String attribute, String value) {
        System.out.println("Your " + attribute + ": " + value);
    }

    /**
     * Print a list of values of given attribute
     * @param attribute Name of attribute to print
     * @param values List of values to print
     */
    public void printUserAttribute(String attribute, String[] values) {
        System.out.println("Your " + attribute + ": ");
        for (String value : values) {
            System.out.println(value);
        }
    }

    /**
     * Print ArrayList of values of given attribute
     * @param attribute Name of attribute to print
     * @param values ArrayList of values to print
     */
    public void printUserAttribute(String attribute, ArrayList<String> values) {
        printUserAttribute(attribute, (String[]) values.toArray());
    }

    /**
     * Prints a generic action successful message
     * @param action action to print
     */
    public void actionSuccessful(String action) {
        System.out.println("The " + action + " was successful.");
    }

    /**
     * Print a list of rooms
     * @param rooms list of rooms to print
     */
    public void printRooms(String[] rooms) {
        System.out.println("Rooms:");
        for (String room : rooms) {
            System.out.println(room);
        }
    }

    /**
     * Print an ArrayList of rooms
     * @param rooms ArrayList of rooms to print
     */
    public void printRooms(ArrayList<String> rooms) {
        this.printRooms((String[]) rooms.toArray());
    }

    /**
     * Print a list of messages
     * @param messages list of messages to print
     */
    public void printMessages(ArrayList<String> messages) {
        System.out.println("Here are your messages: ");
        for (String message : messages) {
            System.out.println(message);
        }
    }

    /**
     * Print a list of available list of times an event can be scheduled.
     * @param talkSlots
     */
    // using the printOccupiedTimes method below instead
    public void printAvailableTalkSlots(ArrayList<String> talkSlots) {
        System.out.println("Timeslots in which a talk is able to be scheduled: ");
        for (String talkSlot : talkSlots) {
            System.out.println(talkSlot);
        }
    }

    /**
     * Print a list of times that an event has already been scheduled in
     * @param rm RoomManager containing information about the schedules
     * @param tm TalkManager containing information about the events
     * @throws DoesNotExistException
     */
    public void printOccupiedTimes(RoomManager rm, TalkManager tm) throws DoesNotExistException {
        System.out.println(rm.displayOccupiedTimes(tm));
    }

    /**
     * Gets a list of User emails
     * @param um UserManager containing the user info
     * @return ArrayList of emails
     */
    public ArrayList<String> getUserEmails(UserManager um) {
        ArrayList<String> emailList = um.getUserEmails();
        if (emailList == null) {
            emailList = new ArrayList<>();
        }
        return emailList;
    }

    /**
     * Gets a list of message senders delivered to a user
     * @param im InboxManager containing the message information
     * @param id ID of the recipient
     * @return ArrayList of message senders
     */
    public ArrayList<String> getMessageSenders(InboxManager im, String id) {
        return im.getMessageSenders(id);
    }

    /**
     * Gets a list of message subjects delivered to a user
     * @param im InboxManager containing the message information
     * @param id ID of the recipient
     * @return ArrayList of message subjects
     */
    public ArrayList<String> getMessageSubjects(InboxManager im, String id) {
        return im.getMessageSubjects(id);
    }

    /**
     * Gets a list of message contents delivered to a user
     * @param im InboxManager containing the message information
     * @param id ID of the recipient
     * @return ArrayList of message contents
     */
    public ArrayList<String> getMessageContents(InboxManager im, String id) {
        return im.getMessageContents(id);
    }

    /**
     * Gets the user ID associated with given email
     * @param um UserManager containing the user information
     * @param email email of the user
     * @return User ID associated with the email
     */
    public String getIDByEmail(UserManager um, String email) throws DoesNotExistException, ObjectInvalidException {
        AttributeChanger ac = new AttributeChanger();
        return ac.getUserID(email, um);
    }

}
