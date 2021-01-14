package com.springboot.hbase.service;

import com.springboot.hbase.utli.HBaseConfig;
import javafx.util.Pair;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.*;

/*
* author: ygp
* 该Class用来作HBase数据库的初始化。
* 数据已经硬编码。
 */
public class DateBaseInit {

    /*
    * 根据设计方案，添加存储广告信息的Advertisement Table
    *            添加存储媒体信息的Media Table
    *            添加存储行业信息的Profession Table
    * Media Table 和 Profession Table 是用作条件，去从取出的Advertisement构成的集合中，
    * 筛选满足相应性质的Advertisement。
    *
    *
     */
    public void createDesignedTables(){
        HBaseService hBaseService = new HBaseConfig().getHbaseService();
        List<String> tableColumnFamily = new LinkedList<String>();
        tableColumnFamily.add("ConditionKey");
        tableColumnFamily.add("AdverInfo");
        hBaseService.creatTable("Advertisement", tableColumnFamily);

        // 调用完了上面的createTable之后，Admin是被关闭了吧，这里是要重新创建 HBaseService?
        hBaseService = new HBaseConfig().getHbaseService();
        tableColumnFamily = new LinkedList<String>();
        tableColumnFamily.add("MediaRefer");
        tableColumnFamily.add("MediaCategory");
        hBaseService.creatTable("Media", tableColumnFamily);

        //
        hBaseService = new HBaseConfig().getHbaseService();
        tableColumnFamily = new LinkedList<String>();
        tableColumnFamily.add("ProRefer");
        tableColumnFamily.add("ProCategory");
        hBaseService.creatTable("Profession", tableColumnFamily);

        // 给Media表导入数据。
        HashMap<String, String[]> refer = new HashMap<String, String[]>(){{
            put("城市干道", new String[]{"00000001"});
            put("餐饮酒店", new String[]{"00000010"});
            put("楼宇", new String[]{"00000011"});
            put("商业中心", new String[]{"00000100"});
            put("高速", new String[]{"00000101"});
            put("火车站", new String[]{"00000110"});
            put("机场", new String[]{"00000111"});
            put("公交", new String[]{"00001000"});
            put("地铁", new String[]{"00001001"});
        }};

        for (Map.Entry<String, String[]> entry : refer.entrySet()){
            hBaseService.putData("Media", entry.getKey(),
                    "MediaRefer", new String[]{"value"}, entry.getValue());
        }

        HashMap<String, Pair<String[], String[]>> category = new HashMap<String, Pair<String[], String[]>>() {{
            put("城市干道", new Pair<>(new String[]{"大牌","灯箱","框架/海报","道闸"},
                    new String[]{"00000001","00000010","00000011","00000100"}));

            put("餐饮酒店", new Pair<>(new String[]{"框架/海报"},
                    new String[]{"00000001"}));

            put("楼宇", new Pair<>(new String[]{"大牌","灯箱","框架/海报","刷屏机","道闸"},
                    new String[]{"00000001","00000010","00000011","00000100", "00000101"}));

            put("商业中心", new Pair<>(new String[]{"大牌", "LED"},
                    new String[]{"00000001","00000010"}));

            put("高速", new Pair<>(new String[]{"大牌"},
                    new String[]{"00000001"}));

            put("火车站", new Pair<>(new String[]{"大牌","灯箱"},
                    new String[]{"00000001","00000010"}));

            put("机场", new Pair<>(new String[]{"灯箱"},
                    new String[]{"00000001"}));

            put("公交", new Pair<>(new String[]{"灯箱", "车身"},
                    new String[]{"00000001","00000010"}));

            put("地铁", new Pair<>(new String[]{"灯箱"},
                    new String[]{"00000001"}));
        }};
        for (Map.Entry<String, Pair<String[], String[]>> entry: category.entrySet()){
            hBaseService.putData("Media", entry.getKey(), "MediaCategory",
                    entry.getValue().getKey(), entry.getValue().getValue());
        }

        // 给Profession Table 导入数据。

        HashMap<String, String[]> pRefer = new HashMap<String, String[]>(){{
            put("房地产", new String[]{"00000001"});
            put("互联网APP", new String[]{"00000010"});
            put("医疗保健", new String[]{"00000011"});
            put("服务业", new String[]{"00000100"});
            put("商超百货", new String[]{"00000101"});
            put("建筑材料及服务", new String[]{"00000110"});
            put("家居用品", new String[]{"00000111"});
            put("家电", new String[]{"00001000"});
            put("数码电器", new String[]{"00001001"});
            put("教育培训", new String[]{"00001010"});
            put("金融投资", new String[]{"00001011"});
            put("邮电通讯", new String[]{"00001100"});
            put("娱乐休闲", new String[]{"00001101"});
            put("酒水饮料", new String[]{"00001110"});
            put("食品", new String[]{"00001111"});
            put("交通", new String[]{"00010000"});
            put("个护化妆", new String[]{"00010001"});
            put("服饰", new String[]{"00010010"});
            put("办公", new String[]{"00010011"});
            put("工业", new String[]{"00010100"});
            put("农业", new String[]{"00010101"});
        }};

        for (Map.Entry<String, String[]> entry : pRefer.entrySet()){
            hBaseService.putData("Profession", entry.getKey(),
                    "ProRefer", new String[]{"value"}, entry.getValue());
        }


        HashMap<String, Pair<String[], String[]>> pCategory = new HashMap<String, Pair<String[], String[]>>(){{
            put("房地产", new Pair<>(new String[]{"商品房", "工业厂房及园区", "招商/招租", "房产中介", "物业服务", "设计施工"},
                    new String[]{"00000001", "00000010", "00000011", "00000100", "00000101", "00000110"}));

            put("互联网APP", new Pair<>(new String[]{"搜索引擎", "信息门户", "社交/通讯", "购物类", "招聘类", "影音类",
            "生活服务类", "工具类"},
                    new String[]{"00000001", "00000010", "00000011", "00000100", "00000101", "00000110",
                            "00000111", "00001000"}));

            put("医疗保健", new Pair<>(new String[]{"综合医院", "专科医院", "医美整型", "体验中心", "月子中心", "医疗器械",
            "保健品", "保健设备及服务", "诊所/病房", "药品"},
                    new String[]{"00000001", "00000010", "00000011", "00000100", "00000101", "00000110",
                            "00000111", "00001000", "00001001", "00001010"}));

            put("服务业", new Pair<>(new String[]{"展览展会", "工商服务", "咨询服务", "法律服务", "技术服务", "广告/策划",
            "餐饮服务", "家政服务", "摄影服务", "洗涤服务", "婚庆婚介服务", "丧葬服务", "环保", "宠物服务"},
                    new String[]{"00000001", "00000010", "00000011", "00000100", "00000101", "00000110",
                            "00000111", "00001000", "00001001", "00001010", "00001011", "00001100", "00001101",
                            "00001110"}));

            put("商超百货", new Pair<>(new String[]{"超市/购物中心", "家电卖场", "家居建材", "美容化妆", "零食店"},
                    new String[]{"00000001", "00000010", "00000011", "00000100", "00000101"}));

            put("建筑材料及服务", new Pair<>(new String[]{"装饰装修", "油漆涂料墙纸", "瓷业", "木业", "管业", "采暖器材",
            "新风系统", "建材五金", "橱柜", "电梯", "卫浴洁具"},
                    new String[]{"00000001", "00000010", "00000011", "00000100", "00000101", "00000110",
                            "00000111", "00001000", "00001001", "00001010", "00001011"}));

            put("家居用品", new Pair<>(new String[]{"布艺", "灯具", "床上用品", "家具", "厨具/餐具", "装饰/收藏品",
            "家用清洁"},
                    new String[]{"00000001", "00000010", "00000011", "00000100", "00000101", "00000110",
                            "00000111"}));

            put("家电", new Pair<>(new String[]{"厨房电器", "卫浴电器", "空调电扇冰箱", "洗衣干衣设备", "电视音响及配件",
            "净化设备", "除尘打扫", "小家电产品"},
                    new String[]{"00000001", "00000010", "00000011", "00000100", "00000101", "00000110",
                            "00000111", "00001000"}));

            put("数码电器", new Pair<>(new String[]{"电脑", "电脑配件", "存储设备", "3C产品"},
                    new String[]{"00000001", "00000010", "00000011", "00000100"}));

            put("教育培训", new Pair<>(new String[]{"学前教育", "幼儿园", "中小学培训", "留学/国际教育", "成人教育",
            "语言类培训", "技能培训", "兴趣培养", "学习机", "学习软件"},
                    new String[]{"00000001", "00000010", "00000011", "00000100", "00000101", "00000110",
                            "00000111", "00001000", "00001001", "00001010"}));

            put("金融投资", new Pair<>(new String[]{"银行", "保险", "证券", "投资理财", "典当/拍卖", "彩票", "金融租赁"},
                    new String[]{"00000001", "00000010", "00000011", "00000100", "00000101", "00000110",
                            "00000111"}));

            put("邮电通讯", new Pair<>(new String[]{"手机", "手机配件", "电信服务", "通讯设备"},
                    new String[]{"00000001", "00000010", "00000011", "00000100"}));

            put("娱乐休闲", new Pair<>(new String[]{"旅游服务", "影视/赛事", "运动/健身", "演出/活动", "文化娱乐场所",
            "酒店", "玩具游戏", "乐器乐行", "图书音像制品"},
                    new String[]{"00000001", "00000010", "00000011", "00000100", "00000101", "00000110",
                            "00000111", "00001000", "00001001"}));

            put("酒水饮料", new Pair<>(new String[]{"白酒", "啤酒", "葡萄酒", "含酒精饮料", "碳酸饮料", "功能饮料",
            "乳制饮品", "水", "果汁", "咖啡/茶饮料", "茶叶"},
                    new String[]{"00000001", "00000010", "00000011", "00000100", "00000101", "00000110",
                            "00000111", "00001000", "00001001", "00001010", "00001011"}));

            put("食品", new Pair<>(new String[]{"方便食品", "膨化食品", "传统食品", "宠物食品", "冰雪食品", "烘烤食品",
            "食用油", "调味品", "蔬菜水果", "生鲜", "米面杂粮", "糖果/零食", "奶粉"},
                    new String[]{"00000001", "00000010", "00000011", "00000100", "00000101", "00000110",
                            "00000111", "00001000", "00001001", "00001010", "00001011", "00001100", "00001101"}));

            put("交通", new Pair<>(new String[]{"非机动车", "轿车/客车", "货车/工程车辆", "飞机游艇", "汽车设备及配件",
            "燃油润滑", "出租/客运服务", "汽车销售租赁及养护", "航空/船舶服务", "物流/快递"},
                    new String[]{"00000001", "00000010", "00000011", "00000100", "00000101", "00000110",
                            "00000111", "00001000", "00001001", "00001010"}));

            put("个护化妆", new Pair<>(new String[]{"护肤品", "彩妆", "美发美容美体", "口腔护理", "洗发护发", "香水",
            "母婴用品", "女性用品", "男士护理", "个人清洁", "计生用品"},
                    new String[]{"00000001", "00000010", "00000011", "00000100", "00000101", "00000110",
                            "00000111", "00001000", "00001001", "00001010", "00001011"}));

            put("服饰", new Pair<>(new String[]{"内衣", "男装", "珠宝首饰", "女装", "童装", "皮衣皮革", "鞋",
            "配饰", "腕表", "箱包", "眼镜", "专卖店"},
                    new String[]{"00000001", "00000010", "00000011", "00000100", "00000101", "00000110",
                            "00000111", "00001000", "00001001", "00001010", "00001011", "00001100"}));

            put("办公", new Pair<>(new String[]{"办公家具", "办公设备及耗材", "文具", "办公软件"},
                    new String[]{"00000001", "00000010", "00000011", "00000100"}));

            put("工业", new Pair<>(new String[]{"机械设备及配件", "原材料", "电源", "仪器仪表", "线缆"},
                    new String[]{"00000001", "00000010", "00000011", "00000100", "00000101"}));

            put("农业", new Pair<>(new String[]{"化肥", "农兽药", "饲料", "农林渔牧物资", "烟草", "农场"},
                    new String[]{"00000001", "00000010", "00000011", "00000100", "00000101", "00000110"}));
        }};

        for (Map.Entry<String, Pair<String[], String[]>> entry : pCategory.entrySet()){
            hBaseService.putData("Profession", entry.getKey(),
            "ProCategory", entry.getValue().getKey(), entry.getValue().getValue());
        }
    }

