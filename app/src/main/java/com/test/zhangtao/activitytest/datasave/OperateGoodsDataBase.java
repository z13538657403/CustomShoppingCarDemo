package com.test.zhangtao.activitytest.datasave;

import android.content.Context;

/**
 * Created by zhangtao on 16/12/31.
 */

public class OperateGoodsDataBase implements GoodsDataBaseInterface
{
    private static OperateGoodsDataBase inStance = new OperateGoodsDataBase();

    public static OperateGoodsDataBase getInStance()
    {
        return inStance;
    }

    private OperateGoodsDataBase()
    {
    }

    @Override
    public int saveGoodsNumber(Context context, int menuPos, int goodsId, String goodsNum, String goodsPrice)
    {
        return OperateGoodsDataBaseStatic.saveGoodsNumber(context, menuPos, goodsId, goodsNum, goodsPrice);
    }

    @Override
    public int getSecondGoodsNumber(Context context, int menuPos, int goodsId)
    {
        return OperateGoodsDataBaseStatic.getSecondGoodsNumber(context , menuPos , goodsId);
    }

    @Override
    public int getSecondGoodsNumberAll(Context context, int menuPos)
    {
        return OperateGoodsDataBaseStatic.getSecondGoodsNumberAll(context , menuPos);
    }

    @Override
    public int getSecondGoodsPriceAll(Context context, int menuPos)
    {
        return OperateGoodsDataBaseStatic.getSecondGoodsPriceAll(context , menuPos);
    }

    @Override
    public void deleteAll(Context context)
    {
        OperateGoodsDataBaseStatic.deleteAll(context);
    }
}
