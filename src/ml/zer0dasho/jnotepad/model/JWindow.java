package ml.zer0dasho.jnotepad.model;

import java.io.File;

import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import ml.zer0dasho.jnotepad.utils.Utils;

public class JWindow {
	
	public ObservableList<JTab> tabs = FXCollections.observableArrayList();

	public JWindow() {
		this.tabs.addListener((ListChangeListener<JTab>)(c) -> {});
	}
	
	public void open(File file) {
		JTab tab = new JTab(file.getName(), Utils.readFile(file));
		tab.setFile(file);
		
		tabs.add(tab);
	}
}