    /*
    * @Description 按索引获取一个String，String的值来代表Media字段。字段的前8位代表Media类型，后8位代表Media的具体类目。
    * @Description 设计这个函数的初衷是为了随机生成数据时，给一个Advertisement随机生成一个ConditionKey的 前 16字段。
     */
    public static String getMediaTypeByRandom(int index){

        String[] MediaConditions = {
                //城市干道,后8位全0表示全选。
                "0000000100000001",
                "0000000100000010",
                "0000000100000011",
                "0000000100000100",

                //餐饮酒店
                "0000001000000001",

                //楼宇
                "0000001100000001",
                "0000001100000010",
                "0000001100000011",
                "0000001100000100",
                "0000001100000101",

                //商业中心
                "0000010000000001",
                "0000010000000010",

                //高速
                "0000010100000001",

                //火车站
                "0000011000000001",
                "0000011000000010",

                //机场
                "0000011100000001",

                //公交
                "0000100000000001",
                "0000100000000010",

                //地铁
                "0000100100000001",
        };
        if (index < 0){
            index = -index;
        }

        index = index % MediaConditions.length;
        return MediaConditions[index];
    }

    /*
    * @Description 按一个值获取一个String，该String代表Profession字段。字段的前8位代表Pro类型，后8位是具体类型下的具体类目。
    * @Description 设计这个函数的初衷是为了随机生成数据时，给一个Advertisement随机生成一个ConditionKey的 后 16字段。
     */
    public static String getProTypeByRandom(int index){

        String[] ProCondition = {
                // 房地产
                "0000000100000001", "0000000100000010", "0000000100000011",
                "0000000100000100", "0000000100000101", "0000000100000110",

                // 互联网APP
                "0000001000000001", "0000001000000010", "0000001000000011",
                "0000001000000100", "0000001000000101", "0000001000000110",
                "0000001000000111", "0000001000001000",

                // 医疗保健
                "0000001100000001", "0000001100000010",
                "0000001100000011", "0000001100000100", "0000001100000101",
                "0000001100000110", "0000001100000111", "0000001100001000",
                "0000001100001001", "0000001100001010",

                // 服务业
                "0000010000000001", "0000010000000010",
                "0000010000000011", "0000010000000100", "0000010000000101",
                "0000010000000110", "0000010000000111", "0000010000001000",
                "0000010000001001", "0000010000001010", "0000010000001011",
                "0000010000001100", "0000010000001101", "0000010000001110",

                // 商超百货
                "0000010100000001", "0000010100000010",
                "0000010100000011", "0000010100000100", "0000010100000101",

                // 建筑材料及服务
                "0000011000000001", "0000011000000010",
                "0000011000000011", "0000011000000100", "0000011000000101",
                "0000011000000110", "0000011000000111", "0000011000001000",
                "0000011000001001", "0000011000001010", "0000011000001011",

                // 家居用品
                "0000011100000001", "0000011100000010",
                "0000011100000011", "0000011100000100", "0000011100000101",
                "0000011100000110", "0000011100000111",

                // 家电
                "0000100000000001", "0000100000000010",
                "0000100000000011", "0000100000000100", "0000100000000101",
                "0000100000000110", "0000100000000111", "0000100000001000",

                // 数码电器
                "0000100100000001", "0000100100000010",
                "0000100100000011", "0000100100000100",

                // 教育培训
                "0000101000000001", "0000101000000010",
                "0000101000000011", "0000101000000100", "0000101000000101",
                "0000101000000110", "0000101000000111", "0000101000001000",
                "0000101000001001", "0000101000001010",

                // 金融投资
                "0000101100000001", "0000101100000010",
                "0000101100000011", "0000101100000100", "0000101100000101",
                "0000101100000110", "0000101100000111",

                // 邮电通讯
                "0000110000000001", "0000110000000010",
                "0000110000000011", "0000110000000100",

                // 娱乐休闲
                "0000110100000001", "0000110100000010",
                "0000110100000011", "0000110100000100", "0000110100000101",
                "0000110100000110", "0000110100000111", "0000110100001000",
                "0000110100001001",

                // 酒水饮料
                "0000111000000001", "0000111000000010",
                "0000111000000011", "0000111000000100", "0000111000000101",
                "0000111000000110", "0000111000000111", "0000111000001000",
                "0000111000001001", "0000111000001010", "0000111000001011",

                // 食品
                "0000111100000001", "0000111100000010",
                "0000111100000011", "0000111100000100", "0000111100000101",
                "0000111100000110", "0000111100000111", "0000111100001000",
                "0000111100001001", "0000111100001010", "0000111100001011",
                "0000111100001100", "0000111100001101", "0000111100001110",

                // 交通
                "0001000000000001", "0001000000000010",
                "0001000000000011", "0001000000000100", "0001000000000101",
                "0001000000000110", "0001000000000111", "0001000000001000",
                "0001000000001001", "0001000000001010",

                // 个护化妆
                "0001000100000001", "0001000100000010",
                "0001000100000011", "0001000100000100", "0001000100000101",
                "0001000100000110", "0001000100000111", "0001000100001000",
                "0001000100001001", "0001000100001010", "0001000100001011",

                // 服饰
                "0001001000000001", "0001001000000010",
                "0001001000000011", "0001001000000100", "0001001000000101",
                "0001001000000110", "0001001000000111", "0001001000001000",
                "0001001000001001", "0001001000001010", "0001001000001011",
                "0001001000001100",

                // 办公
                "0001001100000001", "0001001100000010",
                "0001001100000011", "0001001100000100",

                // 工业
                "0001010000000001", "0001010000000010",
                "0001010000000011", "0001010000000100", "0001010000000101",

                // 农业
                "0001010100000001", "0001010100000010",
                "0001010100000011", "0001010100000100", "0001010100000101",
                "0001010100000110"
        };
        if (index < 0){
            index = -index;
        }

        index = index % ProCondition.length;
        return ProCondition[index];
    }

