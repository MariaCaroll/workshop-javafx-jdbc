package gui;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import application.Main;
import gui.util.Alerts;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.MenuItem;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.VBox;

public class MainViewController  implements Initializable {
	
	@FXML
	private MenuItem menuItemVendedor;
	
	@FXML
	private MenuItem menuItemDepartamento;
	
	@FXML
	private MenuItem menuItemAbout;
	
	// inico:  trata os elementos do menu em  scene buider
	@FXML
	public void onMenuItemVendedorAction() {
		System.out.println("onMenuItemVendedorAction");	
	}
	
	@FXML
	public void onMenuItemVDepartamentoAction() {
		loadView("/gui/DepartamentList.fxml");	
	}
	
	@FXML
	public void onMenuItemAboutAction() {
		loadView("/gui/About.fxml");	
	}
	
	//fim:  trata os os elementos do menu em  scene buider//
	

	@Override
	public void initialize(URL uri, ResourceBundle rb) {
		
		
	}
	
	//incluir o contudo do main about no main menu
	private synchronized void loadView(String absoluteName) {
		
		try {
			
			FXMLLoader loader = new FXMLLoader(getClass().getResource(absoluteName));
			VBox newVBox = loader.load();
			
			Scene mainScene = Main.getMainScene();
			VBox mainVbox = (VBox) ((ScrollPane) mainScene.getRoot()).getContent();
			
			Node mainMenu = mainVbox.getChildren().get(0);
			mainVbox.getChildren().clear();
			mainVbox.getChildren().add(mainMenu);
			mainVbox.getChildren().addAll(newVBox.getChildren());
			
		} catch (IOException e) {
			Alerts.showAlert("IO Exception", "Erro ao Carregar a Página", e.getMessage(), AlertType.ERROR);
		}
	}

}
