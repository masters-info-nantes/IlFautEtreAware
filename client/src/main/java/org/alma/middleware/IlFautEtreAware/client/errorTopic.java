package application;

import javafx.animation.PauseTransition;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.util.Duration;

public class errorTopic extends Application {

    @Override
    public void start(Stage primaryStage) {
    	
        primaryStage.setTitle("Erreur - Faut �tre aware");
        GridPane grid = new GridPane();
        grid.setAlignment(Pos.CENTER);
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(25, 25, 25, 25));
        Scene scene = new Scene(grid, 380, 210);
        scene.getStylesheets().add
        (errorTopic.class.getResource("errorTopic.css").toExternalForm());

        Text scenetitle = new Text("Choisis une cha�ne\npour acc�der au tchat, sinon");
        Text redir = new Text("fermeture automatique");

        scenetitle.setStyle("-fx-text-alignment:center");
        scenetitle.setFont(Font.font("Tahoma", FontWeight.NORMAL, 20));
        redir.setFont(Font.font("Tahoma", FontWeight.NORMAL, 20));
        grid.add(scenetitle, 0, 0, 2, 1);
        grid.add(redir, 2, 2);
        //Fermeture d'une fen�tre apr�s un d�compte
        PauseTransition pause = new PauseTransition(Duration.seconds(5));
        pause.setOnFinished(e -> primaryStage.hide());
        pause.play();

        final Text actiontarget = new Text();
        grid.add(actiontarget, 1, 4);
        
       
       
        

        scenetitle.setId("welcome-text");
        redir.setId("redir-text");
        actiontarget.setId("actiontarget");
        primaryStage.setScene(scene); 
        primaryStage.setResizable(false);     
        primaryStage.show();
    }
}