package Controllers;

import Entities.*;
import Exceptions.*;
import UseCases.*;

import java.util.ArrayList;

/**
 * Handles registration purposes for the use case storage class.
 *
 * @version 2.0
 */

public class Registrar implements java.io.Serializable{

    private RoomManager rm;
    private TalkManager tm;
    private UserManager um;
    private AttributeChanger ac;

    /**
     *
     * @param usecasestorage USECASESTORAGE OBJECT
     */

    public Registrar(UseCaseStorage usecasestorage) {
        this.rm = usecasestorage.getRoomManager();
        this.tm = usecasestorage.getTalkManager();
        this.um = usecasestorage.getUserManager();
        this.ac = usecasestorage.getAttributeChanger();

    }

    /**
     * Registers attendee for an event.
     * @param userID -  The attendee identification
     * @param eventID - The event identification
     */
    public void register(String userID, String eventID) throws DoesNotExistException, ObjectInvalidException {
        ac.addToEventList(userID, eventID, um);
    }

    /**
     * Deregisters attendee from an event.
     * @param userID - The attendee identification
     * @param eventID - The talk identification
     */
    public void cancel(String userID, String eventID) throws DoesNotExistException, ObjectInvalidException {
        ac.removeFromEventList(userID, eventID, um);
    }

    /**
     * Returns the list of events that an attendee is registered in.
     * @param userID - The attendee identification
     * @return list of the attendee's registered talks.
     */
    public ArrayList<String> getRegisteredTalks(String userID) throws ObjectInvalidException, DoesNotExistException {
        return ac.getRegisteredTalks(userID, um);
    }

}