    /*
    * @param imgPath 图片的地址
    * @Description 从图片地址得到一个存储了图片信息的 byte[],后续可以将该byte[] 存入数据库。
     */
    public static byte[] generateImgData(String imgPath) throws IOException {
        FileInputStream fis = new FileInputStream(imgPath);
        byte[] imgByte = new byte[fis.available()];
        fis.read(imgByte);
        fis.close();

        return imgByte;
    }

    /*
    * @author ygp
    * @Description 随机获取一个在上海市范围内的地理坐标，返回Pair对象，getKey()获取纬度值，getValue()获取经度值。
     */
    public static Pair<Double, Double> getAGeoCorrd(){
        double maxLatValue = 31.883333;
        double minLatValue = 30.666666;
        double maxLngValue = 122.211000;
        double minLngValue = 120.861000;

        double latitude = (Math.random() * (maxLatValue - minLatValue)) + minLatValue;
        double longitude = (Math.random() * (maxLngValue - minLngValue)) + minLngValue;

        String lat = String.format("%.6f", latitude);
        String lng = String.format("%.6f", longitude);

        latitude = Double.parseDouble(lat);
        longitude = Double.parseDouble(lng);

        return new Pair<Double, Double>(latitude, longitude);
    }


    /*
    * @Description 随机生成 AdvInfo 的内容。
     */
    public static String getAdNameByRandom(int index){

        String[] AdNameConditions = {

                "苗医生",
                "vivo",
                "CBD家具",
                "BABO",
                "农夫山泉",
                "小肚鸡肠",
                "海鲜火锅",
                "恒大上林苑",
                "贝壳",
                "万象城",
                "明月青城",
                "天猫",
                "京东",
                "联想",
                "HUAWEI",
                "ONLY",
                "宝岛眼镜",
                "海澜之家",
                "BOLON",
                "安晶钢化玻璃",
                "新生植发",
                "中国银行",
                "六喜珠宝",
                "老凤祥银楼",
                "GIORDANO",
                "九牧王男裤",
                "OMI",
                "平乐春色满园",
                "保利珑堂里院",
                "双凤桥",
                "外滩东岸",
                "山水颐墅",
                "交投璟云府",
                "宏盛发地产",
                "德商天骄城学府",
                "GUCCI",
                "兰蔻",
                "宝沃汽车",
                "中影股份",
                "沃尔沃",
                "特仑苏",
                "百事可乐",
                "卡姿兰",
                "SDEER",
                "椰树牌椰汁",
                "自如网",
                "奥迪e-tron",
                "黑茶养生火锅",
                "天慧",
                "科比形象宣传",
                "韦佳游泳健身",
                "安居客",
                "汉堡王",
                "珠珠成韵珠心算",
                "CGV星聚汇",
                "建国别克4S",
                "金堂万达广场",
                "美好小酥肉",
                "花样年江山",
                "峨眉钰泉",
                "琪达",
                "森态牛油",
                "合力叉车",
                "何白蚁",
                "八益床垫",
                "皇派门窗",
                "万科时代之光",
                "古井贡酒古16",
                "雅迪电动车",
                "鲁花",
                "海信阅读手机",
                "三棵树漆",
                "红花郎",
                "火星人集成灶",
                "快客",
                "洋河蓝色经典梦之蓝M6+",
                "梦天水漆木门",
                "爱氏晨曦",
                "第八代经典五粮液",
                "水星家纺",
                "西域香妃",
                "牛栏山",
                "钓鱼台珐琅彩酒",
                "天府龙芽",
                "高尔夫酒",
                "同程旅游",
                "做饭家",
                "爱达乐川皇酥",
                "川观新闻",
                "极光口腔",
                "康师傅酸辣牛肉面",
                "斑马AI课",
                "环球融创未来城",
                "医大整形",
                "军大整形",
                "戴氏教育",
                "作业帮直播课",
                "易车APP",
                "米易",
                "雪花啤酒马尔斯绿",
                "TCL电视系列",
                "南台月月饼",
                "圣大家电",
                "君乐宝优萃",
                "老板大吸力油烟机",
                "京东金融",
                "习酒",
                "KOALA考拉爱车",
                "三耗记",
                "箭牌卫浴",
                "一心堂大健康药店",
                "品贡然自",
                "简州大耳羊",
                "皇家宠物食品",
                "通威集团",
                "嘉岳农业生态黄牛",
                "金明阳线缆",
                "智雨灌溉",
                "天硕电气",
                "森泉环境集团",
                "山特",
                "龙瀛电力",
                "天硕配电柜",
                "宁江机床",
                "信元奥耐克LED",
                "劲浪体育",
                "周大福珠宝",
                "周生生珠宝",
                "I Do",
                "衣广汇服装批发城",
                "易工司",
                "瀚博文旅",
                "中国移动5G+",
                "中国电信5G",
                "中国联通沃视频",
                "vivo S7",
                "联想拯救者电竞手机Pro",
                "三星Galaxy Zflip 5G",
                "中国移动5G+智慧爱家",
                "小天才电话手表Z6",
                "小米10至尊纪念版",
                "云南白药养元青",
                "滴露",
                "完美日记口红",
                "蕾特恩专业祛痘",
                "完子心选",
                "CLARINS娇韵诗弹簧霜",
                "小奥汀冰雾散粉",
                "花西子同心锁口红",
                "杰士邦",
                "MAC全新柔雾唇釉",
                "英科医疗洗手液",
                "春娇黄氏霜",
                "行动教育",
                "英孚英语",
                "华一世纪股权激励",
                "望子成龙",
                "精锐个性化教育",
                "丹秋名师堂",
                "瑞思英语",
                "外教少儿英语教育",
                "经典教育",
                "极客数学帮",
                "小鸟音响",
                "联想扬天V340",
                "佳能",
                "华为OceanStor存储",
                "润兴装饰工程有限公司",
                "尚品本色木门",
                "创美道",
                "朗润装饰",
                "飞宇门窗",
                "太子家居",
                "全友全屋定制",
                "慕思寝具",
                "BAYI床垫",
                "大自然床垫",
                "现代筑美",
                "飞利浦吸脱一体机",
                "富安娜家纺",
                "恩阳芦笋",
                "国莎红巴火锅底料",
                "蒲江猕猴桃",
                "惠氏铂臻",
                "蟹兴阁",
                "蟹都汇",
                "熊猫不走蛋糕",
                "老坛子老酸菜",
                "拉卡拉电签POS",
                "永恒办公家具",
                "管家婆",
                "科斯特",
                "金蝶云",
                "钉钉",
                "海尔智家",
                "老板中式蒸箱",
                "海信电视",
                "森鹰空调窗",
                "美国开利家用中央空调",
                "CHIFFO前锋",
                "ROBAM老板",
                "森歌集成灶",
                "森岗电器",
                "松下电饭煲",
                "方太集成灶",
                "毛豆新车网",
                "空港租车",

        };
        if (index < 0){
            index = -index;
        }

        index = index % AdNameConditions.length;
        return AdNameConditions[index];
    }

