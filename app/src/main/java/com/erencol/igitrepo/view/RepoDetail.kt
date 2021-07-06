package com.erencol.igitrepo.view

import android.content.Context
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import com.erencol.igitrepo.R
import com.erencol.igitrepo.databinding.ActivityRepoDetailBinding
import com.erencol.igitrepo.model.GitRepo
import com.erencol.igitrepo.utils.Constants
import com.erencol.igitrepo.utils.loadImage

class RepoDetail : AppCompatActivity() {
    private lateinit var gitRepo : GitRepo
    private lateinit var menuItem: Menu
    private lateinit var binding: ActivityRepoDetailBinding
    private lateinit var sharedPrefences: SharedPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this,R.layout.activity_repo_detail)
        binding.lifecycleOwner = this
        sharedPrefences = this?.getSharedPreferences(Constants.FAVORITE_KEY, Context.MODE_PRIVATE)
        initlayout()
    }

    fun initlayout(){
        supportActionBar?.setHomeButtonEnabled(true)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        checkBundle()
    }

    fun addToStarred(){
        val editor = sharedPrefences.edit()
        editor.putBoolean(gitRepo.id.toString(),true)
        editor.commit()
    }

    fun removeStarred(){
        val editor = sharedPrefences.edit()
        editor.putBoolean(gitRepo.id.toString(),false)
        editor.commit()
    }

    fun checkBundle(){
        val bundle = intent.getBundleExtra(Constants.GIT_REPO_BUNDLE)
        gitRepo = bundle?.getParcelable<GitRepo>(Constants.GIT_REPO_ITEM)!!
        fillValues()
    }

    fun fillValues(){
        gitRepo?.owner?.userAvatar?.let { binding.profileImage.loadImage(it) }
        binding.ownerNameTextview.text = gitRepo?.owner?.login
        binding.starCountTextview.text = gitRepo?.repoStarCount.toString()
        binding.openIssuesCountTextview.text = gitRepo?.openIssueCount.toString()
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
       when(item.itemId) {
           android.R.id.home ->
               onBackPressed()
           R.id.star ->
               starOnclick()
       }
        return true
    }

    fun starOnclick() {
        if(gitRepo.isStarred) {
            menuItem.getItem(0).setIcon(getDrawable(R.drawable.ic_star_empty))
            removeStarred()
        } else {
            menuItem.getItem(0).setIcon(getDrawable(R.drawable.ic_star))
            addToStarred()
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.detail_menu, menu)
        menuItem = menu!!
        if(gitRepo.isStarred)
            menuItem.getItem(0).setIcon(getDrawable(R.drawable.ic_star))
        else
            menuItem.getItem(0).setIcon(getDrawable(R.drawable.ic_star_empty))
        return true
    }
}