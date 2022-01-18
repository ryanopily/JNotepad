package ml.zer0dasho.jnotepad.component;

import java.io.IOException;

import javafx.fxml.FXMLLoader;
import ml.zer0dasho.jnotepad.controller.JTabController;

public class JTabView {
	
	public static JTabController create() throws IOException {
		FXMLLoader loader = new FXMLLoader(JTabView.class.getResource("jtab.fxml"));
		loader.load();
	   
		return loader.getController();
	}
}