    public static String getAdThemeByRandom(int index){

        String[] AdThemeConditions = {

                "金融",
                "房地产",
                "互联网APP",
                "医疗",
                "饮食",
                "商城",
                "家具用品",
                "服饰",
                "办公",
                "工业",
                "农业",
                "数码电器",
                "休闲娱乐",
        };
        if (index < 0){
            index = -index;
        }
        index = index % AdThemeConditions.length;
        return AdThemeConditions[index];
    }

    public static String getAdMasterByRandom(int index){

        String[] AdMasterConditions = {

                "恒大地产",
                "中国银行",
                "安晶半导体有限公司",
                "阿里巴巴",
                "川茶集团",
                "四川郎酒集团",
                "水井坊股份有限公司",
                "小肚鸡肠",
                "君乐宝乳业集团",
                "好记食品酿造股份有限公司",
                "周大福珠宝有限公司",
                "暴龙眼镜",
                "一汽大众",
                "金螳螂家",
                "长安马自达",
                "古摄影",
                "一汽大众汽车有限公司",
                "空港汽车租赁服务有限公司",
                "北京易车信息科技有限公司",
                "金毛豆技术开发有限公司",
                "艾玛科技集团股份有限公司",
                "上汽通用汽车有限公司",
                "金堂东孚汽车销售服务有限公司",
                "浙江合众新能源车有限公司",
                "沃尔沃汽车销售有限公司",
                "天津小屋信息科技有限公司",
                "保利房地产开发有限公司",
                "华润置地有限公司",
                "都江堰外滩置业有限公司",
                "交投轨道城市发展有限公司",
                "成都奥成置业有限公司",
                "中建人居雅苑房地产开发有限公司",
                "安徽合力股份有限公司",
                "山特电子有限公司",
                "四川天硕电气设备有限公司",
                "长沙信元电子科技有限公司",
                "成都智雨节水灌溉科技有限公司",
                "森泉环境科技集团有限公司",
                "英孚语言培训有限公司",
                "四川丹秋教育管理集团有限公司",
                "精锐教育培训学校有限公司",
                "戴氏教育科技有限公司",
                "成都望子成龙培训学校有限公司",
                "作业本教育科技有限公司",
                "经典教育",
                "北京领语堂教育科技发展有限公司",
                "珠珠成韵珠心算",
                "上海老凤祥有限公司",
                "四川省爱恋珠宝有限公司",
                "佐丹奴服饰有限公司",
                "绫致时装有限公司",
                "厦门雅瑞光学有限公司",
                "福建省欧米投资有限公司",
                "大连北方宝岛眼镜有限公司",
                "维沃移动通信有限公司",
                "中国移动通信集团有限公司",
                "中国电信集团有限公司",
                "海信集团有限公司",
                "三星投资有限公司",
                "广东小天才科技有限公司",
                "小米科技有限责任公司",
                "南京苗邦美业企业管理有限公司",
                "云南白药集团股份有限公司",
                "利洁时家化有限公司",
                "完美日记化妆品有限公司",
                "成都蕾特恩美容有限公司",
                "美派美睫",
                "娇韵诗化妆品有限公司",
                "上海水适化妆品有限公司",
                "花西子化妆品有限公司",
                "武汉杰士邦卫生用品有限公司",
                "雅诗兰黛商贸有限公司",
                "英科医疗科技股份有限公司",
                "同程网络科技股份有限公司",
                "丽约养生spa",
                "中共米易县委",
                "环球融创会展文旅集团有限公司",
                "北京爱奇艺科技有限公司",
                "2020中国网球巡回赛",
                "青白江区君程悦酒吧",
                "四川卫视",
                "中国贵州茅台酒厂有限责任公司",
                "咪咕数字传媒有限公司",
                "北京华品博睿网络技术有限公司",
                "橙心优选科技发展有限公司",
                "中国银联股份有限公司",
                "四川省网商协会",
                "北京五八信息技术有限公司",
                "京东世纪贸易有限公司",
                "红星美凯龙世博家居生活广场有限公司",
                "宜家家居有限公司",
                "沃尔玛投资有限公司",
                "中粮地产有限公司",
                "吉选超市有限公司",
                "国美电器有限公司",
                "海尔电器销售有限公司",
                "老板电器有限公司",
                "TCL王牌电器有限公司",
                "火星人厨具股份有限公司",
                "哈尔滨森鹰窗业股份有限公司",
                "美的集团股份有限公司",
                "浙江森歌电器有限公司",
                "宁波方太营销有限公司",
                "松下电器有限公司",
                "蒲江猕猴桃",
                "四川老坛子食品有限公司",
                "恩阳芦笋",
                "君乐宝乳液集团",
                "蓝河营养品有限公司",
                "蟹都汇餐饮管理有限公司",
                "苏州市阳澄湖镇蟹兴阁水产养殖基地",
                "江苏蟹都汇水产有限公司",
                "好记食品酿造股份有限公司",
                "成都熊猫不走烘焙有限公司",
                "兴业银行股份有限公司",
                "华泰保险经纪有限公司",
                "中国民生银行股份公司",
                "江苏京东邦能投资管理有限公司",
                "北京恒天明泽基金销售有限公司",
                "平安养老保险股份有限公司",
                "中国建设银行股份有限公司",
                "兴业银行股份有限公司",
                "中国邮政储蓄银行有限公司",

        };
        if (index < 0){
            index = -index;
        }
        index = index % AdMasterConditions.length;
        return AdMasterConditions[index];
    }


