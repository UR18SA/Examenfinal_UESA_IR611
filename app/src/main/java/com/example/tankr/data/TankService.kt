package com.example.tankr.data

import retrofit2.http.GET
import retrofit2.http.Path

// Query String ?
// Path
interface TankService {
    @GET("api/tanks/{id}/dashboard")
    suspend fun getTankInfo(@Path("id") id: String) : TankInfo
}