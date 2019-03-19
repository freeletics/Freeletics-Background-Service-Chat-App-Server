package com.checkify

import okhttp3.MediaType
import okhttp3.MultipartBody
import okhttp3.OkHttpClient
import okhttp3.RequestBody
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.Part
import java.io.File
import java.util.UUID

fun main() {

    val client = OkHttpClient()

    val retrofit = Retrofit.Builder()
        .baseUrl("http://0.0.0.0:8080")
        .build()

    val api = retrofit.create(UploadApi::class.java)

    val uuid = UUID.randomUUID().toString()
    val file = File("/Users/hannes/Desktop/thomas.png")

    val fileReqBody = RequestBody.create(MediaType.parse("image/*"), file)
    // Create MultipartBody.Part using file request-body,file name and part name
    val part = MultipartBody.Part.createFormData("upload", file.getName(), fileReqBody)
    //Create request body with text description and text media type
    val description = RequestBody.create(MediaType.parse("text/plain"), uuid)

    val response = api.upload(part, description).execute()
    println(response)
}

interface UploadApi {

    @POST("images")
    @Multipart
    fun upload(@Part file: MultipartBody.Part, @Part("uuid") requestBody: RequestBody): Call<Unit>
}