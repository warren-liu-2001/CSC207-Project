package GUI;

import Controllers.UserAccountSystem;
import Exceptions.DoesNotExistException;
import UseCases.UserType;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.text.*;
import javafx.stage.Stage;

import java.lang.reflect.Array;
import java.net.URL;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.GregorianCalendar;
import java.util.ResourceBundle;

/**
 * Controller for the CreationScreen. Handles organizer functions related to creating accounts, rooms, events, etc.
 */
public class CreationScreenController implements Initializable {
    private Stage mainStage;
    private UserAccountSystem uas;
    private String objectType; // to keep track of what we are creating
    private UserMenuPanel userMenuPanel;
    private String[] creatableItems;
    private String[] userTypes;
    private String[] eventTypes;
    private ArrayList<String> addedSpeakersList;
    private ArrayList<String> allSpeakersList;

    @FXML
    private TextField field1; // user email / room name
    @FXML
    private TextField field2; // user name / talk|room start time (HH:MM)
    @FXML
    private TextField field3; // user password / talk duration / room close time
    @FXML
    private TextField field4; // confirm user password / talk capacity / room capacity
    @FXML
    private TextField field5; // room of talk
    @FXML
    private DatePicker datePicker; // to pick talk date

    @FXML
    private Text prompt1; // user email / talk title / room name
    @FXML
    private Text prompt2; // user name / talk|room start time (HH:MM)
    @FXML
    private Text prompt3; // user pass / talk duration / room close time (HH:MM)
    @FXML
    private Text prompt4; // confirm user pass / talk|room capacity
    @FXML
    private Text prompt5; // talk date
    @FXML
    private Text prompt6; // talk room location
    @FXML
    private Text isSuccessful;
    @FXML
    private Text addedSpeakersText;
    @FXML
    private Text allSpeakersText;

    @FXML
    private ListView<String> addedSpeakers;
    @FXML
    private ListView<String> allSpeakers;

    @FXML
    private ComboBox<String> itemSelector;
    @FXML
    private Button confirmOptionButton;
    @FXML
    private ComboBox<String> typeSelector;
    @FXML
    private Button confirmCreationButton;


    /**
     * Initialize a new CreationScreenController to control the CreationScreen.
     * @param main the stage for the screen
     * @param uas UserAccountSystem holding the info of the conference
     */
    public CreationScreenController(Stage main, UserAccountSystem uas) {
        this.mainStage = main;
        this.uas = uas;
        this.objectType = "";
        this.userMenuPanel = new UserMenuPanel(uas);
        this.allSpeakersList = uas.getUseCaseStorage().getUserManager().getUserEmails(UserType.SPEAKER);
        this.addedSpeakersList = new ArrayList<String>();
    }

    /**
     * Reset the screen when the CreationScreen is selected.
     * @param location
     * @param resources
     */
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        this.resetCreationFields();

