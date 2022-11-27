package kr.ac.skuniv.white_cane_project.data;

import com.google.gson.annotations.SerializedName;

public class LoginData {
    @SerializedName("userEmail")
    String userEmail;

    @SerializedName("userPwd")
    String userPwd;

    public LoginData(String userEmail, String userPwd) {
        this.userEmail = userEmail;
        this.userPwd = userPwd;
    }
}
