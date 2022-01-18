package ml.zer0dasho.jnotepad.component;

import java.io.IOException;

import javafx.fxml.FXMLLoader;
import ml.zer0dasho.jnotepad.controller.JWindowController;

public class JWindowView {
	public static JWindowController create() throws IOException {
		FXMLLoader loader = new FXMLLoader(JWindowView.class.getResource("jnotepad.fxml"));
		loader.load();
				   
		return loader.getController();
	}
}