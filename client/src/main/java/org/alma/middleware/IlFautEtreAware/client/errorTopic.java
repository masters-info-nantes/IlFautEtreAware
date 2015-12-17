package org.alma.middleware.IlFautEtreAware.client;

import javafx.animation.PauseTransition;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.util.Duration;

public class errorTopic extends Application {

    @Override
    public void start(Stage primaryStage) {
    	
        primaryStage.setTitle("Erreur - Faut être aware");
        primaryStage.getIcons().add(new Image("/images/logo.png"));
        GridPane grid = new GridPane();
        grid.setAlignment(Pos.CENTER);
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(25, 25, 25, 25));
        Scene scene = new Scene(grid, 380, 210);
        scene.getStylesheets().add
        ("/css/errorTopic.css");

        Text scenetitle = new Text("Choisis une chaine\npour accéder au tchat, sinon");
        Text redir = new Text("fermeture automatique");

        scenetitle.setStyle("-fx-text-alignment:center");
        scenetitle.setFont(Font.font("Tahoma", FontWeight.NORMAL, 20));
        redir.setFont(Font.font("Tahoma", FontWeight.NORMAL, 20));
        grid.add(scenetitle, 0, 0, 2, 1);
        grid.add(redir, 2, 2);
        //Fermeture d'une fenï¿½tre aprï¿½s un dï¿½compte
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