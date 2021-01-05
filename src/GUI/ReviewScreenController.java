package GUI;

import Controllers.UserAccountSystem;
import UseCases.UserType;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class ReviewScreenController implements Initializable {

    private Stage mainStage;
    private UserAccountSystem uas;
    private UserMenuPanel userMenuPanel;

    @FXML
    private ComboBox<String> ratingOptions;
    @FXML
    private TextArea review;
    @FXML
    private Text isSuccessful;


    /**
     * Initialize a new ReviewScreenController to control the ReviewScreen.
     * @param main the stage for the screen
     * @param uas UserAccountSystem holding the info of the conference
     */
    public ReviewScreenController(Stage main, UserAccountSystem uas) {
        this.mainStage = main;
        this.uas = uas;
        this.userMenuPanel = new UserMenuPanel(uas);
    }


    /**
     * Called to initialize a controller after its root element has been
     * completely processed.
     */
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        isSuccessful.setText("");
        ratingOptions.getItems().addAll(new String[]{"1 star >:(", "2 stars :(", "3 stars :/", "4 stars :)", "5 stars :D"});
    }

    /**
     * Submits the feedback the user gave
     */
    @FXML
    private void submitRating() {
        int rating = ratingOptions.getSelectionModel().getSelectedIndex() + 1; // options are in increasing rating order
        String reviewText = review.getText();
        if (uas.getFeedbackController().giveFeedback(rating, reviewText)) {
            isSuccessful.setText("Feedback given successfully");
        } else {
            isSuccessful.setText("Could not receive Feedback. Try giving us 5 stars instead >:(");
        }
        // possibly restrict a user to only 1 feedback?
    }

    /**
     * Exports the feedback to a file
     */
    @FXML
    private void exportFeedback() {
        uas.getFeedbackController().promptFileExport();
        isSuccessful.setText("Feedback exported successfully"); // always succeeds?
    }

    /**
     * Returns to the menu screen
     */
    @FXML
    private void returnToMenu() {
        userMenuPanel.loadAttendeeMenuPanel(mainStage);
    }
}
