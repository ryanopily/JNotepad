package ml.zer0dasho.jnotepad;

import java.io.File;

import javafx.application.Application;
import javafx.stage.Stage;
import ml.zer0dasho.jnotepad.model.JTab;
import ml.zer0dasho.jnotepad.model.JWindow;

public class Main extends Application {

	public static void main(String...args) {
		Application.launch(args);
	}

	@Override
	public void start(Stage stage) throws Exception {
		JWindow notepad = Notepad.create(stage);
		
		this.getParameters().getRaw().forEach(param -> {
			File file = new File(param);
			if(file.exists()) {
				notepad.open(file);
			}
		});
		
		if(notepad.tabs.size() == 0)
			notepad.tabs.add(new JTab("Untitled", ""));
	}
}
