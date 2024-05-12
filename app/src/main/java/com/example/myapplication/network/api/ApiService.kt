package com.example.myapplication.network.api

import com.example.myapplication.network.bean.SearchReplyBean
import io.reactivex.rxjava3.core.Observable
import retrofit2.http.GET
import retrofit2.http.Path

interface ApiService {

    interface ApiService {
        @GET("api/{param}/news/latest")
        fun getZhihuData(@Path("param") param: Int): Observable<SearchReplyBean>
    }
}