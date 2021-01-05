package Controllers;

import Presenters.FeedbackPresenter;
import Presenters.IFeedbackPresenter;
import UseCases.*;

/**
 * Controls the behaviour for the feedback classes.
 *
 * @version 2.0
 */
public class FeedbackController implements java.io.Serializable{

    private FeedbackManager fbm;
    private IFeedbackPresenter ifbp = new FeedbackPresenter();

    public FeedbackController(FeedbackManager fbm, FeedbackPresenter fbp) {
        this.fbm = fbm;
    }


    /**
     *
     * @param rating the rating of the event
     * @param content  the content of the message to be sent
     * @return if message was successfully inserted
     */
    public boolean giveFeedback(int rating, String content) {
        if (1 <= rating && rating <= 5) {
            this.fbm.createFeedback(rating, content);
            return true;
        }
        return false;
    }

    public void queryFeedback(String ID) {
        this.fbm.WriteFeedbackToConsole(ID, ifbp);
    }

    public void queryMostRecentFeedback() {
        this.fbm.WriteMostRecentFeedback(ifbp);
    }

    public void queryAllFeedback() {
        this.fbm.WriteAllFeedback(ifbp);
    }

    public void promptFileExport() {this.fbm.WriteToExternalFile(ifbp);}

}
