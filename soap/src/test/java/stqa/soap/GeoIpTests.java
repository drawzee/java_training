package stqa.soap;

import lavasoft.GeoIPService;
import org.testng.annotations.Test;

import static org.testng.Assert.*;

public class GeoIpTests {

    @Test
    public void myIP() {
        String geoIP = new GeoIPService().getGeoIPServiceSoap12().getIpLocation("151.228.167.229");
        assertEquals(geoIP, "<GeoIP><Country>GB</Country><State>F9</State></GeoIP>");
    }

}
