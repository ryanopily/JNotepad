package ml.zer0dasho.jnotepad.model;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class JTab {

	private File file;
	
	public StringProperty title;
	public StringProperty content;

	public JTab() {
		this("Untitled", "");
	}
	
	public JTab(String title, String content) {
		this.title = new SimpleStringProperty(title);
		this.content = new SimpleStringProperty(content);
	}
		
	public boolean save() {
		title.setValue(file.getName());
			
		try(FileOutputStream fos = new FileOutputStream(file)) {
			fos.write(content.get().getBytes());
			fos.flush();
			return true;
		} catch (IOException e) {
			e.printStackTrace();
			return false;
		}
	}
	
	public File getFile() {
		return file;
	}
	
	public void setFile(File file) {
		this.file = file;
		title.setValue(file.getName());
	}
}