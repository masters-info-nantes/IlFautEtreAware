package org.alma.middleware.IlFautEtreAware.client;

import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
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
    private final static String newline = "\n";
    String Identifiants;
    String Channel;
    Text channelName = new Text();
    
    public void start(Stage primaryStage) {
 
    	GridPane grid = new GridPane();
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(25, 25, 25, 25));
        Scene scene = new Scene(grid, 1000, 600);

        primaryStage.setTitle("Forum - Faut être aware");
        primaryStage.setScene(scene);
        //scene.getStylesheets().add(Connection.class.getResource("JCVD.css").toExternalForm());
        primaryStage.show();
        
        Text identifiants = new Text("Bonjour à toi "+ Identifiants +" dans la galaxie de la question qu'est le forum!");
        identifiants.setFont(Font.font("Tahoma", FontWeight.NORMAL, 20));
        identifiants.setId("welcome-text");
        
        Button btnDeco = new Button();
        btnDeco.setText("Déconnexion");
        btnDeco.setOnAction(new EventHandler<ActionEvent>() {
 
            @Override
            public void handle(ActionEvent event) {
            	Connexion();
            }
            public void Connexion(){
            	Connection connect = new Connection();
            	connect.start(primaryStage);
        		
        	}
        });
        
        //Liste des abonnements
        Text TopicsIns = new Text("Liste des abonnements :");
        
        ListView<String> ListInscrits = new ListView<String>();
        ObservableList<String> items = FXCollections.observableArrayList();
        ListInscrits.setItems(items);
        
        
        Button btnNew = new Button();
        btnNew.setText("Nouveau thème");
        btnNew.setOnAction(
                new EventHandler<ActionEvent>() {
                    @Override
                    public void handle(ActionEvent event) {
                        final Stage dialog = new Stage();
                        dialog.initModality(Modality.APPLICATION_MODAL);
                        dialog.initOwner(primaryStage);
                        VBox dialogVbox = new VBox(20);
                        dialogVbox.getChildren().add(new Text("Choisissez le titre du nouveau Topic :"));
                        TextField Topic = new TextField();
                        dialogVbox.getChildren().add(Topic);
                        Button btnNT = new Button();
                        dialogVbox.getChildren().add(btnNT);
                        btnNT.setText("Créer");
                        Scene dialogScene = new Scene(dialogVbox, 100, 120);
                        dialog.setScene(dialogScene);
                       
                        dialog.show();
                        btnNT.setOnAction(
                                new EventHandler<ActionEvent>() {
                                    @Override
                                    public void handle(ActionEvent event) {
                                    	
                                    	openFenetreChat();
                                    	dialog.close();
                                    }
                                    public void openFenetreChat(){
                                		//FenetreChat ft = new FenetreChat();
                                		//ft.start(primaryStage);
                                		System.out.println("zozo");
                                		
                                	}
                                }
                         );
                    }
                 });
        Button btnGo = new Button();
        btnGo.setText("Aller au tchat");
       
        btnGo.setOnAction(
                new EventHandler<ActionEvent>() {
                    @Override
                    public void handle(ActionEvent event) {                    	
                    	openFenetreChat();                    	
                    }
                    public void openFenetreChat(){
                		//FenetreChat ft = new FenetreChat();
                		//ft.start(primaryStage);
                    	if (channelName.getText().equals("")){
                    	  getTopic(ListInscrits.getSelectionModel().getSelectedItem());
                    	  channelName = new Text("Topic "+ Channel + " :");
                    	  System.out.println("VIDE: "+channelName);
                    	}
                    	else {
                    		channelName.setText("");
                    		getTopic(ListInscrits.getSelectionModel().getSelectedItem());
                    		channelName = new Text("Topic "+ Channel + " :");
                    		System.out.println("NON VIDE");
                    	}
                    	grid.add(channelName,1,2); 
                	}

                });
        
        
        
        Text TopicsDispos = new Text("Topics disponibles :");
       
        ListView<String> ListDispo = new ListView<String>();
        ObservableList<String> items2 = FXCollections.observableArrayList();
        ListDispo.setItems(items2);
        
        
        Button btnInscri = new Button();
        btnInscri.setText("S'inscrire");
        
        btnInscri.setOnAction(
        new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                final Stage dialog = new Stage();
                dialog.initModality(Modality.APPLICATION_MODAL);
                dialog.initOwner(primaryStage);
                VBox dialogVbox = new VBox();
                dialogVbox.getChildren().add(new Text("Topic créé !"));
                Button btnNT = new Button();
                dialogVbox.getChildren().add(btnNT);
                btnNT.setText("Rejoindre");
                Scene dialogScene = new Scene(dialogVbox, 0, 0);
                dialog.setScene(dialogScene);
               
                dialog.show();
                btnNT.setOnAction(
                        new EventHandler<ActionEvent>() {
                            @Override
                            public void handle(ActionEvent event) {
                            	
                            	openFenetreChat();
                            	dialog.close();
                            }
                            public void openFenetreChat(){
                        		//FenetreChat ft = new FenetreChat();
                        		//ft.start(primaryStage);
                        		System.out.println("zozo)");
                        		
                        	}
                        }
                );
            }
        });
        
        
      

        grid.add(identifiants, 1,0);
        grid.add(btnDeco, 0,0);
        grid.add(TopicsIns, 0,2);
        grid.add(ListInscrits, 0,3);
        grid.add(btnNew, 0,4);
        grid.add(btnGo, 0,5);
        grid.add(TopicsDispos, 0,6);
        grid.add(ListDispo, 0,7);
        grid.add(btnInscri, 0,8);
        
    }
    public void getID (String ID) {
    	Identifiants = ID;
    	System.out.println(Identifiants);
    }
    public void getTopic (String Name){
    	Channel = Name;
    }
    /*public static void main(String[] args) {
        launch(args);
    }*/
}