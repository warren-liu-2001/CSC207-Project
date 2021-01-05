package Entities;


import UseCases.UserType;

import java.util.ArrayList;

/**
 * Represents an attendee entity in our system.
 *
 * @version 2.0
 */
public class Attendee extends User implements java.io.Serializable {

    // Instance variables
    private String name;
    private String email;
    private String userID;
    private String password;
    private ArrayList<String> contacts;
    private ArrayList<String> registeredTalks;

    /**
     *
     * @param email email of the attendee
     * @param userID userID of the attendee
     * @param name name of the attendee
     *
     */

    public Attendee(String email, String userID, String name) {
        super(email, userID, name);
        this.setPassword("abcdefghijklmn");
        this.contacts = new ArrayList<String>();
        this.registeredTalks = new ArrayList<String>();
        setType(UserType.ATTENDEE);
    }

    /**
     *
     * @return the string representation of an attendee
     */
    @Override
    public String toString() {
        return "Entities.Attendee{" +
                "id='" + this.getUserID() + '\'' +
                "email='" + this.getEmail() + '\'' +
                ", name='" + this.getName() + '\'' +
                ", password='" + this.getPassword() + '\'' +
                ", contacts=" + this.getContacts() +
                '}';
    }

    /**
     *
     * @param niceTalk event to be registered's ID
     * @return if event has been added
     */
    public boolean registerForEvent(String niceTalk) {
        if (this.registeredTalks.contains(niceTalk)) {
            return false;
        }
        this.registeredTalks.add(niceTalk);
        return true;
    }

    /**
     *
     * @return this registered events
     */

    public ArrayList<String> getRegisteredEvents(){
        return registeredTalks;
    }

    /**
     *
     * @param badTalk remove talk from registration
     * @return whether talk has been removed
     */

    public boolean deregisterFromEvent(String badTalk) {
        if (!(this.registeredTalks.contains(badTalk))) {
            return false;
        }
        this.registeredTalks.remove(badTalk);
        return true;
    }

    /**
     *
     * @return if this attendee is a speaker, which is always false
     */
    public boolean isSpeaker() {return false;}

    /**
     *
     * @return if this attendee is an organizer, which is always false
     */
    public boolean isOrganizer() {return false;}


    /**
     *
     * @return if this is an admin, which is always false
     */
    public boolean isAdmin() {return false;}


    /**
     *
     * @param contacts set contacts to this
     */
    public void setContacts(ArrayList<String> contacts) {
        this.contacts = contacts;
    }

    /**
     *
     * @param registeredTalks set the registered talks to this
     */

    public void setRegisteredTalks(ArrayList<String> registeredTalks) {
        this.registeredTalks = registeredTalks;
    }

}
