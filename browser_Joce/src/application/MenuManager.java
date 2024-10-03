package application;

import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebHistory;
import javafx.scene.web.WebView;

/**
 * The class manages the menu, including book marks, history, printer, and settings.
 */
public class MenuManager {
	private WebEngine webEngine;
	private WebView webView;
	
	private BookmarkManager bookmarkMgr;
	private Menu bookmarkMenu;
	
	private HistoryManager historyMgr;
	private Menu historyMenu;
	
	private PrinterManager printerMgr;
	private Menu printerMenu;
	
	private SettingManager settingMgr;
	private Menu settingMenu;
	
	private MenuBar menuBar;
	

    public MenuManager(WebEngine webEngine, WebView webView) {
    	this.webEngine = webEngine;
    	this.webView = webView;
    	
    	this.historyMgr = new HistoryManager(webEngine);
    	this.bookmarkMenu = new Menu("Bookmarks"); 
    	
        this.bookmarkMgr = new BookmarkManager(webEngine);
        this.historyMenu = new Menu("History");
        
        this.printerMgr = new PrinterManager(webView);
        this.printerMenu = new Menu("Printer");

        this.settingMgr = new SettingManager(webEngine, webView);
        this.settingMenu = new Menu("Settings");
        
        this.menuBar = new MenuBar();
        
        this.addMenuItems();
    }

    private void addMenuItems() {
        // Create menu items
        this.updateBookmarkMenu();      
        this.updateHistoryMenu();
        this.addPrinter();
        this.addSetting();

        // Add all items to MenuBar
        menuBar.getMenus().addAll(bookmarkMenu, historyMenu, printerMenu,settingMenu);
    }
    
    public void updateBookmarkMenu() {
        bookmarkMenu.getItems().clear(); 
        for (String bookmark : bookmarkMgr.getBookmarks()) {
            MenuItem bookmarkItem = new MenuItem(bookmark);
            bookmarkItem.setOnAction(e -> webEngine.load(bookmark)); 
            bookmarkMenu.getItems().add(bookmarkItem);
        }
    }
    
    public void updateHistoryMenu() {
    	historyMenu.getItems().clear();
//    	System.out.println("History entries count: " + historyMgr.getHistoryEntries().size()); 
    	for(WebHistory.Entry entry : historyMgr.getHistoryEntries()) {
    		MenuItem historyItem = new MenuItem(entry.getUrl());
    		historyItem.setOnAction(e -> historyMgr.loadHistoryEntry(entry.getUrl()));
    		historyMenu.getItems().add(historyItem);
    	}
    }
    
    public void addPrinter() {
    	MenuItem printItem = new MenuItem("Print");
    	printItem.setOnAction(e -> printerMgr.printPage());
    	printerMenu.getItems().add(printItem);
    }
    
    
    public void addSetting() {
    	MenuItem setHomeItem = new MenuItem("Set as home page");
    	setHomeItem.setOnAction(e -> {
    	    String currentUrl = webEngine.getLocation();  
    	    settingMgr.setHomeScreen(currentUrl);        
    	});
    	
    	MenuItem goHomeItem = new MenuItem("Go to home page");
    	goHomeItem.setOnAction(e ->settingMgr.loadHomeScreen());
    	
    	MenuItem zoomItem = new MenuItem("Set zoom");
    	zoomItem.setOnAction(e -> settingMgr.setZoomDialog());
    	
    	MenuItem switchTheme = new MenuItem("Swtich theme");
    	switchTheme.setOnAction(e ->{
    		if (settingMgr.getTheme().equals("light")) {
    			settingMgr.setTheme("sky");
            } else {
            	settingMgr.setTheme("light");
            }
    	});
    	
    	MenuItem resetItem = new MenuItem("Reset");
    	resetItem.setOnAction(e -> settingMgr.reset());
    	settingMenu.getItems().addAll(setHomeItem, goHomeItem, zoomItem, switchTheme, resetItem);
    }
    
    
    public BookmarkManager getBookmarkManager() {
    	return this.bookmarkMgr;
    }
    
    public MenuBar getMenuBar() {
    	return this.menuBar;
    }
}
