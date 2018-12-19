package com.meizu.code.frame.base.frame.manager;

import com.meizu.code.frame.utils.CodeFrameUtils;
import com.meizu.code.greendao.DaoMaster;
import com.meizu.code.greendao.DaoSession;

import org.greenrobot.greendao.database.Database;

/**
 * 数据库管理类
 * <p>
 * Created by mxm on 10:41.
 */

public class DataBaseManager {
    private static final String DATA_BASE_NAME = "codeFrame";
    private DaoSession mDaoSession;

    private DataBaseManager() {
    }

    private static class DataBaseManagerHolder {
        private static final DataBaseManager HOLDER = new DataBaseManager();
    }

    public static DataBaseManager getInstance() {
        return DataBaseManagerHolder.HOLDER;
    }

    public void setUpDataBase() {
        DaoMaster.DevOpenHelper openHelper = new DaoMaster
                .DevOpenHelper(CodeFrameUtils.getInstance().getGlobalContext(),
                DATA_BASE_NAME);
        Database db = openHelper.getWritableDb();
        DaoMaster daoMaster = new DaoMaster(db);
        mDaoSession = daoMaster.newSession();
    }

    public DaoSession getDaoSession() {
        if (mDaoSession == null) {
            setUpDataBase();
        }
        return mDaoSession;
    }
}
