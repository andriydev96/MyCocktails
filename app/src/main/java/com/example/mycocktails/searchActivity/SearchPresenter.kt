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

    //Gets the list of categories and passes it to the search activity
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

    //Gets the list of ingredients and passes it to the search activity
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

    //If both lists have been successfully recovered, tells the view to activate the interface
    private fun checkLists(){
        if (gotCategories && gotIngredients) {
            view.showInterface(true) }
    }

    //Saves chosen category, erases possible input from ingredients AutoCompleteTextView and enables/disables the respective buttons
    fun setChosenCategory(category: Category) {
        chosenCtgr = category
        view.manageButton(view.buttonSearchByCategory, true)
        view.manageButton(view.buttonSearchByIngredient, false)
        view.autoCompleteTextView.setText("")
    }

    //Saves chosen ingredient and enables/disables the respective buttons
    fun setChosenIngredient(ingredient: Ingredient) {
        chosenIngr = ingredient
        view.manageButton(view.buttonSearchByCategory, false)
        view.manageButton(view.buttonSearchByIngredient, true)
    }
}