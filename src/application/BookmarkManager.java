package application;

import java.util.ArrayList;
import java.util.List;

import javafx.scene.web.WebEngine;

public class BookmarkManager {
	private WebEngine webEngine;
	private List<String> bookmarks;
	
	public BookmarkManager(WebEngine webEngine) {
		this.webEngine = webEngine;
		this.bookmarks =  new ArrayList<>();
	}
	
	public void editBookmark() {
		String url = this.webEngine.getLocation();
		if(this.bookmarks.contains(url)) {
			this.bookmarks.remove(url);
		}else {
			this.bookmarks.add(url);
		}
			
	}

	public List<String> getBookmarks() {
		return bookmarks;
	}
	
		
	public boolean isBookmarked() {
		String url = this.webEngine.getLocation();
		if(this.bookmarks.contains(url)) {
			return true;
		}
		return false;
	}
	
	public void removeBookmark(String url) {
		if(this.bookmarks.contains(url)) {
			this.bookmarks.remove(url);
		}
	}
	

	

	
}
