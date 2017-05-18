package com.calintat.explorer.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.calintat.explorer.App;
import com.calintat.explorer.R;
import com.calintat.explorer.helper.PrefsHelper;

public class PassActivity extends AppCompatActivity {
    Toolbar toolbar;
    CheckBox onOffPassword;
    CheckBox deleteAll;
    LinearLayout llChangePass;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pass);

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Пароль");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        llChangePass = (LinearLayout) findViewById(R.id.llChangePass);
        onOffPassword = (CheckBox) findViewById(R.id.onOffPassword);
        if (!PrefsHelper.readPrefBool(App.getContext(), App.PREF_CHANGE_PASSWORD))
            llChangePass.setEnabled(false);
        deleteAll = (CheckBox) findViewById(R.id.deleteAll);
        if (PrefsHelper.readPrefBool(App.getContext(), App.PREF_PASSWORD_ACTIVE)) {
            onOffPassword.setChecked(true);
        }
        if (PrefsHelper.readPrefBool(App.getContext(), App.PREF_DELETE_AFTER_10_ATTEMPT)) {
            deleteAll.setChecked(true);
        }
        onOffPassword.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                if (onOffPassword.isChecked()) {
                    PrefsHelper.writePrefString(App.getContext(), App.PREF_PASSWORD, null);
                    PrefsHelper.writePrefBool(App.getContext(), App.PREF_PASSWORD_ACTIVE, false);
                    Intent intent = new Intent(App.getContext(), PasswordActivity.class);
                    startActivity(intent);
                    Toast.makeText(PassActivity.this, "Включил пароль", Toast.LENGTH_SHORT).show();
                } else {
                    PrefsHelper.writePrefString(App.getContext(), App.PREF_PASSWORD, null);
                    PrefsHelper.writePrefBool(App.getContext(), App.PREF_PASSWORD_ACTIVE, false);
                    Toast.makeText(PassActivity.this, "Выключил пароль", Toast.LENGTH_SHORT).show();
                    PrefsHelper.writePrefBool(App.getContext(), App.PREF_CHANGE_PASSWORD, false);
                }
            }
        });

        deleteAll.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                if (deleteAll.isChecked()) {
                    PrefsHelper.writePrefBool(App.getContext(), App.PREF_DELETE_AFTER_10_ATTEMPT, true);
                    Toast.makeText(PassActivity.this, "Включил удаление", Toast.LENGTH_SHORT).show();
                } else {
                    PrefsHelper.writePrefBool(App.getContext(), App.PREF_DELETE_AFTER_10_ATTEMPT, false);
                    Toast.makeText(PassActivity.this, "Выключил удаление", Toast.LENGTH_SHORT).show();
                }
            }
        });

        findViewById(R.id.llChangePass).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PrefsHelper.writePrefBool(App.getContext(), App.PREF_CHANGE_PASSWORD, true);
                Intent intent = new Intent(App.getContext(), PasswordActivity.class);
                startActivity(intent);
                Toast.makeText(PassActivity.this, "Сменить пароль", Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }
}
