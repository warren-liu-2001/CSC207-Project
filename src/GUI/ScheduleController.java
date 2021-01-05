package GUI;

import Controllers.UserAccountSystem;
import Exceptions.*;
import Presenters.ScheduleViewer;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

/**
 * Controls the ScheduleScreen
 */
public class ScheduleController implements Initializable {

    private final Stage mainStage;
    private final ScheduleViewer scheduleViewer;
    private final UserAccountSystem uas;
    private UserMenuPanel userMenuPanel;

    /**
     * Initializes a ScheduleController to control the ScheduleScreen
     * @param main
     * @param uas
     */
    public ScheduleController(Stage main, UserAccountSystem uas) {
        this.mainStage = main;
        this.uas = uas;
        this.userMenuPanel = new UserMenuPanel(uas);
        this.scheduleViewer = new ScheduleViewer(uas.getUserAccount(), uas.getUseCaseStorage());
    }


    @FXML
    private ListView<String> registeredEvents;

    @FXML
    private ListView<String> availableEvents;

    @FXML
    private Text registered;

    @FXML
    private Text mainList;

    @FXML
    private MenuItem deleteEventA;

    @FXML
    private MenuItem editEventA;

    @FXML
    private MenuItem registerEventA;

    /**
     * Initializes the controller class.
     * @param resources
     * @param location
     */
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        if(uas.getUserAccount().isAttendee()) {
            deleteEventA.setVisible(false);
            editEventA.setVisible(false);
        }
        if(uas.getUserAccount().isSpeaker()){
            registered.setText("");
            registeredEvents.setVisible(false);
            mainList.setText("Talks and Panels you are giving:");
        }
        if (uas.getUserAccount().isOrganizer()) {
            registerEventA.setVisible(false);
            registeredEvents.setVisible(false);
        }
        this.updateAvailable();
        this.updateRegistered();

    }

    @FXML
    private PasswordField confirm;

    @FXML
    private Text actiontarget;

    /**
     * Logs the user out of the account and loads the LoginScreen
     */
    @FXML
    private void handleLogOut() {
        uas.getUserAccount().logout();
        new LoginPanel(uas).loadLogInPanel(mainStage);
    }

    /**
     * Registers the user in the event selected.
     */
    @FXML
    private void registerInEvent() {
        try {
            String selected = availableEvents.getSelectionModel().getSelectedItem();
            String id = selected.split(" - ")[selected.split(" - ").length-1];       //get Selected talk id
            uas.getRegistrar().register(uas.getUserAccount().getUserID(),id);
            this.updateRegistered();
        } catch (DoesNotExistException e) {
            e.printStackTrace();
        } catch (ObjectInvalidException e) {
            e.printStackTrace();
        }
    }

    /**
     * Updates the event lists
     */
    private void updateRegistered() {
        try {
            registeredEvents.getItems().clear();
            registeredEvents.getItems().removeAll();
            ArrayList<String> registered = scheduleViewer.viewMyEvents(uas.getUseCaseStorage());
            registeredEvents.getItems().addAll(registered);
        } catch (DoesNotExistException e) {
            e.printStackTrace();
        } catch (ObjectInvalidException e) {
            e.printStackTrace();
        }
    }

    /**
     * Loads the menu screen
     */
    @FXML
    private void returnToMenu() {
        userMenuPanel.loadAttendeeMenuPanel(mainStage);
    }

    /**
     * Cancel's the user's spot in an event
     */
    @FXML
    private void unenrollFromEvent() {
        try {
            String selected = registeredEvents.getSelectionModel().getSelectedItem();
            String id = selected.split(" - ")[selected.split(" - ").length - 1];       //get Selected user email
            uas.getRegistrar().cancel(uas.getUserAccount().getUserID(), id);                                          //call add friend method
            this.updateAvailable();
            updateRegistered();
        } catch (DoesNotExistException e) {
            e.printStackTrace();
        } catch (ObjectInvalidException e) {
            e.printStackTrace();
        }

    }

    /**
     * Update the list of available events
     */
    private void updateAvailable() {
        try {
            availableEvents.getItems().clear();

            if(uas.getUserAccount().isSpeaker()) {
                availableEvents.getItems().addAll(scheduleViewer.getHostingTalks(uas));
            } else {
                ArrayList<String> available = scheduleViewer.viewAvailableEvents();
                availableEvents.getItems().setAll(available);
            }
        } catch (DoesNotExistException e) {
            e.printStackTrace();
        } catch (ObjectInvalidException e) {
            e.printStackTrace();
        }
    }

    /**
     * Deletes an event
     */
    @FXML
    private void handleDeleteEventA(){
        String selected = availableEvents.getSelectionModel().getSelectedItem();
        if (selected != null) {
            String id = selected.split(" - ")[selected.split(" - ").length-1];       //get Selected user email
            uas.getTalkOrganizer().removeEvent(id);
            updateAvailable();
        }
    }

    /**
     * Edits an event by loading the EventEditScreen
     */
    @FXML
    private void handleEditEventA() {
        String selected = availableEvents.getSelectionModel().getSelectedItem();
        if (selected != null) {
            String id = selected.split(" - ")[selected.split(" - ").length-1];       //get Selected user email
            new EventEditPanel(uas).loadEventEditScreen(mainStage,id);
        }
    }
}
