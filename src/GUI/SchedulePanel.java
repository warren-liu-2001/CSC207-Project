package GUI;

import Controllers.UserAccountSystem;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

/**
 * Panel for the ScheduleScreen
 */
public class SchedulePanel {
    private final UserAccountSystem uas;

    /**
     * Initializes the SchedulePanel with the conference data
     * @param uas UserAccountSystem containing information of the conference
     */
    public SchedulePanel(UserAccountSystem uas) {
        this.uas = uas;
    }

    /**
     * Changes the main stage's scene to be the SchedulePanel.
     */
    public void loadSchedulePanel(Stage mainStage) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("ScheduleScreen.fxml"));
            loader.setController(new ScheduleController(mainStage, uas));
            Parent root = loader.load();
            Scene scene = new Scene(root,700,400);
            loader.setRoot(getClass().getResource("ScheduleScreen.fxml"));
            mainStage.setScene(scene);
            mainStage.centerOnScreen();
        } catch (IOException e) {
            System.out.println("Unable to load");
            e.printStackTrace();
        }
    }
}
