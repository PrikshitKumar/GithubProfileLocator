package com.prikshitkumar.githubprofilelocator.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.JsonArrayRequest
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import com.prikshitkumar.githubprofilelocator.R
import com.prikshitkumar.githubprofilelocator.adapter.RecyclerViewAdapter
import com.prikshitkumar.githubprofilelocator.model.RecyclerViewData
import com.squareup.picasso.Picasso
import org.json.JSONException

class Activity2 : AppCompatActivity() {
    lateinit var image: String
    lateinit var reposURL: String
    var repos = 0
    val repoList = arrayListOf<RecyclerViewData>()

    lateinit var username: TextView
    lateinit var title: TextView
    lateinit var imageView: ImageView
    lateinit var emptyText: TextView
    lateinit var recyclerView: RecyclerView
    lateinit var progressBarLayout: ConstraintLayout
    lateinit var recyclerViewAdapter: RecyclerViewAdapter
    lateinit var layoutManager: RecyclerView.LayoutManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_2)

        initializeTheFields()

        /*repoList.add(
            RecyclerViewData("Repo1", "This is Repository number 1."),
            RecyclerViewData("Repo2", "This is Repository number 2."),
            RecyclerViewData("Repo3", "This is Repository number 3.")
        )

        if(repoList.isNotEmpty()) {
            emptyText.visibility = View.GONE
            recyclerViewAdapter = RecyclerViewAdapter(this@Activity2, repoList)
            recyclerView.adapter = recyclerViewAdapter
            recyclerView.layoutManager = layoutManager
        }*/

        val queue = Volley.newRequestQueue(this@Activity2)
        val url = intent.getStringExtra("profileURL")

        try {
            val jsonObjectRequest = object : JsonObjectRequest(Request.Method.GET, url, null,
            Response.Listener {
                username.text = it.getString("name").toString()
                title.text = it.getString("bio").toString()
                image = it.getString("avatar_url")
                repos = it.getInt("public_repos")
                reposURL = it.getString("repos_url")
                Picasso.get().load(image).error(R.drawable.github_logo).into(imageView)
                userRepositories()
            },
            Response.ErrorListener {
                Toast.makeText(this@Activity2, "User Not Found!", Toast.LENGTH_SHORT).show()
                finish()
            }) {
                override fun getHeaders(): MutableMap<String, String> {
                    val headers = HashMap<String, String>()
                    headers["content-Type"] = "application/JSON"
                    return headers
                }
            }
            queue.add(jsonObjectRequest)
        }
        catch(e: JSONException) {
            Toast.makeText(this@Activity2, "Might be Server Side Problem!", Toast.LENGTH_SHORT).show()
        }
    }

    private fun initializeTheFields() {
        imageView = findViewById(R.id.id_userLogo)
        username = findViewById(R.id.id_name)
        title = findViewById(R.id.id_title)
        emptyText = findViewById(R.id.id_noReposTxt)
        recyclerView = findViewById(R.id.id_recyclerView)
        progressBarLayout = findViewById(R.id.id_progressBarLayout)
        layoutManager = LinearLayoutManager(this@Activity2)
    }

    private fun userRepositories() {
        if(repos != 0) {
            emptyText.visibility = View.GONE
            val repoQueue = Volley.newRequestQueue(this@Activity2)
            try {
                val jsonArrayRequest = object : JsonArrayRequest(Request.Method.GET, reposURL, null,
                    Response.Listener {
                        for(i in 0 until it.length()) {
                            val json = it.getJSONObject(i)
                            val recyclerViewDataObject = RecyclerViewData(
                                json.getString("name"),
                                json.getString("description"),
                                json.getString("html_url")
                            )
                            repoList.add(recyclerViewDataObject)
                        }
                        recyclerViewAdapter = RecyclerViewAdapter(this@Activity2, repoList)
                        recyclerView.adapter = recyclerViewAdapter
                        recyclerView.layoutManager = layoutManager

                        progressBarLayout.visibility = View.GONE
                    },
                    Response.ErrorListener {
                        Toast.makeText(this@Activity2, "Check your Internet Connection!", Toast.LENGTH_SHORT).show()
                    }) {
                    override fun getHeaders(): MutableMap<String, String> {
                        val headers = HashMap<String, String>()
                        headers["content-Type"] = "application/JSON"
                        return headers
                    }
                }
                repoQueue.add(jsonArrayRequest)
            }
            catch(e: JSONException) {
                Toast.makeText(this@Activity2, "Might be Server Side Problem!", Toast.LENGTH_SHORT).show()
            }
        }
        else {
            progressBarLayout.visibility = View.GONE
            emptyText.visibility = View.VISIBLE
        }
    }
}