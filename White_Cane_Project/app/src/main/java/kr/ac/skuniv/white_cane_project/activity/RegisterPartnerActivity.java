package kr.ac.skuniv.white_cane_project.activity;

import android.content.Intent;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import kr.ac.skuniv.white_cane_project.R;
import kr.ac.skuniv.white_cane_project.data.RegisterPartnerData;
import kr.ac.skuniv.white_cane_project.data.RegisterPartnerResponse;
import kr.ac.skuniv.white_cane_project.network.RetrofitClient;
import kr.ac.skuniv.white_cane_project.network.ServiceApi;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RegisterPartnerActivity extends AppCompatActivity {

    private TextView mTextViewName;
    private TextView mTextViewPartner;
    private EditText mEditText;
    private Button mButton;
    private ServiceApi service;


    String userType2;
    String name2;
    String email2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_partner);
        ActionBar a = getSupportActionBar();
        a.hide();
        mTextViewName = (TextView) findViewById(R.id.textViewName);
        mTextViewPartner = (TextView) findViewById(R.id.textViewPartner);
        mEditText = (EditText) findViewById(R.id.EditTextEmail);
        mButton = (Button) findViewById(R.id.registerButton);

        service = RetrofitClient.getClient().create(ServiceApi.class);

        Intent intent = getIntent();
        String name = intent.getStringExtra("name");
        String email = intent.getStringExtra("email"); //로그인한 유저의 이메일
        String userType = intent.getStringExtra("userType");

        userType2 = userType;
        name2 = name;
        email2 = email;
        mTextViewName.setText(name + "님, 이제 거의 다 왔어요 !");
        if(userType.equals("Impairment")){
            mTextViewPartner.setText("보호자의 이메일을 입력해주세요.");
        }
        else if (userType.equals("Protector")){
            mTextViewPartner.setText("피보호자의 이메일을 입력해주세요");
        }

        mButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                attemptLogin();
            }
        });

    }
    private void attemptLogin() {
        mEditText.setError(null);

        String partnerEmail = mEditText.getText().toString();

        boolean cancel = false;
        View focusView = null;

        // 이메일의 유효성 검사
        if (partnerEmail.isEmpty()) {
            mEditText.setError("이메일을 입력해주세요.");
            focusView = mEditText;
            cancel = true;
        } else if (!isEmailValid(partnerEmail)) {
            mEditText.setError("@를 포함한 유효한 이메일을 입력해주세요.");
            focusView = mEditText;
            cancel = true;
        }

        if (cancel) {
            focusView.requestFocus();
        }
        else {
            startRegisterPartner(new RegisterPartnerData(email2,mEditText.getText().toString(),userType2));
        }
    }
    private boolean isEmailValid(String email) {
        return email.contains("@");
    }

    private void startRegisterPartner(RegisterPartnerData data) {
        service.userRegisterPartner(data).enqueue(new Callback<RegisterPartnerResponse>() {
            @Override
            public void onResponse(Call<RegisterPartnerResponse> call, Response<RegisterPartnerResponse> response) {
                RegisterPartnerResponse result = response.body();
                Toast.makeText(RegisterPartnerActivity.this, result.getMessage(), Toast.LENGTH_SHORT).show();
                if(result.getCode() == 200) {
                    Intent intent = new Intent(getApplicationContext(), BluetoothActivity.class);
                    intent.putExtra("email", email2); //로그인한 유저의 이메일
                    intent.putExtra("name", name2); //로그인한 유저의 이름
                    intent.putExtra("userType",userType2); //로그인한 유저의 타입
                    intent.putExtra("partnerEmail",mEditText.getText().toString()); //로그인한 유저의 파트너 이메일
                    startActivity(intent);
                }
            }

            @Override
            public void onFailure(Call<RegisterPartnerResponse> call, Throwable t) {
                Toast.makeText(RegisterPartnerActivity.this, "로그인 에러 발생", Toast.LENGTH_SHORT).show();
                Log.e("로그인 에러 발생", t.getMessage());
            }
        });
    }

}