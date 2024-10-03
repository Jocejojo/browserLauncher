package application;

import javafx.beans.binding.Bindings;
import javafx.scene.control.Button;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.web.WebEngine;

public class NavigationBar {
	private HBox navigationBar;
	private TextField urlField;
	private WebEngine webEngine;
	private Button backButton,forwardButton,reloadButton, goButton, bookmarkButton;
   
	private MenuManager menuMgr;
	private BookmarkManager bookmarkMgr;
	
	private ProgressBar progressBar;
	
	private ImageView backView = new ImageView(
			new Image(getClass().getResourceAsStream("arrow_left.png")));
	private ImageView forwardView = new ImageView(
			new Image(getClass().getResourceAsStream("arrow_right.png")));
	private ImageView reloadView = new ImageView(
			new Image(getClass().getResourceAsStream("arrows_counterclockwise.png")));	
	
	private int size = 20;
	
	
	public NavigationBar(WebEngine webEngine, MenuManager menuMgr) {
		this.webEngine = webEngine;
		this.urlField = new TextField();
		
		this.menuMgr = menuMgr;
		this.bookmarkMgr = menuMgr.getBookmarkManager();
	
		this.addButton();      
		this.navigationBar = new HBox(10, backButton, forwardButton, 
				reloadButton, urlField, goButton, bookmarkButton);
		HBox.setHgrow(urlField, Priority.ALWAYS);
		this.navigationBar.setSpacing(5);
		
		this.setupProgressBar();
	}
	
	
	public void addButton() {
		//Set back button
		backButton = new Button();
		backView.setFitHeight(size);
		backView.setFitWidth(size);
		backButton.setGraphic(backView); 
		backButton.setOnAction(e ->{
        	this.webEngine.executeScript("history.back()");
        });
			
		//Set forward button
		forwardButton = new Button();
		forwardView.setFitHeight(size);
		forwardView.setFitWidth(size);
		forwardButton.setGraphic(forwardView);
        forwardButton.setOnAction(e ->{
        	this.webEngine.executeScript("history.forward()");
        });
        
        //Set reload button
		reloadButton = new Button();
		reloadView.setFitHeight(size);
		reloadView.setFitWidth(size);
		reloadButton.setGraphic(reloadView);
        reloadButton.setOnAction(e ->{
        	this.webEngine.reload();
        });
        
        //Set go button and enter
		goButton = new Button("Go");
        goButton.setOnAction(e ->{
        	this.loadPage();
        });
        
        urlField.setOnKeyPressed(e ->{
        	if(e.getCode() == KeyCode.ENTER) {
        		goButton.fire();
        	}
        });
        
        //Set book mark button
        bookmarkButton = new Button();
        updateBookmarkIcon();

        bookmarkButton.setOnAction(e -> {
            bookmarkMgr.editBookmark(); 
            updateBookmarkIcon(); 
            menuMgr.updateBookmarkMenu();
        });
	}
	
	public void updateBookmarkIcon() {
		ImageView bookmarkView;
		if(this.bookmarkMgr.isBookmarked()) {
			bookmarkView = new ImageView(new Image(getClass().getResourceAsStream("star_filled.png")));
		}else {
			bookmarkView = new ImageView(new Image(getClass().getResourceAsStream("star_blank.png")));
		}
		bookmarkView.setFitHeight(size);
		bookmarkView.setFitWidth(size);
		bookmarkButton.setGraphic(bookmarkView);
//		System.out.println(bookmarkMgr.getBookmarks());
	}
	
	//Load page with or without http://
	public void loadPage() {
		String urlText = this.urlField.getText();
		if(!urlText.startsWith("http://")) {
			urlText = "http://" + urlText;
		}
		this.webEngine.load(urlText);
		this.urlField.setText(urlText); 
		updateBookmarkIcon(); 
		menuMgr.updateHistoryMenu();
	}
	
	private void setupProgressBar() {
	    // Initialise process bar
	    progressBar = new ProgressBar(0);
	    progressBar.setPrefWidth(200); 
	    progressBar.setVisible(false); 

	   
	    
	    // Set a listener for web engine
        progressBar.progressProperty().bind(webEngine.getLoadWorker().progressProperty());
        progressBar.visibleProperty().bind(
                Bindings.when(progressBar.progressProperty().lessThan(0).or(progressBar.progressProperty().isEqualTo(1)))
                        .then(false)
                        .otherwise(true)
        );
        progressBar.managedProperty().bind(progressBar.visibleProperty());
        progressBar.setMaxWidth(Double.MAX_VALUE);
	    
	}
	
	public HBox getNavBar() {
		return navigationBar;
	}
	
	public ProgressBar getProgressBar() {
		return progressBar;
	}
	
}
