package com.example.mycocktails.resultsActivity

import android.util.Log
import com.example.mycocktails.CocktailModel
import com.example.mycocktails.Database.CocktailFullData
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class ResultsPresenter(private val view: ResultsActivity, private val model: CocktailModel) {
    init {
        doSearch()
    }

    var cocktailDataList = ArrayList<CocktailFullData>()

    fun doSearch(){
        if (view.searchType == "Category") {
            doCategorySearch(view.criterion)
        } else {
            doIngredientSearch(view.criterion)
        }
    }

    fun doCategorySearch(category: String){
        if (view.localSearch){
            model.getDrinksByCategoryDB(
                    {
                        view.showProgressBar(false)
                        cocktailDataList = it
                        view.displayDrinks(cocktailDataList)
                        Log.d("APP-ACTION", "Got drink data from $category category from the database. -> $it")
                    }, {view.showError(it.toString())}, view.criterion)
        } else {
            model.getDrinksByCategoryInet(
                    {
                        cocktailDataList.clear()
                        var dataRetrieved = 0
                        val totalData = it.size
                        for (id in it) {
                            model.getDrinkFullDataInet(
                                    { it ->
                                        cocktailDataList.add(it)
                                        dataRetrieved++
                                        if (dataRetrieved == totalData) {
                                            cocktailDataList.sortBy { it.cocktail.name }
                                            view.showProgressBar(false)
                                            view.displayDrinks(cocktailDataList)
                                            Log.d("APP-ACTION", "Got drink data from $category category from the internet.")
                                        }
                                    },
                                    { view.showError(it.toString()) }, id)
                        }
                    },
                    {
                        view.showError(it.toString())
                    }, category)
        }
    }

    fun doIngredientSearch(ingredient: String){
        if (view.localSearch){
            model.getDrinksByIngredientDB(
                    {
                        view.showProgressBar(false)
                        cocktailDataList = it
                        view.displayDrinks(cocktailDataList)
                        Log.d("APP-ACTION", "Got drink data with $ingredient from the database. -> $it")
                    }, {view.showError(it.toString())}, view.criterion)
        } else {
            model.getDrinksByIngredientInet(
                    {
                        cocktailDataList.clear()
                        var dataRetrieved = 0
                        val totalData = it.size
                        for (id in it) {
                            model.getDrinkFullDataInet(
                                    {
                                        cocktailDataList.add(it)
                                        dataRetrieved++
                                        if (dataRetrieved == totalData) {
                                            cocktailDataList.sortBy { it.cocktail.name }
                                            view.showProgressBar(false)
                                            view.displayDrinks(cocktailDataList)
                                            Log.d("APP-ACTION", "Got drink data containing $ingredient from the internet.")
                                        }
                                    },
                                    { view.showError(it.toString()) }, id)
                        }
                    },
                    {
                        view.showError(it.toString())
                    }, ingredient)
        }
    }

    fun cocktailSelect(position: Int) {
        view.launchDataActivity(cocktailDataList[position])
    }
}