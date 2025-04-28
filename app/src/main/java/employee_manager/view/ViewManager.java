package employee_manager.view;

import java.io.IOException;

import employee_manager.util.Constants;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.*;
import javafx.stage.*;

public class ViewManager {
    public static void switchScene(String fxmlFilePath, ActionEvent event) throws IOException{
        Parent root = FXMLLoader.load(ViewManager.class.getResource(fxmlFilePath));
        Stage stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        Scene scene = new Scene(root, Constants.SCREEN_WIDTH, Constants.SCREEN_HEIGHT);
        stage.setScene(scene);
        stage.show();
    }
}