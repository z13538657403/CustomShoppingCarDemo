package com.test.zhangtao.activitytest.datasave;

import android.content.Context;

/**
 * Created by zhangtao on 16/12/31.
 */

public interface GoodsDataBaseInterface
{
    /** 添加和删除购物车的数量 */
    int saveGoodsNumber(Context context , int menuPos , int goodsId , String goodsNum , String goodsPrice);

    /** 根据第一级的下标 得到第二级对应购物的数据  */
    int getSecondGoodsNumber(Context context , int menuPos , int goodsId);

    /** 根据第一级的下标，得到第二级的所有购物数量 */
    int getSecondGoodsNumberAll(Context context , int menuPos);

    /** 根据第一级的下标，得到第二级的所有购物的价格  */
    int getSecondGoodsPriceAll(Context context , int menuPos);

    /** 删除所有的购物数据 */
    void deleteAll(Context context);
}
