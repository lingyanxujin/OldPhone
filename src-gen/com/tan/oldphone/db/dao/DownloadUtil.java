package com.tan.oldphone.db.dao;

import java.util.List;

import de.greenrobot.dao.internal.DaoConfig;
import de.greenrobot.dao.query.QueryBuilder;

public class DownloadUtil extends ContactInfoDao{
    
    public DownloadUtil(DaoConfig config) {
		super(config);
		// TODO Auto-generated constructor stub
	}

    /**查询某个表是否包含某个id**/
	public boolean isSaved(int ID)
    {
        QueryBuilder<ContactInfo> qb = this.queryBuilder();
        qb.where(Properties.Id.eq(ID));
        qb.buildCount().count();
        return qb.buildCount().count() > 0 ? true : false;
    }
	
	/**获取整个表的数据集合,一句代码就搞定**/
	public List<ContactInfo> getPhotoGallery()
	{
	    return this.loadAll();// 获取图片相册
	}
	
}
