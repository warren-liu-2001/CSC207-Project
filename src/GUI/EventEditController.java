package GUI;

import Controllers.UseCaseStorage;
import Controllers.UserAccountSystem;
import Exceptions.DoesNotExistException;
import Exceptions.ObjectInvalidException;
import Gateway.Serializer;
import Presenters.ScheduleViewer;
import UseCases.EventSearch;
import UseCases.TalkManager;
import UseCases.UserType;
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
 * Controller for the EventEditScreen
 */
public class EventEditController implements Initializable {
    private final Stage mainStage;
    private final UserAccountSystem uas;
    private final String id;

    // speaker lists
    private ArrayList<String> addedSpeakersList;
    private ArrayList<String> allSpeakersList;

    @FXML
    private TextField field4; // confirm user password / talk capacity / room capacity
    @FXML
    private Text isSuccessful;

    @FXML
    public ListView<String> addedSpeakers;
    @FXML
    public ListView<String> allSpeakers;

    @FXML
    private Button confirmUpdateButton;

    //Assume isTalk return true for both Panels and Talks

    /**
     * Initializes an EventEditController to control the EventEditScreen
     * @param main stage ths screen will be on
     * @param uas UserAccountSystem containing conference data
     * @param id
     */
    public EventEditController(Stage main, UserAccountSystem uas, String id) {
        this.mainStage = main;
        this.uas = uas;
        this.id = id;
        if (uas.getUseCaseStorage().getTalkManager().isEvent(id)){
            this.allSpeakersList = uas.getUseCaseStorage().getUserManager().getUserNames(UserType.SPEAKER);
            try {
                this.addedSpeakersList = uas.getUseCaseStorage().getTalkManager().getTalkSpeaker(id);
            } catch (DoesNotExistException e) {
                e.printStackTrace();
                this.addedSpeakersList = new ArrayList<String>();
            }
            for(String same : allSpeakersList){
                if (addedSpeakersList.contains(same))
                    addedSpeakersList.remove(same);
            }

        } else{
            this.allSpeakersList = new ArrayList<String>();
            this.addedSpeakersList = new ArrayList<String>();
        }
    }

    /**
     * Reset the appearance of the EventEditScreen
     * @param location
     * @param resources
     */
    @Override
    public void initialize(URL location, ResourceBundle resources) {
//        this.allSpeakersList = uas.getUseCaseStorage().getUserManager().getUserNames(UserType.SPEAKER);
//        this.addedSpeakersList =
//                uas.getUseCaseStorage().get;
        isSuccessful.setText("");

    }

    /**
     * Moves the selected speaker from the lists of all speakers to the list of added speakers.
     */
    public void addSpeaker() {
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
    public void removeSpeaker() {
        String removedSpeaker = addedSpeakers.getSelectionModel().getSelectedItem();
        if (removedSpeaker != null) {
            addedSpeakersList.remove(removedSpeaker);
            allSpeakersList.add(removedSpeaker);
            this.updateLists();
        }
    }

    /**
     * Go back to scheduler screen
     */
    @FXML
    private void goToScheduler() {
        new SchedulePanel(uas).loadSchedulePanel(mainStage);
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
        new UserMenuPanel(uas).loadAttendeeMenuPanel(mainStage);
    }

    /**
     * Update event once the confirm button is clicked
     */
    @FXML
    private void updateEvent() throws DoesNotExistException, ObjectInvalidException {
        boolean successful = false;
        int capacity = Integer.parseInt(field4.getText());
        System.out.println(capacity);
        System.out.println(uas.getUseCaseStorage().getTalkManager().isTalk(id));
        System.out.println(uas.getUseCaseStorage().getTalkManager().isEvent(id));

        if (uas.getUseCaseStorage().getTalkManager().isTalk(id)) {
            System.out.println(0);
            successful = updateTalk(capacity);
         } else  {
            System.out.println(1);
            successful = uas.getUseCaseStorage().getTalkManager().changeCapacity(id,capacity);
        }

        // show if successful or not
        if (successful) {
            isSuccessful.setText("Updated Successfully");
        } else {
            isSuccessful.setText("Update Failed");
        }
    }

    /**
     * Helper to update Talk and Panel types of events specifically
     */
    private boolean updateTalk(int capacity) throws DoesNotExistException, ObjectInvalidException {
        //TODO Check for Talk vs Panel
        TalkManager tm = uas.getUseCaseStorage().getTalkManager();
        boolean pt1 = tm.changeCapacity(id,capacity);
        boolean pt2 = tm.replaceSpeakers(id, addedSpeakersList, uas.getUseCaseStorage().getUserManager());
        System.out.println(pt1 +" " + pt2);
        return pt1 && pt2;
    }
}
