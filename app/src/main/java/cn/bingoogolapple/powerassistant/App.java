package cn.bingoogolapple.powerassistant;

import android.app.Activity;
import android.app.ActivityManager;
import android.app.Application;

import com.mobisage.android.MobiSageManager;

import java.util.LinkedList;

import cn.bingoogolapple.powerassistant.util.SpUtil;
import cn.bingoogolapple.powerassistant.util.ToastUtil;

public class App extends Application {
    private static final String TAG = App.class.getSimpleName();
    private static App sInstance;
    private ActivityManager mActivityManager;
    private LinkedList<Activity> mActivities = new LinkedList<Activity>();
    private long mLastPressBackKeyTime;

    @Override
    public void onCreate() {
        super.onCreate();
        sInstance = this;
        mActivityManager = (ActivityManager) getSystemService(ACTIVITY_SERVICE);
        SpUtil.init(this);
        MobiSageManager.getInstance().initMobiSageManager (this,"FBUUx3uAmQ4khj1Okw==");
    }

    public static App getInstance() {
        return sInstance;
    }

    public void addActivity(Activity activity) {
        mActivities.add(activity);
    }

    public void removeActivity(Activity activity) {
        mActivities.remove(activity);
    }

    public void exitWithDoubleClick() {
        if (System.currentTimeMillis() - mLastPressBackKeyTime <= 1500) {
            exit();
        } else {
            mLastPressBackKeyTime = System.currentTimeMillis();
            ToastUtil.makeText(R.string.exit_tips);
        }
    }

    public void exit() {
        Activity activity;
        while (mActivities.size() != 0) {
            activity = mActivities.poll();
            if (!activity.isFinishing()) {
                activity.finish();
            }
        }
    }

    /**
     * 获取当前版本名称
     *
     * @return
     */
    public String getCurrentVersionName() {
        try {
            return getPackageManager().getPackageInfo(getPackageName(), 0).versionName;
        } catch (Exception e) {
            // 利用系统api getPackageName()得到的包名，这个异常根本不可能发生
            return null;
        }
    }
}
