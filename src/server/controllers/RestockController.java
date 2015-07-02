package server.controllers;

import java.net.URL;
import java.util.ArrayList;
import java.util.Properties;
import java.util.ResourceBundle;

import javax.mail.Message;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import marketlib.Costumer;
import marketlib.Product;
import server.marketserver.MarketServer;

public class RestockController implements Initializable{
	MarketServer ms = MarketServer.newMarketServer(); 
	
	private ArrayList<Product> outOfStockList;
	private ArrayList<Costumer> costumersList;
	
	private String msgBody = "Os produtos cujo estoque foi reabastecido foram: ";
	
    @FXML
    private TableColumn<?, ?> columnID;

    @FXML
    private TableView<Product> tableRestock;

    @FXML
    private TextField textRestock;

    @FXML
    private TableColumn<?, ?> columnName;

    @FXML
    private Button btRestock;

    //Ao clicar no botao atualiza estoque e envia email para todos os usuarios
    @FXML
    void onClickRestock(ActionEvent event) {
    	
    	//atualiza estoques
    	for(Product p : outOfStockList){
    		try {
				ms.addQuantity(p.getID(), Integer.parseInt(textRestock.getText()));
				 msgBody = msgBody + p.getName() + ";";
			} 
    		
    		catch (NumberFormatException e) {}
    		
    		catch (Exception e) {}
    	}
    	//envia email
    	try{
    		costumersList = ms.registeredCostumersList();
    		
    		Properties props = new Properties();
    		props.put("mail.smtp.auth", "true");
    		props.put("mail.smtp.starttls.enable", "true");
    		props.put("mail.smtp.host", "smtp.gmail.com");
    		props.put("mail.smtp.port", "587");
    		//Recebe dados do email a ser enviado
    		Session session = Session.getInstance(props,
    				  new javax.mail.Authenticator() {
    					protected PasswordAuthentication getPasswordAuthentication() {
    						return new PasswordAuthentication("testetrabpoo@gmail.com", "testetrabpoo123");
    					}
    				  });
    		//envia email para todos os usuarios
    		for(Costumer c : costumersList)
    		{
    			Message msg = new MimeMessage(session);
                msg.setFrom(new InternetAddress("testetrabpoo@gmail.com"));
                msg.addRecipient(Message.RecipientType.TO,
                                 new InternetAddress(c.getEmail()));
                msg.setSubject("New products avaiable");
                msg.setText(msgBody);
                Transport.send(msg);
                System.out.println("Email enviado");
    		}
    	}
    	catch(Exception e)
    	{
    		System.out.println("Erro");
    	} 	
    	
    	//fecha janela
    	Stage stage = (Stage) btRestock.getScene().getWindow();
		stage.close();
    }

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		try {
			outOfStockList = ms.outOfStock();
		} 
		
		catch (Exception e) {}
		
		columnName.setCellValueFactory(new PropertyValueFactory<>("name"));
		columnID.setCellValueFactory(new PropertyValueFactory<>("ID"));
		
		tableRestock.setItems(FXCollections.observableArrayList(outOfStockList));
	}
    
    

}
