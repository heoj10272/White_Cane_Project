package kr.ac.skuniv.white_cane_project.network;

import kr.ac.skuniv.white_cane_project.data.JoinData;
import kr.ac.skuniv.white_cane_project.data.JoinResponse;
import kr.ac.skuniv.white_cane_project.data.LoginData;
import kr.ac.skuniv.white_cane_project.data.LoginResponse;

import kr.ac.skuniv.white_cane_project.data.RegisterPartnerData;
import kr.ac.skuniv.white_cane_project.data.RegisterPartnerResponse;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface ServiceApi {
    @POST("/user/login")
    Call<LoginResponse> userLogin(@Body LoginData data);

    @POST("/user/join")
    Call<JoinResponse> userJoin(@Body JoinData data);

    @POST("/user/register_partner")
    Call<RegisterPartnerResponse> userRegisterPartner(@Body RegisterPartnerData data);
}