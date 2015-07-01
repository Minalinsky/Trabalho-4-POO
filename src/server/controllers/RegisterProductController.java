package server.controllers;

import server.marketserver.MarketServer;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;

public class RegisterProductController 
{

    @FXML
    private TextField textName;

    @FXML
    private TextField textValidity;

    @FXML
    private TextField textQuantity;

    @FXML
    private Button btRegister;

    @FXML
    private TextField textProvider;

    @FXML
    private Pane paneRegisterProduct;

    @FXML
    private TextField textPrice;
    
    @FXML
    private TextField textID;
    
    @FXML
    private Label labelMsg;

    @FXML
    void onClickRegister(ActionEvent event) throws Exception
    {
    	MarketServer ms = MarketServer.newMarketServer();
    	if(textName.getText().equals("") || textID.getText().equals("") || textPrice.getText().equals("")  
    			||  textValidity.getText().equals("") || textProvider.getText().equals("") || textQuantity.getText().equals(""))
    	{
    		labelMsg.setText("Campos(s) inválido(s)");
    		labelMsg.setVisible(true);
    	}
    	else
    	{
    		String registerFlag = ms.writeProduct(textName.getText(), textID.getText(), textPrice.getText(), textValidity.getText(), 
    				textProvider.getText(), Integer.parseInt((textQuantity.getText())));
    	
    		if(registerFlag.equals("success"))
    		{
    			labelMsg.setText("Cadastro realizado com sucesso");
    			labelMsg.setVisible(true);
    		}
    		else if(registerFlag.equals("duplicated product"))
    		{
    			labelMsg.setText("Produto já cadastrado");
    			labelMsg.setVisible(true);
    		}
    		else
    		{
    			labelMsg.setText("Erro ao efetuar cadastro");
    			labelMsg.setVisible(true);
    		}
    	}
    }
}