package com.repo.contactbook

import retrofit2.Call
import retrofit2.http.GET

interface ContactsApi {
    @GET("getMyList")
    fun getContacts(): Call<Contacts>
}