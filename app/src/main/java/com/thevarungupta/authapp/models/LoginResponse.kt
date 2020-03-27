package com.thevarungupta.authapp.models

data class LoginResponse (
    var token: String,
    var user: User
)