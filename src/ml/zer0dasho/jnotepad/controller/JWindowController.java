package ml.zer0dasho.jnotepad.controller;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import javafx.collections.ListChangeListener;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.MenuBar;
import javafx.scene.control.TabPane;
import javafx.scene.control.TextArea;
import javafx.scene.input.TransferMode;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.shape.Circle;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import ml.zer0dasho.jnotepad.component.JTabView;
import ml.zer0dasho.jnotepad.model.JTab;
import ml.zer0dasho.jnotepad.model.JWindow;
import ml.zer0dasho.jnotepad.utils.Utils;

public class JWindowController implements Initializable {
	
	// Model
	public Scene scene;
	public JWindow model;

	// View
	@FXML private VBox root;
	@FXML private HBox window_toolbar;
	@FXML private Circle close_button;
	@FXML private MenuBar window_menu;
	@FXML private TabPane window_content;
	
	private List<JTabController> tabs = new ArrayList<>();

	// Controller
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		this.scene = new Scene(root);
		this.model = new JWindow();
		
		initPreferences();
		scene.getStylesheets().add("file:preferences.css");
		model.tabs.addListener(this::tabsChanged);
	}
	
	@FXML
	private void New(ActionEvent e) {
		model.tabs.add(new JTab("Untitled", ""));
	}

	@FXML
	private void Open(ActionEvent e) {
		FileChooser chooser = new FileChooser();
		List<File> selection = chooser.showOpenMultipleDialog(scene.getWindow());
		
		if(selection == null)
			return;
		
		selection.forEach(choice -> {
			JTab tab = new JTab(choice.getName(), Utils.readFile(choice));
			tab.setFile(choice);
			model.tabs.add(tab);
		});
	}
	
	@FXML
	private void Save(ActionEvent e) {
		JTab tab = model.tabs.get(window_content.getSelectionModel().getSelectedIndex());
		
		if(tab.getFile() == null)
			SaveAs(e);
		else 
			tab.save();
	}
		
	@FXML
	private void SaveAs(ActionEvent e) {
		FileChooser chooser = new FileChooser();
		File choice = chooser.showSaveDialog(scene.getWindow());
		
		if(choice == null || (!choice.exists() && !Utils.Try(() -> choice.createNewFile())))
			return;

		JTab tab = model.tabs.get(window_content.getSelectionModel().getSelectedIndex());
		tab.setFile(choice);
		tab.save();
	}
	
	@FXML
	private void Preferences(ActionEvent e) {
		File file = new File("preferences.css");
		initPreferences();
			
		JTab tab = new JTab("preferences.css", Utils.readFile(file)) {
			@Override
			public boolean save() {
				if(super.save()) {
					scene.getStylesheets().clear();
					scene.getStylesheets().add("file:preferences.css");
					return true;
				}
				return false;
			}
		};

		tab.setFile(file);
		model.tabs.add(tab);
	}
	
	@FXML
	private void Close(ActionEvent e) {
		model.tabs.remove(window_content.getSelectionModel().getSelectedIndex());
	}
	
	@FXML
	private void closeWindow(Event e) {
		Stage stage = (Stage) scene.getWindow();
		stage.close();
	}
	
	@FXML
	private void Undo(ActionEvent e) {
		TextArea tab_text_area = currentTab().tab_text_area;
		tab_text_area.undo();
	}
	
	@FXML
	private void Redo(ActionEvent e) {
		TextArea tab_text_area = currentTab().tab_text_area;
		tab_text_area.redo();
	}
	
	@FXML
	private void Cut(ActionEvent e) {
		TextArea tab_text_area = currentTab().tab_text_area;
		tab_text_area.cut();
	}
	
	@FXML
	private void Copy(ActionEvent e) {
		TextArea tab_text_area = currentTab().tab_text_area;
		tab_text_area.copy();
	}
	
	@FXML
	private void Paste(ActionEvent e) {
		TextArea tab_text_area = currentTab().tab_text_area;
		tab_text_area.paste();
	}
	
	@FXML
	private void Delete(ActionEvent e) {
		TextArea tab_text_area = currentTab().tab_text_area;
		tab_text_area.deleteText(tab_text_area.selectionProperty().get());
	}
	
	@FXML
	private void SelectAll(ActionEvent e) {
		TextArea tab_text_area = currentTab().tab_text_area;
		tab_text_area.selectAll();
	}
	
	@FXML
	private void UnselectAll(ActionEvent e) {
		TextArea tab_text_area = currentTab().tab_text_area;
		tab_text_area.deselect();
	}
	
	private JTabController currentTab() {
		int index = window_content.getSelectionModel().getSelectedIndex();
		return tabs.get(index);
	}
	
	private void initPreferences() {
		File file = new File("preferences.css");
		
		if(!file.exists()) {
			Utils.Try(() -> {
				file.createNewFile();
				Utils.writeFile(file, Utils.readStream(model.getClass().getResource("preferences.css").openStream()));
			});
		}
	}
	
	private void tabsChanged(ListChangeListener.Change<? extends JTab> change) {
		while(change.next()) {
			if(change.wasAdded()) {
				change.getAddedSubList().forEach(this::addTab);
			}
			
			else if(change.wasRemoved()) {
				change.getRemoved().forEach(tab -> removeTab(change.getFrom()));
				if(model.tabs.size() == 0)
					model.tabs.add(new JTab());
			}
		}
	}
	
	private void addTab(JTab tab) {
		try {
			JTabController controller = JTabView.create();	
			controller.setModel(tab);
			controller.tab.setOnCloseRequest((e) -> model.tabs.remove(tab));
			
			controller.tab_text_area.setOnDragOver((e) -> {
				e.acceptTransferModes(TransferMode.COPY);
			});
			
			controller.tab_text_area.setOnDragDropped((e) -> {
				e.getDragboard().getFiles().forEach(model::open);
			});
			
			tabs.add(controller);
			window_content.getTabs().add(controller.tab);
			window_content.getSelectionModel().selectLast();
		} catch (IOException e) {
			e.printStackTrace();
			return;
		}
	}
	
	private void removeTab(int index) {
		tabs.remove(index);
		window_content.getTabs().remove(index);
	}
}