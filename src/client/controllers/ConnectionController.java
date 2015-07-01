package client.controllers;

import client.marketclient.MarketClient;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class ConnectionController 
{

    @FXML
    private TextField textIP;

    @FXML
    private Button btConnect;
    
    @FXML
    private Label labelError;

    @FXML
    void onClickConnect(ActionEvent event) throws Exception 
    {
    	String connection; 
    	MarketClient mc = MarketClient.newMarketClient();
    	
    	connection = mc.connect(textIP.getText());
    	
    	if(connection.equals("success"))
    	{    		
    		Stage primaryStage = new Stage();
    		Parent root = FXMLLoader.load(getClass().getResource("../views/Login.fxml"));
    		Scene scene = new Scene(root);
    		primaryStage.setScene(scene);
    		primaryStage.show();
    	}
    	else
    	{
    		labelError.setVisible(true);
    	}
    }

}