package UseCases;

import Entities.*;
import Exceptions.DoesNotExistException;

import java.time.LocalTime;
import java.util.Date;
import java.util.HashMap;
import java.util.ArrayList;
import java.util.Map;

/**
 * <h1>Inbox Manager Use Case Class</h1>
 * Manages the inboxes of attendees including:
 * <ul>
 *     <li>Storing a hashmap of messages and their associated users inboxes</li>
 *     <li>Allow viewing of a specific inbox</li>
 *     <li>Adding of messages to a specific inbox</li>
 *     <li>Removal of messages from a specific inbox</li>
 * </ul> *
 *
 * @author  Adam Paslawski
 * @version 2.0
 *
 */
public class InboxManager implements java.io.Serializable {
    /**
     * Data representation of chats and their associated inboxes.
     * This includes Entities.Room chats to message lists for that room as well as user to user chats.
     */
    private HashMap<String , ArrayList<Message>> messageDb;


    public InboxManager(){
        this.messageDb = new HashMap<String , ArrayList<Message>>();
    }


    /**
     *@return A list of message objects, the inbox of the specified user.
     */
    public ArrayList<Message> getMessages(String userId){
        ArrayList<Message> messages = messageDb.get(userId);

        if (messages != null) {
            return messages;
        } else {
            return new ArrayList<>();
        }
    }
    public Message createMessage(String id, String from , String to, String subject, String body, LocalTime time){
        Message output = new Message(from , to , subject , body);
        output.setMessageId(id);
        return output;
    }
    /**
     *@param messageToBeAdded message object to be sent.
     *@param userReceiving attendee object of recipient.
     */
    public Boolean addMessage(Message messageToBeAdded , String userReceiving){
        ArrayList<Message> newList = messageDb.get(userReceiving);
        if (newList == null) {
            // make a new list instead
            newList = new ArrayList<>();
        }
        newList.add(messageToBeAdded);
        messageDb.put(userReceiving, newList);
        return getMessages(userReceiving).contains(messageToBeAdded);
    }
    /**
     *Checks the User as that recipient added before adding a message to the recipients inbox.
     *@param userSending Object of User that is sending the message.
     *@param recipient Object of the User receiving the message.
     *@param messageToBeAdded Id of the message object being sent from userName to recipient.
     *TODO add an exception for user not being on contacts as well as an exception for user not existing.
     */
    public void sendMessage(String userSending, String recipient , Message messageToBeAdded, UserManager um) throws DoesNotExistException {
        ArrayList<String> userSendingContacts = um.findUser(userSending).getContacts();
        if(userSendingContacts.contains(recipient)){
            addMessage(messageToBeAdded, recipient);
        }
        else{
            throw new DoesNotExistException("The user you are trying to send to does not exist in your contact list.");
        }
    }

    public Message generateMessage(String SenderID, String RecipientID, String Content, String Subject) {
        return new Message(SenderID, RecipientID, Subject, Content);
    }
    public void sendMessage(String SenderID, String RecipientID, String Content, String Subject, UserManager um) throws DoesNotExistException {
        try {String senderName = um.findUser(SenderID).getName();
            String receiverName = um.findUser(RecipientID).getName();
            this.sendMessage(SenderID, RecipientID,
                this.generateMessage(senderName, receiverName, Content, Subject), um);}
        catch (DoesNotExistException e) {
            throw new DoesNotExistException("The user you are trying to send to does not exist in contacts");
        }
    }
    /**
     * Deletes a message from a users inbox.
     * @param user user to have a message deleted from their inbox.
     * @param messageToBeDeleted message object to be deleted from the inbox.
     */
    public Boolean deleteMessage(User user, Message messageToBeDeleted) throws DoesNotExistException {
        //Check if that message is in inbox
        if(getMessages(user.getUserID()).contains(messageToBeDeleted)) {
            //delete Entities.Message
            getMessages(user.getUserID()).remove(messageToBeDeleted);
            return true;
        }
        // THis allows admins to indiscriminately delete every message
        else if (user.isAdmin() && messageDb.containsValue(messageToBeDeleted)){
            String key = null;
            for (HashMap.Entry<String, ArrayList<Message>> entry : messageDb.entrySet()) {
                ArrayList<Message> messageslist = entry.getValue();
                if (messageslist.contains(messageToBeDeleted)) {
                    key = entry.getKey();
                    break;
                }
            }
            try {messageDb.get(key).remove(messageToBeDeleted);}
            catch (Exception e3) {return false;}
            return true;
        }
        else{
            throw new DoesNotExistException("The message you are trying to delete does not exist in this inbox.");
        }
    }

    /**
     * Sends multiple messages to a list of Users.
     * @param userIds Array list of User ID's to sent to
     * @param messageToBeSent message object to be sent to all users.
     */
    public void sendMultiple(ArrayList<String> userIds,Message messageToBeSent){
        for (String userId : userIds){
            addMessage(messageToBeSent, userId);
        }
    }

    /**
     * Creates and sends a message to a list of Users.
     * @param userIds Array list of User ID's to sent to
     * @param from who the message is from
     * @param subject Subject of the message
     * @param body body of the message
     * @param um the UserManager containing the information of these users
     */
    public void sendMultiple(ArrayList<String> userIds,String from, String subject, String body, UserManager um) throws DoesNotExistException {
        String senderName = um.findUser(from).getName();
        Message messageToBeSent = new Message(senderName, userIds, subject, body);
        sendMultiple(userIds, messageToBeSent);
    }

    /**
     * Gets a list of the subjects of the messages the user received.
     * @param userID id of the user who's subjects are being retrieved
     * @return an ArrayList containing the subjects of the messages the user received
     */
    public ArrayList<String> getMessageSubjects(String userID) {
        ArrayList<Message> messages = this.getMessages(userID);
        ArrayList<String> messageSubjects = new ArrayList<>();
        for (Message message : messages) {
            messageSubjects.add(message.getSubject());
        }
        return messageSubjects;
    }

    /**
     * Gets a list of the senders of the messages the user received.
     * @param userID id of the user who's senders are being retrieved
     * @return an ArrayList containing the sender's names that they received
     */
    public ArrayList<String> getMessageSenders(String userID) {
        ArrayList<Message> messages = this.getMessages(userID);
        ArrayList<String> messageSenders = new ArrayList<>();
        if (messages != null) {
            for (Message message : messages) {
                messageSenders.add(message.getFrom());
            }
        }
        return messageSenders;
    }

    /**
     * Gets a list of the contents of the messages the user received.
     * @param userID id of the user who's message contents are being retrieved
     * @return an ArrayList containing the contents of the messages that they received
     */
    public ArrayList<String> getMessageContents(String userID) {
        ArrayList<Message> messages = this.getMessages(userID);
        ArrayList<String> messageBodies = new ArrayList<>();
        for (Message message : messages) {
            messageBodies.add(message.getBody());
        }
        return messageBodies;
    }

    /**
     * Finds the message with matching subject and body, if it exists
     * @param toID ID of the user this message is to
     * @param subject Subject of the message
     * @param body Body of the message
     * @return The message found
     * @throws DoesNotExistException If there is no matching message
     */
    public Message findMessage(String toID, String subject, String body) throws DoesNotExistException {
        // find the message with specified parameters
        for (Message message : this.getMessages(toID)) {
            if (message.getSubject().equals(subject) && message.getBody().equals(body)) {
                return message;
            }
        }
        // message not found
        throw new DoesNotExistException("Message does not exist");
    }
}
