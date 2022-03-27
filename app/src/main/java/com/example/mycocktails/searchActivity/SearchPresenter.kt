package com.example.mycocktails.searchActivity

import android.util.Log
import com.example.mycocktails.CocktailModel
import com.example.mycocktails.Database.Category
import com.example.mycocktails.Database.CocktailFullData
import com.example.mycocktails.Database.Ingredient

class SearchPresenter(private val view: SearchActivity, private val model: CocktailModel) {
    init {
        view.showInterface(false)
        getCategoryList()
        getIngredientList()
    }
    var gotCategories = false
    var gotIngredients = false
    lateinit var chosenCtgr : Category
    lateinit var chosenIngr : Ingredient

    fun getCategoryList() {
        model.getCategories(
                {
                    view.showCategories(it as ArrayList<Category>)
                    gotCategories = true
                    checkLists()
                },
                {view.showError(it.toString())}
        )
    }

    fun getIngredientList(){
        model.getIngredients(
                {
                    view.showIngredients(it as ArrayList<Ingredient>)
                    gotIngredients = true
                    checkLists()
                },
                {view.showError(it.toString())}
        )
    }

    fun checkLists(){
        if (gotCategories && gotIngredients) {
            view.showInterface(true) }
    }

    fun setChosenCategory(category: Category) {
        chosenCtgr = category
        view.manageButton(view.buttonSearchByCategory, true)
        view.manageButton(view.buttonSearchByIngredient, false)
        view.autoCompleteTextView.setText("")
    }

    fun setChosenIngredient(ingredient: Ingredient) {
        chosenIngr = ingredient
        view.manageButton(view.buttonSearchByCategory, false)
        view.manageButton(view.buttonSearchByIngredient, true)
    }
}