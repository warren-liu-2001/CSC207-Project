package Controllers;


import Entities.Talk;
import UseCases.*;
import Exceptions.*;


import java.util.ArrayList;


/**
 * A controller for the access of the User's account to perform needed functions
 *
 * @version 2.0
 */
public class UserAccount {

    // Instance variables
    private String userID;
    private UseCaseStorage useCaseStorage;
    private UserManager um;
    private AttributeChanger ac;
    private EventSearch es;

    private Boolean isAttendee;
    private Boolean isOrganizer;
    private Boolean isSpeaker;
    private Boolean isAdmin;

    /**
     * Initialize a new UserAccount.
     * @param useCases takes a UseCaseStorage object and stores it.
     */

    public UserAccount(UseCaseStorage useCases) {
        this.useCaseStorage = useCases;
        this.um = this.useCaseStorage.getUserManager();
        this.ac = this.useCaseStorage.getAttributeChanger();
        this.es = new EventSearch();

        this.isOrganizer = false;
        this.isSpeaker = false;
        this.isAttendee = false;
        this.isAdmin = false;
    }

    // Getters and Setters

    /**
     * Get if the account associated with this userID refers to an Organizer.
     * @return if the current accessed account is an organizer
     */
    public Boolean isOrganizer() {
        return this.isOrganizer;
    }

    /**
     * Get if the account associated with this userID refers to an Speaker.
     * @return if the current accessed account is a speaker
     */
    public Boolean isSpeaker() {
        return this.isSpeaker;
    }

    /**
     * Get if the account associated with this userID refers to an Attendee.
     * @return if the current accessed account is an attendee
     */

    public Boolean isAttendee() {
        return this.isAttendee;
    }


    public Boolean isAdmin() {
        return this.isAdmin;
    }

    /**
     * Getter for the user's id.
     * @return The user's id.
     */
    public String getUserID() {
        return this.userID;
    }

    /**
     * Changes the user's email.
     * @param newEmail The new email to change to.
     * @return if email was successfully set
     */
    public Boolean setEmail(String newEmail) {
        try {
            this.ac.setUserEmail(this.getUserID(), newEmail, this.um);
            return true;
        } catch (DoesNotExistException | ObjectInvalidException e) {
            return false;
        }
    }

    /**
     * Changes the user's password
     * @param newPassword The new password to change to.
     * @return if password was successfully set
     */
    public Boolean setPassword(String newPassword) {
        try {
            this.ac.setUserPassword(this.getUserID(), newPassword, this.um);
            return true;
        } catch (DoesNotExistException | ObjectInvalidException e) {
            return false;
        }
    }

    /**
     * Changes the user's name
     * @param newName the new name of the attendee
     * @return if name was successfully set
     */

    public Boolean setName(String newName) throws DoesNotExistException {
        try {
            this.ac.setUserName(this.getUserID(), newName, this.um);
            return true;
        } catch (DoesNotExistException | ObjectInvalidException e) {
            return false;
        }
    }

    // Login System

