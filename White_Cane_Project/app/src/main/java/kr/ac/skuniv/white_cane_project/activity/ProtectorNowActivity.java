package kr.ac.skuniv.white_cane_project.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;

import kr.ac.skuniv.white_cane_project.R;

public class ProtectorNowActivity extends AppCompatActivity {

    @Override
    protected void onCreate (@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.protector_now);

        ActionBar a = getSupportActionBar();
        a.hide();
    }
}