    public static String getAdPriceByRandom(int index){

        String[] AdPriceConditions = {

                "10000元/天",
                "50500元/天",
                "63000元/天",
                "70400元/天",
                "82000元/天",
                "99000元/天",
                "14000元/天",
                "15000元/天",
                "12000元/天",
                "11000元/天",
                "20000元/天",
                "10000元/天",
                "50000元/天",
                "65000元/天",
                "73000元/天",
                "84000元/天",
                "93000元/天",
                "24000元/天",
                "25000元/天",
                "22000元/天",
                "21000元/天",
                "21000元/天",
                "180000元/天",
                "53000元/天",
                "60200元/天",
                "70700元/天",
                "88000元/天",
                "98000元/天",
                "44000元/天",
                "45000元/天",
                "42000元/天",
                "41000元/天",
                "880000元/天",
        };
        if (index < 0){
            index = -index;
        }
        index = index % AdPriceConditions.length;
        return AdPriceConditions[index];
    }

    public static String getAdDateByRandom(int index){

        String[] AdDateConditions = {

                "2020-08-11",
                "2020-08-12",
                "2020-08-13",
                "2020-08-14",
                "2020-08-15",
                "2020-08-16",
                "2020-08-17",
                "2020-08-18",
                "2020-08-19",
                "2020-08-20",
                "2020-09-11",
                "2020-09-12",
                "2020-09-13",
                "2020-09-14",
                "2020-09-15",
                "2020-09-16",
                "2020-09-17",
                "2020-09-18",
                "2020-09-19",
                "2020-09-20",
                "2020-09-21",
                "2020-09-22",
                "2020-09-23",
                "2020-09-24",
                "2020-09-25",
                "2020-09-26",
                "2020-09-27",
                "2020-09-28",
                "2020-09-29",
                "2020-09-30",
                "2020-10-11",
                "2020-10-12",
                "2020-10-13",
                "2020-10-14",
                "2020-10-15",
                "2020-10-16",
                "2020-10-17",
                "2020-10-18",
                "2020-10-19",
                "2020-10-20",
                "2020-11-11",
                "2020-11-12",
                "2020-11-13",
                "2020-11-14",
                "2020-11-15",
                "2020-11-16",
                "2020-11-17",
                "2020-11-18",
                "2020-11-19",
                "2020-11-20",
                "2020-12-01",
                "2020-12-02",
                "2020-12-03",
                "2020-12-04",
                "2020-12-05",
                "2020-12-06",
                "2020-12-07",
                "2020-12-08",
                "2020-12-09",
                "2020-12-10",
                "2020-12-21",
                "2020-12-22",
                "2020-12-23",
                "2020-12-24",
                "2020-12-25",
                "2020-12-26",
                "2020-12-27",
                "2020-12-28",
                "2020-12-29",
                "2020-12-30",
        };
        if (index < 0){
            index = -index;
        }
        index = index % AdDateConditions.length;
        return AdDateConditions[index];
    }