        // initialize the ComboBoxes
        creatableItems = new String[]{"Create a User", "Create a Room", "Create an Event"}; //"Create a Party", "Create a Panel", "Create a Talk"};
        userTypes = new String[]{"Attendee", "Organizer", "Speaker", "Admin"};
        eventTypes = new String[]{"Party", "Panel", "Talk"};
        itemSelector.getItems().addAll(creatableItems);
    }

    /**
     * Helper method to reset/hide the creation buttons and text fields
     */
    private void resetCreationFields() {
        // hide the prompts and fields
        field1.setVisible(false);
        field2.setVisible(false);
        field3.setVisible(false);
        field4.setVisible(false);
        field5.setVisible(false);
        prompt1.setText("");
        prompt2.setText("");
        prompt3.setText("");
        prompt4.setText("");
        prompt5.setText("");
        prompt6.setText("");
        field1.setText("");
        field2.setText("");
        field3.setText("");
        field4.setText("");
        field5.setText("");
        addedSpeakersText.setText("");
        allSpeakersText.setText("");
        typeSelector.setVisible(false);
        datePicker.setVisible(false);
        confirmCreationButton.setVisible(false);
        this.objectType = "";
        isSuccessful.setText("");
        allSpeakers.setVisible(false);
        addedSpeakers.setVisible(false);

        // clear speaker and type lists
        typeSelector.getItems().clear();
        addedSpeakers.getItems().clear();
        allSpeakers.getItems().clear();

        // initialize speaker lists
        this.allSpeakersList = uas.getUseCaseStorage().getUserManager().getUserEmails(UserType.SPEAKER);
        this.addedSpeakersList = new ArrayList<String>();
    }

    /**
     * Display the correct fields and text needed to allow the user to create the item they specified.
     */
    @FXML
    private void confirmOptionButtonClicked() {
        this.resetCreationFields();


        objectType = itemSelector.getValue();
        if (objectType.equals("Create a User")) {
            // make fields visible
            field1.setVisible(true);
            field2.setVisible(true);
            field3.setVisible(true);
            field4.setVisible(true);
            typeSelector.setVisible(true);
            confirmCreationButton.setVisible(true);

            // prompt text initialization
            prompt1.setText("Enter the account's email: ");
            prompt2.setText("Enter the account's name: ");
            prompt3.setText("Enter the account's password: ");
            prompt4.setText("Confirm password: ");

            // add items to the lists
            typeSelector.getItems().addAll(userTypes);

        } else if (objectType.equals("Create a Room")) {

            // make fields visible
            field1.setVisible(true);
            field2.setVisible(true);
            field3.setVisible(true);
            field4.setVisible(true);
            confirmCreationButton.setVisible(true);

            // prompt text initialization
            prompt1.setText("Enter the name of the Room: ");
            prompt2.setText("Enter the Room open time (HH:MM): ");
            prompt3.setText("Enter the Room closing time (HH:MM): ");
            prompt4.setText("Enter the capacity of the Room");

        } else if (objectType.equals("Create an Event")) {
            // make fields visible
            datePicker.setVisible(true);
            field1.setVisible(true);
            field2.setVisible(true);
            field3.setVisible(true);
            field4.setVisible(true);
            field5.setVisible(true);
            typeSelector.setVisible(true);
            confirmCreationButton.setVisible(true);
            addedSpeakers.setVisible(true);
            allSpeakers.setVisible(true);

            // prompt text initialization
            prompt1.setText("Enter the title of the Event: ");
            prompt2.setText("Enter the Event start time (HH:MM): ");
            prompt3.setText("Enter the duration of the Event (in minutes): ");
            prompt4.setText("Enter the capacity of the Event");
            prompt5.setText("Enter the date of the Event: ");
            prompt6.setText("Enter the room name this Event is in: ");
            addedSpeakersText.setText("Added Speakers");
            allSpeakersText.setText("All Speakers");

            // add items to the lists
            typeSelector.getItems().addAll(eventTypes);
            allSpeakers.getItems().addAll(allSpeakersList);
        }
    }

    /**
     * Create the object specified when the user clicked on the confirm button.
     */
    @FXML
    private void createObject() {

        Boolean successful = false;

        if (objectType.equals("Create a User")) {
            successful = createUser();
        } else if (objectType.equals("Create a Room")) {
            successful = createRoom();
        } else if (objectType.equals("Create an Event")) {
            successful = createEvent();

        }

        // show if successful or not
        if (successful) {
            isSuccessful.setText("Creation succeeded");
        } else {
            isSuccessful.setText("Creation failed");
        }

    }

    /**
     * Helper method that creates a user based on type of account selected and the info provided.
     * @return Whether the user creation was successful or not
     */
    private Boolean createUser() {
        Boolean successful = false;
        // check if password matches the confirmation
        if (field3.getText().equals(field4.getText())) {
            // determine which type of user to create, and create it
            String accountType = typeSelector.getValue();
            if (accountType.equals("Attendee")) {
                successful = uas.getUserAccount().createAttendee(field1.getText(), field2.getText(), field3.getText());
            } else if (accountType.equals("Organizer")) {
                successful = uas.getUserAccount().createOrganizer(field1.getText(), field2.getText(), field3.getText());
            } else if (accountType.equals("Admin")) {
                successful = uas.getUserAccount().createAdmin(field1.getText(), field2.getText(), field3.getText());
            } else if (accountType.equals("Speaker")) {
                successful = uas.getUserAccount().createSpeaker(field1.getText(), field2.getText(), field3.getText());
            } else {
                successful = false;
            }
        } else {
            successful = false;
        }
        return successful;
    }

    /**
     * Helper method that creates a Room based on the info provided.
     * @return Whether the Room creation was successful or not
     */
    private Boolean createRoom() {
        Boolean successful = false;
        try {
            String roomName = field1.getText();

            // get room opening time
            String roomOpenTime = field2.getText();

            int[] openHourMin = new int[2];
            if (!getHourAndMinute(openHourMin, roomOpenTime)) {
                return false; // invalid hour/min format
            }

            // get room closing time
            String roomCloseTime = field3.getText();

            int[] closingHourMin = new int[2];
            if (!getHourAndMinute(closingHourMin, roomCloseTime)) {
                return false; // invalid hour/min format
            }

            //  check if closing hour is before open hour
            if (closingHourMin[0] < openHourMin[0]) {
                return false;
            } else if (closingHourMin[0] == openHourMin[0]){
                // need to compare the minutes
                if (closingHourMin[1] <= openHourMin[1]) {
                    return false;
                }
            }

            // get capacity
            int roomCapacity = Integer.parseInt(field4.getText());

            // create the room
            successful = uas.getUserAccount().createRoom(openHourMin[0], openHourMin[1], closingHourMin[0], closingHourMin[1], roomCapacity, roomName); // does not work atm
        } catch (NumberFormatException e) {
            return false;
        }
        return successful;
    }

    /**
     * Helper method that creates a Event based on the info provided.
     * @return Whether the Event creation was successful or not
     */
    private Boolean createEvent() {
        Boolean successful = false;
        try {
            // talk title
            String title = field1.getText();

            // Parse the starting time of the event
            String time = field2.getText();
            int[] hourMin = new int[2];
            if (!getHourAndMinute(hourMin, time)) {
                return false; // invalid hour/min format
            }

            LocalTime startTime = LocalTime.of(hourMin[0], hourMin[1]);

            // duration and capacity of the event
            int duration = Integer.parseInt(field3.getText());
            int capacity = Integer.parseInt(field4.getText());

            // date of the event
            LocalDate date = datePicker.getValue();
            GregorianCalendar gcDate = new GregorianCalendar(date.getYear(), date.getMonthValue(), date.getDayOfMonth());

            // speakers of the event
            ArrayList<String> speakerEmails = addedSpeakersList;

            // id of the person organized this event
            String eventOrganizer = uas.getUserAccount().getUserID();
            ArrayList<String> eventOrganizerList= new ArrayList<>();
            eventOrganizerList.add(eventOrganizer);

            // check which event to create
            String eventType = typeSelector.getValue();

            // room of the event
            String roomName = field5.getText();

            // could also just automatically create the corresponding event type based on # of speakers

            // get room id from the name
            String roomID = uas.getUseCaseStorage().getRoomManager().findRoomID(roomName);
            if (eventType.equals("Party")) {
                // check if no speakers
                if (speakerEmails.size() == 0) {
                    // create a party
                    successful = uas.getTalkOrganizer().addParty(title, gcDate, startTime, duration, eventOrganizerList, roomID, capacity);
                } else {
                    successful = false;
                }
            } else if (eventType.equals("Talk")) {
                // check if only 1 speaker
                if (speakerEmails.size() == 1) {
                    // create a talk
                    successful = uas.getTalkOrganizer().addTalk(title, gcDate, startTime, speakerEmails.get(0), duration, eventOrganizerList, roomID, capacity);
                } else {
                    successful = false;
                }
            } else if (eventType.equals("Panel")) {
                // check if >= 2 speakers
                if (speakerEmails.size() >= 2) {
                    // create a panel
                    successful = uas.getTalkOrganizer().addPanel(title, gcDate, startTime, speakerEmails, duration, eventOrganizerList, roomID, capacity);
                } else {
                    successful = false;
                }
            } else {
                successful = false;
            }
        } catch (NumberFormatException e) {
            // something like the user entered a word in the duration field happened
            System.out.println("Incorrect number formatting");
            successful = false;
        } catch (DoesNotExistException e) {
            // Event was not created successfully
            System.out.println("Failed to create event");
            successful = false;
        }
        return successful;
    }

    /**
     * Helper method that creates a Room based on the info provided.
     * @param hourMin Array that will containing the hour and index 0 and minute and index 1
     * @param time String to get the time from
     * @return Whether hour and minute was extracted from the time properly
     */
    private boolean getHourAndMinute(int[] hourMin, String time) {
        // Parse the starting time of the event
        int hour;
        int minute;

        // check if formatted correctly
        try {
            String[] split_time = time.split(":");

            if (split_time.length != 2) {
                return false;
            }

            hour = Integer.parseInt(split_time[0]);
            minute = Integer.parseInt(split_time[1]);

            // then check if 0 <= hour, minute <= 23 or 59
            if (hour < 0 || hour > 23 || minute < 0 || minute > 59) {
                return false;
            }
        } catch (NumberFormatException e) {
            return false;
        }
        // hour and minute are valid, so continue
        hourMin[0] = hour;
        hourMin[1] = minute;
        return true;
    }

    /**
     * Moves the selected speaker from the lists of all speakers to the list of added speakers.
     */
    @FXML
    private void addSpeaker() {
        String addedSpeaker = allSpeakers.getSelectionModel().getSelectedItem();
        if (addedSpeaker != null) {
            addedSpeakersList.add(addedSpeaker);
            allSpeakersList.remove(addedSpeaker);
            this.updateLists();
        }
    }

    /**
     * Moves the selected speaker from the lists of added speakers to the list of all speakers.
     */
    @FXML
    private void removeSpeaker() {
        String removedSpeaker = addedSpeakers.getSelectionModel().getSelectedItem();
        if (removedSpeaker != null) {
            addedSpeakersList.remove(removedSpeaker);
            allSpeakersList.add(removedSpeaker);
            this.updateLists();
        }
    }

    /**
     * Helper method that updates the list of speakers
     */
    private void updateLists() {
        addedSpeakers.getItems().clear();
        allSpeakers.getItems().clear();
        addedSpeakers.getItems().addAll(addedSpeakersList);
        allSpeakers.getItems().addAll(allSpeakersList);
    }

    /**
     * Returns to the menu screen
     */
    @FXML
    private void returnToMenu() {
        userMenuPanel.loadAttendeeMenuPanel(mainStage);
    }
}
