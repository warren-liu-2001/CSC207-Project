package UseCases;

import Entities.*;

/**
 * Feedback Builder Class
 *
 * @version 2.0
 */
public class FeedbackBuilder implements java.io.Serializable {


    private int Score;
    private String FeedbackID;
    private String Content;

    public FeedbackBuilder() {
    }

    /**
     *
     * @param Score Set score
     */
    public void setScore(int Score){
        this.Score = Score;
    }

    /**
     *
     * @param FeedbackIDENT set the identity
     */

    public void setFeedbackID(String FeedbackIDENT){
        this.FeedbackID = FeedbackIDENT;
    }

    /**
     *
     * @param Content set the content
     */
    public void setContent(String Content){
        this.Content = Content;
    }

    /**
     *
     * @return the feedback object that is generated.
     */
    public Feedback getFeedback(){
        Feedback IPRFdbk = new Feedback(0, "FeedbackUnsetID", "Lorem Ipsum");
        IPRFdbk.setScore(this.Score);
        IPRFdbk.setFeedbackID(this.FeedbackID);
        IPRFdbk.setContent(this.Content);
        return IPRFdbk;
    }
}
