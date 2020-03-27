package com.thevarungupta.authapp.app

class Endpoints {

    companion object{

        private const val URL_LOGIN = "login"
        private const val URL_REGISTER = "register"

        fun getRegisterUrl(): String{
            return Config.BASE_URL + URL_REGISTER
        }

        fun getLoginUrl(): String{
            return Config.BASE_URL+ URL_LOGIN
        }

    }

}