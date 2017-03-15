import java.util.ArrayList;
import java.util.Set;
import java.util.regex.Pattern;

import edu.uci.ics.crawler4j.crawler.Page;
import edu.uci.ics.crawler4j.crawler.WebCrawler;
import edu.uci.ics.crawler4j.parser.HtmlParseData;
import edu.uci.ics.crawler4j.url.WebURL;



public class MyCrawler  extends WebCrawler  {
	
	static StringBuilder sb = new StringBuilder();
	static StringBuilder esb = new StringBuilder();
	static StringBuilder ssb = new StringBuilder();
	static StringBuilder svsb = new StringBuilder();
	//private final static Pattern FILTERS = Pattern.compile(".*(\\.(css|js|gif|jpg"+"|png|mp3|mp3|zip|gz))$");
	private final static Pattern FILTERS = Pattern.compile(".*(\\.(css|js|map|xml|rss"+"|mp3|mp3|zip|gz))$");
	//private final static Pattern FILTERS = Pattern.compile(".*(\\.(com|html|doc|pdf|jpg"+ "|png|jpeg|gif|bmp))$");
	static int b;
	CrawlStatistics myCrawlStat;
	
	public MyCrawler() {
        myCrawlStat = new CrawlStatistics();
    }
	
	
	@Override
	public boolean shouldVisit(Page ReferringPage,WebURL url)
	{
		
		
		String href = url.getURL().toLowerCase();
		
		String validity ;
		System.out.println(href);
		svsb.append(href);
		myCrawlStat.setSB(href);
		svsb.append(',');
		svsb.append("N_OK");
		svsb.append('\n');
		
		if (!FILTERS.matcher(href).matches() && (href.startsWith("https://www.nytimes.com/")|| href.startsWith("https://www.nytimes.com/"))){
			validity = "OK";
		}
		else{
			validity = "N_OK";
		}
		myCrawlStat.urlsEncountered.add(new UrlStatistics(href,validity));
		// filters which specify which domains we can hit and which ones we cannot.
		return !FILTERS.matcher(href).matches() && (href.startsWith("http://www.nytimes.com/")|| href.startsWith("https://www.nytimes.com/"));
				//|| href.startsWith("https://www.nytimes.com/")||href.startsWith("http://www.nyt.com/")||href.contains("nyt.com")); 
		
		//return true;
	}
	

	@Override 
	public void visit(Page page)
	{
	    String url =page.getWebURL().getURL();
		ArrayList<String> olinks = new ArrayList<String>();
	    sb.append(url);
		sb.append(",");
			
	    esb.append(url);
	    esb.append(",");
		
	    esb.append(page.getStatusCode());
	    esb.append("\n");

	    String tempContentType = page.getContentType().toString();
	    if (tempContentType.contains(";"))
	    {
	    	tempContentType = tempContentType.split(";")[0];
	    }
	    sb.append(page.getContentData().length);
    	sb.append(",");
    	myCrawlStat.incProcessedPages();
    	
	    /*System.out.println("URL: "+ url);*/ 
	    if (page.getParseData() instanceof HtmlParseData) {
	    	HtmlParseData htmlParseData = (HtmlParseData) page.getParseData();
	    	String text =htmlParseData.getText();
	    	String html =htmlParseData.getHtml();
	    	Set<WebURL>links = htmlParseData.getOutgoingUrls();
	    	
	    	
	    	for(WebURL link:links){
	    		olinks.add(link.getURL().toString());
	    	}
	    	
	    	sb.append(links.size());
	    	sb.append(",");
	    	
	    /*	System.out .println("Content Type: " + page.getContentType().toString());
	    	System.out .println("Text length: " + text.length());
	    	System.out .println("Html length: " + html.length());
	    	System.out .println("Number of outgoing links: " + links.size());*/
	    	b+=links.size();
	    	myCrawlStat.incTotalLinks(links.size());
	    	myCrawlStat.urlsFetched.add(new UrlStatistics(url.toString(),page.getContentData().length,tempContentType,"",olinks));
	    }
	    else
	    {
	    	myCrawlStat.urlsFetched.add(new UrlStatistics(url.toString(),page.getContentData().length,tempContentType,"",olinks));
	    	sb.append("NA");
	    	sb.append(",");
	    	
	    }
		sb.append(tempContentType);
    	sb.append("\n");
    }

	
	@Override
	protected void onContentFetchError(WebURL webUrl) {
        
		System.out .println(webUrl);
		
		
		logger.warn("Can't fetch content of: {}", webUrl.getURL());
		
        // Do nothing by default (except basic logging)
        // Sub-classed can override this to add their custom functionality
    }
	@Override
	 protected void onUnexpectedStatusCode(String urlStr, int statusCode, String contentType,String description) {
			logger.warn("Skipping URL: {}, StatusCode: {}, {}, {}", urlStr, statusCode, contentType,description);
			System.out.println(statusCode);
			// Do nothing by default (except basic logging)
			// Sub-classed can override this to add their custom functionality
			
		
	}
	
	@Override 
	protected void handlePageStatusCode(WebURL webUrl, int statusCode, String statusDescription) {
        // Do nothing by default
        // Sub-classed can override this to add their custom functionality
		if (statusCode!=200){
	    	esb.append(webUrl.getURL());
	    	esb.append(',');
	    	esb.append(statusCode);
	    	
	    	esb.append('\n');
	    }
		myCrawlStat.urlsAttempted.add(new UrlStatistics(webUrl.getURL(),statusCode,statusDescription));
    }
	@Override
	protected WebURL handleUrlBeforeProcess(WebURL curURL) {
		
		String url = curURL.getURL();
		url = url.replace(',', '-');
		ssb.append(url);
		ssb.append(',');
		ssb.append("OK");
		ssb.append('\n');
		
		return curURL;
    }
	@Override
    public Object getMyLocalData() {
        return myCrawlStat;
        
    }

}