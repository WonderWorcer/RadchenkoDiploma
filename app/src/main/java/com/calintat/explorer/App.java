package com.calintat.explorer;

import android.app.Application;
import android.content.Context;

/**
 * Created by Анастасия on 12.05.2017.
 */

public class App extends Application {

    public final static String PREF_PASSWORD = "prefPassword";
    public final static String PREF_PASSWORD_ACTIVE = "PrefPasswordActive";
    public final static String PREF_CHANGE_PASSWORD = "prefChangePassword";
    public final static String PREF_DELETE_AFTER_10_ATTEMPT = "prefDeleteAfter10Attempt";

    private static Context context;

    public static Context getContext() {
        return context;
    }

    public static void setContext(Context mContext) {
        context = mContext;
    }
}
