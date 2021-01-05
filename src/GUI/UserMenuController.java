package GUI;

import Controllers.UserAccountSystem;
import Exceptions.DoesNotExistException;
import Exceptions.ObjectInvalidException;
import Presenters.DisplayInfo;
import Presenters.Menus;
import UseCases.AttributeChanger;
import UseCases.UserManager;
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
 * Controller for the UserMenuScreen
 */
public class UserMenuController implements Initializable {
    private final Stage mainStage;
//    private UserMenuPanel userMenuPanel;
    private final UserAccountSystem uas;
    private LoginPanel loginPanel;
    private MessageScreenPanel messageScreenPanel;
    private CreationScreenPanel creationScreenPanel;
    private DeletionScreenPanel deletionScreenPanel;
    private ReviewScreenPanel reviewScreenPanel;

    // fxml things
//    private ComboBox menuOptions;
    private Button confirmOptionButton;

    @FXML
    private Text name;

    @FXML
    private Text email;

    @FXML
    private Text password;

    @FXML
    private ComboBox<String> menuOptions;

    @FXML
    private Text changeText;

    @FXML
    private TextField newChange;

    @FXML
    private Text userType;

    @FXML
    private ListView<String> friends;

    @FXML
    private ListView<String> searchResults;

    @FXML
    private MenuItem chosenUser;

    /**
     * Initialize a UserMenuController to control the UserMenuScreen
     * @param main Stage of the UserMenuScreen
     * @param uas UserAccountSystem with the conference data
     */
    public UserMenuController(Stage main, UserAccountSystem uas) {
        this.mainStage = main;
        this.uas = uas;
        this.loginPanel = new LoginPanel(uas);
        this.messageScreenPanel = new MessageScreenPanel(this.uas);
        this.creationScreenPanel = new CreationScreenPanel(this.uas);
        this.deletionScreenPanel = new DeletionScreenPanel(this.uas);
        this.reviewScreenPanel = new ReviewScreenPanel(this.uas);
    }

    /**
     * Adds the selected user to this user's friends list
     */
    @FXML
    private void handleAddFriendAction() {
        String user = searchResults.getSelectionModel().getSelectedItem();
        if (user != null) {
            String email = user.split(" - ")[user.split(" - ").length-1];       //get Selected user email
            uas.getUserAccount().addFriend(email);                                          //call add friend method
            this.updateFriendsList();                                                       //update the list
        }
    }

    /**
     * Removes the selected user from this user's friends list
     */
    @FXML
    private void handleRemoveFriendAction() {
        String user = friends.getSelectionModel().getSelectedItem();
        if (user != null) {
            String email = user.split(" - ")[user.split(" - ").length-1];       //get Selected user email
            uas.getUserAccount().removeFriend(email);                                          //call add friend method
            this.updateFriendsList();                                                       //update the list
        }
    }

    /**
     * Displays a text field to change personal information if the user selects so
     */
    @FXML
    private void handleOptionSelect() {
        String selected = menuOptions.getValue();
        if (selected.equals("Change your email") || selected.equals("Change your name") || selected.equals("Change your password")){
            changeText.setVisible(true);
            newChange.setVisible(true);
            changeText.setText("Enter new " + selected.split(" ")[selected.split(" ").length-1] +":");
        } else {
            changeText.setVisible(false);
            newChange.setVisible(false);
        }
    }

    /**
     * Perform the option that the user selected
     */
    @FXML
    private void handleConfirmAction() {
        String selected = menuOptions.getValue();
        try {
            switch (selected) {
                case "Change your email":
                    uas.getUserAccount().setEmail(newChange.getText());
                    newChange.setText("");
                    this.displayUserInfo();
                    break;
                case "Change your name":
                    uas.getUserAccount().setName(newChange.getText());
                    newChange.setText("");
                    this.displayUserInfo();
                    break;
                case "Change your password":
                    uas.getUserAccount().setPassword(newChange.getText());
                    newChange.setText("");
                    this.displayUserInfo();
                    break;
                case "Go to Scheduler":
                    new SchedulePanel(uas).loadSchedulePanel(mainStage);
                    break;
                case "Go to Inbox":
                    new MessageScreenPanel(uas).loadMessageScreenPanel(mainStage);
                    break;
                case "Create a User/Event/Room":
                    creationScreenPanel.loadCreationScreenPanel(mainStage);
                    break;
                case "Delete a Message":
                    deletionScreenPanel.loadDeletionScreenPanel(mainStage);
                    break;
                case "Leave a review":
                    reviewScreenPanel.loadReviewScreenPanel(mainStage);
                    break;
            }
        } catch (DoesNotExistException e) {
            e.printStackTrace();
        }
    }

