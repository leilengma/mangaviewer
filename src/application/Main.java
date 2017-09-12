package application;
	
import java.awt.image.BufferedImage;
import java.io.IOException;

import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyCombination;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

public class Main extends Application {
    private Stage primaryStage;
    private GridPane mainLayout;
    private Scene scene;
    private ObservableList<LocatedImage> imgList = FXCollections.observableArrayList();

    @Override
    public void start(Stage primaryStage) throws Exception{
        this.primaryStage = primaryStage;
        primaryStage.setTitle("Manga Viewer");
        showMainView();
    }
    
    public Scene getScene() {
    	return scene;
    }
    
    public ObservableList<LocatedImage> getImages(){
    	return imgList;
    }
    
    public GridPane getMainlayout() {
    	return mainLayout;
    }
    
    
    public Stage getPrimaryStage(){
        return primaryStage;
    }
    private void showMainView() throws IOException{
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(Main.class.getResource("MainView.fxml"));
        mainLayout = loader.load();
        this.scene = new Scene(mainLayout);
        primaryStage.setScene(scene);
        primaryStage.show();
        MainItemsController mainController = loader.getController();
        mainController.setMainApp(this);
        mainController.initalize(mainLayout);
    }

    public static void main(String[] args) {
        launch(args);
    }
}

