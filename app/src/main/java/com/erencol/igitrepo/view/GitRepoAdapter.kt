package com.erencol.igitrepo.view

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.erencol.igitrepo.R
import com.erencol.igitrepo.model.GitRepo
import com.erencol.igitrepo.utils.Constants
import com.erencol.igitrepo.utils.loadImage

class GitRepoAdapter(var gitRepoList: ArrayList<GitRepo>):
    RecyclerView.Adapter<GitRepoAdapter.ViewHolder>() {

    fun update(newRepos: List<GitRepo>){
        gitRepoList.clear()
        gitRepoList.addAll(newRepos)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view= LayoutInflater.from(parent.context).inflate(R.layout.git_repo_item, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return gitRepoList.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
       holder.bind(gitRepoList.get(position))
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val repoName = itemView.findViewById<TextView>(R.id.tvRepoName)
        private val repoImage = itemView.findViewById<ImageView>(R.id.repoImage)
        private val starImage = itemView.findViewById<ImageView>(R.id.starImage)

        fun bind(gitRepo: GitRepo) {
            repoName.text = gitRepo.repoName
            gitRepo.owner?.userAvatar?.let { repoImage.loadImage(it) }
            if(gitRepo.isStarred)
                starImage.visibility = View.VISIBLE
            else
                starImage.visibility = View.INVISIBLE


            itemView.setOnClickListener {
                val detailIntent = Intent(itemView.context,RepoDetail::class.java)
                val bundle = Bundle()
                bundle.putParcelable(Constants.GIT_REPO_ITEM,gitRepo)
                detailIntent.putExtra(Constants.GIT_REPO_BUNDLE,bundle)
                itemView.context.startActivity(detailIntent)
            }
        }
    }
}