package client.controllers;

import client.marketclient.MarketClient;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class RegisterClientController 
{

    @FXML
    private Pane paneRegisterClient;

    @FXML
    private TextField textAddress;

    @FXML
    private TextField textName;

    @FXML
    private Button btRegister;

    @FXML
    private TextField textPassword;

    @FXML
    private TextField textEmail;

    @FXML
    private TextField textPhone;
    
    @FXML
    private TextField textID;

    @FXML
    private Text textError;

    @FXML
    void onClickRegister(ActionEvent event) throws Exception 
    {
    	MarketClient mc = MarketClient.newMarketClient();
    	
    	if(mc.registerCostumer(textName.getText(), textAddress.getText(), textPhone.getText(), 
    			textEmail.getText(), textID.getText(), textPassword.getText()).equals("success"))
    	{
    		Stage primaryStage = new Stage();
	    	Parent root = FXMLLoader.load(getClass().getResource("../views/MenuClient.fxml"));
			Scene scene = new Scene(root);
			primaryStage.setScene(scene);
			primaryStage.show();
			
			Stage stage = (Stage) btRegister.getScene().getWindow();
    		stage.close();
    	}
    	
    	else{
    		textError.setVisible(true);
    	}
    
    
    }

}