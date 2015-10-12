package org.alma.middleware.IlFautEtreAware.client;

import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;

import org.alma.middleware.IlFautEtreAware.common.IServer;
import org.alma.middleware.IlFautEtreAware.common.RMIConfig;

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

	private Client client;
	private IServer server = null;
	private String username = "";
	
	public void launchApp(String[] args){
		launch(args);
	}
	
    
    @Override
    public void start(Stage primaryStage) {
    	
        primaryStage.setTitle("Connection - Faut être aware");
        GridPane grid = new GridPane();
        grid.setAlignment(Pos.CENTER);
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(25, 25, 25, 25));
        Scene scene = new Scene(grid, 480, 300);
        scene.getStylesheets().add
        (Connection.class.getResource("css/application.css").toExternalForm());

        Text scenetitle = new Text("Bienvenue à toi, à moi, à tous");
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
                	if (userTextField.getText().equals("")) {
                    	errorWindow(primaryStage);
                    }else{
    					openNewWindow(userTextField,primaryStage);
                    }
                }
            }
        });
        

        btn.setOnAction(new EventHandler<ActionEvent>() {

        	@Override
            public void handle(ActionEvent e) {
                if (userTextField.getText().equals("")) {
                	errorWindow(primaryStage);
                }else{
					openNewWindow(userTextField,primaryStage);
                }
            }
            
        });

        scenetitle.setId("welcome-text");
        actiontarget.setId("actiontarget");
        primaryStage.setScene(scene); 
        primaryStage.setResizable(false);     
        primaryStage.show();

    }
    
    public void openNewWindow(TextField tf, Stage stage){
    	username = tf.getText();
    	try {
			client = new Client();
		} catch (RemoteException e1) {
			e1.printStackTrace();
		}
    	client.setName(username);
    	try {
            server = (IServer) Naming.lookup("rmi://" + RMIConfig.SERVER_IP + ":" + RMIConfig.SERVER_PORT + "/" + RMIConfig.APP_NAME);
        } catch (NotBoundException e) {
            e.printStackTrace();
        } catch (MalformedURLException e) {
            System.err.println("RMI: Retrieve malformed URL");
            e.printStackTrace();
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    	try {
			server.login(client);
			Forum forum = new Forum();
			//forum.getID(username);
			forum.getClient(client);
			forum.getServer(server);
			forum.start(stage);
		} catch (RemoteException e) {
			e.printStackTrace();
		}
    	
    }
    
    public void errorWindow(Stage stage) {
       	error error = new error();
       	error.start(stage);
    }
    
    
}