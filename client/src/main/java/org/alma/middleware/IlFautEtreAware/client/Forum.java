package org.alma.middleware.IlFautEtreAware.client;

import java.rmi.RemoteException;
import java.util.ArrayList;

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
import javafx.scene.image.Image;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;


public class Forum extends Application {
	
    private TextField input;
    private TextArea output; 
    private Button btnEnvoyer;
    private Text channelName = new Text();
    private GridPane grid;
    
    private final static String newline = "\n";
    private String Identifiants;
    private String Channel;
   
    private Client client;
    private IServer server;
    private ITopic selectedTopic;
    
    private ListView<String> listInscrits;
    private ListView<String> listDispo;
    private ObservableList<String> listInscritsItems;
    private ObservableList<String> listDispoItems;
    
    public void start(Stage primaryStage) throws RemoteException {
 
    	//CrÃ©ation de la fenÃªtre avec ses paramÃ¨tres de taille et de disposition
    	grid = new GridPane();
    	primaryStage.setX(200);
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(25, 25, 25, 25));
        Scene scene = new Scene(grid, 1100, 600);
        Stage secondStage = new Stage();

        //DÃ©finition nom et style css de la fenÃªtre
        primaryStage.setTitle("Forum - Faut être aware");
        primaryStage.getIcons().add(new Image("/images/logo.png"));
        primaryStage.setScene(scene);
        scene.getStylesheets().add("/css/JCVD.css");
        primaryStage.show();
        primaryStage.setOnCloseRequest(new EventHandler<WindowEvent>() {
        	@Override
        	public void handle(WindowEvent t){
        		System.out.println("Client closed");
        		System.exit(0);
        	}
        });
        
        Text identifiants = new Text("Bonjour à  toi "+ Identifiants +" dans la galaxie de la question qu'est le forum!");
        identifiants.setFont(Font.font("Tahoma", FontWeight.NORMAL, 20));
        identifiants.setId("welcome-text");
        
