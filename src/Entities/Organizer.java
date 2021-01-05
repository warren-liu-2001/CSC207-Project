package Entities;

import UseCases.UserType;

import java.util.ArrayList;

/**
 * Represents an Entities.Organizer, which is a special type of Entities.Attendee
 *
 * @version 2.0
 */

public class Organizer extends User implements java.io.Serializable {

    private ArrayList<String> contacts;
    private ArrayList<String> hostingTalks;

    /**
     *
     * @param email the email of the organizer
     * @param userID the UID of the organizer
     * @param name the name of the organizer
     */

    public Organizer(String email, String userID, String name) {
        super(email, userID, name);
        this.setPassword("qwerty");
        this.hostingTalks = new ArrayList<String>();
        setType(UserType.ORGANIZER);
    }


    /**
     *
     * @return a string representation of an organizer.
     */
    @Override
    public String toString() {
        return "Entities.Organizer{" +
                "email='" + this.getEmail() + '\'' +
                ", name='" + this.getName() + '\'' +
                ", password='" + this.getPassword() + '\'' +
                ", contacts=" + this.getContacts() +
                '}';
    }

    /**
     *
     * @return if this organizer is a speaker, which is always false
     */
    public boolean isSpeaker() {return false;}

    /**
     *
     * @return if this organizer is an organizer, which is always true.
     */
    public boolean isOrganizer() {return true;}

    /**
     *
     * @return if an organizer is an admin, which is always false.
     */
    public boolean isAdmin() {return false;}

    /**
     * Add the hosting ID for a talk, for this Organizer
     * @param talkID the ID of the talk.
     */
    public void AddHostingTalk(String talkID) {
        this.hostingTalks.add(talkID);
    }

    /**
     * remove talkID from the list of hostingtalks for this organizer.
     * @param talkID the talkID to be removed
     * @return whether the hosting talk has been removed or not
     */

    public boolean RemoveHostingTalk(String talkID) {
        if (this.hostingTalks.contains(talkID)){
            this.hostingTalks.remove(talkID);
            return true;
        }
        else {return false;}
    }

    /**
     *
     * @param contacts set the contacts
     */

    public void setContacts(ArrayList<String> contacts) {
        this.contacts = contacts;
    }

    /**
     *
     * @return list of talks this organizer is hosting, in terms of IDs
     */
    public ArrayList<String> getHostingTalks() {
        return hostingTalks;
    }

    /**
     *
     * @param hostingTalks setting the hosting talks
     */
    public void setHostingTalks(ArrayList<String> hostingTalks) {
        this.hostingTalks = hostingTalks;
    }

}
