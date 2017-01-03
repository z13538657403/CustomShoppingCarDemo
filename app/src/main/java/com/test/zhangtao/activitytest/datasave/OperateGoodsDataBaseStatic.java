package com.test.zhangtao.activitytest.datasave;

import android.content.Context;
import android.util.Log;
import com.lidroid.xutils.DbUtils;
import com.lidroid.xutils.db.sqlite.Selector;
import com.lidroid.xutils.exception.DbException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by zhangtao on 16/12/31.
 */

public class OperateGoodsDataBaseStatic
{
    /**
     *添加和删除商品数量
     */
    public static int saveGoodsNumber(Context context , int menuPos , int goodsId , String goodsNum , String goodsPrice)
    {
        DbUtils dbUtils = DbUtils.create(context);
        GoodsBean goodsBean = null;
        goodsBean = new GoodsBean();
        goodsBean.setMenuPos(menuPos);
        goodsBean.setGoodsId(goodsId);
        goodsBean.setGoodsNum(goodsNum);
        goodsBean.setGoodsPrice(goodsPrice);

        try
        {
            GoodsBean bean = dbUtils.findFirst(Selector.from(GoodsBean.class).
                    where("menuPos" , "=" , menuPos).and("goodsId" , "=" , goodsId));
            //如果有这条数据，数量直接加1，否则直接插入表
            if (bean == null)
            {
                Log.d("Tag" , "还没有该商品");
                dbUtils.save(goodsBean);
                return getSecondGoodsNumber(context , menuPos , goodsId);
            }
            else
            {
                Log.d("Tag" , "已经有该商品");
                return updateNum(context , menuPos , goodsId , goodsNum);
            }
        }
        catch (DbException e)
        {
            e.printStackTrace();
        }
        Log.d("Tag" , "添加商品失败");
        dbUtils.close();
        return 0;
    }

    /** 修改数量，直接传入数量 */
    public static int updateNum(Context context, int menuPos, int goodsId, String goodsNum)
    {
        DbUtils dbUtils = DbUtils.create(context);

        try
        {
            GoodsBean bean = dbUtils.findFirst(Selector.from(GoodsBean.class)
                    .where("menuPos" , "=" , menuPos).and("goodsId" , "=" , goodsId));
            bean.setGoodsNum(goodsNum);
            dbUtils.update(bean);
            return getSecondGoodsNumber(context , menuPos , goodsId);
        }
        catch (DbException e)
        {
            e.printStackTrace();
        }
        dbUtils.close();
        return 0;
    }

    /** 根据下标得到第二级对应购物的数量  */
    public static int getSecondGoodsNumber(Context context, int menuPos, int goodsId)
    {
        DbUtils dbUtils = DbUtils.create(context);
        if (dbUtils == null)
        {
            Log.d("Tag" , "还没有该数据库");
            return 0;
        }

        try
        {
            GoodsBean bean = dbUtils.findFirst(Selector.from(GoodsBean.class)
                    .where("menuPos" , "=" , menuPos).and("goodsId" , "=" , goodsId));
            if (bean == null)
            {
                Log.d("Tag" , "还没有储存商品");
                return 0;
            }
            else
            {
                return Integer.parseInt(bean.getGoodsNum());
            }
        }
        catch (DbException e)
        {
            e.printStackTrace();
        }
        dbUtils.close();
        Log.d("Tag" , "获取商品数量失败");
        return 0;
    }

    /**
     *    根据第一级的下标，得到第二级的所有购物数量
     */
    public static int getSecondGoodsNumberAll(Context context , int menuPos)
    {
        DbUtils dbUtils = DbUtils.create(context);
        int mSecondGoodsNum = 0;
        ArrayList<GoodsBean> mGoodsBeanList = null;
        mGoodsBeanList = getSecondGoodsTypeList(context);
        if (mGoodsBeanList == null)
        {
            return 0;
        }
        for (int i = 0 ; i < mGoodsBeanList.size() ; i++)
        {
            if (mGoodsBeanList.get(i).getMenuPos() == menuPos)
            {
                mSecondGoodsNum += Integer.parseInt(mGoodsBeanList.get(i).getGoodsNum());
            }
        }
        dbUtils.close();
        return mSecondGoodsNum ;
    }

    //获取所有的数据行
    public static ArrayList<GoodsBean> getSecondGoodsTypeList(Context context)
    {
        DbUtils dbUtils = DbUtils.create(context);
        ArrayList<GoodsBean> list = null;
        try
        {
            list = (ArrayList<GoodsBean>) dbUtils.findAll(GoodsBean.class);
            if (list == null)
            {
                return null;
            }
            else
            {
                return list;
            }
        }
        catch (DbException e)
        {
            e.printStackTrace();
        }
        dbUtils.close();
        return null;
    }

    /**
     *   根据第一级的下标，得到第二级的所有购物的价格
     */
    public static int getSecondGoodsPriceAll(Context context , int menuPos)
    {
        DbUtils dbUtils = DbUtils.create(context);
        int mSecondGoodsPrice = 0;
        ArrayList<GoodsBean> mGoodsBeanList = null;
        mGoodsBeanList = getSecondGoodsTypeList(context);
        if (mGoodsBeanList == null)
        {
            Log.d("Tag" , "获取商品类型总数失败");
            return 0;
        }
        for (int i = 0 ; i < mGoodsBeanList.size() ; i++)
        {
            if (mGoodsBeanList.get(i).getMenuPos() == menuPos)
            {
                mSecondGoodsPrice += Integer.parseInt(mGoodsBeanList.get(i).getGoodsNum())
                        * Integer.parseInt(mGoodsBeanList.get(i).getGoodsPrice());
            }
        }
        dbUtils.close();
        return mSecondGoodsPrice;
    }

    /**
     *   删除所有的购物数据
     */
    public static void deleteAll(Context context)
    {
        DbUtils dbUtils = DbUtils.create(context);
        try
        {
            List<GoodsBean> records = dbUtils.findAll(GoodsBean.class);
            dbUtils.deleteAll(records);
        }
        catch (DbException e)
        {
            e.printStackTrace();
        }
        dbUtils.close();
    }
}
