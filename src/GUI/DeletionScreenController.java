package GUI;

import Controllers.UserAccountSystem;
import Exceptions.DoesNotExistException;
import Exceptions.ObjectInvalidException;
import Presenters.DisplayInfo;
import Presenters.ScheduleViewer;
import UseCases.UserType;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.scene.text.*;
import javafx.stage.Stage;

import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

/**
 * Controls the elements on the DeletionScreen
 */
public class DeletionScreenController implements Initializable {
    private Stage mainStage;
    private UserAccountSystem uas;
    private UserMenuPanel userMenuPanel;
    private DisplayInfo displayInfo;
    private String selectedUserID;
    private int messageIndex; // index of selected message to delete

    @FXML
    private Text messageDeletedSuccessful;
    @FXML
    private Text messageSelectionPrompt;

    @FXML
    private ComboBox<String> messageList;
    @FXML
    private ComboBox<String> userList;

    // message display
    @FXML
    private GridPane messageDisplay;
    @FXML
    private Text messageSender;
    @FXML
    private Text messageRecipient;
    @FXML
    private Text messageSubject;
    @FXML
    private Text messageBody;

    @FXML
    private Button deleteMessageButton;

    /**
     * Initializes a DeletionScreenController.
     * @param main the stage for this controller.
     * @param uas the UserAccountSystem with the data of the conference.
     */
    public DeletionScreenController(Stage main, UserAccountSystem uas) {
        this.mainStage = main;
        this.uas = uas;
        this.userMenuPanel = new UserMenuPanel(uas);
        //this.sv = new ScheduleViewer(uas.getUserAccount(), uas.getUseCaseStorage());
        this.selectedUserID = "";
        this.messageIndex = 0;
        this.displayInfo = new DisplayInfo();
    }

    /**
     * Reset screen upon being loaded.
     * @param location
     * @param resources
     */
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        messageSelectionPrompt.setText("");
        messageDisplay.setVisible(false);
        this.selectedUserID = "";
        this.messageIndex = 0;
        this.deleteMessageButton.setVisible(false);
        messageDeletedSuccessful.setText("");

        // initialize lists
        messageList.getItems().clear();
        messageList.setVisible(false);
        userList.getItems().clear();
        userList.getItems().addAll(displayInfo.getUserEmails(uas.getUseCaseStorage().getUserManager()));
    }

    /**
     * Show the list of messages this user received
     */
    @FXML
    private void userSelected() {
        // hide the message display, if it is showing
        messageDisplay.setVisible(false);
        deleteMessageButton.setVisible(false);

        // get that user's messages in the combobox
        messageSelectionPrompt.setText("Select a message to delete (subject):");
        messageList.setVisible(true);
        messageList.getItems().clear();
        try {
            this.selectedUserID = displayInfo.getIDByEmail(uas.getUseCaseStorage().getUserManager(), userList.getValue());
        } catch (DoesNotExistException | ObjectInvalidException e) {
            // do nothing, means the user hasn't received any messages yet
        }
        messageList.getItems().addAll(uas.getUseCaseStorage().getInboxManager().getMessageSubjects(selectedUserID));
    }

    /**
     * Display the selected message
     */
    @FXML
    private void messageSelected() {
        // display delete button
        deleteMessageButton.setVisible(true);

        // display that message
        messageDisplay.setVisible(true);

        messageIndex = messageList.getSelectionModel().getSelectedIndex();

        messageSender.setText(displayInfo.getMessageSenders(uas.getUseCaseStorage().getInboxManager(), selectedUserID).get(messageIndex));
        messageRecipient.setText(userList.getValue());
        messageSubject.setText(displayInfo.getMessageSubjects(uas.getUseCaseStorage().getInboxManager(), selectedUserID).get(messageIndex));
        messageBody.setText(displayInfo.getMessageContents(uas.getUseCaseStorage().getInboxManager(), selectedUserID).get(messageIndex));
    }

    /**
     * Delete the selected message when the button is clicked
     */
    @FXML
    private void deleteMessage() {
        // delete the message
        String subject = displayInfo.getMessageSubjects(uas.getUseCaseStorage().getInboxManager(), selectedUserID).get(messageIndex);
        String body = displayInfo.getMessageContents(uas.getUseCaseStorage().getInboxManager(), selectedUserID).get(messageIndex);

        if (uas.getUserAccount().deleteMessage(uas.getMessenger(), selectedUserID, subject, body)) {
            messageDeletedSuccessful.setText("Message deleted");

            // remove the message from the list and reset the message display
            messageDisplay.setVisible(false);
            messageList.getItems().clear();
            try {
                this.selectedUserID = displayInfo.getIDByEmail(uas.getUseCaseStorage().getUserManager(), userList.getValue());
            } catch (DoesNotExistException | ObjectInvalidException e) {
                // do nothing, means the user hasn't received any messages yet
            }

        } else {
            messageDeletedSuccessful.setText("Could not delete message");
        }
    }

    /**
     * Returns to the menu screen
     */
    @FXML
    private void returnToMenu() {
        userMenuPanel.loadAttendeeMenuPanel(mainStage);
    }
}
