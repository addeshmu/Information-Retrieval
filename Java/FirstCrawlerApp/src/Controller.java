import java.io.File;
import java.io.PrintWriter;
import java.util.Dictionary;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import edu.uci.ics.crawler4j.crawler.CrawlConfig;
import edu.uci.ics.crawler4j.crawler.CrawlController;
import edu.uci.ics.crawler4j.fetcher.PageFetcher;
import edu.uci.ics.crawler4j.robotstxt.RobotstxtConfig;
import edu.uci.ics.crawler4j.robotstxt.RobotstxtServer;



public class Controller {

	private static final Logger logger = LoggerFactory.getLogger(MyCrawler.class);
	
	public static void main(String[] args) throws Exception {
		// TODO Auto-generated method stub
				
		String crawlStorageFolder = "src/crawlResult1";
		int numberOfCrawlers = 7;
		int Size_L500 = 0, Size_L1k = 0, Size_L10K = 0,Size_L100K = 0,Size_H100K = 0,Size_H1000K = 0;
		Map<Integer,Integer> statusCodeCorpus = new TreeMap<Integer,Integer>();
		Map<String,Integer> ContentTypeCorpus = new TreeMap<String,Integer>();
		Map<Integer,String> statusCodeDesc = new TreeMap<Integer,String>();
		CrawlConfig config = new CrawlConfig();
		//Sets the Crawl Data Folder
		config.setCrawlStorageFolder(crawlStorageFolder);
		//Sets Politeness Parameters
		config.setPolitenessDelay(10);
		//Sets Maximum depth to crawl on a binary tree
        config.setMaxDepthOfCrawling(16);
        //Sets Max Pages to Fetch
        config.setMaxPagesToFetch(20000);
        config.setFollowRedirects(true);
        config.setIncludeHttpsPages(true);
        //Sets Resumable Crawl - Boolean value
        config.setResumableCrawling(false);
        
        config.setIncludeBinaryContentInCrawling(true);
        
		PageFetcher pageFetcher = new PageFetcher(config);
		RobotstxtConfig robottxtConfig  = new RobotstxtConfig();
		
		RobotstxtServer robotstxtServer = new  RobotstxtServer(robottxtConfig, pageFetcher);
		CrawlController controller = new CrawlController(config, pageFetcher, robotstxtServer);
		
		//Start of the crawl
		controller.addSeed("http://www.nytimes.com/");
		
		

		MyCrawler.sb.append("VISITED URLS");
		MyCrawler.sb.append(",");
		
		MyCrawler.sb.append("SIZE");
		MyCrawler.sb.append(",");
		
		MyCrawler.sb.append("NUMBER OF OUTGOING LINKS");
		MyCrawler.sb.append(",");
		
		MyCrawler.sb.append("CONTENT-TYPE");
		MyCrawler.sb.append("\n");
		

		MyCrawler.esb.append("ATTEMPTED URLS");
		MyCrawler.esb.append(",");
		
		MyCrawler.esb.append("STATUS");
		MyCrawler.esb.append("\n");

		//Crawling starts here
		controller.start(MyCrawler.class, numberOfCrawlers);
		 
		PrintWriter pw = new PrintWriter(new File("src/Fetch.csv"));
		PrintWriter pwAll = new PrintWriter(new File("src/All.csv"));
		PrintWriter pwFetchesAttemped = new PrintWriter(new File("src/Attempted.csv"));
		PrintWriter pwShouldVisit = new PrintWriter(new File("src/ShouldVisit.csv"));
		
		PrintWriter myStats = new PrintWriter(new File("src/Stats.txt"));
		
		PrintWriter pwn = new PrintWriter(new File("src/MUTEX.csv"));
		StringBuilder nb = new StringBuilder();
		List<Object> crawlersLocalData = controller.getCrawlersLocalData();
        long totalLinks = 0;
        CrawlStatistics Collater = new CrawlStatistics();
        int totalProcessedPages = 0;
        for (Object localData : crawlersLocalData) {
            CrawlStatistics stat = (CrawlStatistics) localData;
            totalLinks += stat.getTotalLinks();
            totalProcessedPages += stat.getTotalProcessedPages();
            Collater.urlsAttempted.addAll(stat.urlsAttempted);
            Collater.urlsEncountered.addAll(stat.urlsEncountered);
            Collater.urlsFetched.addAll(stat.urlsFetched);
        }
      /*  nb.append("URLs Attempted");
        nb.append(',');
        nb.append("Status Code");
        nb.append('\n');*/	
        
        for(UrlStatistics temp: Collater.urlsAttempted){
        	
        	nb.append(temp.urlString+','+temp.sc+'\n');
        	
        }
        for(UrlStatistics temp:Collater.urlsFetched){
        	
        	if(temp.downLoadedSize/1024<1){
        		Size_L1k +=1;
        		continue;
        	}
        	else if(temp.downLoadedSize/1024<10){
        		Size_L10K +=1;
        		continue;
        	}
        	else if(temp.downLoadedSize/1024<100){
        		Size_L100K +=1;
        		continue;
        	}
        	else if(temp.downLoadedSize/1024<1000){
        		Size_H100K +=1;
        		continue;
        	}
        	else if(temp.downLoadedSize/1024>=1000){
        		Size_H1000K +=1;
        		continue;
        	}
        }
        int failedFetches = 0,abortedFetches = 0;
        for(UrlStatistics temp:Collater.urlsAttempted){
        	if (!(200<=temp.sc && temp.sc<=299)){
        	
        		if (temp.sc>=300 && temp.sc<400){
        			abortedFetches +=1;
        		}else{
        			failedFetches +=1;
        		}
        	}
        	
        	if(statusCodeCorpus.containsKey(temp.sc)){
        		int val = statusCodeCorpus.get(temp.sc);
        		statusCodeCorpus.put(temp.sc, val+1);
        		
        	}
        	else{
        		statusCodeCorpus.put(temp.sc, 1);
        		statusCodeDesc.put(temp.sc,temp.scDesc);
        	}
        	
        }
        for(UrlStatistics temp:Collater.urlsFetched){
        	if (ContentTypeCorpus.containsKey(temp.pageType)){
        		int val = ContentTypeCorpus.get(temp.pageType);
        		ContentTypeCorpus.put(temp.pageType, val+1);
        	}
        	else{
        		ContentTypeCorpus.put(temp.pageType, 1);
        	}
        }
        
        pwFetchesAttemped.write(nb.toString());
        pwFetchesAttemped.close();
        nb.setLength(0);
        /*nb.append("URLs Fetched");
        nb.append(',');
        nb.append("Size in Bytes");
        nb.append(',');
        nb.append("Outgoing URLs");
        nb.append(',');
        nb.append("ContentType");
        nb.append('\n');*/
        for(UrlStatistics temp: Collater.urlsFetched){
        	
        	nb.append(temp.urlString+','+temp.downLoadedSize+','+temp.outgoingLinks.size()+','+temp.pageType+'\n');
        	
        }
        pw.write(nb.toString());
        pw.close();
        nb.setLength(0);
        for(UrlStatistics temp: Collater.urlsEncountered){
        	
        	nb.append(temp.urlString+','+temp.pageType+'\n');
        	
        }
      
        pwShouldVisit.write(nb.toString());
        pwShouldVisit.close();
        
        
        
        nb.setLength(0);
        nb.append("=============================================");
        nb.append('\n');
        nb.append("Name: Amit Deepak Deshmukh");
        nb.append('\n');
        nb.append("USC ID: 6361489447");
        nb.append('\n');
        nb.append("News Site Crawled: http://www.nytimes.com");
        nb.append('\n');
        nb.append("=============================================");
        nb.append('\n');
        nb.append('\n');
        nb.append("=============================================");
        nb.append('\n');
        nb.append("Fetch Statistics: "+'\n');
        nb.append("=============================================");
        nb.append('\n');
        nb.append("# fetches attempted: "+Collater.urlsAttempted.size()+'\n');
        nb.append("# fetches succeeded: "+statusCodeCorpus.get(200)+'\n');
        nb.append("# fetches failed   : "+failedFetches+'\n');
        nb.append("# fetches aborted  : "+abortedFetches+'\n');
        
        
        
        
        HashSet<String> DeDuplicatingSet = new HashSet<String>();
        
        int UURLS = 0;
        int DUURLS = 0;
        int OUURLS = 0;
        
        nb.append('\n');
        nb.append("=============================================");
        nb.append('\n');
        nb.append("Outgoing URLs: "+ '\n');
        nb.append("=============================================");
        nb.append('\n');
        nb.append("# Total URLs extracted              : "+Collater.urlsEncountered.size()+'\n');
        for (UrlStatistics temp: Collater.urlsEncountered){
        	
        	if(!DeDuplicatingSet.contains(temp.urlString)){
        		DeDuplicatingSet.add(temp.urlString);
        		UURLS+=1;
        		if(temp.pageType.equals("N_OK")){
        			OUURLS+=1;
        			
        		}else{
        			DUURLS+=1;
        		}
        	}
        	
        }
        nb.append("# unique URLs extracted             : "+ UURLS+'\n');
        nb.append("# unique URLs within a news website : "+ DUURLS+'\n');
        nb.append("# unique URLs outside a news website: "+ OUURLS+'\n');
        nb.append('\n');
        nb.append("=============================================");
        nb.append('\n');
        nb.append("Status Codes: "+'\n');
        nb.append("=============================================");
        nb.append('\n');
        
        for(int key : statusCodeCorpus.keySet()){
        	nb.append(key+" "+statusCodeDesc.get(key)+" : "+statusCodeCorpus.get(key)+'\n');
        }
        nb.append('\n');
        nb.append("=============================================");
        nb.append('\n');
        nb.append("Sizes: "+'\n');
        nb.append("=============================================");
        nb.append('\n');
        
        nb.append("< 1KB        : "+ Size_L1k+'\n');
        nb.append("1KB ~ <10KB  : "+ Size_L10K+'\n');
        nb.append("10KB ~ <100KB: "+ Size_L100K+'\n');
        nb.append("100KB ~ <1MB : "+ Size_H100K+'\n');
        nb.append(">= 1MB       : "+ Size_H1000K+'\n');
        
        nb.append('\n');
        nb.append("=============================================");
        nb.append('\n');
        nb.append("Content Types:");
        nb.append('\n');
        nb.append("=============================================");
        nb.append('\n');
        for (String Key : ContentTypeCorpus.keySet()){
        	nb.append(Key+" : "+ContentTypeCorpus.get(Key)+'\n');
        }
        
        
        
        myStats.write(nb.toString());
        myStats.close();
        nb = null;
        
        //Just testing something	 through the logger 
        /*logger.info("Aggregated Statistics:");
        logger.info("\tProcessed Pages: {}", totalProcessedPages);
        logger.info("\tTotal Links found: {}", totalLinks);*/
       // logger.info("\tTotal Text Size: {}", totalTextSize);
        
        System.out.println("done!!1!!!!!!!!!");
        System.out.println(MyCrawler.b);
        
	}

}