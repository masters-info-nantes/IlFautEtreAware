package org.alma.middleware.IlFautEtreAware.client;

import javafx.animation.PauseTransition;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.util.Duration;

public class Deconnection extends Application {

    
    @Override
    public void start(Stage primaryStage) {
    	
        primaryStage.setTitle("D�connection - Faut �tre aware");
        Stage secondStage = new Stage();
        GridPane grid = new GridPane();
        grid.setAlignment(Pos.CENTER);
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(25, 25, 25, 25));
        Scene scene = new Scene(grid, 480, 300);
        scene.getStylesheets().add
        (Deconnection.class.getResource("application.css").toExternalForm());

        Text scenetitle = new Text("A bient�t mon ami\n et sois toujours aware");
        scenetitle.setStyle("-fx-text-alignement: center");
        scenetitle.setFont(Font.font("Tahoma", FontWeight.NORMAL, 20));
        grid.add(scenetitle, 0, 0, 2, 1);
        PauseTransition pause = new PauseTransition(Duration.seconds(5));
        PauseTransition pause2 = new PauseTransition(Duration.seconds(5));
        pause.setOnFinished(e -> primaryStage.hide());
        Connection connect = new Connection();
        pause2.setOnFinished(e -> connect.start(secondStage));
        pause.play();
        pause2.play();
		

        
        
        scenetitle.setId("goodbye-text");
        primaryStage.setScene(scene); 
        primaryStage.setResizable(false);     
        primaryStage.show();
        
        
       
    }
    
}