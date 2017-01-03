package com.test.zhangtao.activitytest.datasave;

import com.lidroid.xutils.db.annotation.Column;

/**
 * Created by zhangtao on 16/12/31.
 */

public class GoodsBean extends GoodsBase
{
    @Column(column = "menuPos")
    private int menuPos;
    @Column(column = "goodsId")
    private int goodsId;
    @Column(column = "goodsNum")
    private String goodsNum;
    @Column(column = "goodsPrice")
    private String goodsPrice;

    public int getMenuPos() {
        return menuPos;
    }

    public void setMenuPos(int menuPos) {
        this.menuPos = menuPos;
    }

    public int getGoodsId() {
        return goodsId;
    }

    public void setGoodsId(int goodsId) {
        this.goodsId = goodsId;
    }

    public String getGoodsNum() {
        return goodsNum;
    }

    public void setGoodsNum(String goodsNum) {
        this.goodsNum = goodsNum;
    }

    public String getGoodsPrice() {
        return goodsPrice;
    }

    public void setGoodsPrice(String goodsPrice) {
        this.goodsPrice = goodsPrice;
    }

    @Override
    public String toString()
    {
        return "GoodsBean{ " +
                "menuPos='" + menuPos + "\'" +
                ", goodsId='" + goodsId + "\'" +
                ", goodsNum='" + goodsNum + "\'" +
                ", goodsPrice='" + goodsPrice + "\'" +
                '}';
    }
}
