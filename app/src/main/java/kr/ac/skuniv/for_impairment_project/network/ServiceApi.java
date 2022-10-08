package kr.ac.skuniv.for_impairment_project.network;

import kr.ac.skuniv.for_impairment_project.data.JoinData;
import kr.ac.skuniv.for_impairment_project.data.JoinResponse;
import kr.ac.skuniv.for_impairment_project.data.LoginData;
import kr.ac.skuniv.for_impairment_project.data.LoginResponse;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface ServiceApi {
    @POST("/user/login")
    Call<LoginResponse> userLogin(@Body LoginData data);

    @POST("/user/join")
    Call<JoinResponse> userJoin(@Body JoinData data);
}