    public static String getAdLocationByRandom(int index){

        String[] AdLocationConditions = {

                "11街道-01号",
                "13街道-02号",
                "65街道-12号",
                "23街道-04号",
                "76街道-24号",
                "23街道-56号",
                "76街道-53号",
                "33街道-87号",
                "70街道-27号",
                "21街道-98号",
                "17街道-34号",

                "34街道-501号",
                "55街道-702号",
                "32街道-112号",
                "90街道-104号",
                "54街道-224号",
                "14街道-561号",
                "19街道-531号",
                "15街道-877号",
                "37街道-217号",
                "38街道-298号",
                "46街道-304号",

                "21街道-10号",
                "61街道-200号",
                "71街道-123号",
                "86街道-403号",
                "43街道-244号",
                "26街道-562号",
                "79街道-913号",
                "20街道-847号",
                "15街道-273号",
                "52街道-908号",
                "49街道-374号",

                "43街道-91号",
                "66街道-702号",
                "46街道-122号",
                "74街道-303号",
                "48街道-243号",
                "94街道-565号",
                "74街道-593号",
                "56街道-675号",
                "31街道-273号",
                "16街道-938号",
                "29街道-134号",
        };
        if (index < 0){
            index = -index;
        }
        index = index % AdLocationConditions.length;
        return AdLocationConditions[index];
    }


