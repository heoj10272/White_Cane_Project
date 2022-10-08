package kr.ac.skuniv.for_impairment_project.data;

import com.google.gson.annotations.SerializedName;

public class JoinResponse {
    @SerializedName("code")
    private int code;

    @SerializedName("message")
    private String message;

    public int getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }
}
