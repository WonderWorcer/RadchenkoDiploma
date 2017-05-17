package com.calintat.explorer.activities;

import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.calintat.explorer.App;
import com.calintat.explorer.R;
import com.calintat.explorer.helper.DBHelper;
import com.calintat.explorer.helper.PrefsHelper;
import com.calintat.explorer.view.PwdGestureView;

public class PasswordActivity extends AppCompatActivity {
    PwdGestureView mPwdGestureView;
    String firstPassword = "";
    boolean isFirstPasswordEnter = false;
    TextView tv_pwd, tv_input_pwd;
    RadioGroup rg_RadioGroup;
    DBHelper dbHelper = new DBHelper(App.getContext());
    SQLiteDatabase db = dbHelper.getWritableDatabase();
    int failedCount;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_password);
        mPwdGestureView = (PwdGestureView) findViewById(R.id.pwd_view);

        tv_pwd = (TextView) findViewById(R.id.tv_pwd);
        tv_input_pwd = (TextView) findViewById(R.id.tv_input_pwd);
        rg_RadioGroup = (RadioGroup) findViewById(R.id.rg_RadioGroup);

        try {
            mPwdGestureView.setOldPwd(PrefsHelper.readPrefString(App.getContext(), App.PREF_PASSWORD));
            failedCount = 0;
        } catch (Exception e) {
            e.printStackTrace();
        }
        tv_pwd.setText("Введите пароль");
//        mPwdGestureView.setOldPwd("012543");
        mPwdGestureView.setIsDrawLine(true);

        mPwdGestureView.startWork(new PwdGestureView.GetPwd() {
            @Override
            public void onGetPwd(String pwd) {
                if (PrefsHelper.readPrefBool(App.getContext(), App.PREF_CHANGE_PASSWORD)) {
                    if (pwd == "true") {
                        PrefsHelper.writePrefBool(App.getContext(), App.PREF_CHANGE_PASSWORD, false);
                        PrefsHelper.writePrefString(App.getContext(), App.PREF_PASSWORD, null);
                        PrefsHelper.writePrefBool(App.getContext(), App.PREF_PASSWORD_ACTIVE, false);
                        mPwdGestureView.setOldPwd(null);
                        tv_pwd.setText("Введите пароль");
                    } else
                        tv_pwd.setText("Введите пароль верно");
                } else if (pwd == "true")
                    onBackPressed();
                else if (isFirstPasswordEnter) {
                    if (pwd.equals(firstPassword)) {
                        PrefsHelper.writePrefString(App.getContext(), App.PREF_PASSWORD, pwd);
                        PrefsHelper.writePrefBool(App.getContext(), App.PREF_PASSWORD_ACTIVE, true);
                        onBackPressed();
                    } else {
                        firstPassword = "";
                        isFirstPasswordEnter = false;
                        tv_pwd.setText("Пароль не верен, попробуйте еще раз");
                    }

                } else if (!PrefsHelper.readPrefBool(App.getContext(), App.PREF_PASSWORD_ACTIVE)) {
                    firstPassword = pwd;
                    isFirstPasswordEnter = true;
                    tv_pwd.setText("Повторите пароль");
                } else if (PrefsHelper.readPrefBool(App.getContext(), App.PREF_DELETE_AFTER_10_ATTEMPT)) {
                    failedCount++;
                    tv_pwd.setText(Integer.toString(failedCount) + "Неправильный попыток");
                    if (failedCount == 10)
                        db.delete(dbHelper.TABLE_WORDS, null, null);
                } else
                    tv_pwd.setText("Введите правильный пароль");
            }
        });


    }
}
