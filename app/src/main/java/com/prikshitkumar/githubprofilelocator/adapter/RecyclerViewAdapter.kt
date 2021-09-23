package com.prikshitkumar.githubprofilelocator.adapter

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.prikshitkumar.githubprofilelocator.R
import com.prikshitkumar.githubprofilelocator.model.RecyclerViewData

class RecyclerViewAdapter(val context: Context, val repoList: ArrayList<RecyclerViewData>) : RecyclerView.Adapter<RecyclerViewAdapter.RepoViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RepoViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.items_ui, parent, false)
        return RepoViewHolder(view)
    }

    override fun onBindViewHolder(holder: RepoViewHolder, position: Int) {
        val repo = repoList[position]

        holder.repoName.text = repo.repoTitle
        holder.repoDescription.text = repo.repoDescription

        holder.repoName.setOnClickListener {
            val intent = Intent(Intent.ACTION_VIEW)
            intent.data = Uri.parse(repo.repoURL)
            context.startActivity(intent)
        }
    }

    override fun getItemCount(): Int {
        return repoList.size
    }


    class RepoViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val repoName: TextView = view.findViewById(R.id.id_repoName)
        val repoDescription: TextView = view.findViewById(R.id.id_repoDescription)
    }
}