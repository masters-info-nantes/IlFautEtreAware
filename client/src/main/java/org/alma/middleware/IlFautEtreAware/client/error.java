package org.alma.middleware.IlFautEtreAware.client;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class error extends Application {


    @Override
    public void start(Stage primaryStage) {
    	
        primaryStage.setTitle("Erreur - Faut être aware");
        GridPane grid = new GridPane();
        grid.setAlignment(Pos.CENTER);
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(25, 25, 25, 25));
        Scene scene = new Scene(grid, 480, 260);
        scene.getStylesheets().add
        (error.class.getResource("css/error.css").toExternalForm());

        Text scenetitle = new Text("Rentre un nom d'utilisateur, sinon");
        scenetitle.setFont(Font.font("Tahoma", FontWeight.NORMAL, 20));
        grid.add(scenetitle, 0, 0, 2, 1);

        

        Button btn = new Button("Retour");
        HBox hbBtn = new HBox(10);
        hbBtn.setAlignment(Pos.BOTTOM_RIGHT);
        hbBtn.getChildren().add(btn);
        grid.add(hbBtn, 1, 3);

        final Text actiontarget = new Text();
        grid.add(actiontarget, 1, 4);
        
        btn.setOnAction(new EventHandler<ActionEvent>() {

            @Override
            public void handle(ActionEvent e) {
                actiontarget.setText("Sign in button pressed");
                openNewWindow();
            }
            public void openNewWindow() {
            	Connection connect = new Connection();
            	connect.start(primaryStage);
            }
        });
        btn.setOnKeyPressed(new EventHandler<KeyEvent>()
        {
            @Override
            public void handle(KeyEvent ke)
            {
            	Connection connect = new Connection();
            	connect.start(primaryStage);
                }
            
        });
        

        scenetitle.setId("welcome-text");
        actiontarget.setId("actiontarget");
        primaryStage.setScene(scene); 
        primaryStage.setResizable(false);     
        primaryStage.show();
    }
}