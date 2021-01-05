package GUI;

import Controllers.FeedbackController;
import Controllers.UseCaseFactory;
import Controllers.UseCaseStorage;
import Controllers.UserAccountSystem;
import Gateway.*;
import UseCases.AttributeChanger;
import javafx.application.Application;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import GUI.LoginPanel;

import java.sql.SQLException;

/**
 * The main Access point for our App
 *
 * @version 2.0
 */
public class DisplayMain extends Application implements java.io.Serializable{


    /**
     * Launch the App
     * @param args N/A
     */
    public static void main(String[] args) {
        launch(args);
    }

    /**
     * Loads in information from the database and opens the login screen.
     * @param mainStage The stage the login screen will be on
     */
    @Override
    public void start(Stage mainStage) {
        //Serializer gate = new Serializer();
        //UseCaseStorage ucs = gate.Read();
        UseCaseStorage ucs;
        try {
            DataBase d = new DataBase<>();

            // parameters are just empty strings, remove them when the methodsare fixed
            ucs = new UseCaseStorage(d.createTalkManager(d.getEventInfo("")),
                    d.createRoomManager(d.getRoomInfo("")),
                    d.createInboxManager(d.getMessageInfo("")),
                    d.createUserManager(d.getAllUserInfo()),
                    new AttributeChanger());

        } catch (SQLException throwables) {
            throwables.printStackTrace();
            // problem loading from database, make new use case instances instead
            ucs = new UseCaseFactory().getUseCaseStorage();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
            // problem loading from database, make new use case instances instead
            ucs = new UseCaseFactory().getUseCaseStorage();
            Serializer gate = new Serializer();
            ucs = gate.Read();
        } catch (Exception e3) {
            e3.printStackTrace();
            Serializer gate = new Serializer();
            ucs = gate.Read();
        }

        // Emergency
        //Serializer gate = new Serializer();
        //ucs = gate.Read();

        UserAccountSystem uas = new UserAccountSystem(ucs);

        // Feedback Serializer to be added.

        FeedbackSerializer fbs = new FeedbackSerializer();
        FeedbackController fbc = fbs.Read();
        uas.setFeedbackController(fbc);

        // start the login screen
        LoginPanel p = new LoginPanel(uas);
        p.loadLogInPanel(mainStage);
        mainStage.show();
    }
}
