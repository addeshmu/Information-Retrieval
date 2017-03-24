package module3;

//Java utilities libraries
import java.util.ArrayList;
import processing.core.PGraphics;
//import java.util.Collections;
//import java.util.Comparator;
import java.util.List;

//Processing library
import processing.core.PApplet;

//Unfolding libraries
import de.fhpotsdam.unfolding.UnfoldingMap;
import de.fhpotsdam.unfolding.marker.Marker;
import de.fhpotsdam.unfolding.data.PointFeature;
import de.fhpotsdam.unfolding.marker.SimplePointMarker;
import de.fhpotsdam.unfolding.providers.Google;
import de.fhpotsdam.unfolding.providers.MBTilesMapProvider;
import de.fhpotsdam.unfolding.utils.MapUtils;

//Parsing library
import parsing.ParseFeed;
import processing.core.PShape;
/** EarthquakeCityMap
 * An application with an interactive map displaying earthquake data.
 * Author: UC San Diego Intermediate Software Development MOOC team
 * @author Your name here
 * Date: July 17, 2015
 * */
public class EarthquakeCityMap extends PApplet {

	// You can ignore this.  It's to keep eclipse from generating a warning.
	private static final long serialVersionUID = 1L;

	// IF YOU ARE WORKING OFFLINE, change the value of this variable to true
	private static final boolean offline = false;
	
	// Less than this threshold is a light earthquake
	public static final float THRESHOLD_MODERATE = 5;
	// Less than this threshold is a minor earthquake
	public static final float THRESHOLD_LIGHT = 4;

	/** This is where to find the local tiles, for working without an Internet connection */
	public static String mbTilesString = "blankLight-1-3.mbtiles";
	
	// The map
	private UnfoldingMap map;
	
	//feed with magnitude 2.5+ Earthquakes
	private String earthquakesURL = "https://earthquake.usgs.gov/earthquakes/feed/v1.0/summary/all_day.atom";
	//private String earthquakesURL = "http://volcanoes.usgs.gov/v/hans_notice.html?notice_identifier=DOI-USGS-AVO-2016-07-07T14:46:33-08:00";
	
    public List<Marker> markers; 
	public void setup() {
		size(950, 600, OPENGL);

		if (offline) {
		    map = new UnfoldingMap(this, 200+10, 50, 700, 500, new MBTilesMapProvider(mbTilesString));
		    earthquakesURL = "2.5_week.atom"; 	// Same feed, saved Aug 7, 2015, for working offline
		}
		else {
			map = new UnfoldingMap(this, 200+10, 50, 700, 500, new Google.GoogleMapProvider());
			// IF YOU WANT TO TEST WITH A LOCAL FILE, uncomment the next line
			//earthquakesURL = "2.5_week.atom";
		}
		
	    map.zoomToLevel(2);
	    MapUtils.createDefaultEventDispatcher(this, map);	
			
	    // The List you will populate with new SimplePointMarkers
	    List<Marker> markers = new ArrayList<Marker>();

	    //Use provided parser to collect properties for each earthquake
	    //PointFeatures have a getLocation method
	    List<PointFeature> earthquakes = ParseFeed.parseEarthquake(this, earthquakesURL);
	    
	    // These print statements show you (1) all of the relevant properties 
	    // in the features, and (2) how to get one property and use it
	    if (earthquakes.size() > 0) {
	    	PointFeature f = earthquakes.get(0);
	    	System.out.println(f.getProperties());
	    	Object magObj = f.getProperty("magnitude");
	    	float mag = Float.parseFloat(magObj.toString());
	    	// PointFeatures also have a getLocation method
	    }
	    int s = earthquakes.size();
	    for (int i=1;i<s;i++)
	    {
	    	PointFeature f = earthquakes.get(i);
	    	SimplePointMarker  marker = createMarker(f);
	    	Object magObj = f.getProperty("magnitude");
	    	float mag = Float.parseFloat(magObj.toString());
	    	
	    	if (mag<THRESHOLD_LIGHT)
	    	{
	    		marker.setColor(color(0,0,255));
	    		marker.setStrokeWeight(0);
	    		marker.setRadius(10);
	    	}
	    	
	    	else if(THRESHOLD_LIGHT<mag && mag<THRESHOLD_MODERATE)
	    	{
	    		marker.setColor(color(255,255,0));
	    		marker.setStrokeWeight(0);
	    		marker.setRadius(15);
	    	}
	    	
	    	else
	    	{
	    		marker.setColor(color(255,0,0));
	    		marker.setStrokeWeight(0);
	    		marker.setRadius(20);
	    		
	    	}
	    	markers.add(marker);
	    	
	    }
	    map.addMarkers(markers);
	    
	    
	    // Here is an example of how to use Processing's color method to generate 
	    // an int that represents the color yellow.  
	    //TODO: Add code here as appropriate
	    
	}
		
	// A suggested helper method that takes in an earthquake feature and 
	// returns a SimplePointMarker for that earthquake
	// TODO: Implement this method and call it from setUp, if it helps
	private SimplePointMarker createMarker(PointFeature feature)
	{
		// finish implementing and use this method, if it helps.
		return new SimplePointMarker(feature.getLocation());
	}
	
	public void draw() {
	    background(5);
	    map.draw();
	    addKey();
	}


	// helper method to draw key in GUI
	// TODO: Implement this method to draw the key
	private void addKey() 

	{	fill(color(100,100,100));
		quad(100,50,100,50+250,100+108,50+250,100+108,50);		
		fill(color(0,0,0));
		textSize(10);
		text("EARTHQUAKE LEGEND",110,60);
		fill(color(255,0,0));
		ellipse(110,95,10,10);
		textSize(10);
		text("HIGH MAG",120,95);
		//println(markers.get(1).getLocation().x);
		//line(markers.get(5).getLocation().x,markers.get(5).getLocation().y,markers.get(6).getLocation().x,markers.get(6).getLocation().y);
		fill(color(255,255,0));
		ellipse(110,175,10,10);
		textSize(10);
		text("MODERATE MAG",120,175);
	
		fill(color(0,0,255));
		ellipse(110,275,10,10);
		textSize(10);
		text("LOW/SAFE MAG",120,275);
		
		
		
	}
}
