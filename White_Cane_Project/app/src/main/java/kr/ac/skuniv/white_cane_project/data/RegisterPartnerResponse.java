package kr.ac.skuniv.white_cane_project.data;

import com.google.gson.annotations.SerializedName;

public class RegisterPartnerResponse {
    @SerializedName("code")
    private int code;

    @SerializedName("message")
    private String message;

    @SerializedName("userName")
    private String partnerName;

    @SerializedName("userType")
    private String partnerType;

    @SerializedName("partnerPartnerEmail")
    private String partnerPartnerEmail;

    public int getCode() { return code; }

    public String getMessage() { return message; }

    public String getPartnerName() { return partnerName; }

    public String getPartnerType() { return partnerType; }

    public String getPartnerPartnerEmail() { return partnerPartnerEmail; }
}
