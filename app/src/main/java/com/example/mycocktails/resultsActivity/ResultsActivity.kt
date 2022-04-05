package com.example.mycocktails.resultsActivity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ProgressBar
import android.widget.TextView
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.mycocktails.CocktailModel
import com.example.mycocktails.Database.Category
import com.example.mycocktails.Database.CocktailFullData
import com.example.mycocktails.R
import com.example.mycocktails.dataActivity.DataActivity

class ResultsActivity : AppCompatActivity() {
    lateinit var progressBar: ProgressBar
    lateinit var recyclerView: RecyclerView
    lateinit var presenter: ResultsPresenter
    lateinit var criterion: String
    lateinit var searchType: String
    var localSearch: Boolean = true

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_results)

        progressBar = findViewById(R.id.progressBar2)
        recyclerView = findViewById(R.id.recyclerView)

        criterion = intent.getStringExtra("CRITERION")!!
        searchType = intent.getStringExtra("SEARCH_TYPE")!!
        localSearch = intent.getBooleanExtra("LOCAL_SEARCH", true)

        presenter = ResultsPresenter(this, CocktailModel(this))

        supportActionBar!!.setTitle("$searchType search: $criterion 🔍")
    }

    fun displayDrinks(cocktailDataList: ArrayList<CocktailFullData>) {
        if (recyclerView.adapter == null) {
            recyclerView.also {
                var adapter = ResultAdapter(cocktailDataList)
                it.adapter = adapter
                it.layoutManager = LinearLayoutManager(this)
                adapter.setOnItemClickListener(object: ResultAdapter.onItemClickListener{
                    override fun onItemClick(position: Int) {
                        presenter.cocktailSelect(position)
                    }
                })
            }
        } else
            recyclerView.adapter?.notifyDataSetChanged()
    }

    fun showProgressBar(show: Boolean){
        progressBar.isVisible = show
    }

    fun showError(message: String){
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }

    fun launchDataActivity(cocktail: CocktailFullData){
        val intent = Intent(this, DataActivity::class.java).also {
            it.putExtra("COCKTAIL", cocktail.cocktail)
            it.putExtra("INGREDIENTS", cocktail.cocktailIngredients)
        }
        startActivity(intent)
    }
}