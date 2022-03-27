package com.example.mycocktails.resultsActivity

import android.util.Log
import com.example.mycocktails.CocktailModel
import com.example.mycocktails.Database.CocktailFullData

class ResultsPresenter(private val view: ResultsActivity, private val model: CocktailModel) {
    init {
        doSearch()
    }

    var cocktailDataList = ArrayList<CocktailFullData>()

    fun doSearch(){
        if (view.searchType == "Category"){
            doCategorySearch(view.criterion)
        } else {
            doIngredientSearch(view.criterion)
        }
    }

    fun doCategorySearch(category: String){
        model.getDrinksByCategory(
            {
                cocktailDataList.clear()
                var dataRetrieved = 0
                val totalData = it.size
                for (id in it) {
                    model.getDrinkFullData(
                        {
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

    fun doIngredientSearch(ingredient: String){
        model.getDrinksByIngredient(
            {
                cocktailDataList.clear()
                var dataRetrieved = 0
                val totalData = it.size
                for (id in it) {
                    model.getDrinkFullData(
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

    fun cocktailSelect(position: Int) {
        view.launchDataActivity(cocktailDataList[position])
    }
}