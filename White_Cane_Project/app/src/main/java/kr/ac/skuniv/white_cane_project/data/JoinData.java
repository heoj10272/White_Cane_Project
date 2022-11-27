package kr.ac.skuniv.white_cane_project.data;

import com.google.gson.annotations.SerializedName;

public class JoinData {
    @SerializedName("userName")
    private String userName;

    @SerializedName("userEmail")
    private String userEmail;

    @SerializedName("userPwd")
    private String userPwd;

    @SerializedName("userType")
    private String userType;


    public JoinData(String userName, String userEmail, String userPwd, String userType) {
        this.userName = userName;
        this.userEmail = userEmail;
        this.userPwd = userPwd;
        this.userType = userType;
    }
}
