package com.land.spider.kuaidi;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.Spider;
import us.codecraft.webmagic.processor.PageProcessor;
import us.codecraft.webmagic.selector.Selectable;

public class KuaiDiApp implements PageProcessor {


    private Logger logger = LogManager.getLogger(getClass());

    public static void main(String[] args) {
//        String[] str = {"446417613174"};
        String[] str = {"446417613174","446428410514","446440153790","447242854539","446283912874","446923545230"};
        KuaiDiApp app = new KuaiDiApp();

        for(String s:str){
            app.getZTO(s);
        }

    }

    void getZTO(String txtbill){
//        long startTime, endTime;
//        System.out.println("开始爬取...");
//        startTime = System.currentTimeMillis();
        Spider.create(new KuaiDiApp()).addUrl("http://www.zto.com/GuestService/BillNew?txtbill="+txtbill+"%0D%0A").thread(5).run();
//        endTime = System.currentTimeMillis();
//        System.out.println("爬取结束，耗时约" + ((endTime - startTime) / 1000) + "秒，抓取了"+count+"条记录");
    }


    @Override
    public void process(Page page) {
        //单号
        Selectable danhao = page.getHtml().xpath("//div[@class='fl w540 querycont ']/h3/text()");
        Selectable Status = page.getHtml().xpath("//div[@class='fl w540 querycont ']/input");
        Selectable time = page.getHtml().xpath("//li[@class='pr firstChild current1']/div[@class='time']/tidyText()");
        Selectable s = page.getHtml().xpath("//div[@class='on']/tidyText()");
        logger.info(danhao+" 状态："+Status.$("input","value")+" 时间："+time+" 快递结果："+s+"]");
        count++;
    }
    // 抓取网站的相关配置，包括编码、抓取间隔、重试次数等
    private Site site = Site.me().setRetryTimes(3).setSleepTime(100);
    private static int count =0;

    @Override
    public Site getSite() {
        return site;
    }
}
