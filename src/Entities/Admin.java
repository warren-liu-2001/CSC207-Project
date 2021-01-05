package Entities;

import UseCases.UserType;

import java.util.ArrayList;

/**
 * Admin class, which is able to remove events and messages at will
 *
 * @version 2.0
 */

public class Admin extends Organizer implements java.io.Serializable{

    private String password;
    private ArrayList<String> contacts;
    private ArrayList<String> hostingTalks;

    /**
     * @param email  the email of the organizer
     * @param userID the UID of the organizer
     * @param name   the name or ght organizer
     */
    public Admin(String email, String userID, String name) {
        super(email, userID, name);
        setType(UserType.ADMIN);
    }

    /**
     *
     * @return if this is an organizer, which is always true
     */
    @Override
    public boolean isOrganizer() {
        return super.isOrganizer();
    }

    /**
     *
     * @return if this is a speaker, which is always false
     */
    public boolean isSpeaker() {return false;}

    /**
     *
     * @return if this is an admin, which is always true
     */
    @Override
    public boolean isAdmin() {
        return true;
    }

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
     * @return password
     */
    public String getPassword() {return this.password;}

    /**
     *
     * @param password set the password to this
     */
    @Override
    public void setPassword(String password) {
        this.password = password;
    }

    /**
     *
     * @param contacts set the contacts list for the admin to contacts
     */
    public void setContacts(ArrayList<String> contacts) {
        this.contacts = contacts;
    }

    /**
     *
     * @param hostingTalks set the hostingtalks list for this admin to hostingtalks
     */
    public void setHostingTalks(ArrayList<String> hostingTalks) {
        this.hostingTalks = hostingTalks;
    }

}
