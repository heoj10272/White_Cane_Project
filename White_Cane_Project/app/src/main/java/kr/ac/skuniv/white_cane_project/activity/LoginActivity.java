package kr.ac.skuniv.white_cane_project.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import kr.ac.skuniv.white_cane_project.R;
import kr.ac.skuniv.white_cane_project.data.LoginData;
import kr.ac.skuniv.white_cane_project.data.LoginResponse;
import kr.ac.skuniv.white_cane_project.network.RetrofitClient;
import kr.ac.skuniv.white_cane_project.network.ServiceApi;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginActivity extends AppCompatActivity {
    private AutoCompleteTextView mEmailView;
    private EditText mPasswordView;
    private Button mEmailLoginButton;
    private Button mJoinButton;
    private ProgressBar mProgressView;
    private ServiceApi service;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        ActionBar a = getSupportActionBar();
        a.hide();

        mEmailView = (AutoCompleteTextView) findViewById(R.id.login_email);
        mPasswordView = (EditText) findViewById(R.id.login_password);
        mEmailLoginButton = (Button) findViewById(R.id.login_button);
        mJoinButton = (Button) findViewById(R.id.join_button);
        mProgressView = (ProgressBar) findViewById(R.id.login_progress);

        service = RetrofitClient.getClient().create(ServiceApi.class);

        mEmailLoginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                attemptLogin();
            }
        });
        mJoinButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), JoinActivity.class);
                startActivity(intent);
            }
        });
    }

    private void attemptLogin() {
        mEmailView.setError(null);
        mPasswordView.setError(null);

        String email = mEmailView.getText().toString();
        String password = mPasswordView.getText().toString();

        boolean cancel = false;
        View focusView = null;

        // ??????????????? ????????? ??????
        if (password.isEmpty()) {
            mEmailView.setError("??????????????? ??????????????????.");
            focusView = mEmailView;
            cancel = true;
        } else if (!isPasswordValid(password)) {
            mPasswordView.setError("6??? ????????? ??????????????? ??????????????????.");
            focusView = mPasswordView;
            cancel = true;
        }

        // ???????????? ????????? ??????
        if (email.isEmpty()) {
            mEmailView.setError("???????????? ??????????????????.");
            focusView = mEmailView;
            cancel = true;
        } else if (!isEmailValid(email)) {
            mEmailView.setError("@??? ????????? ????????? ???????????? ??????????????????.");
            focusView = mEmailView;
            cancel = true;
        }

        if (cancel) {
            focusView.requestFocus();
        } else {
            startLogin(new LoginData(email, password));
            showProgress(true);
        }
    }

    private void startLogin(LoginData data) {
        service.userLogin(data).enqueue(new Callback<LoginResponse>() {
            @Override
            public void onResponse(Call<LoginResponse> call, Response<LoginResponse> response) {
                LoginResponse result = response.body();
                Toast.makeText(LoginActivity.this, result.getMessage(), Toast.LENGTH_SHORT).show();
                if(result.getCode() == 200) { //???????????? ???????????????
                    if(result.getUserType().equals("Protector")){ //????????????
                        Intent intent = new Intent(getApplicationContext(), ProtectorActivity.class);
                        intent.putExtra("email", mEmailView.getText().toString()); //???????????? ????????? ?????????
                        intent.putExtra("name", result.getUserName()); //???????????? ????????? ??????
                        intent.putExtra("userType",result.getUserType()); //???????????? ????????? ??????
                        intent.putExtra("partnerEmail",result.getPartnerEmail()); //???????????? ????????? ????????? ?????????
                        startActivity(intent);
                        showProgress(false);
                    }
                    else {//???????????????
                        if (result.getPartnerEmail() == null) { //????????? ???????????? ?????????
                            Intent intent2 = new Intent(getApplicationContext(), RegisterPartnerActivity.class);
                            intent2.putExtra("email", mEmailView.getText().toString());
                            intent2.putExtra("name", result.getUserName());
                            intent2.putExtra("userType", result.getUserType());
                            startActivity(intent2);
                            showProgress(false);

                        } else { //????????? ???????????? ?????????
                            Intent intent = new Intent(getApplicationContext(), BluetoothActivity.class);
                            intent.putExtra("email", mEmailView.getText().toString()); //???????????? ????????? ?????????
                            intent.putExtra("name", result.getUserName()); //???????????? ????????? ??????
                            intent.putExtra("userType", result.getUserType()); //???????????? ????????? ??????
                            intent.putExtra("partnerEmail", result.getPartnerEmail()); //???????????? ????????? ????????? ?????????
                            startActivity(intent);
                            showProgress(false);
                        }
                    }
                }
                showProgress(false);
            }

            @Override
            public void onFailure(Call<LoginResponse> call, Throwable t) {
                Toast.makeText(LoginActivity.this, "????????? ?????? ??????", Toast.LENGTH_SHORT).show();
                Log.e("????????? ?????? ??????", t.getMessage());
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
