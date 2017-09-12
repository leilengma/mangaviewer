package custom.event.handler;


import javafx.scene.control.ListView;
import javafx.scene.input.MouseEvent;
import javafx.event.EventHandler;

public class ClickToNextPageHandler implements EventHandler<MouseEvent> {
	private double mousePosX;
	private double mousePosY;
	private ListView lv;
	
	public ClickToNextPageHandler(ListView lv) {
		this.lv = lv;
	}
	
	@Override
	public void handle(MouseEvent event) {
		if(event.getEventType().equals(MouseEvent.MOUSE_PRESSED)) {
			mousePosX = event.getSceneX();
			mousePosY = event.getSceneY();
		}else if(event.getEventType().equals(MouseEvent.MOUSE_RELEASED)) {
			if(event.getSceneX() == mousePosX && event.getSceneY() == mousePosY)
				lv.getSelectionModel().selectNext();
		}

		
	}

}
