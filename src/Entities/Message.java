package Entities;

import java.time.LocalTime;
import java.util.Date;
import java.util.ArrayList;
import java.util.Objects;
import java.util.UUID;

/**
 * <h1>Entities.Message Entity Class</h1>
 * Representation of messages as objects. *
 * @author  Adam Paslawski
 * @version 2.0
 */
public class Message implements java.io.Serializable {
    private String messageId;
    private String from;
    private String to;
    private ArrayList<String> recipientList;
    private String subject;
    private String body;
    private LocalTime time;

    /**
     * Constructor for the message object when sent to a single person.
     * @param from attendee object from which the message is being passed.
     * @param to attendee object that the message is being passed to.
     * @param subject String object containing the subject of the message.
     * @param body String object containing the body of the message.
     */
    public Message(String from,String to,String subject,String body){
        this.messageId = "message"+ UUID.randomUUID().toString();
        this.from = from;
        this.to = to;
        this.subject = subject;
        this.body = body;
        this.time = LocalTime.now();
    }

    /**
     * Constructor for a message sent to many people
     */
    public Message(String from,ArrayList<String> recipientList,String subject,String body){
        this.messageId = "message"+ UUID.randomUUID().toString();
        this.from = from;
        this.recipientList = recipientList;
        this.subject = subject;
        this.body = body;
        this.time = LocalTime.now();
    }
    public Message(String body){
        this.body = body;
    }
    /**
     * Overiding the toString method
     * @return a String representation for the message object.
     */
    @Override
    public String toString() {

        return "From: "+ this.from +"\n"+
                "To: " + this.to + "\n"+
                "Date: " + this.time + "\n"+
                "subject: " + this.subject + "\n"+
                "message: " + this.body + "\n";
    }

    /**
     * check if objects are equal
     * @param o other object
     * @return if objects are equal
     */

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Message message = (Message) o;
        return Objects.equals(from, message.from) &&
                Objects.equals(to, message.to) &&
                Objects.equals(recipientList, message.recipientList) &&
                Objects.equals(subject, message.subject) &&
                Objects.equals(body, message.body) &&
                Objects.equals(time, message.time);
    }

    /**
     *
     * @return hashcode of object
     */
    @Override
    public int hashCode() {
        return Objects.hash(from, to, recipientList, subject, body, time);
    }


    /**
     *
     * @return who's message from, UserID
     */
    public String getFrom() {
        return from;
    }

    /**
     *
     * @return the recipients ID
     */
    public String getTo() {
        return to;
    }

    /**
     *
     * @return list of recipients
     */
    public ArrayList<String> getRecipientList() {
        return recipientList;
    }

    /**
     *
     * @return the subject
     */
    public String getSubject() {
        return subject;
    }

    /**
     *
     * @return the body
     */
    public String getBody() {
        return body;
    }

    /**
     *
     * @return the time
     */
    public LocalTime getTime() {
        return time;
    }

    /**
     *
     * @return the ID of the message
     */
    public String getMessageId() {
        return messageId;
    }

    /**
     *
     * @param from set the from
     */
    public void setFrom(String from){this.from = from;}

    /**
     *
     * @param to set to
     */
    public void setTo(String to){this.to=to;}

    /**
     *
     * @param id set the ID
     */
    public void setMessageId(String id){this.messageId = id;}
}