package kr.ac.skuniv.white_cane_project.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import kr.ac.skuniv.white_cane_project.data.JoinData;
import kr.ac.skuniv.white_cane_project.data.JoinResponse;
import kr.ac.skuniv.white_cane_project.network.RetrofitClient;
import kr.ac.skuniv.white_cane_project.network.ServiceApi;

import kr.ac.skuniv.white_cane_project.R;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class JoinActivity extends AppCompatActivity {
    private AutoCompleteTextView mEmailView;
    private EditText mPasswordView;
    private EditText mNameView;
    private Button mJoinButton;
    private ProgressBar mProgressView;
    private ServiceApi service;
    public String type = "";

    private RadioGroup UserTypeGroup; // 라디오버튼 그룹
    private RadioButton btn_Protector, btn_Impairment; // 라디오버튼

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_join);
        ActionBar a = getSupportActionBar();
        a.hide();

        mEmailView = (AutoCompleteTextView) findViewById(R.id.join_email);
        mPasswordView = (EditText) findViewById(R.id.join_password);
        mNameView = (EditText) findViewById(R.id.join_name);
        mJoinButton = (Button) findViewById(R.id.join_button);
        mProgressView = (ProgressBar) findViewById(R.id.join_progress);
        service = RetrofitClient.getClient().create(ServiceApi.class);
        mJoinButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                attemptJoin();
            }
        });

        UserTypeGroup = (RadioGroup) findViewById(R.id.UserTypeGroup); // 라디오버튼 그룹

        btn_Protector = (RadioButton) findViewById(R.id.btn_Protector); // 보호자 버튼
        btn_Impairment = (RadioButton) findViewById(R.id.btn_Impairment); // 장애인 버튼

        btn_Protector.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                type = "Protector";
            }
        });

        btn_Impairment.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                type = "Impairment";
            }
        });
    }

//    RadioGroup.OnCheckedChangeListener radioGroupButtonChangeListener = new RadioGroup.OnCheckedChangeListener() {
//        @Override public void onCheckedChanged(RadioGroup UserTypeGroup, @IdRes int i) {
//            if(i == R.id.btn_Protector){
//                Toast.makeText(JoinActivity.this, "보호자입니다.", Toast.LENGTH_SHORT).show();
//                usertype = "protector";
//
//            }
//            else if(i == R.id.btn_Impairment){
//                Toast.makeText(JoinActivity.this, "장애인입니다.", Toast.LENGTH_SHORT).show();
//                usertype = "impairment";
//            }
//        }
//    };

    public void attemptJoin() {
        mNameView.setError(null);
        mEmailView.setError(null);
        mPasswordView.setError(null);

        String name = mNameView.getText().toString();
        String email = mEmailView.getText().toString();
        String password = mPasswordView.getText().toString();
        String usertype = type;

        boolean cancel = false;
        View focusView = null;

        // 패스워드의 유효성 검사
        if (password.isEmpty()) {
            mEmailView.setError("비밀번호를 입력해주세요.");
            focusView = mEmailView;
            cancel = true;
        } else if (!isPasswordValid(password)) {
            mPasswordView.setError("6자 이상의 비밀번호를 입력해주세요.");
            focusView = mPasswordView;
            cancel = true;
        }

        // 이메일의 유효성 검사
        if (email.isEmpty()) {
            mEmailView.setError("이메일을 입력해주세요.");
            focusView = mEmailView;
            cancel = true;
        } else if (!isEmailValid(email)) {
            mEmailView.setError("@를 포함한 유효한 이메일을 입력해주세요.");
            focusView = mEmailView;
            cancel = true;
        }

        // 이름의 유효성 검사
        if (name.isEmpty()) {
            mNameView.setError("이름을 입력해주세요.");
            focusView = mNameView;
            cancel = true;
        }

        if (cancel) {
            focusView.requestFocus();
        } else {
            startJoin(new JoinData(name, email, password, usertype));
            showProgress(true);
        }
    }

    private void startJoin(JoinData data) {
        service.userJoin(data).enqueue(new Callback<JoinResponse>() {
            @Override
            public void onResponse(Call<JoinResponse> call, Response<JoinResponse> response) {
                JoinResponse result = response.body();
                Toast.makeText(JoinActivity.this, result.getMessage(), Toast.LENGTH_SHORT).show();
                showProgress(false);

                if (result.getCode() == 200) {
                    finish();
                }
            }

            @Override
            public void onFailure(Call<JoinResponse> call, Throwable t) {
                Toast.makeText(JoinActivity.this, "회원가입 에러 발생", Toast.LENGTH_SHORT).show();
                Log.e("회원가입 에러 발생", t.getMessage());
                showProgress(false);
            }
        });
    }

    private boolean isEmailValid(String email) {
        return email.contains("@");
    }

    private boolean isPasswordValid(String password) {
        return password.length() >= 6;
    }

    private void showProgress(boolean show) {
        mProgressView.setVisibility(show ? View.VISIBLE : View.GONE);
    }
}