package com.example.mycocktails.searchActivity

import com.example.mycocktails.CocktailModel
import com.example.mycocktails.Database.Category
import com.example.mycocktails.Database.Ingredient

class SearchPresenter(private val view: SearchActivity, private val model: CocktailModel) {
    init {
        getCategoryList()
        getIngredientList()
        //model.getDrinksByCategory({},{view.showError(it.toString())},"Shake")
    }

    fun getCategoryList(){
        model.getCategories(
                {view.showCategories(it as ArrayList<Category>)},
                {view.showError(it.toString())}
        )
    }

    fun getIngredientList(){
        model.getIngredients(
                {view.showIngredients(it as ArrayList<Ingredient>)},
                {view.showError(it.toString())}
        )
    }
}