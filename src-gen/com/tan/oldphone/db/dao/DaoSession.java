package com.tan.oldphone.db.dao;

import android.database.sqlite.SQLiteDatabase;

import java.util.Map;

import de.greenrobot.dao.AbstractDao;
import de.greenrobot.dao.AbstractDaoSession;
import de.greenrobot.dao.identityscope.IdentityScopeType;
import de.greenrobot.dao.internal.DaoConfig;

import com.tan.oldphone.db.dao.ContactInfo;

import com.tan.oldphone.db.dao.ContactInfoDao;

// THIS CODE IS GENERATED BY greenDAO, DO NOT EDIT.

/**
 * {@inheritDoc}
 * 
 * @see de.greenrobot.dao.AbstractDaoSession
 */
public class DaoSession extends AbstractDaoSession {

    private final DaoConfig contactInfoDaoConfig;

    private final ContactInfoDao contactInfoDao;

    public DaoSession(SQLiteDatabase db, IdentityScopeType type, Map<Class<? extends AbstractDao<?, ?>>, DaoConfig>
            daoConfigMap) {
        super(db);

        contactInfoDaoConfig = daoConfigMap.get(ContactInfoDao.class).clone();
        contactInfoDaoConfig.initIdentityScope(type);

        contactInfoDao = new ContactInfoDao(contactInfoDaoConfig, this);

        registerDao(ContactInfo.class, contactInfoDao);
    }
    
    public void clear() {
        contactInfoDaoConfig.getIdentityScope().clear();
    }

    public ContactInfoDao getContactInfoDao() {
        return contactInfoDao;
    }

}