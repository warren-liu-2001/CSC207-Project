package GUI;

import Controllers.FeedbackController;
import Controllers.UseCaseStorage;
import Controllers.UserAccount;
import Controllers.UserAccountSystem;
import Exceptions.DoesNotExistException;
import Gateway.DataBase;
import Gateway.Serializer;
import Gateway.FeedbackSerializer;
import UseCases.UserManager;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.PasswordField;
import javafx.scene.text.Text;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.awt.*;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class LoginController {

    private final Stage mainStage;
    //private LoginPanel loginPanel;
    private SignUpPanel signUpPanel;
    private UserAccountSystem uas;

    /**
     * Instantiates the login controller to control the login screen.
     * @param main The stage the screen shows up on
     * @param uas The UserAccountSystem containing the data of the conference
     */
    public LoginController(Stage main, UserAccountSystem uas) {
        this.uas = uas;
        this.mainStage = main;
        this.signUpPanel = new SignUpPanel(this.uas);
    }

    @FXML
    private TextField person;

    @FXML
    private TextField userId;

    @FXML
    private Text actiontarget;

    @FXML
    private PasswordField passwordField;

    /**
     * Verifies the login information and if it is correct, logs the user in to the account.
     */
    @FXML
    private void handleSubmitButtonAction() {
        if(uas.getUserAccount().login(person.getText(),userId.getText(),passwordField.getText())){
            new UserMenuPanel(uas).loadAttendeeMenuPanel(mainStage);
            actiontarget.setText("Login Success");
        } else {
            actiontarget.setText("Login Failed");
        }
    }

    /**
     * Takes the user to the sign up screen.
     */
    @FXML
    private void handleSignUpButtonAction() {
        signUpPanel.loadSignUpPanel(mainStage);
    }

    /**
     * Called when the save and exit button is pressed. Saves the program data to the database and exits the window.
     */
    @FXML
    private void saveAndExit() throws SQLException, ClassNotFoundException, DoesNotExistException {
        // save
        try {
            DataBase d = new DataBase<>(); // might want to use the same database obj as in displayMain? idk if it messes something up
            d.setUserManager(uas.getUseCaseStorage().getUserManager());
            d.setInboxManager(uas.getUseCaseStorage().getInboxManager(), uas.getUseCaseStorage().getUserManager());
            d.setRoomManager(uas.getUseCaseStorage().getRoomManager());
            d.setTalkManager(uas.getUseCaseStorage().getTalkManager());
        }catch(SQLException s) {
            System.out.println("Could not write to database properly");
            s.printStackTrace(); // to more easily see where something went wrong
        }
        catch (DoesNotExistException | ClassNotFoundException e) {
            System.out.println("A Class or Entity was not found");
            e.printStackTrace();
        }
        FeedbackSerializer fbs = new FeedbackSerializer();

        fbs.Write(uas.getFeedbackController());

        Serializer gate = new Serializer();
        gate.Write(uas.getUseCaseStorage());

        // close window
        mainStage.close();
    }
}
