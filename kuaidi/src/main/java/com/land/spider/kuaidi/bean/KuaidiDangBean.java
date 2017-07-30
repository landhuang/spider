package com.land.spider.kuaidi.bean;

public class KuaidiDangBean {


    /**
     * 快递单号
     */
    String danghao;

    /**
     * 公司
     */
    String company;


    /**
     * 最后物流信息
     */
    String context;

    /**
     * 敏感货
     */
    String sensitive;


    /**
     * 物流状态
     */
    String status;


    /**
     * 物品
     */
    String goods;

    /**
     * 数量
     */
    String quantity;


    /**
     * 价格
     */
    String price;

    /**
     * 最后更新时间
     */
    String lastupdate;

    /**
     * 物流最后时间
     */
    String ftime;


    /**
     * 1 已签收
     * 0 没签收
     */
    String ischeck;

    /**
     * 3 已签收
     * 0 没签收
     */
    String state;


    public String getDanghao() {
        return danghao;
    }

    public void setDanghao(String danghao) {
        this.danghao = danghao;
    }

    public String getCompany() {
        return company;
    }

    public void setCompany(String company) {
        this.company = company;
    }

    public String getContext() {
        return context;
    }

    public void setContext(String context) {
        this.context = context;
    }

    public String getSensitive() {
        return sensitive;
    }

    public void setSensitive(String sensitive) {
        this.sensitive = sensitive;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getGoods() {
        return goods;
    }

    public void setGoods(String goods) {
        this.goods = goods;
    }

    public String getQuantity() {
        return quantity;
    }

    public void setQuantity(String quantity) {
        this.quantity = quantity;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getLastupdate() {
        return lastupdate;
    }

    public void setLastupdate(String lastupdate) {
        this.lastupdate = lastupdate;
    }

    public String getFtime() {
        return ftime;
    }

    public void setFtime(String ftime) {
        this.ftime = ftime;
    }

    public String getIscheck() {
        return ischeck;
    }

    public void setIscheck(String ischeck) {
        this.ischeck = ischeck;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }
}
