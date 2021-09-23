package com.prikshitkumar.githubprofilelocator.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import com.prikshitkumar.githubprofilelocator.R
import org.json.JSONException

class MainActivity : AppCompatActivity() {
    lateinit var username: EditText
    lateinit var search: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        username = findViewById(R.id.id_username)
        search = findViewById(R.id.id_btnSearch)

        val baseURL = "https://api.github.com/users/"
        val queue = Volley.newRequestQueue(this)

        search.setOnClickListener {
            when {
                TextUtils.isEmpty(username.text) -> {
                    username.setError("Required!")
                }
                else -> {
                    val url = baseURL + username.text.toString()
                    val intent = Intent(this@MainActivity, Activity2::class.java)
                    intent.putExtra("profileURL", url)
                    startActivity(intent)
                }
            }
        }
    }
}