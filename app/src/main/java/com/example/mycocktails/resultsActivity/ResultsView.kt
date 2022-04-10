package com.example.mycocktails.resultsActivity

import com.example.mycocktails.Database.CocktailFullData

interface ResultsView {
    fun displayDrinks(cocktailDataList: ArrayList<CocktailFullData>)
    fun showProgressBar(show: Boolean)
    fun showError(message: String)
    fun launchDataActivity(cocktail: CocktailFullData)
}