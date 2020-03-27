package com.thevarungupta.authapp.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import com.google.gson.GsonBuilder
import com.thevarungupta.authapp.R
import com.thevarungupta.authapp.app.Endpoints
import com.thevarungupta.authapp.helpers.SessionManager
import com.thevarungupta.authapp.models.LoginResponse
import com.thevarungupta.authapp.models.User
import kotlinx.android.synthetic.main.activity_login.*
import org.json.JSONObject

class LoginActivity : AppCompatActivity(), View.OnClickListener {

    lateinit var sessionManager: SessionManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        sessionManager = SessionManager(this)

        init()
    }

    private fun init() {
        button_login.setOnClickListener(this)
        text_view_register.setOnClickListener(this)
    }

    override fun onClick(view: View) {
        when (view.id) {
            R.id.button_login -> {
                progress_bar.visibility = View.VISIBLE
                var email = edit_text_email.text.toString()
                var password = edit_text_password.text.toString()
                var user = User(email = email, password = password)
                login(user)
            }
            R.id.text_view_register -> {
                startActivity(Intent(this, RegisterActivity::class.java))
            }
        }
    }

    private fun login(user: User) {

        var params = JSONObject()
        params.put(User.KEY_EMAIL, user.email)
        params.put(User.KEY_PASSWORD, user.password)


        var requestQueue = Volley.newRequestQueue(this)
        var request = JsonObjectRequest(
            Request.Method.POST,
            Endpoints.getLoginUrl(),
            params,
            Response.Listener { response ->
                progress_bar.visibility = View.GONE
                var gson = GsonBuilder().create()
                var loginResponse = gson.fromJson(response.toString(), LoginResponse::class.java)
                sessionManager.saveUser(loginResponse.user, loginResponse.token)
                startActivity(Intent(this, MainActivity::class.java))
            },
            Response.ErrorListener {
                progress_bar.visibility = View.GONE
                Toast.makeText(applicationContext, it.message, Toast.LENGTH_SHORT).show()
            }
        )
        requestQueue.add(request)
    }
}
