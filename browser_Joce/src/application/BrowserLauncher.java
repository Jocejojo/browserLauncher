package application;
	
import javafx.application.Application;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.MenuBar;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;


public class BrowserLauncher extends Application {
	
	private WebView webView;
	private WebEngine webEngine;
	private NavigationBar navBar;
	private MenuBar menuBar;
	
	@Override
	public void start(Stage primaryStage) {
		try {
			primaryStage.setTitle("Web Browser Joce");
			
			//Initialise webView
			webView = new WebView();
			webEngine = this.webView.getEngine();
			webEngine.load("http://ecs.wgtn.ac.nz");
			
			//Instantiate navigation bar and menu
			MenuManager menuMgr = new MenuManager(webEngine, webView);	
			menuBar = menuMgr.getMenuBar();
			navBar = new NavigationBar(webEngine, menuMgr);	
			
			HBox topBar = new HBox(navBar.getNavBar(), menuBar);
			HBox.setHgrow(navBar.getNavBar(), Priority.ALWAYS);

			VBox vbox = new VBox();
			vbox.getChildren().addAll(topBar, navBar.getProgressBar());
			
			//Set layout
			VBox root = new VBox();
			root.getChildren().addAll(vbox,webView);
			root.setSpacing(10);
			VBox.setVgrow(webView, Priority.ALWAYS);

			//Set scene
			Scene scene = new Scene(root);
			primaryStage.setScene(scene);
			primaryStage.sizeToScene();
			primaryStage.show();
			
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	
	public static void main(String[] args) {
		launch(args);
	}
	
	
	
}
