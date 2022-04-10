package com.example.mycocktails.searchActivity

import android.os.Bundle
import android.widget.Button
import com.example.mycocktails.Database.Category
import com.example.mycocktails.Database.Ingredient

interface SearchView {
    fun showCategories(list: ArrayList<Category>)
    fun showIngredients(list: ArrayList<Ingredient>)
    fun showError(message: String)
    fun manageButton(button: Button, boolean: Boolean)
    fun showInterface(visible: Boolean)
}