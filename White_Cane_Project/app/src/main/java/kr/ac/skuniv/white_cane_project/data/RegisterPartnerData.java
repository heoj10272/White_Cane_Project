package kr.ac.skuniv.white_cane_project.data;

import com.google.gson.annotations.SerializedName;

public class RegisterPartnerData {

    @SerializedName("userEmail")
    String userEmail;

    @SerializedName("partnerEmail")
    String partnerEmail;

    @SerializedName("userType")
    String userType;

    public RegisterPartnerData(String userEmail, String partnerEmail, String userType){
        this.userEmail = userEmail;
        this.partnerEmail  = partnerEmail;
        this.userType = userType;
    }
}
