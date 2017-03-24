import processing.core.PApplet;
import de.fhpotsdam.unfolding.UnfoldingMap;
import de.fhpotsdam.unfolding.geo.Location;
import de.fhpotsdam.unfolding.providers.Google;
import de.fhpotsdam.unfolding.utils.MapUtils;

/**
 * Hello World!
 * 
 * This is the basic stub to start creating interactive maps.
 */
public class HelloUCSDWorld extends PApplet {

	UnfoldingMap map1;
	UnfoldingMap map2;

	public void setup() {
		size(800, 600, OPENGL);
		this.background(255,255,0);

		map1 = new UnfoldingMap(this,20,20,200,200, new Google.GoogleTerrainProvider());
		map1.zoomAndPanTo(14, new Location(32.881, -117.238)); // UCSD
		map2 = new UnfoldingMap(this,300,20,200,200, new Google.GoogleTerrainProvider());
		map2.zoomAndPanTo(14, new Location(32.881, -117.238)); // UCSD

		MapUtils.createDefaultEventDispatcher(this, map1);
		MapUtils.createDefaultEventDispatcher(this, map2);
	}
	

	public void draw() {
		background(0);
		map1.draw();
		map2.draw();
	}

}
