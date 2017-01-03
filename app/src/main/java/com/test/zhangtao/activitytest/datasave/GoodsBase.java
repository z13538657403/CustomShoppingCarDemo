package com.test.zhangtao.activitytest.datasave;

/**
 * Created by zhangtao on 16/12/31.
 */

public class GoodsBase
{
    //@Id   如果没有命名为id或_id的主键时，需要为主键添加此注解
    //@NoAutoIncrement    int,long类型的id默认自增，不想使用自增时添加此注解
    private int id;
    public int getId()
    {
        return id;
    }

    public void setId(int id)
    {
        this.id = id;
    }
}
