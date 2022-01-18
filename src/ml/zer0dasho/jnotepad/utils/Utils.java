package ml.zer0dasho.jnotepad.utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import javafx.event.EventHandler;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.util.Pair;

public class Utils {
	
	public static Map<Node, Pair<Double, Double>> stageOffset = new HashMap<Node, Pair<Double, Double>>();

	public static void makeCustomWindow(Stage stage) {
		stage.initStyle(StageStyle.TRANSPARENT);
		stage.getScene().setFill(Color.TRANSPARENT);
		
		Parent root = stage.getScene().getRoot();
		
		String style = 
				"-fx-padding: 20;" +
				"-fx-effect: dropshadow(gaussian, rgba(0, 0, 0, 0.4), 5, 0.4, 0, 0);" +
				"-fx-background-color: transparent;";
		
		root.setStyle(style + root.getStyle());
	}
	
	public static void makeDraggable(Stage stage, Node node) {
		node.setOnMousePressed(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                Pair<Double,Double> offset = new Pair<Double,Double>(event.getSceneX(), event.getSceneY());
            	stageOffset.put(node, offset);
            }
        });

		node.setOnMouseDragged(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
            	Pair<Double,Double> offset = stageOffset.get(node);
            	
            	Optional.ofNullable(offset).ifPresent(o -> {
            		stage.setX(event.getScreenX() - o.getKey());
                    stage.setY(event.getScreenY() - o.getValue());
            	});
            }
        });
	}
	
	public static void writeFile(File file, byte[] data) {
		try(FileOutputStream fos = new FileOutputStream(file)) {
			fos.write(data);
			fos.flush();
		}catch(IOException ex) {
			ex.printStackTrace();
		}
	}
	
	public static byte[] readStream(InputStream stream) {
		byte[] result;
		try {
			result = stream.readAllBytes();
			stream.close();
			return result;
		} catch (IOException e) {
			e.printStackTrace();
			return null;
		}

	}
	
	public static String readFile(File file) {
		try(FileInputStream fis = new FileInputStream(file)) {
		 	return new String(fis.readAllBytes());
		} catch(IOException ex) {
			return null;
		}
	}
	
	public static interface Tryable {
		
		public void Try() throws Exception;
		
	}
	
	public static boolean Try(Tryable tryable) {
		try {
			tryable.Try();
			return true;
		} catch(Exception ex) {
			ex.printStackTrace();
		}
		
		return false;
	}
}
