/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package GUI;
import Controllers.UserAccountSystem;
import GUI.DisplayMain;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.scene.control.TextField;

import java.io.IOException;

/**
 * Panel for the LoginScreen
 */
public class LoginPanel {
    private final UserAccountSystem uas;

    /**
     * Initializes the LoginPanel with the conference data
     * @param uas UserAccountSystem containing information of the conference
     */
    public LoginPanel(UserAccountSystem uas) {
        this.uas = uas;
    }

    /**
     * Changes the main stage's scene to be the Login Panel.
     */
    public void loadLogInPanel(Stage mainStage) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("LoginScreen.fxml"));
            loader.setController(new LoginController(mainStage,uas));
            Parent root = loader.load();
            Scene scene = new Scene(root,700,400);
            loader.setRoot(getClass().getResource("LoginScreen.fxml"));
            mainStage.setScene(scene);
            mainStage.centerOnScreen();
        } catch (IOException e) {
            System.out.println("Unable to load");
            e.printStackTrace();
        }
	}

}
