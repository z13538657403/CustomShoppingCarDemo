package com.test.zhangtao.activitytest.recycler;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.test.zhangtao.activitytest.R;
import com.test.zhangtao.activitytest.activity.MainActivity;
import com.test.zhangtao.activitytest.datasave.DemoData;
import com.test.zhangtao.activitytest.datasave.GoodsDataBaseInterface;
import com.test.zhangtao.activitytest.datasave.OperateGoodsDataBase;
import java.util.List;

/**
 * Created by zhangtao on 16/12/31.
 */

public class ContentAdpater extends RecyclerView.Adapter<ContentAdpater.ContentViewHolder>
{
    private LayoutInflater mLayoutInflater;
    private List<String> mListContentData;
    private Context mContext;
    private OnItemClickListener mOnItemClickListener;
    /** 数据操作接口 */
    private GoodsDataBaseInterface mGoodsDataBaseInterface = null;

    //定义接口
    public interface OnItemClickListener
    {
        void onItemClick(ContentViewHolder holder , int position);
        void onItemPlusClick(ContentViewHolder holder , int position);
        void onItemSubClick(ContentViewHolder holder , int position);
    }

    public ContentAdpater(Context context , List<String> datas)
    {
        this.mListContentData = datas;
        this.mContext = context;
        mLayoutInflater = LayoutInflater.from(mContext);
        mGoodsDataBaseInterface = OperateGoodsDataBase.getInStance();
    }

    public void setOnItemClickListener(OnItemClickListener listener)
    {
        this.mOnItemClickListener = listener;
    }

    @Override
    public ContentViewHolder onCreateViewHolder(ViewGroup parent, int viewType)
    {
        View view = mLayoutInflater.inflate(R.layout.item_menu_content , parent , false);
        ContentViewHolder viewHolder = new ContentViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ContentViewHolder holder, int position)
    {
        holder.mImageView.setImageResource(DemoData.LISTMENU_IMAGES[position]);
        holder.mTitle.setText(DemoData.LISTMENU_TITLES[position]);
        holder.mSale.setText("月售" + DemoData.LISTMENU_NUMBERS[position]);
        holder.mPrice.setText(DemoData.LISTMENU_PRICES[position]);

        holder.mRatingBar.setRating(Float.parseFloat(DemoData.LISTMENU_STARS[position]));
        holder.mRatingBar.getRating();

        //获取存储的商品数量
        if (mGoodsDataBaseInterface.getSecondGoodsNumber(mContext ,
                MainActivity.SELECTPOSITION , DemoData.LISTMENU_GOODSIDS[holder.getLayoutPosition()]) == 0)
        {
            holder.mNumber.setText("");
            holder.mNumber.setVisibility(View.GONE);
            holder.mImgSub.setVisibility(View.GONE);
        }
        else
        {
            String number = "" + mGoodsDataBaseInterface.getSecondGoodsNumber(mContext ,
                    MainActivity.SELECTPOSITION , DemoData.LISTMENU_GOODSIDS[holder.getLayoutPosition()]);
            holder.mNumber.setText(number);
            holder.mNumber.setVisibility(View.VISIBLE);
            holder.mImgSub.setVisibility(View.VISIBLE);
        }

        setOnClickListener(holder);
    }

    private void setOnClickListener(final ContentViewHolder holder)
    {
        if (mOnItemClickListener != null)
        {
            holder.itemView.setOnClickListener(new View.OnClickListener()
            {
                @Override
                public void onClick(View v)
                {
                    mOnItemClickListener.onItemClick(holder , holder.getLayoutPosition());
                }
            });

            holder.mImgSub.setOnClickListener(new View.OnClickListener()
            {
                @Override
                public void onClick(View v)
                {
                    mOnItemClickListener.onItemSubClick(holder , holder.getLayoutPosition());
                }
            });

            holder.mImgPlus.setOnClickListener(new View.OnClickListener()
            {
                @Override
                public void onClick(View v)
                {
                    mOnItemClickListener.onItemPlusClick(holder , holder.getLayoutPosition());
                }
            });
        }
    }


    @Override
    public int getItemCount()
    {
        return mListContentData.size();
    }

    public class ContentViewHolder extends RecyclerView.ViewHolder
    {
        public ImageView mImageView , mImgPlus , mImgSub , mBuyImg;
        public TextView mTitle , mSale , mPrice , mNumber;
        public RatingBar mRatingBar;

        public ContentViewHolder(View itemView)
        {
            super(itemView);

            mBuyImg = (ImageView) itemView.findViewById(R.id.item_menu_content_buy);
            mImageView = (ImageView) itemView.findViewById(R.id.item_menu_content_img);
            mImgSub = (ImageView) itemView.findViewById(R.id.item_menu_content_sub);
            mImgPlus = (ImageView) itemView.findViewById(R.id.item_menu_content_plus);
            mTitle = (TextView) itemView.findViewById(R.id.item_menu_content_title);
            mRatingBar = (RatingBar) itemView.findViewById(R.id.item_menu_content_star);
            mSale = (TextView) itemView.findViewById(R.id.item_menu_content_sale);
            mPrice = (TextView) itemView.findViewById(R.id.item_menu_content_price);
            mNumber = (TextView) itemView.findViewById(R.id.item_menu_content_number);
        }
    }
}