    /**
     * Log in to an Attendee/Organizer/Speaker account
     * @param email Email of the account.
     * @param name Name of the account.
     * @param password Password of the account.
     * @return Whether the login was successful or not.
     */
    public Boolean login(String email, String name, String password) {
        try {
            // First find the id of the user
            String userID = this.ac.getUserID(email, this.um);


            // Check if the name and password match (we already know email matches from prev line)
            if (this.ac.getUserName(userID, this.um).equals(name) && this.ac.getUserPassword(userID, this.um).equals(password)) {

                // login successful
                this.userID = userID;

                // check type
                if (this.ac.getType(userID, this.um).equals(UserType.ATTENDEE)) {
                    this.isAttendee = true;
                } else if (this.ac.getType(userID, this.um).equals(UserType.SPEAKER)) {
                    this.isSpeaker = true;
                } else if (this.ac.getType(userID, this.um).equals(UserType.ORGANIZER)) {
                    this.isOrganizer = true;
                } else if (this.ac.getType(userID, this.um).equals(UserType.ADMIN)) {
                    this.isAdmin = true;
                }
                return true;
            } else {
                return false;
            }
        } catch (DoesNotExistException | ObjectInvalidException e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * Create and login to a new Attendee account with the specified login details. 1234 is the password
     * for the Attendee.
     * @param email the email of the attendee
     * @param name the name of the attendee
     * @throws DoesNotExistException if downstream there is an error
     */


    public Boolean registerAttendee(String email, String name) throws DoesNotExistException {
        return this.registerAttendee(email, name, "1234"); // Using 1234 as a default pass
    }

    /**
     * Create and login to a new Attendee account with the specified login details.
     * @param email the email
     * @param name the name
     * @param password password
     * @throws DoesNotExistException if login throws an error
     */

    public Boolean registerAttendee(String email, String name, String password) throws DoesNotExistException {
        // check if the name or email is already in use
        if (checkDuplicateEmail(email) || checkDuplicateName(name)) {
            return false;
        }
        // Create the new Attendee
        this.um.setEmail(email);
        this.um.setName(name);
        this.um.setPassword(password);
        this.um.createUser(UserType.ATTENDEE);

        // Login to this newly created account
        return this.login(email, name, password);
    }

    /**
     * Create and login to a new Organizer account with the specified login details. 1234 is the password
     * for the Organizer.
     * @param email email of organizer
     * @param name the name
     * @throws DoesNotExistException if login fails
    */
    public Boolean registerOrganizer(String email, String name) throws DoesNotExistException {
        return this.registerOrganizer(email, name, "1234"); // Using 1234 as a default pass
    }

    /**
     * Create and login to a new Organizer account with the specified login details.
     * @param password the password of the organizer
     * @param email email of organizer
     * @param name the name
     * @throws DoesNotExistException if login fails
     */

    public Boolean registerOrganizer(String email, String name, String password) throws DoesNotExistException {
        // check if the name or email is already in use
        if (checkDuplicateEmail(email) || checkDuplicateName(name)) {
            return false;
        }
        // Create the new Organizer
        this.um.setEmail(email);
        this.um.setName(name);
        this.um.setPassword(password);
        this.um.createUser(UserType.ORGANIZER);

        // Login to this newly created account
        return this.login(email, name, password);
    }

    /**
     * logs out of current account
     */
    // Log out of the current account
    public void logout() {
        this.userID = "";
        this.isSpeaker = false;
        this.isOrganizer = false;
        this.isAttendee = false;
        this.isAdmin = false;
    }

    // Signup system

    /**
     *
     * @param talkID talk the guy wants to register for
     * @param registrar the registrar system
     * @return whether it has been registered
     */
    public Boolean register(String talkID, Registrar registrar) {
        // Only Attendees can register for events
        try {
            if (this.isAttendee()) {
                registrar.register(talkID, this.userID);
                return true;
            }
            return false;
        } catch (DoesNotExistException | ObjectInvalidException e) {
            return false;
        }
    }

    /**
     * @return User Manager
     */
    public UserManager getUm() {
        return um;
    }

    /**
     * @return Attribute Changer
     */
    public AttributeChanger getAc() {
        return ac;
    }

    /**
     * Try to cancel an Attendee's spot at a talk
     * @param talkID ID of talk they want to deregister from
     * @param registrar registrar object used to deregister
     * @return whether the Attendee's spot was cancelled
     * @throws DoesNotExistException if Attendee is not registered for the talk
     */
    public Boolean cancel(String talkID, Registrar registrar) throws DoesNotExistException {
        // Only Attendees can cancel their spots in events
        try {
            if (this.isAttendee()) {
                registrar.cancel(this.userID, talkID);
                return true;
            }
            return false;
        } catch (DoesNotExistException | ObjectInvalidException e) {
            return false;
        }
    }

    // Messaging System

    /**
     * Send a message to a single attendee
     * @param subject the subject of message
     * @param content the content of message
     * @param recipientEmail the recipient's email
     * @param messenger the messenger controller in charge
     * @return whether message was sent
     */
    public Boolean sendMessage(String subject, String content, String recipientEmail, Messenger messenger) {
        try {
            messenger.sendPrivateMessage(content, this.getUserID(), ac.getUserID(recipientEmail, this.um), subject);
            return true;
        } catch (DoesNotExistException | ObjectInvalidException e) {
            return false;
        }
    }


    /**
     * Send a message as a speaker to all attendees at a talk. Use messageEvent instead.
     * @param talkID talk a speaker is at
     * @param subject the subject
     * @param content the content of the message
     * @param messenger the messenger controller in charge
     * @return whether message was sent to all attendees
     */
    // speaker messages all at a talk they speak at
    public Boolean messageTalk(String talkID, String subject, String content, Messenger messenger){

        // Only Speakers can send a message to everyone attending a talk
        if (this.isSpeaker()) {

            try {
                // Check if they actually speak at that talk
                RoomManager rm = this.useCaseStorage.getRoomManager();
                TalkManager tm = this.useCaseStorage.getTalkManager();
                ArrayList talkList = this.es.getEventsBySpeaker(this.getUserID(), tm, rm);

                if (talkList.contains(tm.findTalk(talkID))) {

                    // Yes they do, so send a message
                    messenger.sendMassMessage(this.getUserID(), subject, content, (Talk) tm.findTalk(talkID));
                    return true;
                } else {
                    return false;
                }
            } catch (DoesNotExistException e) {
                return false;
            }
        } else {
            // Not a speaker
            return false;
        }
    }

    /**
     * As a speaker, message all Attendees at an event you speak at.
     * @param eventName event to send a message to
     * @param subject the subject
     * @param content the content of the message
     * @param messenger the messenger controller in charge
     * @return whether message was sent to all attendees
     */
    public Boolean messageEventByName(String eventName, String subject, String content, Messenger messenger){

        // Only Speakers can send a message to everyone attending an event
        if (this.isSpeaker()) {

            try {
                // Check if they actually speak at that event
                RoomManager rm = this.useCaseStorage.getRoomManager();
                TalkManager tm = this.useCaseStorage.getTalkManager();
                ArrayList talkList = this.es.getEventsBySpeaker(this.getUserID(), tm, rm);

                if (talkList.contains(tm.findTalkByTitle(eventName))) {

                    // Yes they do, so send a message
                    messenger.sendMassMessage(this.getUserID(), subject, content, tm.findTalkByTitle(eventName));
                    return true;
                } else {
                    return false;
                }
            } catch (DoesNotExistException e) {
                return false;
            }
        } else {
            // Not a speaker
            return false;
        }
    }

    /**
     * Send a message to all events a speaker speaks at
     * @param talkList list of talks at which the speaker is speaking at
     * @param subject subject of message
     * @param content content of message
     * @param messenger the messenger controller
     * @return whether message was sent to all rooms
     */
    // A speaker can message multiple talks that they speak at
    public Boolean messageMultipleTalks (ArrayList<String> talkList, String subject, String content,
                                         Messenger messenger) {
        if (this.isSpeaker()) {
            Boolean fullySuccessful = true;
            for (String talkID : talkList) {
                if (!messageTalk(talkID, subject, content, messenger)) {
                    fullySuccessful = false;
                }
            }

            return fullySuccessful;
        } else {
            return false;
        }
    }


    /**
     * Message all attendees as an organizer
     * @param subject the subject of message
     * @param content the content
     * @param messenger messenger controller
     * @return whether it was sent to all attendees at the conference
     */
    // Organizer messages all attendees at the conference
    public Boolean messageAllAttendees(String subject, String content, Messenger messenger) {

        // Only organizers can message everyone
        if (this.isOrganizer()) {
            try {
                messenger.sendMassMessage(this.getUserID(), subject, content, "ATTENDEE");
                return true;
            } catch (DoesNotExistException e) {
                return false;
            }
        } else {
            // Not an organizer
            return false;
        }
    }

    /**
     * Organizer messages all speakers at a conference
     * @param subject the subject of a message
     * @param content the content of a message
     * @param messenger the messenger controller
     * @return whether it was sent or not
     */
    // Organizer messages all speakers at the conference
    public Boolean messageAllSpeakers(String subject, String content, Messenger messenger) {

        // Only organizers can message all speakers
        if (this.isOrganizer()) {
            try {
                messenger.sendMassMessage(this.getUserID(), subject, content, "SPEAKER");
                return true;
            } catch (DoesNotExistException e) {
                return false;
            }
        } else {
            return false;
        }
    }
    /**
     * The User adds an Attendee as a friend, like Salman!
     * @param friendEmail email of the Entities.Attendee to add as a friend.
     * @return Whether adding a friend was successful or not
     * @throws DoesNotExistException If the friend does not exist. :(
     */
    public Boolean addFriend(String friendEmail) {
        // You cannot be your own friend. Sadge
        try {
            if (this.ac.getUserEmail(this.getUserID(), this.um).equals(friendEmail)) {
                return false;
            }
        } catch (DoesNotExistException | ObjectInvalidException e) {
            return false;
        }

        // Try to add friend
        try {
            this.ac.addContact(this.getUserID(), this.ac.getUserID(friendEmail, this.um), this.um);
            return true;
        } catch (DoesNotExistException | ObjectInvalidException e) {
            return false;
        }
    }

    /**
     * Remove a friend. For example, if your friendship with Mudasir was ended, you would use this.
     * @param removeEmail Email of the friend to be removed
     * @return whether the friend has been removed
     */

    public Boolean removeFriend(String removeEmail) {
        try {
            this.ac.removeContact(this.getUserID(), ac.getUserID(removeEmail, this.um), this.um);
            return true;
        } catch (DoesNotExistException | ObjectInvalidException e) {
            return false;
        }
    }

    /**
     * Create a room as an organizer
     * @param openHour when the room opens in terms of the hour
     * @param openMinute the minute at which the room opens
     * @param closingHour hour at which the room closes
     * @param closingMinute the minute at which the room closes
     * @return if room was created
     */

    public Boolean createRoom(int openHour, int openMinute, int closingHour, int closingMinute, int capacity) {
        // Only Organizers can create rooms
        if (this.isOrganizer()) {
            // All talks must take place between 9 am - 5 pm (hrs 9~17)
            if (openHour >= 9 && openHour <= 17 && closingHour >= 9 && closingHour <= 17 &&
                    openHour < closingHour) {
                this.useCaseStorage.getRoomManager().addRoom(openHour, openMinute, closingHour, closingMinute, capacity);
                return true;
            }
        }
        return false;
    }

    /**
     * Create a room as an organizer
     * @param openHour when the room opens in terms of the hour
     * @param openMinute the minute at which the room opens
     * @param closingHour hour at which the room closes
     * @param closingMinute the minute at which the room closes
     * @param capacity attendee limit of this room
     * @param name name of the room
     * @return if room was created
     */

    public Boolean createRoom(int openHour, int openMinute, int closingHour, int closingMinute, int capacity, String name) {

        // room names need to be unique
        if (checkDuplicateRoomName(name)) {
            return false;
        }
        // Only Organizers can create rooms
        if (this.isOrganizer()) {
            this.useCaseStorage.getRoomManager().addRoom(openHour, openMinute, closingHour, closingMinute, capacity, name);
            return true;
        }
        return false;
    }

    /**
     * As an organizer, create a speaker for the event with given email and name.
     * @param email email of speaker
     * @param name name of the speaker
     * @return whether the speaker was enabled or not
     */

    public Boolean createSpeaker(String email, String name) {
        return createSpeaker(email, name, "1234"); // uses 1234 as a default password
    }

    /**
     * As an organizer, create a speaker for the event. Overloaded above method with a password parameter.
     * @param email email of speaker
     * @param name name of the speaker
     * @param password password of the speaker
     * @return whether the speaker was created or not
     */

    public Boolean createSpeaker(String email, String name, String password) {
        // check if the name or email is already in use
        if (checkDuplicateEmail(email) || checkDuplicateName(name)) {
            return false;
        }
        // Only Organizers can create Speakers
        if (this.isOrganizer()) {
            // Create the new Speaker
            this.um.setEmail(email);
            this.um.setName(name);
            this.um.setPassword(password);

            this.um.createUser(UserType.SPEAKER);
            return true;
        } else {
            return false;
        }
    }

    /**
     * As an organizer, create an attendee for the event.
     * @param email email of attendee
     * @param name name of the attendee
     * @param password password of the attendee
     * @return whether the attendee was created or not
     */

    public Boolean createAttendee(String email, String name, String password) {
        // check if the name or email is already in use
        if (checkDuplicateEmail(email) || checkDuplicateName(name)) {
            return false;
        }
        // Only Organizers can create other accounts
        if (this.isOrganizer()) {
            // Create the new Attendee
            this.um.setEmail(email);
            this.um.setName(name);
            this.um.setPassword(password);

            this.um.createUser(UserType.ATTENDEE);
            return true;
        } else {
            return false;
        }
    }

    /**
     * As an organizer, create an organizer for the event.
     * @param email email of organizer
     * @param name name of the organizer
     * @param password password of the organizer
     * @return whether the organizer was created or not
     */

    public Boolean createOrganizer(String email, String name, String password) {
        // check if the name or email is already in use
        if (checkDuplicateEmail(email) || checkDuplicateName(name)) {
            return false;
        }
        // Only Organizers can create other accounts
        if (this.isOrganizer()) {
            // Create the new Organizer
            this.um.setEmail(email);
            this.um.setName(name);
            this.um.setPassword(password);

            this.um.createUser(UserType.ORGANIZER);
            return true;
        } else {
            return false;
        }
    }

    /**
     * As an organizer, create an admin for the event.
     * @param email email of admin
     * @param name name of the admin
     * @param password password of the admin
     * @return whether the admin was created or not
     */

    public Boolean createAdmin(String email, String name, String password) {
        // check if the name or email is already in use
        if (checkDuplicateEmail(email) || checkDuplicateName(name)) {
            return false;
        }
        // Only Organizers can create other accounts
        if (this.isOrganizer()) {
            // Create the new Admin
            this.um.setEmail(email);
            this.um.setName(name);
            this.um.setPassword(password);

            this.um.createUser(UserType.ADMIN);
            return true;
        } else {
            return false;
        }
    }

    /**
     * Delete a message
     * @param m Messenger with the message information
     * @param toID The recipient of the message
     * @param msgSubject Subject of the message
     * @param msgBody Body of the message
     * @return Whether the message was deleted successfully
     */
    public Boolean deleteMessage(Messenger m, String toID, String msgSubject, String msgBody) {
        // try to delete the message
        // can only delete if admin
        if (this.isAdmin()) {
            try {
                m.deleteMessage(toID, msgSubject, msgBody);
                return true;
            } catch (DoesNotExistException e) {
                return false;
            }
        } else {
            return false;
        }
    }

    /**
     *
     * @param email email to check
     * @return if email already exists
     */
    // checks if the email already exists in the UserManager
    private Boolean checkDuplicateEmail(String email) {
        return this.um.getUserEmails().contains(email);
    }

    /**
     *
     * @param name name of the user
     * @return if name is duplicate
     */
    // checks if the name already exists in the UserManager
    private Boolean checkDuplicateName(String name) {
        return this.um.getUserNames().contains(name);
    }


    /**
     *
     * @param roomName the room name
     * @return if the room name is duplicated
     */

    // checks if the room name already exists in the RoomManager
    private Boolean checkDuplicateRoomName(String roomName) {
        return useCaseStorage.getRoomManager().getRoomNames().contains(roomName);
    }
}