    /**
     * Logs the user out of the account and loads the login screen
     */
    @FXML
    private void handleLogOut() {
        uas.getUserAccount().logout();
        loginPanel.loadLogInPanel(mainStage);
    }

    /**
     * Initializes the controller class.
     * @param resources
     * @param location
     */
    @Override
    public void initialize(URL location, ResourceBundle resources) {
//        name.setText(this.uas.getUserInfo().getName());
        changeText.setVisible(false);
        newChange.setVisible(false);
//        this.addOptionsDropDown(menuOptions,attendeeOptions);
        this.initDropDown();
        this.addOptionsDropDown();
        this.displayUserInfo();
        this.updateFriendsList();
        this.updateUserList();
    }

    /**
     * Displays the user info on the screen
     */
    private void displayUserInfo(){
        name.setText(uas.getUserInfo().getName());
        email.setText(uas.getUserInfo().getEmail());
        password.setText(uas.getUserInfo().getPassword());
        userType.setText(uas.getUserInfo().getType());
    }

    /**
     * Updates the list of users
     */
    private void updateUserList() {
        DisplayInfo d = new DisplayInfo();
        ArrayList<String> names = uas.getUserAccount().getUm().getUserNames();
        ArrayList<String> emails = uas.getUserAccount().getUm().getUserEmails();
        ArrayList<String> userList = d.combineUsersInfo(names, emails);
        userList.remove(uas.getUserInfo().getName() + " - " + uas.getUserInfo().getEmail());
        searchResults.getItems().setAll(userList);
    }

    /**
     * Updates the user's friend list
     */
    private void updateFriendsList() {
        ArrayList<String> names = new ArrayList<>();
        ArrayList<String> emails = new ArrayList<>();
        AttributeChanger ac = uas.getUserAccount().getAc();
        UserManager um = uas.getUserAccount().getUm();
        try {
            for (String id : uas.getUserInfo().viewContacts()) {
                names.add(ac.getUserName(id, um));
                emails.add(ac.getUserEmail(id, um));
            }
        } catch (DoesNotExistException e) {
            e.printStackTrace();
        } catch (ObjectInvalidException e) {
            e.printStackTrace();
        }
        DisplayInfo d = new DisplayInfo();
        friends.getItems().setAll(d.combineUsersInfo(names, emails));
    }

    /**
     * Initialize the menu drop down with the general user options
     */
    private void initDropDown(){
//        menuOptions.getItems().removeAll(menuOptions.getItems());
//        menuOptions.getSelectionModel().select("Option B");

        ArrayList<String> userOptions= new Menus().printUserMenu();

        menuOptions.getItems().addAll(userOptions);
    }

    /**
     * Initialize the menu drop down with specific account type options
     */
    private void addOptionsDropDown(){
        Menus menu = new Menus();
//        menuOptions.getItems().removeAll(menuOptions.getItems());

         if (this.uas.getUserAccount().isAttendee()){
            menuOptions.getItems().addAll(menu.printAttendeeMenu());
        } else if (this.uas.getUserAccount().isOrganizer()) {
            menuOptions.getItems().addAll(menu.printOrganizerMenu());
        } else if (this.uas.getUserAccount().isSpeaker()) {
            menuOptions.getItems().addAll(menu.printSpeakerMenu());
        } else{
            menuOptions.getItems().addAll(menu.printAdminMenu());
        }

//        menuOptions.getSelectionModel().select("Option B");
    }

}
