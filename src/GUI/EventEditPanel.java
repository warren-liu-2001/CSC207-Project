package GUI;

import Controllers.UserAccountSystem;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

/**
 * Panel for the EventEditScreen
 */
public class EventEditPanel {

    private final UserAccountSystem uas;

    /**
     * Initializes the EventEditPanel with the conference data
     * @param uas UserAccountSystem containing information of the conference
     */
    public EventEditPanel(UserAccountSystem uas) {
        this.uas = uas;
    }

    /**
     * Changes the main stage's scene to be the EventEditScreen.
     */
    public void loadEventEditScreen(Stage mainStage, String id) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("EventEditScreen.fxml"));
            loader.setController(new EventEditController(mainStage, uas, id));
            Parent root = loader.load();
            Scene scene = new Scene(root,700,400);
            loader.setRoot(getClass().getResource("EventEditScreen.fxml"));
            mainStage.setScene(scene);
            mainStage.centerOnScreen();
        } catch (IOException e) {
            System.out.println("Unable to load");
            e.printStackTrace();
        }
    }
}
