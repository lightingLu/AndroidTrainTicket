package base;

import android.app.Application;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import utils.AssetsDatabaseManager;

/**
 * Created by Three on 2016/8/30.
 */
public class MyApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        // 初始化数据库管理，只需要调用一次
        AssetsDatabaseManager.initManager(this);
        System.out.println("=========================数据库初始化成功");
        //  获取管理对象，因为数据库需要通过管理对象才能够获取

    }
}
