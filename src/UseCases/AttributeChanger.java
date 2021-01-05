package UseCases;

import Entities.*;
import Exceptions.*;
import sun.awt.geom.AreaOp;

import java.util.ArrayList;

/**
 *Attribute Changer and getter
 *
 * @version 2.0
 */
public class AttributeChanger  implements java.io.Serializable {

    /**
     *
     * @param userID userID
     * @param um userManager
     * @return the userName of the user
     * @throws DoesNotExistException if the user doesnt exist
     * @throws ObjectInvalidException if the object is invalidly formatted
     */

    public String getUserName(String userID, UserManager um) throws DoesNotExistException, ObjectInvalidException {
        return um.findUser(userID).getName();
    }

    /**
     *
     * @param userID userID
     * @param name the username to be replaced
     * @param um the usermanager
     * @throws DoesNotExistException if the user doesnt exist
     * @throws ObjectInvalidException if the object is invalidly formatted
     */
    public void setUserName(String userID, String name, UserManager um) throws DoesNotExistException, ObjectInvalidException {
        um.findUser(userID).setName(name);
    }

    /**
     *
     * @param userID userID
     * @param um userManager
     * @return the user email of the user
     * @throws DoesNotExistException if the user doesnt exist
     * @throws ObjectInvalidException if the object is invalidly formatted
     */
    public String getUserEmail(String userID, UserManager um) throws DoesNotExistException, ObjectInvalidException {
        return um.findUser(userID).getEmail();
    }


    /**
     *
     * @param userID userID
     * @param email the user email that will replace the old one
     * @param um the usermanager
     * @throws DoesNotExistException if the user doesnt exist
     * @throws ObjectInvalidException if the object is invalidly formatted
     */
    public void setUserEmail(String userID, String email, UserManager um) throws DoesNotExistException, ObjectInvalidException {
        um.findUser(userID).setEmail(email);
    }

    /**
     *
     * @param userID userID
     * @param um userManager
     * @return the password of the user
     * @throws DoesNotExistException if the user doesnt exist
     * @throws ObjectInvalidException if the object is invalidly formatted
     */

    public String getUserPassword(String userID, UserManager um) throws DoesNotExistException, ObjectInvalidException {
        return um.findUser(userID).getPassword();
    }

    /**
     *
     * @param userID userID
     * @param password the new password
     * @param um the usermanager
     * @throws DoesNotExistException if the user doesnt exist
     * @throws ObjectInvalidException if the object is invalidly formatted
     */

    public void setUserPassword(String userID, String password, UserManager um) throws DoesNotExistException, ObjectInvalidException {
        um.findUser(userID).setPassword(password);
    }

    /**
     *
     * @param email the email of the user
     * @param um usermanager
     * @return the ID of the user
     * @throws DoesNotExistException If the user simply does not exist
     * @throws ObjectInvalidException if the object is invalid
     */

    public String getUserID(String email, UserManager um) throws DoesNotExistException, ObjectInvalidException {
        return um.findUserWithEmail(email).getUserID();
    }


    /**
     *
     * @param attendeeAdding attendee that wants a new friend =)
     * @param attendeeBeingAdded The friend that is going to be added =)
     * @throws ObjectInvalidException if the object is invalidly formatted
     * @throws DoesNotExistException if object DNE
     */

    public void addContact(Attendee attendeeAdding , Attendee attendeeBeingAdded) throws ObjectInvalidException, DoesNotExistException {
        attendeeAdding.addtoContacts(attendeeBeingAdded.getUserID());

    }

    /**
     *
     * @param attendeeIdAdding attendee that wants a new friend =)
     * @param attendeeIdBeingAdded The friend that is going to be added =)
     * @param um the user Manager
     * @throws ObjectInvalidException if the object is invalidly formatted
     * @throws DoesNotExistException if object DNE
     */
    // overloaded above with id, id params instead of id, Attendee
    public void addContact(String attendeeIdAdding , String attendeeIdBeingAdded, UserManager um) throws ObjectInvalidException, DoesNotExistException {
        // check if said friend exists
        um.findUser(attendeeIdBeingAdded);
        // add
        um.findUser(attendeeIdAdding).addtoContacts(attendeeIdBeingAdded);
    }

    /**
     * Remove the contact
     * @param attendeeIdDeleting The guy who's going through the "Friendship Ended with Mudasir" saga's ID =(
     * @param attendeeIdBeingDeleted Mudasir's ID =(
     * @param um the usermanager object
     * @throws ObjectInvalidException if the object is improperly formatted
     * @throws DoesNotExistException if the object DNE
     */
    public void removeContact(String attendeeIdDeleting , String attendeeIdBeingDeleted, UserManager um) throws ObjectInvalidException, DoesNotExistException {
        um.findUser(attendeeIdDeleting).removefromContacts(attendeeIdBeingDeleted);
    }

    /**
     *
     * @param userID user ID
     * @param um user Manager
     * @return contact ID list
     * @throws DoesNotExistException if DNE
     * @throws ObjectInvalidException if improper formatting of object
     */
    public ArrayList<String> getContactsID(String userID, UserManager um) throws DoesNotExistException, ObjectInvalidException {
        User user = um.findUser(userID);
        return user.getContacts();
    }

    /**
     *
     * @param userId userID
     * @param talkId talk ID to be removed
     * @param um usermanager
     * @throws ObjectInvalidException if Invalid Object Formatting
     * @throws DoesNotExistException if DNE
     */
    public void removeFromEventList(String userId , String talkId, UserManager um) throws ObjectInvalidException, DoesNotExistException {
        User user = um.findUser(userId);
        if (user.getType().equals(UserType.ATTENDEE)){
            Attendee asAttendee = (Attendee) user;
            asAttendee.deregisterFromEvent(talkId);
        }
    }

    /**
     *
     * @param userId User ID to add event
     * @param eventId the event ID
     * @param um the usermanager
     * @throws ObjectInvalidException if object invalid
     * @throws DoesNotExistException if Object DNE
     */
    public void addToEventList(String userId , String eventId, UserManager um) throws ObjectInvalidException, DoesNotExistException {
        User user = um.findUser(userId);
        if (user.getType().equals(UserType.ATTENDEE)){
            Attendee asAttendee = (Attendee) user;
            asAttendee.registerForEvent(eventId);
        }
    }

    /**
     *
     * @param userId the User ID of the user
     * @param um the usermanager
     * @return the string list of all talkids the thingy is done
     * @throws ObjectInvalidException if object Invalid
     * @throws DoesNotExistException if object DNE
     */

    public ArrayList<String> getRegisteredTalks(String userId, UserManager um) throws ObjectInvalidException, DoesNotExistException {
        User user =  um.findUser(userId);
        return user.getRegisteredEvents();
    }

    /**
     *
     * @param userId useriD
     * @param um usermanger
     * @return the type of the user
     * @throws ObjectInvalidException if Object is Invalid
     * @throws DoesNotExistException if object DNE
     */

    public UserType getType(String userId, UserManager um) throws ObjectInvalidException, DoesNotExistException {
        return um.findUser(userId).getType();
    }

    /**
     *
     * @param ID id of the user
     * @param um manager of the users
     * @return the user obejct with ID
     * @throws DoesNotExistException if user not found or DNE
     */

    public User findUser(String ID, UserManager um) throws DoesNotExistException {
        for (User user: um.getUserList()) {
            if (user.getUserID().equals(ID)) {
                return user;
            }
            if (user.getEmail().equals(ID)) {
                return user;
            }

        }
        throw new DoesNotExistException("This user ID does not exist!");
    }

}
