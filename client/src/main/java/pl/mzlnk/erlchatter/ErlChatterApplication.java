package pl.mzlnk.erlchatter;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class ErlChatterApplication extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("/chat.fxml"));
        primaryStage.setTitle("Erl Chatter");
        primaryStage.setScene(new Scene(root, 600, 800));
        primaryStage.setResizable(false);
        primaryStage.show();
    }

}
