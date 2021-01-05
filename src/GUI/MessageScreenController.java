package GUI;

import Controllers.UserAccountSystem;
import Exceptions.DoesNotExistException;
import Exceptions.ObjectInvalidException;
import Presenters.Menus;
import UseCases.InboxManager;
//import com.sun.deploy.net.DownloadException;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

/**
 * Controller for the MessageScreen.fxml file. Handles functions related to messaging
 */

public class MessageScreenController implements Initializable {

    private final Stage mainStage;
    private UserMenuPanel userMenuPanel;
    private UserAccountSystem uas;

    // booleans to determine which button was clicked
    private Boolean privateMessageButtonClicked;
    private Boolean messageAttendeesButtonClicked;
    private Boolean messageSpeakersButtonClicked;
    private Boolean messageEventButtonClicked;

    // fxml things
    @FXML
    private Text fromOrTo; // says From: when viewing messages, says To (email/event/all attendees/all speakers): when sending message based on message type

    // elements relating to message display
    @FXML
    private Text sender;
    @FXML
    private Text messageSubject;
    @FXML
    private Label messageContent;
    @FXML
    private ComboBox<String> messageList;
    @FXML
    private Button viewMessageButton;

    // elements relating to message creation
    @FXML
    private Button createPrivateMessageButton;
    @FXML
    private Button messageAttendeesButton;
    @FXML
    private Button messageSpeakersButton;
    @FXML
    private Button messageEventButton;

    @FXML
    private TextField recipientField;
    @FXML
    private TextField subjectField;
    @FXML
    private TextField contentField;
    @FXML
    private Text isSuccessful;
    @FXML
    private Button sendMessageButton;

    /**
     * Initializes a MessageScreenController.
     * @param main the stage for this controller.
     * @param uas the UserAccountSystem with the data of the conference.
     */
    public MessageScreenController(Stage main, UserAccountSystem uas) {
        this.mainStage = main;
        this.uas = uas;
        this.userMenuPanel = new UserMenuPanel(uas);
        this.privateMessageButtonClicked = false;
        this.messageAttendeesButtonClicked = false;
        this.messageSpeakersButtonClicked = false;
        this.messageEventButtonClicked = false;
    }

    /**
     * Resets the state of the screen when the MessageScreen is loaded.
     * @param location
     * @param resources
     */
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        // hide message creation fields
        subjectField.setVisible(false);
        recipientField.setVisible(false);
        contentField.setVisible(false);
        sendMessageButton.setVisible(false);

        // pick which buttons to hide or display based on account type
        if (uas.getUserAccount().isOrganizer()) {
            messageAttendeesButton.setVisible(true);
            messageSpeakersButton.setVisible(true);
            messageEventButton.setVisible(false);
        } else if (uas.getUserAccount().isSpeaker()) {
            messageAttendeesButton.setVisible(false);
            messageSpeakersButton.setVisible(false);
            messageEventButton.setVisible(true);
        } else {
            // user is admin / attendee, no special buttons for them
            messageAttendeesButton.setVisible(false);
            messageSpeakersButton.setVisible(false);
            messageEventButton.setVisible(false);
        }

        // reset button clicked status
        this.privateMessageButtonClicked = false;
        this.messageAttendeesButtonClicked = false;
        this.messageSpeakersButtonClicked = false;
        this.messageEventButtonClicked = false;

        // default msg text
        isSuccessful.setText("");
        sender.setText("Message Sender Name");
        messageSubject.setText("Message Subject");
        messageContent.setText("This is where the message's content will appear. Select a message and click the view message button to view a message.");
        fromOrTo.setText("From:");

