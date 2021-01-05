package Controllers;

import UseCases.*;
import Presenters.*;

public class FeedbackControllerFactory {
    private FeedbackController fbc;

    /**
     * creates a new instance of FeedbackController
     */

    public FeedbackControllerFactory(){
        FeedbackManager fbm = new FeedbackManager();
        FeedbackPresenter fbp = new FeedbackPresenter();
        this.fbc = new FeedbackController(fbm, fbp);

    }

    /**
     *
     * @return the instance of the created FeedbackController
     */
    public FeedbackController getFeedbackController() {
        return this.fbc;
    }
}