        Button btnDeco = new Button();
        btnDeco.setText("Déconnexion");
        btnDeco.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
            	Deconnexion(primaryStage);
            }
        });
        
        
        //Liste des abonnements
        Text TopicsIns = new Text("Liste des abonnements :");
        listInscrits = new ListView<String>();
        listInscritsItems = FXCollections.observableArrayList (client.getSubscribedTopic());
        listInscrits.setItems(listInscritsItems);
        
        //Liste des sujets disponibles
        Text TopicsDispos = new Text("Topics disponibles :");    
        listDispo = new ListView<String>();
        listDispoItems = FXCollections.observableArrayList (server.getTopicsName());
        listDispo.setItems(listDispoItems);
        
        //Bouton pour se rendre sur un tchat
        Button btnGo = new Button();
        btnGo.setText("Aller au tchat");
        btnGo.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {            	
            	goToTchat(secondStage);            	
            }			
        });

        //Bouton + mÃ©thode pour l'inscription Ã  un tchat
        Button btnInscri = new Button();
        btnInscri.setText("S'inscrire");
        btnInscri.setOnAction((ActionEvent event) -> {
            String potential = listDispo.getSelectionModel().getSelectedItem();
            if (potential != null) {
            	try {
					server.topicSubscribe(client, server.getTopic(potential));
					listDispo.getSelectionModel().clearSelection();
	            	listDispoItems.remove(potential);
	            	listInscritsItems.add(potential);
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
            }
          });

        //Bouton + mÃ©thode pour la desinscription Ã  un tchat
        Button btnDisabon = new Button();
        btnDisabon.setText("Se désabonner");
        btnDisabon.setOnAction((ActionEvent event) -> {
            String potential = listInscrits.getSelectionModel().getSelectedItem();
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
					listInscrits.getSelectionModel().clearSelection();
	            	listInscritsItems.remove(potential);
	            	listDispoItems.add(potential);
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
            }
          });
        
        //Bouton + mÃ©trode pour la crÃ©ation d'un nouveau tchat
        Button btnNew = new Button();
        btnNew.setText("Nouveau thème");
        btnNew.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
            	
            	
            	windowNewTopic(primaryStage,  secondStage);
            }
        });
      //Bouton + mÃ©thode pour la desinscription Ã  un tchat
        Button btnsuppr = new Button();
        btnsuppr.setText("Supprimer le thème");
        btnsuppr.setOnAction((ActionEvent event) -> {
        	 String potential = listInscrits.getSelectionModel().getSelectedItem();
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
 					listInscrits.getSelectionModel().clearSelection();
 	            	listInscritsItems.remove(potential);
 				} catch (Exception e) {
 					// TODO Auto-generated catch block
 					e.printStackTrace();
 				}
             }
           });
         
        
        
      
        //Disposition des diffÃ©rents Ã©lÃ©ments sur la fenÃªtre princiale
        TopicsIns.setId("Topic");
        TopicsDispos.setId("Topic");
        grid.add(identifiants, 1,0);
        grid.add(btnDeco, 0,0);
        grid.add(TopicsIns, 0,2);
        grid.add(listInscrits, 0,3);
        grid.add(btnGo, 0,5);
        grid.add(btnDisabon, 0,6);
        grid.add(btnNew, 0,7);   
        grid.add(btnsuppr, 0,8); 
        grid.add(TopicsDispos, 0,12);
        grid.add(listDispo, 0,14);
        grid.add(btnInscri, 0,16);
        
    }
    

    //DÃ©but des diffÃ©rentes mÃ©thodes
	public void Deconnexion(Stage stage){
    	Deconnection connect = new Deconnection();
    	connect.start(stage);		
	}
    private void goToTchat(Stage secondStage) {
		getTopic(listInscrits.getSelectionModel().getSelectedItem());
		if(Channel==null){
			errorTopic error = new errorTopic();
           	error.start(secondStage);
		}
		else {
			openFenetreChat();
		}
		
	}
    
    public void createNewTopic(TextField Topic, Stage dialog, Stage secondStage){
    	String NewTopic = Topic.getText();
        if (NewTopic.equals("")) {
			errorNewTopic error = new errorNewTopic();
           error.start(secondStage);
        } else {
        	listInscrits.getSelectionModel().clearSelection();
        	listInscritsItems.add(NewTopic);
        	try {
				server.createTopic(client, NewTopic);
			} catch (RemoteException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
        	dialog.close();
        } 
    }
    
    public void openFenetreChat(){
    	
    	if (listInscrits.getSelectionModel().selectedItemProperty().equals("")){	
			channelName = new Text(Channel + " :");
			
			output = new TextArea();
			getTopicMessages();
			
			input = new TextField();
			input.setOnKeyPressed(new EventHandler<KeyEvent>() {
			    @Override
			    public void handle(KeyEvent ke)
			    {
			        if (ke.getCode().equals(KeyCode.ENTER))
			        {
			         sendMessage();
			        }
			    }
			});
			
			btnEnvoyer = new Button();
			btnEnvoyer.setText("Envoyer\nle message");
			btnEnvoyer.setStyle("-fx-text-alignment:center");
			btnEnvoyer.setOnAction(new EventHandler<ActionEvent>() {
			    @Override
			    public void handle(ActionEvent event) {
			  	  sendMessage();
			    }
			});
			
    	}
    	else {
    		channelName.setText("");
    		getTopic(listInscrits.getSelectionModel().getSelectedItem());
    		channelName = new Text(Channel + " :");
    		
      	  	output = new TextArea();
      	  	getTopicMessages();
      	  	
            input = new TextField();
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
            
            btnEnvoyer = new Button();
            btnEnvoyer.setText("Envoyer\nle message");
            btnEnvoyer.setStyle("-fx-text-alignment:center");
            btnEnvoyer.setOnAction(new EventHandler<ActionEvent>() {
            	 
                @Override
                public void handle(ActionEvent event) {
                	sendMessage();
                }           
            });
            
    	}

        channelName.setId("Topic");
        grid.add(channelName, 1,2);
      	grid.add(output, 1,3,6,10);
      	grid.add(input,1,8,1,8);
      	grid.add(btnEnvoyer,2,8,10,8);

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
    
    public void setClient(Client c){
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
        listDispoItems.add(topic);
    }
    
    protected void windowNewTopic(Stage primaryStage, Stage secondStage) {    	
    	
        final Stage dialog = new Stage();
        dialog.initModality(Modality.APPLICATION_MODAL);
        dialog.initOwner(primaryStage);
        VBox dialogVbox = new VBox(20);
        dialog.setTitle("Nouveau tchat - Faut être aware");
    	primaryStage.getIcons().add(new Image("/images/logo.png"));
    	secondStage.getIcons().add(new Image("/images/logo.png"));
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
        dialogScene.getStylesheets().add("/css/newTopic.css");

        dialog.show();
        btnNT.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
            	createNewTopic(Topic,dialog,secondStage);
            	primaryStage.getIcons().add(new Image("/images/logo.png"));
            	secondStage.getIcons().add(new Image("/images/logo.png"));
            }
        });
        Topic.setOnKeyPressed(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent ke)
            {
                if (ke.getCode().equals(KeyCode.ENTER))
                {
                	createNewTopic(Topic,dialog,secondStage);
                	primaryStage.getIcons().add(new Image("/images/logo.png"));
                	secondStage.getIcons().add(new Image("/images/logo.png"));
                }
            }
        });
		
	}
}