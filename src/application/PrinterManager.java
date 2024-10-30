package application;

import javafx.print.PrinterJob;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;

public class PrinterManager {
	private WebView webView;
	
	public PrinterManager(WebView webView) {
		this.webView = webView;
	}
	
	public void printPage() {
		PrinterJob printerJob = PrinterJob.createPrinterJob();
		if(printerJob != null && printerJob.showPrintDialog(null)) {
		
			printerJob.printPage(webView);
		
		}
	}
}
