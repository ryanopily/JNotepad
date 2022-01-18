package ml.zer0dasho.jnotepad;

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
		notepad.tabs.add(new JTab("Untitled", ""));
	}
}
