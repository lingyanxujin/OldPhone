package com.tan.oldphone.app;

import java.util.List;

import de.greenrobot.dao.AbstractDao;
import de.greenrobot.dao.Property;
import de.greenrobot.dao.query.DeleteQuery;
import de.greenrobot.dao.query.QueryBuilder;

public class DaoUtils {
	//范例1：查询某个表是否包含某个id:
	public static <T,K>  boolean isSaved(AbstractDao<T, K>dao,Property IDPropertiy,int id){
		QueryBuilder<T> qb = dao.queryBuilder();
		qb.where(IDPropertiy.eq(id));
		qb.buildCount().count();
		return qb.buildCount().count() > 0 ? true : false;
	}
	
	//范例2：获取整个表的数据集合,一句代码就搞定！
	public static <T,K>  List<T> queryAllData(AbstractDao<T, K>dao){
		return dao.loadAll();// 获取所有数据
	}
	
	/*//范例3：通过一个字段值查找对应的另一个字段值(为简便直接使用下面方法，也许有更简单的方法，尚未尝试)
	public <T, K> int getTypeId(AbstractDao<T, K>dao,Property IDPropertiy,int id)
	{
	    QueryBuilder<T> qb = dao.queryBuilder();
	    qb.where(IDPropertiy.eq(id));
	    if (qb.list().size() > 0)
	    {
	        return qb.list().get(0).getTypeId();
	    }
	    else
	    {
	        return -1;
	    }
	}*/
	//清空表格数据
	public static <T, K> void clear(AbstractDao<T, K>dao){
		dao.deleteAll();
	}
	//删除某个对象
	public static <T, K> void deleteCityInfo(AbstractDao<T, K>dao,int id,Property IDPropertiy){
		QueryBuilder<T> qb = dao.queryBuilder();
		DeleteQuery<T> bd = qb.where(IDPropertiy.eq(id)).buildDelete();
		bd.executeDeleteWithoutDetachingEntities();
	}
	public static <T,K> T getDataById(AbstractDao<T, K>dao,int id){
		return 	dao.loadByRowId(id);
	}
	//插入数据更加简单，也是只要一句代码便能搞定!

	public static <T, K> Long insert(AbstractDao<T, K> dao,T p){
		return dao.insert(p);
	}
	/*	*//** 查询 *//*
    public  static <T, K> List<EstateLoveListJson> getCityInfoList(AbstractDao<T, K>dao)
    {
        QueryBuilder<T> qb = dao.queryBuilder();
        return qb.list();
    }*/
	public static <T, K> Long update(AbstractDao<T, K>dao,T p){
		return dao.insertOrReplace(p);
		//dao.insertInTx(p);
	}

}
