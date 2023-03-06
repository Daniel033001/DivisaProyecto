package com.example.divisaproyecto.network;

import com.example.divisaproyecto.Modelo.Moneda;

import retrofit2.Call;
import retrofit2.http.GET;

public interface MonedaApi {
    @GET("v6/7edb8bae3cc6a39fa6f23207/latest/USD")
    Call<Moneda> getMoneda();
}
