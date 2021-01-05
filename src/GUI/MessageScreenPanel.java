package GUI;

import Controllers.UserAccountSystem;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

/**
 * Panel for the MessageScreen
 */
public class MessageScreenPanel {
    private final UserAccountSystem uas;

    /**
     * Initializes the MessageScreenPanel with the conference data
     * @param uas UserAccountSystem containing information of the conference
     */
    public MessageScreenPanel(UserAccountSystem uas) {
        this.uas = uas;
    }

    /**
     * Changes the main stage's scene to be the MessageScreenPanel.
     */
    public void loadMessageScreenPanel(Stage mainStage) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("MessageScreen.fxml"));
            loader.setController(new MessageScreenController(mainStage, uas));
            Parent root = loader.load();
            Scene scene = new Scene(root,700,400);
            loader.setRoot(getClass().getResource("MessageScreen.fxml"));
            mainStage.setScene(scene);
            mainStage.centerOnScreen();
        } catch (IOException e) {
            System.out.println("Unable to load");
            e.printStackTrace();
        }
    }
}