    public static String getAdImgByRandom(int index){

        String[] AdImgConditions = {

                "D:\\tempImg/generateImg/01.jpg",
                "D:\\tempImg/generateImg/02.jpg",
                "D:\\tempImg/generateImg/03.jpg",
                "D:\\tempImg/generateImg/04.jpg",
                "D:\\tempImg/generateImg/05.jpg",
                "D:\\tempImg/generateImg/06.jpg",
                "D:\\tempImg/generateImg/07.jpg",
                "D:\\tempImg/generateImg/08.jpg",
                "D:\\tempImg/generateImg/09.jpg",
                "D:\\tempImg/generateImg/10.jpg",
                "D:\\tempImg/generateImg/11.jpg",
                "D:\\tempImg/generateImg/12.jpg",
                "D:\\tempImg/generateImg/13.jpg",
                "D:\\tempImg/generateImg/14.jpg",
                "D:\\tempImg/generateImg/15.jpg",
                "D:\\tempImg/generateImg/16.jpg",
                "D:\\tempImg/generateImg/17.jpg",
                "D:\\tempImg/generateImg/18.jpg",
                "D:\\tempImg/generateImg/19.jpg",
                "D:\\tempImg/generateImg/20.jpg",
                "D:\\tempImg/generateImg/21.jpg",
                "D:\\tempImg/generateImg/22.jpg",
                "D:\\tempImg/generateImg/23.jpg",
                "D:\\tempImg/generateImg/24.jpg",
                "D:\\tempImg/generateImg/25.jpg",
                "D:\\tempImg/generateImg/26.jpg",
                "D:\\tempImg/generateImg/27.jpg",
                "D:\\tempImg/generateImg/28.jpg",
                "D:\\tempImg/generateImg/29.jpg",
                "D:\\tempImg/generateImg/30.jpg",
                "D:\\tempImg/generateImg/31.jpg",
                "D:\\tempImg/generateImg/32.jpg",
                "D:\\tempImg/generateImg/33.jpg",
                "D:\\tempImg/generateImg/34.jpg",
                "D:\\tempImg/generateImg/35.jpg",
                "D:\\tempImg/generateImg/36.jpg",
                "D:\\tempImg/generateImg/37.jpg",
                "D:\\tempImg/generateImg/38.jpg",
                "D:\\tempImg/generateImg/39.jpg",
                "D:\\tempImg/generateImg/40.jpg",
                "D:\\tempImg/generateImg/41.jpg",
                "D:\\tempImg/generateImg/42.jpg",
                "D:\\tempImg/generateImg/43.jpg",
                "D:\\tempImg/generateImg/44.jpg",
                "D:\\tempImg/generateImg/45.jpg",
                "D:\\tempImg/generateImg/46.jpg",
                "D:\\tempImg/generateImg/47.jpg",
                "D:\\tempImg/generateImg/48.jpg",
                "D:\\tempImg/generateImg/49.jpg",
                "D:\\tempImg/generateImg/50.jpg",
                "D:\\tempImg/generateImg/51.jpg",
                "D:\\tempImg/generateImg/52.jpg",
                "D:\\tempImg/generateImg/53.jpg",
                "D:\\tempImg/generateImg/54.jpg",
                "D:\\tempImg/generateImg/55.jpg",
                "D:\\tempImg/generateImg/56.jpg",
                "D:\\tempImg/generateImg/57.jpg",
                "D:\\tempImg/generateImg/58.jpg",
                "D:\\tempImg/generateImg/59.jpg",
                "D:\\tempImg/generateImg/60.jpg",
                "D:\\tempImg/generateImg/61.jpg",
                "D:\\tempImg/generateImg/62.jpg",
                "D:\\tempImg/generateImg/63.jpg",
                "D:\\tempImg/generateImg/64.jpg",
                "D:\\tempImg/generateImg/65.jpg",
                "D:\\tempImg/generateImg/66.jpg",
                "D:\\tempImg/generateImg/67.jpg",
                "D:\\tempImg/generateImg/68.jpg",
                "D:\\tempImg/generateImg/69.jpg",
                "D:\\tempImg/generateImg/70.jpg",
                "D:\\tempImg/generateImg/71.jpg",
                "D:\\tempImg/generateImg/72.jpg",
                "D:\\tempImg/generateImg/73.jpg",
                "D:\\tempImg/generateImg/74.jpg",
                "D:\\tempImg/generateImg/75.jpg",
                "D:\\tempImg/generateImg/76.jpg",
                "D:\\tempImg/generateImg/77.jpg",
                "D:\\tempImg/generateImg/78.jpg",
                "D:\\tempImg/generateImg/79.jpg",
                "D:\\tempImg/generateImg/80.jpg",
                "D:\\tempImg/generateImg/81.jpg",
                "D:\\tempImg/generateImg/82.jpg",
                "D:\\tempImg/generateImg/83.jpg",
                "D:\\tempImg/generateImg/84.jpg",
                "D:\\tempImg/generateImg/85.jpg",
                "D:\\tempImg/generateImg/86.jpg",
                "D:\\tempImg/generateImg/87.jpg",
                "D:\\tempImg/generateImg/88.jpg",
                "D:\\tempImg/generateImg/89.jpg",
                "D:\\tempImg/generateImg/90.jpg",
                "D:\\tempImg/generateImg/91.jpg",
                "D:\\tempImg/generateImg/92.jpg",
                "D:\\tempImg/generateImg/93.jpg",
                "D:\\tempImg/generateImg/94.jpg",
                "D:\\tempImg/generateImg/95.jpg",
                "D:\\tempImg/generateImg/96.jpg",
                "D:\\tempImg/generateImg/97.jpg",
                "D:\\tempImg/generateImg/98.jpg",
                "D:\\tempImg/generateImg/99.jpg",

        };
        if (index < 0){
            index = -index;
        }
        index = index % AdImgConditions.length;
        return AdImgConditions[index];
    }
}
