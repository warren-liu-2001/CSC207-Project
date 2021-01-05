package GUI;

import Controllers.UserAccountSystem;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

/**
 * Panel for the ReviewScreen
 */
public class ReviewScreenPanel {

    private final UserAccountSystem uas;

    /**
     * Initializes the ReviewScreenPanel with the conference data
     * @param uas UserAccountSystem containing information of the conference
     */
    public ReviewScreenPanel(UserAccountSystem uas) {
        this.uas = uas;
    }

    /**
     * Changes the main stage's scene to be the ReviewScreen.
     */
    public void loadReviewScreenPanel(Stage mainStage) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("ReviewScreen.fxml"));
            loader.setController(new ReviewScreenController(mainStage, uas));
            Parent root = loader.load();
            Scene scene = new Scene(root,700,400);
            loader.setRoot(getClass().getResource("ReviewScreen.fxml"));
            mainStage.setScene(scene);
            mainStage.centerOnScreen();
        } catch (IOException e) {
            System.out.println("Unable to load");
            e.printStackTrace();
        }
    }
}
