package GUI;

import Controllers.UserAccountSystem;
import Exceptions.DoesNotExistException;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.PasswordField;
import javafx.scene.text.Text;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

/**
 * Controller for the SignUpScreen
 */
public class SignUpController {

    private final Stage mainStage;
    private LoginPanel loginPanel;
    private UserAccountSystem uas;
    private UserMenuPanel userMenuPanel;

    /**
     * Initializes a SignUpController to control the SignUpScreen
     * @param main stage of the screen
     * @param uas UserAccountSystem with the conference data
     */
    public SignUpController(Stage main, UserAccountSystem uas) {
        this.mainStage = main;
        this.uas = uas;
        this.userMenuPanel = new UserMenuPanel(uas);
        this.loginPanel = new LoginPanel(uas);
    }

    @FXML
    private TextField name;

    @FXML
    private TextField email;

    @FXML
    private PasswordField password;

    @FXML
    private PasswordField confirm;

    @FXML
    private Text actiontarget;

    /**
     * Creates an Organizer
     */
    @FXML
    private void handleCreateOrgAction() {
        try {
            if(password.getText().equals(confirm.getText())) {
                if(uas.getUserAccount().registerOrganizer(email.getText(), name.getText(), password.getText())) {
                    userMenuPanel.loadAttendeeMenuPanel(mainStage);
                    actiontarget.setText("Could Not Login After Registering");
                } else {
                    actiontarget.setText("Could Not Register");
                }
            } else {
                actiontarget.setText("Failed. Passwords do not match");
            }
        } catch (DoesNotExistException e) {
            e.printStackTrace();
        }
    }

    /**
     * Creates an Attendee
     */
    @FXML
    private void handleCreateAttAction() {
        try {
            if(password.getText().equals(confirm.getText())) {
                if(uas.getUserAccount().registerAttendee(email.getText(), name.getText(), password.getText())) {
                    userMenuPanel.loadAttendeeMenuPanel(mainStage);
                    actiontarget.setText("Could Not Login After Registering");
                } else {
                    actiontarget.setText("Could Not Register");
                }
            } else {
                actiontarget.setText("Failed. Passwords do not match");
            }
        } catch (DoesNotExistException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void returnToLogin() {
        loginPanel.loadLogInPanel(mainStage);
    }
}
