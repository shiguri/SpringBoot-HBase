package com.springboot.hbase.utli;

import ch.hsr.geohash.GeoHash;
import javafx.util.Pair;
import org.junit.jupiter.api.Test;
import java.util.HashSet;


class GeoUtilityTest {

    // 40,45,50,55,60 这几个精度下，光40在这个测试下就会达到600万个GeoHash。
    // 调整精度要慎重。
    @Test
    void testGetGeoHashSet() {
        HashSet<String> geoSet;
        geoSet = GeoUtility.getGeoHashSet(31.271127, 30.889603,
                121.874054,120.941541, 40);

        System.out.println(geoSet.size());
    }

    // 该测试用例测试 getLatAndLngFromGeoHashStr() 方法。
    @Test
    public void testGeoHashtoLatLng(){
        GeoHash geoHash = GeoHash.withBitPrecision(31.271127, 121.874054, 60);
        String str = geoHash.toBase32();
        Pair<Double, Double> coord = GeoUtility.getLatAndLngFromGeoHashStr(str);
        double latitude = coord.getKey();
        double longitude = coord.getValue();
        System.out.println("Latitude: " + latitude);
        System.out.println("Longitude: " + longitude);
    }
}