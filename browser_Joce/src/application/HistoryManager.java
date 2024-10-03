package application;


import javafx.collections.ObservableList;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebHistory;

public class HistoryManager {
	
	private WebEngine webEngine;
	private WebHistory webHistory;
	
	public HistoryManager(WebEngine webEngine) {
		this.webEngine = webEngine;
		this.webHistory = this.webEngine.getHistory();	
//		System.out.println(this.webEngine.getLocation());
	}	
	
    public ObservableList<WebHistory.Entry> getHistoryEntries() {
        return webHistory.getEntries();
    }

    // Load a specific URL from history
    public void loadHistoryEntry(String url) {
        webEngine.load(url);  
    }

    
}
