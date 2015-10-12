package org.alma.middleware.IlFautEtreAware.client;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class Connection extends Application {

    /*public static void main(String[] args) {
        launch(args);
    }*/
	public void launchApp(String[] args){
		launch(args);
	}
    
    @Override
    public void start(Stage primaryStage) {
    	
        primaryStage.setTitle("Connection - Faut �tre aware");
        GridPane grid = new GridPane();
        grid.setAlignment(Pos.CENTER);
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(25, 25, 25, 25));
        Scene scene = new Scene(grid, 480, 300);
        scene.getStylesheets().add
        (Connection.class.getResource("application.css").toExternalForm());

        Text scenetitle = new Text("Bienvenue � toi, � moi, � tous");
        scenetitle.setFont(Font.font("Tahoma", FontWeight.NORMAL, 20));
        grid.add(scenetitle, 0, 0, 2, 1);

        Label userName = new Label("Nom d'utilisateur:");
        grid.add(userName, 0, 1);

        TextField userTextField = new TextField();
        grid.add(userTextField, 1, 1);

        Button btn = new Button("Se connecter");
        HBox hbBtn = new HBox(10);
        hbBtn.setAlignment(Pos.BOTTOM_RIGHT);
        hbBtn.getChildren().add(btn);
        grid.add(hbBtn, 1, 3);

        final Text actiontarget = new Text();
        grid.add(actiontarget, 1, 4);
        
        userTextField.setOnKeyPressed(new EventHandler<KeyEvent>()
        {
            @Override
            public void handle(KeyEvent ke)
            {
                if (ke.getCode().equals(KeyCode.ENTER))
                {
                	if (userTextField.getText().equals("")){ 
                		error error = new error();
                		error.start(primaryStage);
                	}
                	else {
                		Forum test = new Forum();
                    	test.getID(userTextField.getText());
                    	test.start(primaryStage);                		
                	}
                }
            }
        });
        

        btn.setOnAction(new EventHandler<ActionEvent>() {

        	@Override
            public void handle(ActionEvent e) {
                if (userTextField.getText().equals("")) {errorWindow();}
                else openNewWindow();
            }
            public void openNewWindow() {
            	Forum test = new Forum();
            	test.getID(userTextField.getText());
            	test.start(primaryStage);
            }
            public void errorWindow() {
               	error error = new error();
               	error.start(primaryStage);
               }
     });

        scenetitle.setId("welcome-text");
        actiontarget.setId("actiontarget");
        primaryStage.setScene(scene); 
        primaryStage.setResizable(false);     
        primaryStage.show();
        
        
       
    }
    
}