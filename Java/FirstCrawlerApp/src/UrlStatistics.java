import java.util.ArrayList;

public class UrlStatistics {

	public int sc;
	public String scDesc;
	public String urlString;
	public int downLoadedSize;
	public String pageType;
	
	public ArrayList<String> outgoingLinks;
	public String extension;

	//with constructor overloading 
	
	public UrlStatistics(String Url,String pageType){
		
		this.pageType = pageType;
		this.urlString = Url;
	}
	public UrlStatistics(String Url,int sc,String scDesk){
		
		this.sc = sc;
		this.scDesc = scDesk;
		this.urlString = Url;
	}
	
	public UrlStatistics(String Url,int downloadSize,String pageType,String extension,ArrayList<String> outgoingLinks){
		this.urlString = Url;
		this.downLoadedSize = downloadSize;
		this.pageType = pageType;
		this.outgoingLinks = outgoingLinks;
		this.extension = extension;
		
		
	}
	
	
}