package client.controllers;


import client.marketclient.MarketClient;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class LoginController 
{

    @FXML
    private TextField textID;

    @FXML
    private TextField textPassword;

    @FXML
    private Pane paneLogin;
    
    @FXML
    private Text textError;

    @FXML
    void onClickLogin(ActionEvent event) throws Exception
    {
    	MarketClient mc = MarketClient.newMarketClient();
    	String loginFlag = mc.login(textID.getText(), textPassword.getText());
    	
    	if(loginFlag.equals("success")){
	    	Stage primaryStage = new Stage();
	    	Parent root = FXMLLoader.load(getClass().getResource("../views/MenuClient.fxml"));
			Scene scene = new Scene(root);
			primaryStage.setScene(scene);
			primaryStage.show();
    	}
    	
    	else{
    		textError.setVisible(true);
    	}
    }

    @FXML
    void onClickRegister(ActionEvent event) throws Exception 
    {
    	Stage primaryStage = new Stage();
    	Parent root = FXMLLoader.load(getClass().getResource("../views/RegisterClient.fxml"));
		Scene scene = new Scene(root);
		primaryStage.setScene(scene);
		primaryStage.show();
    }

}

