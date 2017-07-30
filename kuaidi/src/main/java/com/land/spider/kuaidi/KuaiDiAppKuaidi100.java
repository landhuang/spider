package com.land.spider.kuaidi;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import com.land.spider.kuaidi.bean.KuaidiDangBean;
import com.land.spider.kuaidi.common.ExcelProcess;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.Spider;
import us.codecraft.webmagic.processor.PageProcessor;
import us.codecraft.webmagic.selector.Selectable;

import java.io.IOException;
import java.util.*;

public class KuaiDiAppKuaidi100 implements PageProcessor {
    static private Logger logger = LogManager.getLogger(KuaiDiAppKuaidi100.class);
    static HashMap<String,HashMap<String,String>> kuaidiMap;
    static HashMap<String,KuaidiDangBean> kuaididangHashMap = new HashMap<String,KuaidiDangBean>();
    static ExcelProcess excel = new ExcelProcess();

    public static void main(String[] args) {
        init();
        String kuaidiExcel = "D:\\github\\spider\\kuaidi\\src\\main\\resources\\李玲娟.xlsx";
        try {
            List<HashMap<String,String>> list = excel.read("D:\\github\\spider\\kuaidi\\src\\main\\resources\\李玲娟.xlsx");
            for(HashMap kuaidihao : list){
                getKuaidi100(kuaidihao);
            }
            logger.info("kuaididangHashMap:"+kuaididangHashMap+"]");
            excel.save(kuaididangHashMap,kuaidiExcel);
        } catch (IOException e) {
            e.printStackTrace();
            logger.error(e.getMessage(),e);
        } catch (NullPointerException e) {
            e.printStackTrace();
            logger.error(e.getMessage(),e);
        }

    }

    static void init() {
        kuaidiMap = new HashMap<String, HashMap<String, String>>();
        HashMap<String, String> obj;
        obj = new HashMap<String, String>();
        obj.put("type", "wanxiangwuliu");
        kuaidiMap.put("万象物流", obj);

        obj = new HashMap<String, String>();
        obj.put("type", "shentong");
        kuaidiMap.put("申通快递", obj);

        obj = new HashMap<String, String>();
        obj.put("type", "zhongtong");
        kuaidiMap.put("中通快递", obj);

        obj = new HashMap<String, String>();
        obj.put("type", "yuantong");
        kuaidiMap.put("圆通速递", obj);


        obj = new HashMap<String, String>();
        obj.put("type", "tiantian");
        kuaidiMap.put("天天快递", obj);

        obj = new HashMap<String, String>();
        obj.put("type", "huitongkuaidi");
        kuaidiMap.put("百世快递", obj);

        obj = new HashMap<String, String>();
        obj.put("type", "huitongkuaidi");
        kuaidiMap.put("百世物流", obj);

        obj = new HashMap<String, String>();
        obj.put("type", "yunda");
        kuaidiMap.put("韵达快递", obj);

    }

    static void getKuaidi100(Map<String,String> map){

        /**
         * https://www.kuaidi100.com/autonumber/autoComNum?text=3333269459228
         * response:{"comCode":"","num":"3333269459228","auto":[{"comCode":"shentong","id":"","noCount":1940,"noPre":"33332","startTime":""}]}
         */
        Spider.create(new KuaiDiAppKuaidi100()).addUrl("http://www.kuaidi100.com/query?type="+kuaidiMap.get(map.get("kuaidiType")).get("type")+"&postid="+map.get("kuaidiID")+"&id=19&valicode=&temp=0.6910910809318731").thread(5).run();
//        endTime = System.currentTimeMillis();
//        System.out.println("爬取结束，耗时约" + ((endTime - startTime) / 1000) + "秒，抓取了"+count+"条记录");
    }

    @Override
    public void process(Page page) {

        /**
         * {
         "message":"ok"
         ,"nu":"604002729704694"
         ,"ischeck":"1"
         ,"condition":"F00"
         ,"com":"wanxiangwuliu"
         ,"status":"200"
         ,"state":"3"
         ,"data":[
         {"time":"2017-07-23 18:57:05"
         ,"ftime":"2017-07-23 18:57:05"
         ,"context":"[新市发行站] 广州：新市发行站派件确认手机确认，签收人：本人 "
         ,"location":"新市发行站"}
         ,{"time":"2017-07-23 17:32:02"
         ,"ftime":"2017-07-23 17:32:02"
         ,"context":"[新市发行站] 已签收，签收人：本人手机签收，操作人杜俊贤，联系电话：13189172091"
         ,"location":"新市发行站"}
         ,{"time":"2017-07-23 17:25:12"
         ,"ftime":"2017-07-23 17:25:12"
         ,"context":"[新市发行站] 新市发行站派件中，派件员：杜俊贤，联系电话 ：13189172091"
         ,"location":"新市发行站"}
         ,{"time":"2017-07-22 20:49:11"
         ,"ftime":"2017-07-22 20:49:11"
         ,"context":"[新市发行站] 收件人要求约时配送广州：新市发行站延缓杜俊贤 收件人要求约时配送;下次配送时间：2017-07-22 24:00:00;"
         ,"location":"新市发行站"}
         ,{"time":"2017-07-22 16:27:25"
         ,"ftime":"2017-07-22 16:27:25"
         ,"context":"[新市发行站] 新市发行站派件中，派件员：杜俊贤，联系电话 ：13189172091"
         ,"location":"新市发行站"}
         ,{"time":"2017-07-22 16:14:45"
         ,"ftime":"2017-07-22 16:14:45"
         ,"context":"[新市发行站] 新市发行站派件中，派件员：杜俊贤，联系电话 ：13189172091"
         ,"location":"新市发行站"}
         ,{"time":"2017-07-22 15:37:33"
         ,"ftime":"2017-07-22 15:37:33"
         ,"context":"[新市发行站] 广州：新市发行站到站扫码周超，站点电话：86436563 "
         ,"location":"新市发行站"}
         ]
         }
         */
        logger.info("getJson:"+page.getJson()+"]");
        Selectable nu = page.getJson().jsonPath("$.nu");
        Selectable com = page.getJson().jsonPath("$.com");
        Selectable state = page.getJson().jsonPath("$.state");
        Selectable data = page.getJson().jsonPath("$.data");
        logger.info("nu:"+nu.get()+"]");
        logger.info("com:"+com.get()+"]");
        logger.info("data1:"+data+"]");
        logger.info("data ftime:"+stringToMap(data.toString()).get("ftime")+"]");

        if(data!=null){
            Map<String,String> wuliu = stringToMap(data.toString());
            logger.info("wuliu:"+wuliu+"]");
            KuaidiDangBean dang = new KuaidiDangBean();
            dang.setFtime(wuliu.get("ftime"));
            dang.setContext(wuliu.get("context"));
            dang.setCompany(com.get());
            dang.setDanghao(nu.get());
            dang.setStatus(state.get());
            dang.setState(state.get());

            kuaididangHashMap.put(dang.getDanghao(),dang);
        }
        count++;
    }
    // 抓取网站的相关配置，包括编码、抓取间隔、重试次数等
    private Site site = Site.me().setRetryTimes(3).setSleepTime(100);
    private static int count =0;

    @Override
    public Site getSite() {
        return site;
    }

    /**
     *
     * 函数名称: stringToMap
     * 函数描述: 将json字符串转换为map
     * @param json
     * @return
     */
    public static Map<String, String> stringToMap(String json){
        GsonBuilder gb = new GsonBuilder();
        Gson g = gb.create();
        Map<String, String> map = g.fromJson(json, new TypeToken<Map<String, String>>() {}.getType());
        return map;
    }

}
