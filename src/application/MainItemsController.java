package application;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.imageio.ImageIO;

import custom.event.handler.ClickToNextPageHandler;
import custom.event.handler.PressKeyToNextPageHandler;
import javafx.beans.binding.Bindings;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.embed.swing.SwingFXUtils;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.input.ScrollEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.stage.DirectoryChooser;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.stage.Window;


class LocatedImage {
    private final String url;
    private Image img;
    
    public LocatedImage(String url, Image img) {
        this.img = img;
        this.url = url;
    }
    
    public Image getImage() {
    	return this.img;
    }

    public String getURL() {
        return url;
    }
        
}

enum ViewMode{
	imageWidth,
	panelWidth,
	stageHeight
}

public class MainItemsController {
	@FXML ImageView imgView;
	@FXML MenuBar mainMenu;
	@FXML ScrollPane imgViewScroller;
	@FXML ListView<LocatedImage> thumbnails;
	@FXML AnchorPane imagePane;
	@FXML StackPane imgHolder;
	private Main mainApp;
	private ViewMode mode;

	class ImageCell extends ListCell<LocatedImage>{
		private ImageView img = new ImageView();
		
		@Override
	    protected void updateItem(LocatedImage item, boolean empty) {
			if(empty) return;
			super.updateItem(item, empty);
			img.setImage(item.getImage());
			img.fitWidthProperty().bind(thumbnails.widthProperty()); 
			img.setPreserveRatio(true);
			setGraphic(img);
	    }
	}
	
	private void showImage(LocatedImage image) throws IOException {
		String path = image.getURL();
		Image realimage = new Image(new FileInputStream(path));
		if(mode.equals(ViewMode.imageWidth)) {
	    	imgView.setFitWidth(realimage.getWidth());
	    	imgView.setFitHeight(realimage.getHeight());
		}
    	imgView.setImage(realimage);
	}
	
	public void fullScreen() {
       Stage stage = (Stage) imgView.getScene().getWindow();
       stage.setFullScreen(true);
       mainApp.getMainlayout().getRowConstraints().get(0).setMaxHeight(0);
		
	}
	
	public void fitWidth(ActionEvent ae) {
		MenuItem clickedItem = (MenuItem)ae.getSource();
    	imgView.fitHeightProperty().unbind();
    	imgView.fitWidthProperty().unbind();
    	imgView.setFitWidth(0);
    	imgView.setFitHeight(0);
    	Image img = imgView.getImage();
    	imgView.setImage(null);
		switch(clickedItem.getText()) {
			case "Image Width": 
				mode = ViewMode.imageWidth;
			    imgView.setFitWidth(img.getWidth());
				break;
			case "Panel Width":
				mode = ViewMode.panelWidth;
				imgView.fitWidthProperty().bind(imagePane.widthProperty());
				break;
			case "Program Height":
				mode = ViewMode.stageHeight;
				imgView.fitHeightProperty().bind(imagePane.heightProperty());
				break;
		}
		imgView.setImage(img);
	}
	
	

	public void setMainApp(Main main) {
		mainApp = main;
		thumbnails.setItems(main.getImages());
		thumbnails.setCellFactory(param -> new ImageCell());
	}
	
	public void initalize(GridPane mainLayout) {
		mode = ViewMode.imageWidth;
		//imgView.fitWidthProperty().bind(imagePane.widthProperty());
		imgViewScroller.setPannable(true);
	    
		imgHolder.minWidthProperty().bind(Bindings.createDoubleBinding(() -> 
	    	imgViewScroller.getViewportBounds().getWidth(), imgViewScroller.viewportBoundsProperty()));
		
//		imgViewScroller.setOnScroll(new EventHandler<ScrollEvent>() {
//	        @Override public void handle(ScrollEvent event) {
//	            if(imgViewScroller.getVvalue() == 1.0 && event.getDeltaY()<0)
//	            	thumbnails.getSelectionModel().selectNext();
//	            else if(imgViewScroller.getVvalue() == 0.0 && event.getDeltaY()<0)
//	            	thumbnails.getSelectionModel().selectNext();
//	            else if(imgViewScroller.getVvalue() == 0.0 && event.getDeltaY()>0)
//	            	thumbnails.getSelectionModel().selectPrevious();
//	        }
//		});
//			
		imgView.addEventHandler(MouseEvent.ANY, new ClickToNextPageHandler(thumbnails));
		imgViewScroller.addEventHandler(KeyEvent.ANY, new PressKeyToNextPageHandler(imgViewScroller, thumbnails));
		
		
		mainApp.getScene().addEventHandler(KeyEvent.KEY_PRESSED, (key) -> {
			if(key.getCode().equals(KeyCode.ESCAPE)) {
				if(!mainApp.getPrimaryStage().isFullScreen()) {
					mainLayout.getRowConstraints().get(0).setMaxHeight(25);					
				}
			}
		});
		
		thumbnails.addEventHandler(KeyEvent.KEY_PRESSED, (key) -> {
			if(key.getCode().equals(KeyCode.ESCAPE)) {
				if(!mainApp.getPrimaryStage().isFullScreen()) {
					mainLayout.getRowConstraints().get(0).setMaxHeight(25);					
				}
			}
		});
		
		
		thumbnails.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<LocatedImage>() {
			@Override
			public void changed(ObservableValue<? extends LocatedImage> observable, LocatedImage oldValue, LocatedImage newValue) {
				try {
					showImage(newValue);
					imgViewScroller.setVvalue(0);
				} catch (IOException e) {
					e.printStackTrace();
				}
			}			
		});
		
//		imgViewScroller.setOnKeyPressed(new EventHandler<KeyEvent>() {
//
//			@Override
//			public void handle(KeyEvent event) {
//				if(event.getCode() == KeyCode.SPACE || 
//						(event.getCode() == KeyCode.DOWN && imgViewScroller.getVvalue() == 1.0))
//					thumbnails.getSelectionModel().selectNext();
//				
//			}
//			
//		});
	}	

	@FXML
    public void showFileChooser(ActionEvent ae){
        Window theStage = mainMenu.getScene().getWindow();
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Choose your file");
        fileChooser.showOpenDialog(theStage);
    }
    
    @FXML
    public void showDirectoryChooser(ActionEvent ae) throws IOException {
        Window theStage = mainMenu.getScene().getWindow();
        DirectoryChooser dc = new DirectoryChooser();
        dc.setTitle("Choose your Directory");
        File folder = dc.showDialog(theStage);
        getAllImgs(folder);
    }

	private void getAllImgs(File folder) throws IOException {
		List<LocatedImage> imgs = new ArrayList<LocatedImage>();
		File[] files = folder.listFiles();
		for(File file:files) {
			if(file.getName().endsWith(".jpg")) {
				BufferedImage img = ImageIO.read(file);
				img = resizeImage(img,200);
				Image fxImg = SwingFXUtils.toFXImage(img, null);
				imgs.add(new LocatedImage(file.getPath(), fxImg));
			}			
		}
		mainApp.getImages().setAll(imgs);
        if(!imgs.isEmpty()) {
        	thumbnails.getSelectionModel().select(0);
        }
	}
    
	private static BufferedImage resizeImage(BufferedImage originalImage, int width) {
        double ratio = (double) originalImage.getWidth() / originalImage.getHeight();
		BufferedImage resizedImage  = new BufferedImage(width, (int)(width/ratio), BufferedImage.SCALE_SMOOTH);
        Graphics2D g = resizedImage.createGraphics();
        g.drawImage(originalImage, 0, 0, width, (int) (width/ratio), null);
        g.dispose();
        return resizedImage;
    }
    
}
