package GUI;

import Controllers.UserAccountSystem;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

/**
 * Panel for the DeletionScreen
 */
public class DeletionScreenPanel {

    private final UserAccountSystem uas;

    /**
     * Initializes the DeletionScreenPanel with the conference data
     * @param uas UserAccountSystem containing information of the conference
     */
    public DeletionScreenPanel(UserAccountSystem uas) {
        this.uas = uas;
    }

    /**
     * Changes the main stage's scene to be the DeletionScreen.
     */
    public void loadDeletionScreenPanel(Stage mainStage) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("DeletionScreen.fxml"));
            loader.setController(new DeletionScreenController(mainStage, uas));
            Parent root = loader.load();
            Scene scene = new Scene(root,700,400);
            loader.setRoot(getClass().getResource("DeletionScreen.fxml"));
            mainStage.setScene(scene);
            mainStage.centerOnScreen();
        } catch (IOException e) {
            System.out.println("Unable to load");
            e.printStackTrace();
        }
    }
}
