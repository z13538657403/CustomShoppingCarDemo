package com.test.zhangtao.activitytest.activity;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.test.zhangtao.activitytest.R;
import com.test.zhangtao.activitytest.animutils.GoodsAnimUtil;
import com.test.zhangtao.activitytest.datasave.DemoData;
import com.test.zhangtao.activitytest.datasave.GoodsDataBaseInterface;
import com.test.zhangtao.activitytest.datasave.OperateGoodsDataBase;
import com.test.zhangtao.activitytest.recycler.ContentAdpater;
import com.test.zhangtao.activitytest.recycler.DividerItemDecoration;
import com.test.zhangtao.activitytest.recycler.MenuAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by zhangtao on 16/12/31.
 */

public class MainActivity extends BaseActivity
{
    @ViewInject(R.id.m_list_menu)
    RecyclerView mRecyclerMenu;

    @ViewInject(R.id.m_list_content)
    RecyclerView mRecyclerContent;

    @ViewInject(R.id.m_list_all_price)
    TextView mListAllPrice;

    @ViewInject(R.id.m_list_num)
    TextView mListAllNum;

    @ViewInject(R.id.m_list_car_lay)
    RelativeLayout mCarLay;

    public static int SELECTPOSITION = 0;
    private Context mContext;
    private MenuAdapter mMenuAdapter = null;
    private ContentAdpater mContentAdapter = null;
    private List<String> stringMenuList = new ArrayList<>();
    private List<String> stringContentList = new ArrayList<>();
    GoodsDataBaseInterface mGoodsDataBaseInterface = null;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ViewUtils.inject(this);

        mContext = this;
        initView();
        setRecyclerView();
        initHttp();
    }

    private void initView()
    {
        mGoodsDataBaseInterface = OperateGoodsDataBase.getInStance();
        mGoodsDataBaseInterface.deleteAll(mContext);
    }

    private void setRecyclerView()
    {
        mRecyclerMenu.setLayoutManager(new LinearLayoutManager(mContext , LinearLayoutManager.VERTICAL , false));
        mRecyclerMenu.addItemDecoration(new DividerItemDecoration(mContext , DividerItemDecoration.VERTICAL_LIST));
        mRecyclerContent.setLayoutManager(new LinearLayoutManager(mContext , LinearLayoutManager.VERTICAL , false));
    }

    private void initHttp()
    {
        for (int i = 0 ; i < 10 ; i++)
        {
            stringMenuList.add("1111");
        }

        for (int i = 0 ; i < 4 ; i++)
        {
            stringContentList.add("2222");
        }
        setMenuCommonAdapter();
        setContentCommonAdapter();
    }

    private void setMenuCommonAdapter()
    {
        mMenuAdapter = new MenuAdapter(mContext , stringMenuList);
        mRecyclerMenu.setAdapter(mMenuAdapter);

        mMenuAdapter.setOnItemClickListener(new MenuAdapter.OnItemClickListener()
        {
            @Override
            public void onItemClick(View v, int position)
            {
                SELECTPOSITION = position;
                mMenuAdapter.notifyDataSetChanged();
                mContentAdapter.notifyDataSetChanged();
                setAll();
            }
        });
    }

    private void setContentCommonAdapter()
    {
        mContentAdapter = new ContentAdpater(mContext , stringContentList);
        mRecyclerContent.setAdapter(mContentAdapter);
        mContentAdapter.setOnItemClickListener(new ContentAdpater.OnItemClickListener()
        {
            @Override
            public void onItemClick(ContentAdpater.ContentViewHolder holder , int position)
            {

            }

            @Override
            public void onItemPlusClick(ContentAdpater.ContentViewHolder holder , int position)
            {
                String numText = holder.mNumber.getText().toString().trim();
                if (numText.isEmpty() || numText.equals("0"))
                {
                    holder.mImgPlus.setVisibility(View.VISIBLE);
                    holder.mNumber.setText(getSecondNumber(position , "1"));
                    holder.mNumber.setVisibility(View.VISIBLE);
                    holder.mImgSub.setVisibility(View.VISIBLE);
                }
                else
                {
                    holder.mNumber.setText(mGoodsDataBaseInterface.saveGoodsNumber(mContext , SELECTPOSITION ,
                            DemoData.LISTMENU_GOODSIDS[position] , String.valueOf(Integer.parseInt(numText) + 1) , DemoData.LISTMENU_PRICES[position]) + "");
                }

                GoodsAnimUtil.setAnim(MainActivity.this , holder.mImgPlus , mCarLay);
                GoodsAnimUtil.setOnEndAnimListener(new OnEndAnim());
            }

            @Override
            public void onItemSubClick(ContentAdpater.ContentViewHolder holder , int position)
            {
                String numText = holder.mNumber.getText().toString().trim();
                holder.mNumber.setText(mGoodsDataBaseInterface.saveGoodsNumber(mContext , SELECTPOSITION ,
                        DemoData.LISTMENU_GOODSIDS[position] , String.valueOf(Integer.parseInt(numText) - 1) , DemoData.LISTMENU_PRICES[position]) + "");

                if (holder.mNumber.getText().equals("0"))
                {
                    holder.mNumber.setVisibility(View.GONE);
                    holder.mImgSub.setVisibility(View.GONE);
                }
                setAll();
            }
        });
    }

    private void setAll()
    {
        if (mGoodsDataBaseInterface.getSecondGoodsNumberAll(mContext , SELECTPOSITION) == 0)
        {
            mListAllNum.setVisibility(View.GONE);
            mListAllPrice.setText("$ 0");
            mListAllNum.setText("0");
        }
        else
        {
            mListAllPrice.setText("$" + mGoodsDataBaseInterface.getSecondGoodsPriceAll(mContext , SELECTPOSITION));
            mListAllNum.setText(mGoodsDataBaseInterface.getSecondGoodsNumberAll(mContext , SELECTPOSITION) + "");
            mListAllNum.setVisibility(View.VISIBLE);
        }
    }

    private class OnEndAnim implements GoodsAnimUtil.OnEndAnimListener
    {
        @Override
        public void onEndAnim()
        {
            setAll();
        }
    }

    private String getSecondNumber(int position , String operateNum)
    {
        String number= mGoodsDataBaseInterface.saveGoodsNumber(mContext , SELECTPOSITION ,
                DemoData.LISTMENU_GOODSIDS[position] , operateNum , DemoData.LISTMENU_PRICES[position]) + "";
        return number;
    }
}
