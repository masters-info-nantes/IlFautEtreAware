package org.alma.middleware.IlFautEtreAware.client;

import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.List;

import org.alma.middleware.IlFautEtreAware.common.IServer;
import org.alma.middleware.IlFautEtreAware.common.ITopic;
import org.alma.middleware.IlFautEtreAware.common.Message;

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
    private ITopic selectedTopic;
    private ListView<String> ListInscrits;
    private ObservableList<String> items;
    private ListView<String> ListDispo;
    private ObservableList<String> items2;
    
    public void start(Stage primaryStage) throws RemoteException {
 
    	GridPane grid = new GridPane();
    	primaryStage.setX(200);
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(25, 25, 25, 25));
        Scene scene = new Scene(grid, 1100, 600);
        Stage secondStage = new Stage();

        primaryStage.setTitle("Forum - Faut être aware");
        primaryStage.setScene(scene);
        scene.getStylesheets().add("/org/alma/middleware/IlFautEtreAware/client/css/JCVD.css");
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
        ListInscrits = new ListView<String>();
        items = FXCollections.observableArrayList (client.getSubscribedTopic());
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
                    	  getTopicMessages();
                          input = new TextField();
                          
                          btnEnvoyer = new Button();
                          btnEnvoyer.setText("Envoyer\nle message");
                          btnEnvoyer.setStyle("-fx-text-alignment:center");
                          btnEnvoyer.setOnAction(new EventHandler<ActionEvent>() {
                          	 
                              @Override
                              public void handle(ActionEvent event) {
                            	  sendMessage();
                              }
                         
                          });
                          input.setOnKeyPressed(new EventHandler<KeyEvent>()
                          {
                              @Override
                              public void handle(KeyEvent ke)
                              {
                                  if (ke.getCode().equals(KeyCode.ENTER))
                                  {
                                	  sendMessage();
                                  }
                              }
                          });
                          
                          
                    	}
                    	else {
                    		channelName.setText("");
                    		getTopic(ListInscrits.getSelectionModel().getSelectedItem());
                    		channelName = new Text(Channel + " :");
                      	  	output = new TextArea();
                      	  	getTopicMessages();
                            input = new TextField();
                            btnEnvoyer = new Button();
                            btnEnvoyer.setText("Envoyer\nle message");
                            btnEnvoyer.setStyle("-fx-text-alignment:center");
                            btnEnvoyer.setOnAction(new EventHandler<ActionEvent>() {
                            	 
                                @Override
                                public void handle(ActionEvent event) {
                                	sendMessage();
                                }
                           
                            });
                            input.setOnKeyPressed(new EventHandler<KeyEvent>()
                            {
                                @Override
                                public void handle(KeyEvent ke)
                                {
                                    if (ke.getCode().equals(KeyCode.ENTER))
                                    {
                                    	sendMessage();
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
       
        ListDispo = new ListView<String>();
        items2 =FXCollections.observableArrayList (server.getTopicsName());
        ListDispo.setItems(items2);
        
        
        Button btnInscri = new Button();
        btnInscri.setText("S'inscrire");
        btnInscri.setOnAction((ActionEvent event) -> {
            String potential = ListDispo.getSelectionModel().getSelectedItem();
            if (potential != null) {
            	try {
					server.topicSubscribe(client, server.getTopic(potential));
					ListDispo.getSelectionModel().clearSelection();
	            	items2.remove(potential);
	            	items.add(potential);
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
            }
          });

        Button btnDisabon = new Button();
        btnDisabon.setText("Se désabonner");
        btnDisabon.setOnAction((ActionEvent event) -> {

            String potential = ListInscrits.getSelectionModel().getSelectedItem();
            try {
                if (selectedTopic.getName().equals(potential)) {
                    grid.getChildren().remove(channelName);
                    grid.getChildren().remove(output);
                    grid.getChildren().remove(input);
                    grid.getChildren().remove(btnEnvoyer);
                }
            } catch (RemoteException e) {
                e.printStackTrace();
            }
            if (potential != null) {
            	try {
					server.topicUnsubscribe(client, server.getTopic(potential));
					ListInscrits.getSelectionModel().clearSelection();
	            	items.remove(potential);
	            	items2.add(potential);
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
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
                        dialogScene.getStylesheets().add("/org/alma/middleware/IlFautEtreAware/client/css/newTopic.css");

                        dialog.show();
                        btnNT.setOnAction(
                                new EventHandler<ActionEvent>() {
                                    @Override
                                    public void handle(ActionEvent event) {
                                    	String NewTopic = Topic.getText();
                                        if (NewTopic.equals("")) {
                                			errorNewTopic error = new errorNewTopic();
                                           	error.start(secondStage);       	
                                        } else {
                                        	try {
												server.createTopic(client, NewTopic);
											} catch (RemoteException e) {
												// TODO Auto-generated catch block
												e.printStackTrace();
											}
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
                                     } else {
                                     	ListInscrits.getSelectionModel().clearSelection();
                                     	items.add(NewTopic);
                                     	try {
											server.createTopic(client, NewTopic);
										} catch (RemoteException e) {
											// TODO Auto-generated catch block
											e.printStackTrace();
										}
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
    	try {
			selectedTopic = server.getTopic(Name);
		} catch (RemoteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
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
    
    public void getTopicMessages(){
    	ArrayList<Message> messagesTopic;
		try {
			messagesTopic = selectedTopic.getMessages();
			for(Message m : messagesTopic){
				output.appendText(m.toString()+ newline);
		  	}
		} catch (RemoteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}    
    }

    public void newMessage(Message msg, String topic) throws RemoteException {
        if(selectedTopic.getName().equals(topic))
            output.appendText(msg.toString()+ newline);
    }
    
    public void sendMessage(){
    	String text = input.getText();
  	  	try {
      		Message m = new Message(Identifiants,text);
			selectedTopic.broadcast(m);
      	} catch (RemoteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
      	}
  	  	input.selectAll();
    }

    public void newTopic(String topic) {
        items2.add(topic);
    }
}