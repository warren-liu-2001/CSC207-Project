package Controllers;

import Entities.*;
import Exceptions.DoesNotExistException;
import UseCases.*;

import java.util.ArrayList;

/**
 * The class that stores an Inbox Manager for access by other presenters.
 *
 * @version 0.1.1
 */
public class Messenger implements java.io.Serializable{
    private InboxManager im;
    private UseCaseStorage ucs;
    private UserManager um;

    /**
     * Constructor for Controllers.Messenger Controller
     * Stores Inbox Manager
     */
    public Messenger(UseCaseStorage ucs) {
        this.im = ucs.getInboxManager();
        this.um = ucs.getUserManager();
        this.ucs = ucs;
    }

    /**
     * Send private message from one user to another.
     * @param content The message itself
     * @param senderID The User ID of the sender
     * @param recipientID The User ID of the recipient
     * @param subject The subject of the message
     * @throws DoesNotExistException Error is thrown if the User does not exist
     */
    public void sendPrivateMessage(String content, String senderID, String recipientID, String subject) throws DoesNotExistException {
            this.im.sendMessage(senderID, recipientID, content, subject, um);
    }

    /**
     * Sends a mass message to all users of a certain type
     * @param fromId The id of the user it's from
     * @param subject The subject of the message
     * @param content The content of the message
     * @param type Type of user to send this message to
     * @throws DoesNotExistException if message dne
     */
    public void sendMassMessage(String fromId, String subject, String content, String type) throws DoesNotExistException {
        ArrayList<User> userList = um.getUserList();
        ArrayList<String> allIdOfType = new ArrayList<>();
        //ArrayList<String> failedToAdd = new ArrayList<>();

        for (User user: userList){
            if(user.getType() == UserType.valueOf(type)){
                allIdOfType.add(user.getUserID());
            }else{
                //do nothing
            }
        }
        im.sendMultiple(allIdOfType, fromId, subject, content, um);
    }

    /**
     *Mass Message to people in a specific talk
     */
    public void sendMassMessage(String fromId, String subject, String content, Event talk) throws DoesNotExistException {
        ArrayList<String> UserList = talk.getAttendees();
        im.sendMultiple(UserList, fromId, subject, content, um);
    }

    /**
     * @return Array list of message objects, representing the users inbox.
     */
    public ArrayList<Message> viewInbox(String userId) throws DoesNotExistException {
        return this.im.getMessages(userId);
    }

    /**
     * Delete a message
     * @param toID ID of the recipient of this message
     * @param subject Subject of the message
     * @param body Body of the message
     */
    public void deleteMessage(String toID, String subject, String body) throws DoesNotExistException {
        this.im.deleteMessage(um.findUser(toID), this.im.findMessage(toID, subject, body));
    }
}