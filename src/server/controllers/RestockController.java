package server.controllers;

import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

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
import marketlib.Product;
import server.marketserver.MarketServer;

public class RestockController implements Initializable{
	MarketServer ms = MarketServer.newMarketServer(); 
	
	private ArrayList<Product> outOfStockList;
	
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

    @FXML
    void onClickRestock(ActionEvent event) {
    	
    	for(Product p : outOfStockList){
    		try {
				ms.addQuantity(p.getID(), Integer.parseInt(textRestock.getText()));
			} 
    		
    		catch (NumberFormatException e) {}
    		
    		catch (Exception e) {}
    	}
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
