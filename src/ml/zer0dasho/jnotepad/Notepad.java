package ml.zer0dasho.jnotepad;

import java.io.IOException;

import javafx.scene.Scene;
import javafx.stage.Stage;
import ml.zer0dasho.jnotepad.component.JWindowView;
import ml.zer0dasho.jnotepad.controller.JWindowController;
import ml.zer0dasho.jnotepad.model.JWindow;
import ml.zer0dasho.jnotepad.utils.Utils;

public class Notepad {
	
	public static JWindow create(Stage stage) throws IOException {
		JWindowController controller = JWindowView.create();
		init(stage, controller.scene);
		stage.setTitle("JNotepad");
		stage.show();
		
		return controller.model;
	}
	
	/** Create custom window **/
	private static void init(Stage stage, Scene scene) {
		stage.setScene(scene);
		
		Utils.makeCustomWindow(stage);
		Utils.makeDraggable(stage, scene.getRoot());
	}


}