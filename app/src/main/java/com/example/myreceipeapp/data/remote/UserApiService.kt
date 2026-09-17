package com.example.myreceipeapp.data.remote

import com.example.myreceipeapp.data.remote.dto.User.User
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get

class UserApiService(private val client: HttpClient) {

}