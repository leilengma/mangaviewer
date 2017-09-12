package custom.event.handler;

import java.util.Date;

import javafx.event.EventHandler;
import javafx.scene.control.ListView;
import javafx.scene.control.ScrollPane;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;

public class PressKeyToNextPageHandler implements EventHandler<KeyEvent> {
	private double vvScrollerPos;
	private ScrollPane scroller;
	private ListView lv;
	private long rounds;
	private long start;
	
	public PressKeyToNextPageHandler(ScrollPane scroller, ListView lv) {
		this.scroller = scroller;
		rounds = 0;
		start = 0;
		this.lv=lv;
	}

	@Override
	public void handle(KeyEvent event) {
		if(event.getEventType().equals(KeyEvent.KEY_PRESSED)) {
			vvScrollerPos = scroller.getVvalue();
			if(start == 0) {
				start = new Date().getTime();
			}
			start = new Date().getTime();
		}else if(event.getEventType().equals(KeyEvent.KEY_RELEASED)) {
			System.out.println(new Date().getTime()-start);
			if(event.getCode().equals(KeyCode.SPACE)) {
				lv.getSelectionModel().selectNext();
			}else if(event.getCode().equals(KeyCode.DOWN))
				if(scroller.getVvalue()==vvScrollerPos) 
					if(vvScrollerPos==1.0) 
						lv.getSelectionModel().selectNext();
			}else if(event.getCode().equals(KeyCode.UP)){
				if(scroller.getVvalue()==vvScrollerPos) 
					if(vvScrollerPos==0.0) 
						lv.getSelectionModel().selectPrevious();
		}
	}
		
	
	
}
