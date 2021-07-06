package com.erencol.igitrepo.view

import android.app.SearchManager
import android.content.Context
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.View
import androidx.appcompat.widget.SearchView
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.erencol.igitrepo.R
import com.erencol.igitrepo.databinding.ActivityMainBinding
import com.erencol.igitrepo.model.GitRepo
import com.erencol.igitrepo.utils.Constants
import com.erencol.igitrepo.viewmodel.GitRepoViewModel

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var gitRepoViewModel: GitRepoViewModel
    private lateinit var sharedPrefences: SharedPreferences
    private var gitRepoList= ArrayList<GitRepo>()
    private var repoListAdapter = GitRepoAdapter(arrayListOf())

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this,R.layout.activity_main)
        sharedPrefences = this?.getSharedPreferences(Constants.FAVORITE_KEY, Context.MODE_PRIVATE)
        binding.lifecycleOwner = this
        gitRepoViewModel = ViewModelProvider(this)[GitRepoViewModel::class.java]
        initLayout()
    }

    fun initLayout(){

        binding.recyclerView.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = repoListAdapter
        }
        gitRepoViewModel.gitRepoList.observe(this, Observer { gitRepos ->
            //Check is any repo starred on local
            for (gitRepo in gitRepos) {
                gitRepo.isStarred = sharedPrefences.getBoolean(gitRepo.id.toString(),false)
            }
            gitRepoList.addAll(gitRepos)
            //update adapter.
            gitRepos?.let { repoListAdapter.update(gitRepos) }
        })

        gitRepoViewModel.inProgress.observe(this, Observer { inProgress ->
            if(inProgress)
                binding.progressCircular.visibility = View.VISIBLE
            else
                binding.progressCircular.visibility = View.GONE
        })

        gitRepoViewModel.error.observe(this, Observer { error ->
            if(error.equals("")) {
                binding.messageTextview.visibility = View.GONE
            } else {
                binding.messageTextview.text = error
                binding.messageTextview.visibility = View.VISIBLE
            }
        })

        //makeRequest("erencol")
    }

    fun makeRequest(query: String?){
        query?.let { gitRepoViewModel.getGitRepos(it) }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.main_menu, menu)

        val manager = getSystemService(Context.SEARCH_SERVICE) as SearchManager
        val searchItem = menu.findItem(R.id.search)
        val searchView = searchItem?.actionView as SearchView

        searchView.setSearchableInfo(manager.getSearchableInfo(componentName))

        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener{
            override fun onQueryTextSubmit(query: String?): Boolean {
                searchView.clearFocus()
                searchView.setQuery(query,false)
                searchItem.collapseActionView()
                makeRequest(query)
                return true
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                //on text changed.
                return false
            }
        })

        return true
    }

    override fun onResume() {
        super.onResume()
        //update ui
        if(gitRepoList!= null) {
            for (gitRepo in gitRepoList) {
                gitRepo.isStarred = sharedPrefences.getBoolean(gitRepo.id.toString(), false)
            }
            repoListAdapter.update(gitRepoList)
        }
    }


}