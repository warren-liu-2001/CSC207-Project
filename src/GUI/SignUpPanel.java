/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package GUI;

import Controllers.UserAccount;
import Controllers.UserAccountSystem;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.PasswordField;
import javafx.scene.text.Text;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.awt.*;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import Controllers.UserAccountSystem;
import GUI.DisplayMain;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.scene.control.TextField;

/**
 * Panel for the SignUpScreen
 */
public class SignUpPanel {
    private final UserAccountSystem uas;

    /**
     * Initializes the SignUpPanel with the conference data
     * @param uas UserAccountSystem containing information of the conference
     */
    public SignUpPanel(UserAccountSystem uas) {
        this.uas = uas;
    }
    /**
     * Changes the main stage's scene to be the SignUpPanel.
     */
    public void loadSignUpPanel(Stage mainStage) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("SignUpScreen.fxml"));
            loader.setController(new SignUpController(mainStage,uas));
            Parent root = loader.load();
            Scene scene = new Scene(root,700,400);
            loader.setRoot(getClass().getResource("SignUpScreen.fxml"));
            mainStage.setScene(scene);
            mainStage.centerOnScreen();
        } catch (IOException e) {
            System.out.println("Unable to load");
            e.printStackTrace();
        }
    }

}
