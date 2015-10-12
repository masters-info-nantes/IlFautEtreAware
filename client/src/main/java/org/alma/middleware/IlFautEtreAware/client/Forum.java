package org.alma.middleware.IlFautEtreAware.client;

import java.rmi.RemoteException;

import org.alma.middleware.IlFautEtreAware.common.IServer;

import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.stage.Stage;


public class Forum extends Application {
	
    private TextField input;
    private TextArea output; 
    private Button btnEnvoyer; 
    private final static String newline = "\n";
    String Identifiants;
    String Channel;
    Text channelName = new Text();
    
    private Client client;
    private IServer server;

    public void start(Stage primaryStage) {
 
    	GridPane grid = new GridPane();
    	primaryStage.setX(200);
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(25, 25, 25, 25));
        Scene scene = new Scene(grid, 1100, 600);
        Stage secondStage = new Stage();

        primaryStage.setTitle("Forum - Faut être aware");
        primaryStage.setScene(scene);
        scene.getStylesheets().add(Connection.class.getResource("css/JCVD.css").toExternalForm());
        primaryStage.show();
        
        Text identifiants = new Text("Bonjour à toi "+ Identifiants +" dans la galaxie de la question qu'est le forum!");
        identifiants.setFont(Font.font("Tahoma", FontWeight.NORMAL, 20));
        identifiants.setId("welcome-text");
        
        Button btnDeco = new Button();
        btnDeco.setText("Déconnexion");
        btnDeco.setOnAction(new EventHandler<ActionEvent>() {
 
            @Override
            public void handle(ActionEvent event) {
            	Deconnexion();
            }
            public void Deconnexion(){
            	Deconnection connect = new Deconnection();
            	connect.start(primaryStage);
        		
        	}
        });
        
        //Liste des abonnements
        Text TopicsIns = new Text("Liste des abonnements :");
        
        ListView<String> ListInscrits = new ListView<String>();
        ObservableList<String> items =FXCollections.observableArrayList ("Mythologie","Litterature Française");
        ListInscrits.setItems(items);
        
        
        Button btnNew = new Button();
        btnNew.setText("Nouveau thème");
        
        Button btnGo = new Button();
        btnGo.setText("Aller au tchat");
        
        btnGo.setOnAction(
                new EventHandler<ActionEvent>() {
                    @Override
                    public void handle(ActionEvent event) {
                    	getTopic(ListInscrits.getSelectionModel().getSelectedItem());
                    		if(Channel==null){
                    			errorTopic error = new errorTopic();
                               	error.start(secondStage);
                    		}
                    		else {openFenetreChat();}
                    }
                    public void openFenetreChat(){
                    	
                    	if (ListInscrits.getSelectionModel().selectedItemProperty().equals("")){	
                    	  channelName = new Text(Channel + " :");
                    	  output = new TextArea();
                          input = new TextField();
                          
                          btnEnvoyer = new Button();
                          btnEnvoyer.setText("Envoyer\nle message");
                          btnEnvoyer.setStyle("-fx-text-alignment:center");
                          btnEnvoyer.setOnAction(new EventHandler<ActionEvent>() {
                          	 
                              @Override
                              public void handle(ActionEvent event) {
                              	String text = input.getText();
                  			    output.appendText(Identifiants+": "+text + newline);
                  			    input.selectAll();
                              }
                         
                          });
                          input.setOnKeyPressed(new EventHandler<KeyEvent>()
                          {
                              @Override
                              public void handle(KeyEvent ke)
                              {
                                  if (ke.getCode().equals(KeyCode.ENTER))
                                  {
                                  	String text = input.getText();
                      			    output.appendText(Identifiants+": "+text + newline);
                      			    input.selectAll();
                                  	}
                                  }
                          });
                          
                          
                    	}
                    	else {
                    		channelName.setText("");
                    		getTopic(ListInscrits.getSelectionModel().getSelectedItem());
                    		channelName = new Text(Channel + " :");
                      	  	output = new TextArea();
                            input = new TextField();
                            btnEnvoyer = new Button();
                            btnEnvoyer.setText("Envoyer\nle message");
                            btnEnvoyer.setStyle("-fx-text-alignment:center");
                            btnEnvoyer.setOnAction(new EventHandler<ActionEvent>() {
                            	 
                                @Override
                                public void handle(ActionEvent event) {
                                	String text = input.getText();
                      			    output.appendText(Identifiants+": "+text + newline);
                    			    input.selectAll();
                                }
                           
                            });
                            input.setOnKeyPressed(new EventHandler<KeyEvent>()
                            {
                                @Override
                                public void handle(KeyEvent ke)
                                {
                                    if (ke.getCode().equals(KeyCode.ENTER))
                                    {
                                    	String text = input.getText();
                          			    output.appendText(Identifiants+": "+text + newline);
                        			    input.selectAll();
                                    	}
                                    }
                            });
                            

                    	}

                        channelName.setId("Topic");
                        grid.add(channelName, 1,2);
          	        	grid.add(output, 1,3,6,10);
          	        	grid.add(input,1,8,1,8);
          	        	grid.add(btnEnvoyer,2,8,10,8);

                      	
                    	  	
                    	  
                	}
                });
        
        
       
