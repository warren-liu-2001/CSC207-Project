package GUI;

import Controllers.UserAccountSystem;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

/**
 * Panel for the CreationScreen
 */
public class CreationScreenPanel {

    private final UserAccountSystem uas;

    /**
     * Initializes the CreationScreenPanel with the conference data
     * @param uas UserAccountSystem containing information of the conference
     */
    public CreationScreenPanel(UserAccountSystem uas) {
        this.uas = uas;
    }

    /**
     * Changes the main stage's scene to be the CreationScreen.
     */
    public void loadCreationScreenPanel(Stage mainStage) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("CreationScreen.fxml"));
            loader.setController(new CreationScreenController(mainStage, uas));
            Parent root = loader.load();
            Scene scene = new Scene(root,700,400);
            loader.setRoot(getClass().getResource("CreationScreen.fxml"));
            mainStage.setScene(scene);
            mainStage.centerOnScreen();
        } catch (IOException e) {
            System.out.println("Unable to load");
            e.printStackTrace();
        }
    }
}
