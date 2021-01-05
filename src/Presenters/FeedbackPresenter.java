package Presenters;

import Controllers.FeedbackController;
import Gateway.FeedbackFileExporter;
import UseCases.*;

import java.util.ArrayList;

/**
 * Feedback Presenter Class
 *
 * @version 2.0
 */

public class FeedbackPresenter implements IFeedbackPresenter, java.io.Serializable{


    private FeedbackManager fbm;

    /**
     * The feedback Presenter Initiation
     */
    public FeedbackPresenter() {
    }

    /**
     *
     * @param Information the information needed
     * @return the information to be presented
     */

    public String PrintFeedback(String Information) {
        return Information;
    }

    /**
     *
     * @param InfoList List of feedbacks
     * @return pass that same list of feedbacks onwards
     */
    @Override
    public ArrayList<String> PrintFeedbackList(ArrayList<String> InfoList) {
        return InfoList;
    }

    /**
     *
     * @param list List of info to be exported into a doc
     */
    public void DocExport(ArrayList<String> list) {
        FeedbackFileExporter fbfe = new FeedbackFileExporter();
        fbfe.setFbp(this);
        fbfe.FeedbackExport(list);
    }

    /**
     *
     * @param message message to be printed
     * @return the actual message
     */
    public String MessagePrinter(String message) {
        return message;
    }
}
