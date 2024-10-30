package application;

import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Dialog;
import javafx.scene.control.Slider;
import javafx.scene.layout.VBox;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;


public class SettingManager {

	private WebEngine webEngine;
	private WebView webView;
	private String homeScreenUrl;
	private String theme;
	private double zoomLevel;
	
	public SettingManager(WebEngine webEngine, WebView webView) {
		this.webEngine = webEngine;
		this.webView = webView;
		this.homeScreenUrl = "http://ecs.wgtn.ac.nz";
		this.theme = "light";
		this.zoomLevel = 1.00;
	}
	
    public void setHomeScreen(String url) {    
    	this.homeScreenUrl = url;
    }
    
    public String getHomeScreen() {
        return this.homeScreenUrl;
    }
    
    public void loadHomeScreen() {
        this.webEngine.load(this.homeScreenUrl);
    }
	
    public double getZoomLevel() {
    	return this.zoomLevel;
    }
    
    public void setZoomLevel(double zoomLevel) {
    	this.zoomLevel = zoomLevel;
    	this.webView.setZoom(zoomLevel);
    }
    
    //Use a slider to control zoom level
    public void setZoomDialog() {

        Dialog<Double> dialog = new Dialog<>();
        dialog.setTitle("Set Zoom Level");
        
    	Slider slider = new Slider(0.5, 2.0, zoomLevel);
        slider.setShowTickMarks(true);  // Show tick marks
        slider.setShowTickLabels(true); // Show tick labels
        slider.setMajorTickUnit(0.25);  // Set tick unit 0.25
        slider.setBlockIncrement(0.05); // Set each increment 0.05

        slider.valueProperty().addListener((obs, oldValue, newValue) -> {
            zoomLevel = newValue.doubleValue();
            this.setZoomLevel(zoomLevel);
//            System.out.println("Zoom level set to: " + zoomLevel);
        });


        VBox dialogPane = new VBox(slider);
        dialog.getDialogPane().setContent(dialogPane);
        dialog.getDialogPane().getButtonTypes().addAll(ButtonType.CANCEL);

        dialog.show();
	       
    }
    
    public void switchTheme() {
        Button switchButton = new Button("Switch theme");
        switchButton.setOnAction(e ->{
        	if (theme.equals("light")) {
                theme = "sky"; 
            } else {
                theme = "light";
            }
        });
        
        String themeFile = theme + ".css"; 
        Scene scene = webView.getScene();
        if (scene != null) {
            scene.getStylesheets().clear(); 
            scene.getStylesheets().add(getClass().getResource(themeFile).toExternalForm()); 
        }
    }
    
    
    public void setTheme(String theme) {
		this.theme = theme;
        String themeFile = theme + ".css"; 
        Scene scene = webView.getScene();
        if (scene != null) {
            scene.getStylesheets().clear(); 
            scene.getStylesheets().add(getClass().getResource(themeFile).toExternalForm()); 
        }
	}

	public String getTheme() {
		return theme;
	}

	public void reset() {
		this.homeScreenUrl = "http://ecs.wgtn.ac.nz";
		this.theme = "light";
		this.zoomLevel = 1.00;
		this.webView.setZoom(zoomLevel);
    }
}