        // add the message subjects to the messageList
        ArrayList<String> msgSubjectsArrayList = uas.getUseCaseStorage().getInboxManager().getMessageSubjects(uas.getUserAccount().getUserID());
        String[] messageSubjects = new String[msgSubjectsArrayList.size()];
        for (int i = 0; i < msgSubjectsArrayList.size(); i++) {
            messageSubjects[i] = msgSubjectsArrayList.get(i);
        }
        messageList.getItems().addAll(messageSubjects);
    }

    /**
     * Views the message the user has selected in the drop down menu.
     */
    @FXML
    private void viewMessage() {
        InboxManager im = uas.getUseCaseStorage().getInboxManager();
        String userID = uas.getUserAccount().getUserID();
        // hide and reset message creation fields
        subjectField.setVisible(false);
        recipientField.setVisible(false);
        contentField.setVisible(false);
        sendMessageButton.setVisible(false);
        fromOrTo.setText("From:");
        isSuccessful.setText("");
        this.privateMessageButtonClicked = false;
        this.messageEventButtonClicked = false;
        this.messageAttendeesButtonClicked = false;
        this.messageSpeakersButtonClicked = false;

        // pick the message selected in the ListView and display it on the right, if there is a selected one
        String subject = messageList.getValue();
        int messageIndex = im.getMessageSubjects(userID).indexOf(subject); // will not work if there are multiple messages with the same subject
        if (messageIndex != -1) {
            // display the message
            sender.setText(im.getMessageSenders(userID).get(messageIndex));
            messageSubject.setText(subject);
            messageContent.setText(im.getMessageContents(userID).get(messageIndex));
        }
    }

    /**
     * Prepares the message screen to allow the user to send a private message.
     */
    @FXML
    private void createPrivateMessage() {
        this.enterMessageCreationMode();
        recipientField.setPromptText("email");
        this.privateMessageButtonClicked = true;
        this.messageEventButtonClicked = false;
        this.messageAttendeesButtonClicked = false;
        this.messageSpeakersButtonClicked = false;
    }

    /**
     * Prepares the message screen to allow the user to send a message to all users at an event.
     */
    @FXML
    private void createEventMessage() {
        this.enterMessageCreationMode();
        recipientField.setPromptText("event name");
        this.privateMessageButtonClicked = false;
        this.messageEventButtonClicked = true;
        this.messageAttendeesButtonClicked = false;
        this.messageSpeakersButtonClicked = false;
    }

    /**
     * Prepares the message screen to allow the user to send a message to all Attendees.
     */
    @FXML
    private void createAttendeeMessage() {
        this.enterMessageCreationMode();
        // hide the recipient prompt field - the recipient is all attendees
        recipientField.setVisible(false);
        sender.setText("All Attendees");
        this.privateMessageButtonClicked = false;
        this.messageEventButtonClicked = false;
        this.messageAttendeesButtonClicked = true;
        this.messageSpeakersButtonClicked = false;

    }

    /**
     * Prepares the message screen to allow the user to send a message to all Speakers.
     */
    @FXML
    private void createSpeakerMessage() {
        this.enterMessageCreationMode();
        // hide the recipient prompt field - the recipient is all speakers
        recipientField.setVisible(false);
        sender.setText("All Speakers");
        this.privateMessageButtonClicked = false;
        this.messageEventButtonClicked = false;
        this.messageAttendeesButtonClicked = false;
        this.messageSpeakersButtonClicked = true;

    }

    /**
     * Helper method which hides the message display elements and shows the message creation elements
     */
    private void enterMessageCreationMode() {
        // hide some message display stuff by setting them to empty strings
        sender.setText("");
        messageSubject.setText("");
        messageContent.setText("");
        isSuccessful.setText("");

        // make the message sending things visible
        fromOrTo.setText("To:");
        sendMessageButton.setVisible(true);
        recipientField.setVisible(true);
        subjectField.setVisible(true);
        contentField.setVisible(true);
    }

    /**
     * Send the message as described by the user in the fields and message creation button they clicked
     */
    @FXML
    private void sendMessage() {
        String subject = subjectField.getText();
        String content = contentField.getText();
        Boolean successful = false;

        if (privateMessageButtonClicked) {
            String recipient = recipientField.getText();

            successful = uas.getUserAccount().sendMessage(subject, content, recipient, uas.getMessenger());

        } else if (messageEventButtonClicked) {
            String eventName = recipientField.getText();

            successful = uas.getUserAccount().messageEventByName(eventName, subject, content, uas.getMessenger());

        } else if (messageAttendeesButtonClicked) {
            successful = uas.getUserAccount().messageAllAttendees(subject, content, uas.getMessenger());
        } else if (messageSpeakersButtonClicked) {
            successful = uas.getUserAccount().messageAllSpeakers(subject, content, uas.getMessenger());
        }

        if (successful) {
            isSuccessful.setText("Message sent!");
        } else {
            isSuccessful.setText("Failed to send message");
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
