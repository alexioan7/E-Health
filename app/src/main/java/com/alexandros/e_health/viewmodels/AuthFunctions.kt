package com.alexandros.e_health.viewmodels

interface AuthFunctions {

    fun OnStarted()
    fun OnSuccess()
    fun OnFailure(message: String)
}