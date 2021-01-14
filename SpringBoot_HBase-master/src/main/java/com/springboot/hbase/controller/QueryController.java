package com.springboot.hbase.controller;

import com.springboot.hbase.dto.AdResult;
import com.springboot.hbase.dto.AdvIdResult;
import com.springboot.hbase.entity.Advertisement;
import com.springboot.hbase.entity.ComparatorForAdv;
import com.springboot.hbase.service.DateBaseQuery;
import org.apache.directory.api.util.Strings;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;

/*
* @author ygp
* @Description QueryController 类是系统用来处理前端发送请求的Controller。
*
 */
@Controller
public class QueryController {

    private ArrayList<Advertisement> queriedAdvertisement = new ArrayList<>();

    public QueryController(){}

    @GetMapping("api/queryForAdvId")
    @ResponseBody
    @CrossOrigin
    public Object queryByMapCoord(@RequestParam(value = "maxLat",required = true) double maxLat,
                                           @RequestParam(value = "minLat", required = true) double minLat,
                                           @RequestParam(value = "maxLng", required = true) double maxLng,
                                           @RequestParam(value = "minLng", required = true) double minLng,
                                           @RequestParam(value = "precision", required = true) int precision,
                                           @RequestParam(value = "conditionKey", required = true) String conditionKey){
        System.out.println("queryForAdvId");

        HashSet<Advertisement> advertisements = new HashSet<>();
        HashSet<AdvIdResult> advIdResults = new HashSet<>();
        this.getQueriedAdvertisement().clear();
        try {
            advertisements = DateBaseQuery.queryByLatAndLng(maxLat, minLat, maxLng, minLng, precision,
                    "Advertisement");
        }catch (IOException e){
            System.out.println("查询失败，错误：" + e);
            return (Object) new AdvIdResult("500", "fail", null, null, null);
        }

        String templateStr = getTemplateStr(conditionKey);
        long conditionValue = Long.parseLong(conditionKey, 2);
        long templateValue = Long.parseLong(templateStr, 2);

        for (Advertisement ad : advertisements){
            if ( (ad.getConditionKey() & templateValue) == conditionValue ){
                this.getQueriedAdvertisement().add(ad);
                advIdResults.add(new AdvIdResult("200", "success", ad.getAdvId(), ad.getLatitude(), ad.getLongitude()));
            }
        }

        this.getQueriedAdvertisement().sort(new ComparatorForAdv());

        System.out.println(advIdResults.size());
        return (Object) advIdResults.toArray();
    }

    @GetMapping("api/queryForAdv")
    @ResponseBody
    @CrossOrigin
    public Object queryByAdvId(@RequestParam(value = "advId", required = true) String advId){
        System.out.println("querySecond!!!");
        Advertisement advertisement = null;
        advertisement = getAdvFromQueried(advId);
        if (advertisement != null){
            System.out.println(advertisement);
            return (Object)new AdResult("200", "success", advertisement);
        }else {
            return (Object)new AdResult("500","cache 内 没有该广告", null);
        }
    }


    public String getTemplateStr(String conditionKey){
        int defaultLength = 32;
        String defaultMediaRefer = "00000000";
        String defaultMediaCategory = "00000000";
        String defaultPreRefer = "00000000";
        String defaultPreCategory = "00000000";

        String templateMediaRefer = "11111111";
        String templateMediaCategory = "11111111";
        String templatePreRefer = "11111111";
        String templatePreCategory = "11111111";



        if (conditionKey.length() != defaultLength){
            return defaultMediaRefer + defaultMediaCategory + defaultPreRefer + defaultPreCategory;
        }
        // 由传入的 conditionKey 来构建匹配模板


        int startIndex = 0;
        int endIndex = 8;
        String mediaRefer = conditionKey.substring(startIndex, endIndex);
        String mediaCategory = conditionKey.substring(startIndex + 8, endIndex + 8);
        String preRefer = conditionKey.substring(startIndex + 16, endIndex + 16);
        String preCategory = conditionKey.substring(startIndex + 24, endIndex + 24);

        StringBuilder templateStr = new StringBuilder("");
        if (!Strings.equals(mediaRefer, defaultMediaRefer)){
            templateStr.append(templateMediaRefer);
        } else {
            templateStr.append(defaultMediaRefer);
        }

        if (!Strings.equals(mediaCategory, defaultMediaCategory)){
            templateStr.append(templateMediaCategory);
        } else {
            templateStr.append(defaultMediaCategory);
        }

        if (!Strings.equals(preRefer, defaultPreRefer)){
            templateStr.append(templatePreRefer);
        } else {
            templateStr.append(defaultPreRefer);
        }

        if (!Strings.equals(preCategory, defaultPreCategory)){
            templateStr.append(templatePreCategory);
        } else {
            templateStr.append(defaultPreCategory);
        }

        return templateStr.toString();
    }

    /*
    * @author: ygp
    * @Description: 传入一个AdvId，从queriedAdvertisement 中二分搜索具有该AdvId值的Advertisement。
     */
    public Advertisement getAdvFromQueried(String advId){

        Advertisement advertisement = null;


        int start = 0;
        int end = this.queriedAdvertisement.size() - 1;
        while (start <= end){
            int middle = (start + end)/ 2;
            Advertisement tempAdv = this.queriedAdvertisement.get(middle);
            if (Strings.equals(advId,tempAdv.getAdvId())){
                advertisement = tempAdv;
                break;
            }else if (Integer.parseInt(advId) < Integer.parseInt(tempAdv.getAdvId())){
                end = middle - 1;
            }else if (Integer.parseInt(advId) > Integer.parseInt(tempAdv.getAdvId())){
                start = middle + 1;
            }
        }

        return advertisement;
    }

    public ArrayList<Advertisement> getQueriedAdvertisement(){
        return this.queriedAdvertisement;
    }
}
