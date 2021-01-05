package Entities;

import UseCases.UserType;

import java.util.ArrayList;
import java.util.UUID;

/**
 * Representation of a user in the program
 *
 * @author Warren Liu
 * @version 2.0
 */

public class User implements java.io.Serializable {
    private UserType type;
    private String name;
    private String email;
    private String userID;
    private String password;
    private ArrayList<String> contacts;
    private ArrayList<String> registeredTalks;


    public User User(){
        return this;
    }

    /**
     *
     * @param email email of the attendee
     * @param userID userID of the attendee
     * @param name name of the attendee
     *
     */
    public User(String email, String userID, String name) {
        this.email = email;
        this.name = name;
        this.userID = userID;
        this.password = "abcdefghijklmn";
        this.contacts = new ArrayList<>();
        this.registeredTalks = new ArrayList<>();

    }

    /**
     *
     * @return the name of the Entities.User
     */
    public String getName() {
        return this.name;
    }

    /**
     *
     * @param name rename the attendee
     */

    public void setName(String name) {
        this.name = name;
    }

    /**
     *
     * @return the email
     */

    public String getEmail() {
        return this.email;
    }

    /**
     *
     * @param email set the email
     */

    public void setEmail(String email) {
        this.email = email;
    }

    /**
     *
     * @return the string representation of this user
     *
     */
    public User(){
        String userID = "Attendee " + (new UUID(10,10)).toString();
        this.registeredTalks = new ArrayList<>();
        this.contacts = new ArrayList<>();
    }

    @Override
    public String toString() {
        return "Entities.User{" +
                "id='" + this.getUserID() + '\'' +
                "email='" + this.getEmail() + '\'' +
                "name='" + this.getName() + '\'' +
                "password='" + this.getPassword() + '\'' +
                ", contacts=" + this.getContacts() +
                '}';
    }

    /**
     *
     * @return password
     */

    public String getPassword() {
        return this.password;
    }

    /**
     *
     * @param password set the password
     */

    public void setPassword(String password) {
        this.password = password;
    }

    /**
     *
     * @return the user's type
     */
    public UserType getType() {
        return type;
    }

    /**
     *
     * @param t the type to be used
     */

    public void setType(UserType t) {
        this.type = t;
    }

    /**
     *
     * @return userID
     */

    public String getUserID() {
        return this.userID;
    }

    /**
     *
     * @param userID return userID of the attendee
     */

    public void setUserID(String userID) {
        this.userID = userID;
    }

    /**
     *
     * @return this users contact list
     */

    public ArrayList<String> getContacts() {
        return this.contacts;
    }

    /**
     *
     * @param friendID add a friend to contacts
     * @return whether contact is added
     */

    public boolean addtoContacts(String friendID) {
        if ((this.contacts.contains(friendID))) {
            return false;
        }
        this.contacts.add(friendID);
        return true;
    }

    /**
     *
     * @param notafriend Friendship ended with Mudasir, now Salman is my best friend
     * @return whether friend has been removed
     */

    public boolean removefromContacts(String notafriend) // throws Errors.DoesNotExistError
    {
        if (!(this.contacts.contains(notafriend))) {
            return false;
            // throw new Errors.DoesNotExistError("Error: This Friend is not in your friendlist" + ((Integer)notafriend).toString());

        }
        // Friendship ended with Mudasir, now Salman is my best friend
        this.contacts.remove(notafriend);
        return true;
    }

    /**
     *
     * @param eventID event ID to be added
     */
    public void addRegisteredEvent(String eventID){
        registeredTalks.add(eventID);
    }

    /**
     *
     * @param eventID to be removed
     */

    public void removeRegisteredEvent(String eventID){
        registeredTalks.remove(eventID);
    }


    /**
     *
     * @return this user's registered events in arraylist of IDs
     */
    public ArrayList<String> getRegisteredEvents(){
        return registeredTalks;
    }

    /**
     *
     * @return if this User is an Admin, which is always false
     */
    public boolean isAdmin() {return false;}


    /**
     *
     * @return if this user is an organizer, which is always false
     */
    public boolean isOrganizer() {return false;}

    /**
     *
     * @return if this user is a speaker, which is always false.
     */
    public boolean isSpeaker() {return false;}
}
