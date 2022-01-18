package ml.zer0dasho.jnotepad.model;

import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;

public class JWindow {
	
	public ObservableList<JTab> tabs = FXCollections.observableArrayList();

	public JWindow() {
		this.tabs.addListener((ListChangeListener<JTab>)(c) -> {});
	}
}