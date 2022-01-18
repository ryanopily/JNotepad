module ml.zer0dasho.jnotepad {
	
	requires transitive javafx.graphics;
	requires transitive javafx.controls;
	requires javafx.fxml;
	requires javafx.base;
	
	exports ml.zer0dasho.jnotepad;
	exports ml.zer0dasho.jnotepad.model;
	exports ml.zer0dasho.jnotepad.controller;
	
	opens ml.zer0dasho.jnotepad.controller to javafx.fxml;
}