package com.springboot.hbase.service;

import com.springboot.hbase.entity.Advertisement;
import com.springboot.hbase.utli.GeoUtility;
import org.apache.hadoop.hbase.client.Result;
import org.apache.hadoop.hbase.client.Scan;
import org.apache.hadoop.hbase.filter.Filter;
import org.apache.hadoop.hbase.filter.PrefixFilter;

import java.io.IOException;
import java.util.HashSet;

/*
* author: ygp
* @Description 实现应用的数据库访问，实现需要的Query服务。
 */

public class DateBaseQuery {

    private static HBaseService hBaseService;

    public DateBaseQuery(){
        hBaseService = HBaseService.getInstance();
    }
    /*
    * @author ygp
    * @param tableName 查询的表的名字
    * @param geoBase32 根据经纬度生成的GeoHash的Base32值
    * @Description 根据一条GeoHash，去tableName对应的表中匹配查询以GeoHash为前缀行，并将它们转换成一个Advertisement的集合。返回该集合。
     */

    public static HashSet<Advertisement> queryByGeoHash(String tableName, String geoBase32) throws IOException {
        Scan scan = new Scan();
        Filter filter = new PrefixFilter(geoBase32.getBytes());
        scan.setFilter(filter);

        if (hBaseService == null){
            hBaseService = HBaseService.getInstance();
        }

        HashSet<Result> resultSet = hBaseService.queryResult("Advertisement", scan);
        HashSet<Advertisement> adverSet = new HashSet<>();

        for (Result re : resultSet){
            adverSet.add(Advertisement.parseAdvertisement(re));
        }

        return adverSet;
    }

    /*
     * @author ygp
     * @param maxLat 矩形区域内纬度的最大值
     * @param maxLng 矩形区域内经度的最大值
     * @param minLat 矩形区域内纬度的最小值
     * @param minLng 矩形区域内经度的最小值
     * @param precision 表示精度大小，设置来处理地图放大缩小。注意precision的取值最大是60，最小是0，且必须是5的倍数，建议从40开始取，不然集合会很大。
     * @param tableName 需要查询的表的名字。
     * @Description 通过给出两点经纬度，获取由该两点经纬度构成的矩形区域下的广告集合。
     */
    public static HashSet<Advertisement> queryByLatAndLng(double maxLat, double minLat, double maxLng,
                                                          double minLng, int precision, String tableName)
                                                                                     throws IOException{

        HashSet<String> geoBase32Set = GeoUtility.getGeoHashSet(maxLat, minLat, maxLng, minLng, precision);
        HashSet<Advertisement> advertisementHashSet = new HashSet<>();

        for (String geoString: geoBase32Set){
            advertisementHashSet.addAll(DateBaseQuery.queryByGeoHash(tableName, geoString));
        }

        return advertisementHashSet;
    }
}
