package ml.zer0dasho.jnotepad.controller;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Tab;
import javafx.scene.control.TextArea;
import javafx.scene.layout.HBox;
import ml.zer0dasho.jnotepad.model.JTab;

public class JTabController implements Initializable {
	
	public JTab model;

	@FXML Tab tab;
	@FXML HBox tab_content;
	@FXML TextArea tab_text_area;
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		tab.setText("Untitled");
		tab_text_area.setWrapText(true);
	}
	
	public void setModel(JTab model) {
		this.model = model;
		tab.textProperty().bind(model.title);
		tab_text_area.textProperty().bindBidirectional(model.content);

		tab_text_area.textProperty().addListener((text) -> {
			if(!model.title.get().startsWith("* "))
				model.title.setValue("* " + model.title.get());
		});
	}
}