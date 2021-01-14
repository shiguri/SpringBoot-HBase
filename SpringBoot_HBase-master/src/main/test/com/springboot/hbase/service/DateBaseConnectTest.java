package com.springboot.hbase.service;


import ch.hsr.geohash.GeoHash;
import com.springboot.hbase.entity.Advertisement;
import javafx.util.Pair;
import org.apache.hadoop.hbase.client.Result;
import org.apache.hadoop.hbase.client.Scan;
import org.apache.hadoop.hbase.filter.Filter;
import org.apache.hadoop.hbase.filter.PrefixFilter;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.annotation.Resource;
import java.io.IOException;
import java.nio.charset.Charset;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Random;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
public class DateBaseConnectTest {
    @Resource
    HBaseService hBaseService;

    @Test
    public void testGetResultScanner(){
        Map<String, Map<String, String>> res = hBaseService.getResultScanner("test");
        System.out.println("查询结果！");
        res.forEach((k, value) -> {
            System.out.println(k + "-->" + value);
        });
    }

    /*
    @ Description 初始化数据库
     */
    @Test
    public void init(){
        DateBaseInit dateBaseInit = new DateBaseInit();
        dateBaseInit.createDesignedTables();
        Map<String, Map<String, String>> res1 = hBaseService.getResultScanner("Media");
        Map<String, Map<String, String>> res2 = hBaseService.getResultScanner("Profession");
        System.out.println("Media Table:");
        res1.forEach((k, value) -> {
            System.out.println(k + "-->" + value);
        });
        System.out.println("Profession Table:");
        res2.forEach((k, value) -> {
            System.out.println(k + "-->" + value);
        });
    }

    @Test
    public void testGetAllTablesName(){
        List<String> tablesName = hBaseService.getAllTableNames();
        System.out.println("Tables: ");
        tablesName.forEach(str -> {
            System.out.println(str + " Table!");
        });
    }

    /*
    * @author ygp
    * @Description 向Advertisement表中插入数据的案例
    *
    * @ coord 是一个上海市地点的经纬度，调用getAGeoCoord() 随机获取
    * @ advId 是每一个广告的id，需要保证是独一无二的！！！
    * @ rowKey 是行键 rowkey = geoHash.toBase32() + "_" + advId;
    *
    * @ conditionKeyValue 的值就像示例一样。
    *
    * @ name，theme，master，price，date, location 需要随机生成
    * @ img可以从本地收集的广告集合中随机选择一张，需要是 jpg后缀。注意那个try 语句块，
    * ！！！ byte[] 转 String, string再转 byte[], 会有两次 byte[] 数据不一致的情况发生，
    * 要按try语句块里面那样做，img = new String(imgData, Charset.forName("ISO-8859-1"));
    *
     */
    @Test
    public void testInsertIntoAdvertisement(){
        Pair coord = DateBaseInit.getAGeoCorrd();
        GeoHash geoHash = GeoHash.withBitPrecision((Double) coord.getKey(),(Double) coord.getValue(),60);
        //String advId = "0004";
        int i = 5;
        String advId = String.format("%05d", i);
        String rowkey = geoHash.toBase32() + "_" + advId;

        Random random = new Random();
        String mediaCon = DateBaseInit.getMediaTypeByRandom(random.nextInt());
        String proCon = DateBaseInit.getProTypeByRandom(random.nextInt());
        String conditionKeyValue = mediaCon + proCon;

        String name = "测试的name";
        String theme = "测试的theme";
        String master = "测试的master";
        String price = "10000元/天";
        String date = "2020-12-01";
        String img = "";
        String location = "某某街道-几百号";
        try {
            byte[] imgData;
            imgData = DateBaseInit.generateImgData("D:\\tempImg/generateImg/OIP.jpg");
            img = new String(imgData, Charset.forName("ISO-8859-1"));
        }catch (IOException e){
            System.out.println("加载图片失败，错误: " + e);
        }

        String[] columns = {"value"};
        String[] values = {conditionKeyValue};
        hBaseService.putData("Advertisement", rowkey, "ConditionKey", columns, values);

        String[] columns2 = {"name", "theme", "master", "price", "date", "img", "location"};
        String[] values2 = {name, theme, master, price, date, img, location};

        hBaseService.putData("Advertisement", rowkey, "AdverInfo", columns2, values2);
    }


    /*
    * @author ygp
    * @Description 测试从Hbase中取出一条广告数据。实际开发中调用 DateBaseQuery.queryByLatAndLng()方法就可以了
     */
    @Test
    public void testQuery(){
        String geohashStr = "wtqrcfzp";
        Scan scan = new Scan();
        Filter filter = new PrefixFilter(geohashStr.getBytes());
        scan.setFilter(filter);

        HashSet<Result> results = hBaseService.queryResult("Advertisement", scan);
        HashSet<Advertisement> adverSet = new HashSet<>();
        System.out.println(results.size());
        for (Result re : results){
            try {
                adverSet.add(Advertisement.parseAdvertisement(re));
            }
            catch (IOException e){
                System.out.println(e);
            }
        }

        for (Advertisement ad: adverSet){
            System.out.println(ad.getAdvId());
            System.out.println(ad.getConditionKey());
            System.out.println(ad.getName());
            System.out.println(ad.getPrice());
            System.out.println(ad.getTheme());
        }
    }
    /*
    * @author ygp
    * @Description 随机生成数据 导入到HBase中。
     */
    @Test
    public void InsertDatatoHBase(){
        int i;
        for (i = 8001; i <= 10000; i++)
        {
            Pair coord = DateBaseInit.getAGeoCorrd();
            GeoHash geoHash = GeoHash.withBitPrecision((Double) coord.getKey(), (Double) coord.getValue(), 60);
            String advId = String.format("%05d", i);
            String rowkey = geoHash.toBase32() + "_" + advId;


            Random random = new Random();
            String mediaCon = DateBaseInit.getMediaTypeByRandom(random.nextInt());
            String proCon = DateBaseInit.getProTypeByRandom(random.nextInt());
            String conditionKeyValue = mediaCon + proCon;

            String name = DateBaseInit.getAdNameByRandom(random.nextInt());
            String theme = DateBaseInit.getAdThemeByRandom(random.nextInt());
            String master = DateBaseInit.getAdMasterByRandom(random.nextInt());
            String price = DateBaseInit.getAdPriceByRandom(random.nextInt());
            String date = DateBaseInit.getAdDateByRandom(random.nextInt());
            String img = DateBaseInit.getAdImgByRandom(random.nextInt());
            String location = DateBaseInit.getAdLocationByRandom(random.nextInt());

            try {
                byte[] imgData;
                imgData = DateBaseInit.generateImgData(img);
                img = new String(imgData, Charset.forName("ISO-8859-1"));
            } catch (IOException e) {
                System.out.println("加载图片失败，错误: " + e);
            }

            String[] columns = {"value"};
            String[] values = {conditionKeyValue};
            hBaseService.putData("Advertisement", rowkey, "ConditionKey", columns, values);

            String[] columns2 = {"name", "theme", "master", "price", "date", "img", "location"};
            String[] values2 = {name, theme, master, price, date, img, location};

            hBaseService.putData("Advertisement", rowkey, "AdverInfo", columns2, values2);
        }
    }
}
