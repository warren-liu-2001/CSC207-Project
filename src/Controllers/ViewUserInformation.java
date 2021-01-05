package Controllers;

import Exceptions.DoesNotExistException;
import Exceptions.ObjectInvalidException;
import Presenters.ErrorMessages;
import UseCases.AttributeChanger;
import UseCases.UserManager;
import UseCases.UserType;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.UUID;

/**
 * Viewing the users information
 *
 * @version 2.0
 */
public class ViewUserInformation {

    UseCaseStorage ucs;
    UserAccount ua;
    AttributeChanger ac;
    UserManager um;
    ErrorMessages em;

    /**
     *
     * @param ua user account
     * @param ucs use case storage
     */

    public ViewUserInformation(UserAccount ua, UseCaseStorage ucs) {
        this.ucs = ucs;
        this.ua = ua;
        this.um = ucs.getUserManager();
        this.ac = ucs.getAttributeChanger();
        this.em = new ErrorMessages();
    }

    /**
     * Getter for the user's id.
     * @return The user's id.
     */
    public String getUserID() {
        return this.ua.getUserID();
    }

    /**
     * Getter for the user's email.
     * @return The account's email.
     */
    public String getEmail() {
        try {
            return this.ac.getUserEmail(this.getUserID(), this.um);
        } catch (DoesNotExistException | ObjectInvalidException e) {
            this.em.printError(e);
            return "Could not find email.";
        }
    }

    /**
     * Getter for the user's type.
     * @return The account's type.
     */
    public String getType() {
        try {
            UserType type = this.ac.getType(this.getUserID(), this.um);
            switch (type) {
                case ATTENDEE:
                    return "Attendee";
                case SPEAKER:
                    return "Speaker";
                case ORGANIZER:
                    return "Organizer";
                case ADMIN:
                    return "Admin";
            }
            return "User";
        } catch (DoesNotExistException | ObjectInvalidException e) {
            this.em.printError(e);
            return "Could not find type.";
        }
    }

    /**
     * Getter for the user's password.
     * @return The user's password.
     */
    public String getPassword() {
        try {
            return this.ac.getUserPassword(this.getUserID(), this.um);
        } catch (DoesNotExistException | ObjectInvalidException e) {
            this.em.printError(e);
            return "Could not find password.";
        }
    }

    /**
     * Getter for the user's name.
     * @return The user's name.
     */
    public String getName() {
        try {
            return this.ac.getUserName(this.getUserID(), this.um);
        } catch (DoesNotExistException | ObjectInvalidException e) {
            this.em.printError(e);
            return "Could not find name.";
        }
    }

    /**
     * View inbox
     * @param m messenger
     * @return the inbox of this user
     */
    public ArrayList viewInbox(Messenger m) {
        try {
            return m.viewInbox(this.getUserID());
        } catch (DoesNotExistException e) {
            this.em.printError(e);
            ArrayList<String> a = new ArrayList<>();
            a.add("Could not find messages.");
            return a;
        }
    }

    /**
     * Returns a list of contacts
     * @return list of contacts of the attendee
     * @throws DoesNotExistException if userID DNE.
     */
    public ArrayList<String> viewContacts() throws DoesNotExistException {
        try {
            return this.ac.getContactsID(this.getUserID(), this.um);
        } catch (ObjectInvalidException e) {
            this.em.printError(e);
            ArrayList<String> a = new ArrayList<>();
            a.add("Could not find contacts.");
            return a;
        }
    }
}
