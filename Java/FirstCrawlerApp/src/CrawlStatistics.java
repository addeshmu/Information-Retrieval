import java.util.ArrayList;

public class CrawlStatistics {

	private int totalProcessedPages;
    private long totalLinks;
    private long totalTextSize;
    private StringBuilder svsb = new StringBuilder();
    
    ArrayList<UrlStatistics> urlsAttempted;
    ArrayList<UrlStatistics> urlsFetched;
    ArrayList<UrlStatistics> urlsEncountered;
    
    
    
    public CrawlStatistics(){
    	urlsAttempted = new ArrayList<UrlStatistics>();
    	urlsFetched = new ArrayList<UrlStatistics>();
    	urlsEncountered = new ArrayList<UrlStatistics>();
    }
    
    
    public StringBuilder getSB(){
    	return this.svsb;
    }
    
    public void setSB(String s){
    	this.svsb.append(s);
		this.svsb.append(',');
		this.svsb.append("OK");
		this.svsb.append('\n');
    }

	public int getTotalProcessedPages() {
		return totalProcessedPages;
	}
	public void setTotalProcessedPages(int totalProcessedPages) {
		this.totalProcessedPages = totalProcessedPages;
	}
	public long getTotalLinks() {
		return totalLinks;
	}
	
	public void incProcessedPages() {
		this.totalProcessedPages++;
	}

	public void setTotalLinks(long totalLinks) {
		this.totalLinks = totalLinks;
	}
	public long getTotalTextSize() {
		return totalTextSize;
	}
	public void setTotalTextSize(long totalTextSize) {
		this.totalTextSize = totalTextSize;
	}

	public void incTotalLinks(int count) {
        this.totalLinks += count;
    }

    public void incTotalTextSize(int count) {
        this.totalTextSize += count;
    }
}
