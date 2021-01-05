package UseCases;
import Entities.*;
import Exceptions.DoesNotExistException;
import Presenters.IFeedbackPresenter;

import java.util.ArrayList;
import java.util.Collections;
import java.util.UUID;

/**
 * A Use Case Class to Manage Feedback
 */

public class FeedbackManager implements java.io.Serializable{

    private ArrayList<Feedback> feedbackList;

    /**
     * Creates a FeedbackManager
     */
    public FeedbackManager() {
        this.feedbackList = new ArrayList<Feedback>();

    }

    /**
     * Creates a anonymous feedback with the following content
     * @param Rating How much the rating is
     * @param Content the content
     */

    public void createFeedback(int Rating, String Content) {
        FeedbackBuilder fb = new FeedbackBuilder();
        fb.setScore(Rating);
        UUID id = UUID.randomUUID();
        String FBID = "Feedback "+ id.toString();
        fb.setFeedbackID(FBID);
        fb.setContent(Content);
        Feedback fdbk = fb.getFeedback();
        this.feedbackList.add(fdbk);
    }

    /**
     *
     * @return the list of sorted feedbacks
     */

    public ArrayList<Feedback> SortedFeedbacks() {
        ArrayList<Feedback> copy = new ArrayList<Feedback>();
        for (Feedback fb:
             this.feedbackList) {
            copy.add(fb);
        }
        Collections.sort(copy, Collections.reverseOrder());
        return copy;
    }

    /**
     *
     * @param ID The ID of the feedback
     * @return the ID of the string
     * @throws DoesNotExistException If the Feedback with ID does not exist in the framework
     */
    public Feedback FindFeedback (String ID) throws DoesNotExistException {

        for (Feedback fb:
             this.feedbackList) {
            if (fb.getFeedbackID().equals(ID)) {return fb;}

        }
        throw new DoesNotExistException("This Feedback does not exist");

    }

    /**
     *
     * @return the most recent feedback
     * @throws DoesNotExistException if the most recent feedback is not existent: No feedback list!
     */

    public Feedback FindMostRecentFeedback() throws DoesNotExistException {
        try {return this.SortedFeedbacks().get(0);}
        catch (Exception e) {throw new DoesNotExistException("Your Feedback List is empty");}
    }

    /**
     *
     * @param fbID ID of the feedback
     * @return the string representation of the feedback
     */

    public String WriteFeedback(String fbID) {
        try {
            Feedback fbe = this.FindFeedback(fbID);
            return fbe.toString();
        }
        catch (Exception e2) {
            return "Invalid FeedbackID";

        }
    }

    /**
     *
     * @param fbID The feedback ID
     * @param ibfp the feedback presenter of an interface IFBP
     */

    public void WriteFeedbackToConsole(String fbID, IFeedbackPresenter ibfp) {
        String tobewritten = this.WriteFeedback(fbID);
        ibfp.PrintFeedback(tobewritten);
    }

    /**
     *
     * @param ibfp The Interface IFBP to be passed.
     */
    public void WriteMostRecentFeedback(IFeedbackPresenter ibfp) {
        String tobewrittenID;
        try {
            tobewrittenID = this.FindMostRecentFeedback().getFeedbackID();

            String tobewritten = this.WriteFeedback(tobewrittenID);
            ibfp.PrintFeedback(tobewritten);
        }
        catch (Exception e3) {
            ibfp.PrintFeedback("ERROR: MESSAGE UNQUEUEABLE");
        }
    }

    /**
     *
     * @param ifbp THE IFeedbackPresenter Interface object
     */

    public void WriteAllFeedback(IFeedbackPresenter ifbp) {
        ArrayList<String> Feedbacks = getStrings();
        ifbp.PrintFeedbackList(Feedbacks);
    }

    /**
     *
     * @return A list of sorted feedback string representations!
     */
    private ArrayList<String> getStrings() {
        ArrayList<String> Feedbacks = new ArrayList<String>();
        for (Feedback FB: this.SortedFeedbacks()
             ) {
            Feedbacks.add(FB.toString());
        }
        return Feedbacks;
    }

    /**
     *
     * @param ifbp Uses the IFeedbackPresenter to write to an external file.
     */
    public void WriteToExternalFile(IFeedbackPresenter ifbp) {
        ifbp.DocExport(this.getStrings());
    }


}
