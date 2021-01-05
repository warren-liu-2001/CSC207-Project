package Entities;


import java.sql.Timestamp;
import java.time.Instant;
import java.util.GregorianCalendar;

/**
 * A class representing the feedback system
 *
 * @author warrenliu
 * @version 2.0
 */
public class Feedback implements Comparable<Feedback>, java.io.Serializable{

    private int score;
    private String FeedbackID;
    private String Content;
    private final Instant date;

    /**
     *
     * @param score the score of this feedback, an integer from 1 to 5.
     * @param FBID the ID of the feedback
     * @param content the content of the feedback
     */

    public Feedback(int score, String FBID, String content) {
        this.score = score;
        this.FeedbackID = FBID;
        this.Content = content;
        Timestamp ts = new Timestamp(System.currentTimeMillis());
        this.date = ts.toInstant();
    }

    /**
     *
     * @return the score of the rating
     */
    public int getScore() {
        return this.score;
    }

    /**
     *
     * @param score sets score to this score
     */

    public void setScore(int score) {
        this.score = score;
    }

    /*
     */

    /**
     *
     * @return the feedback ID
     */
    public String getFeedbackID() {
        return this.FeedbackID;
    }

    /**
     *
     * @param feedbackID set feedbackID
     */
    public void setFeedbackID(String feedbackID) {
        this.FeedbackID = feedbackID;
    }

    /**
     *
     * @return the content of the feedback
     */
    public String getContent() {
        return this.Content;
    }

    /**
     *
     * @param content set the content of a feedback
     */

    public void setContent(String content) {
        this.Content = content;
    }

    /**
     *
     * @return a string representation of the Feedback Object
     */

    @Override
    public String toString() {
        return "Feedback{" +
                "FeedbackID=" + FeedbackID +
                ", score='" + score + '\'' +
                ", Content='" + Content + '\'' +
                ", date=" + date +
                '}';
    }

    /**
     * Makes feedback comparable.
     * @param o other Feedback object
     * @return an integer in accordance to the comparable interface
     */
    @Override
    public int compareTo(Feedback o) {
        if (this.date.isAfter(o.date)) {return 1;}
        else if (this.date.isBefore(o.date)) {return -1;}
        else {return 0;}
    }
}
