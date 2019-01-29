package com.zwt.kepler_ec.ec.database;

import android.content.Context;

import org.greenrobot.greendao.database.Database;

/**
 * @author ZWT
 */
public class DatabaseManager {


    private UserProfileDao mUserProfileDao = null;
    private DaoSession mDaoSession = null;

    private DatabaseManager() {
    }

    private static final class Holder {
        private static final DatabaseManager INSTANCE = new DatabaseManager();
    }

    public static DatabaseManager getInstance() {
        return Holder.INSTANCE;
    }

    public DatabaseManager init(Context context) {
        initDao(context);
        return this;
    }

    private void initDao(Context context) {
        final ReleaseOpenHelper helper = new ReleaseOpenHelper(context, "kepler_ec");
        Database db = helper.getWritableDb();
        mDaoSession = new DaoMaster(db).newSession();
        mUserProfileDao = mDaoSession.getUserProfileDao();
    }

    public final UserProfileDao getUserProfileDao(){
        return mUserProfileDao;
    }
}
