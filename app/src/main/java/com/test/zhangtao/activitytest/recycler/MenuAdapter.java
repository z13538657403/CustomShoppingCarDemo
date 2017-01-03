package com.test.zhangtao.activitytest.recycler;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.test.zhangtao.activitytest.R;
import com.test.zhangtao.activitytest.activity.MainActivity;
import com.test.zhangtao.activitytest.datasave.DemoData;

import java.util.List;

/**
 * Created by zhangtao on 16/12/31.
 */

public class MenuAdapter extends RecyclerView.Adapter<MenuAdapter.MenuViewHolder>
{
    private LayoutInflater mLayoutInflater;
    protected List<String> mListMenuData;
    private Context mContext;
    private OnItemClickListener mOnItemClickListener;

    //定义接口
    public interface OnItemClickListener
    {
        void onItemClick(View v , int position);
    }

    public void setOnItemClickListener(OnItemClickListener listener)
    {
        this.mOnItemClickListener = listener;
    }

    public MenuAdapter(Context context , List<String> datas)
    {
        this.mListMenuData = datas;
        this.mContext = context;
        mLayoutInflater = LayoutInflater.from(mContext);
    }

    @Override
    public MenuViewHolder onCreateViewHolder(ViewGroup parent, int viewType)
    {
        View view = mLayoutInflater.inflate(R.layout.item_menu , parent , false);
        MenuViewHolder viewHolder = new MenuViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(MenuViewHolder holder, int position)
    {
        holder.mTextView.setText(DemoData.LISTMENU_STYLES[position]);
        //设置所有的item显示为默认状态
        holder.mLinearLayout.setBackgroundResource(R.color.color_menu_back);
        holder.viewRed.setVisibility(View.GONE);
        holder.viewV.setVisibility(View.INVISIBLE);
        //根据点击item对应的position来改变item的状态
        if (holder.getLayoutPosition() == MainActivity.SELECTPOSITION)
        {
            holder.mLinearLayout.setBackgroundResource(R.color.white);
            holder.viewRed.setVisibility(View.VISIBLE);
            holder.viewV.setVisibility(View.GONE);
        }

        setOnClickListener(holder);
    }

    private void setOnClickListener(final MenuViewHolder holder)
    {
        if (mOnItemClickListener != null)
        {
            holder.itemView.setOnClickListener(new View.OnClickListener()
            {
                @Override
                public void onClick(View v)
                {
                    int layoutPosition = holder.getLayoutPosition();
                    mOnItemClickListener.onItemClick(holder.itemView , layoutPosition);
                }
            });
        }
    }

    @Override
    public int getItemCount()
    {
        return mListMenuData.size();
    }

    class MenuViewHolder extends RecyclerView.ViewHolder
    {
        TextView mTextView;
        LinearLayout mLinearLayout;
        View viewRed , viewV;

        public MenuViewHolder(View itemView)
        {
            super(itemView);
            mTextView = (TextView) itemView.findViewById(R.id.item_menu_text);
            mLinearLayout = (LinearLayout) itemView.findViewById(R.id.black_lay);
            viewRed = itemView.findViewById(R.id.item_menu_view_red);
            viewV = itemView.findViewById(R.id.item_menu_view_v);
        }
    }
}