        Text TopicsDispos = new Text("Topics disponibles :");
       
        
        
        ListView<String> ListDispo = new ListView<String>();
        ObservableList<String> items2 =FXCollections.observableArrayList ("Génie Logiciel","MDE");
        ListDispo.setItems(items2);
        
        
        Button btnInscri = new Button();
        btnInscri.setText("S'inscrire");
        btnInscri.setOnAction((ActionEvent event) -> {
            String potential = ListDispo.getSelectionModel()
                .getSelectedItem();
            if (potential != null) {
            	ListDispo.getSelectionModel().clearSelection();
              items2.remove(potential);
              items.add(potential);
            }
          });

        Button btnDisabon = new Button();
        btnDisabon.setText("Se désabonner");
        btnDisabon.setOnAction((ActionEvent event) -> {
            String potential = ListInscrits.getSelectionModel()
                .getSelectedItem();
            if (potential != null) {
            	ListInscrits.getSelectionModel().clearSelection();
              items.remove(potential);
              items2.add(potential);
            }
          });
        btnNew.setOnAction(
                new EventHandler<ActionEvent>() {
                    @Override
                    public void handle(ActionEvent event) {
                        final Stage dialog = new Stage();
                        dialog.initModality(Modality.APPLICATION_MODAL);
                        dialog.initOwner(primaryStage);
                        VBox dialogVbox = new VBox(20);
                        dialog.setTitle("Nouveau tchat - Faut être aware");
                        Text Title = new Text("Choisissez un titre pour le nouveau tchat:");
                        dialogVbox.getChildren().add(Title);
                        TextField Topic = new TextField();
                        dialogVbox.getChildren().add(Topic);
                        dialogVbox.setPadding(new Insets(10,100,10,10)); 
                        Button btnNT = new Button();
                        dialogVbox.getChildren().add(btnNT);
                        btnNT.setText("Créer nouveau tchat");
                        Scene dialogScene = new Scene(dialogVbox, 400, 150);
                        dialog.setScene(dialogScene);
                        Title.setId("Title");
                        dialogScene.getStylesheets().add
                        (Connection.class.getResource("newTopic.css").toExternalForm());
                       
                        dialog.show();
                        btnNT.setOnAction(
                                new EventHandler<ActionEvent>() {
                                    @Override
                                    public void handle(ActionEvent event) {
                                    	String NewTopic = Topic.getText();
                                            if (NewTopic.equals("")) {

                                    			errorNewTopic error = new errorNewTopic();
                                               	error.start(secondStage);
                                            	
                                            }
                                            else {
                                            	ListInscrits.getSelectionModel().clearSelection();
                                            	items.add(NewTopic);
                                            	dialog.close();
                                            }
                                    	
                                    }
                                });
                        Topic.setOnKeyPressed(new EventHandler<KeyEvent>()
                        {
                            @Override
                            public void handle(KeyEvent ke)
                            {
                                if (ke.getCode().equals(KeyCode.ENTER))
                                {
                                	
                                     	String NewTopic = Topic.getText();
                                             if (NewTopic.equals("")) {

                                     			errorNewTopic error = new errorNewTopic();
                                                	error.start(secondStage);
                                             	
                                             }
                                             else {
                                             	ListInscrits.getSelectionModel().clearSelection();
                                             	items.add(NewTopic);
                                             	dialog.close();
                                             }
                                     	
                                     
                                }
                            }
                        });
                    }
                 });
        
      

        TopicsIns.setId("Topic");
        TopicsDispos.setId("Topic");
        grid.add(identifiants, 1,0);
        grid.add(btnDeco, 0,0);
        grid.add(TopicsIns, 0,2);
        grid.add(ListInscrits, 0,3);
        grid.add(btnGo, 0,5);
        grid.add(btnDisabon, 0,6);
        grid.add(btnNew, 0,7);        
        grid.add(TopicsDispos, 0,12);
        grid.add(ListDispo, 0,14);
        grid.add(btnInscri, 0,16);
        
    }
    public void getID (String ID) {
    	Identifiants = ID;
    	System.out.println(Identifiants);
    }
    public void getTopic (String Name){
    	Channel = Name;
    }
    public void getClient(Client c){
    	client = c;
    	try {
			Identifiants = client.getName();
		} catch (RemoteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }
    public void getServer(IServer s){
    	server = s;
    }
}