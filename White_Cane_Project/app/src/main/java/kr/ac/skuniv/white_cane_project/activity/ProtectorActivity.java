package kr.ac.skuniv.white_cane_project.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import kr.ac.skuniv.white_cane_project.R;

public class ProtectorActivity extends AppCompatActivity {

    private Button mNowButton;
    private Button mPastButton;
    private TextView mNametextView;

    @Override
    protected void onCreate (@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.protector_main);

        ActionBar a = getSupportActionBar();
        a.hide();

        mNowButton = (Button) findViewById(R.id.Nowbutton);
        mPastButton = (Button) findViewById(R.id.Pastbutton);
        Intent intent = getIntent();
        String email = intent.getStringExtra("email");
        String name = intent.getStringExtra("name");





        mNowButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), ProtectorNowActivity.class);
                startActivity(intent);
            }
        });
        mPastButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), ProtectorPastMoveActivity.class);
                startActivity(intent);
            }
        });
    }
}
