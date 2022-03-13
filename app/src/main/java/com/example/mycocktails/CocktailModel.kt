package com.example.mycocktails

import android.content.Context
import android.util.Log
import androidx.room.Room
import com.android.volley.Response
import com.example.mycocktails.Database.Category
import com.example.mycocktails.Database.CocktailDB
import com.example.mycocktails.Database.Ingredient
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class CocktailModel(context: Context) {
    private val network = Network.getInstance(context)
    private val database = Room.databaseBuilder(
            context,
            CocktailDB::class.java,
            "CocktailDB"
    ).build()

    fun getCategories(listener: Response.Listener<List<Category>>, errorListener: Response.ErrorListener){
        GlobalScope.launch(Dispatchers.Main) {
            val categories = withContext(Dispatchers.IO){
                database.categoryDao().getCategories()
            }
            if (categories.isEmpty()){
                network.getCategories({
                    GlobalScope.launch {
                        database.categoryDao().insertCategory(it as ArrayList<Category>)
                    }
                    Log.d("APP-ACTION", "Successfully got category list from the internet.\n$it")
                    listener.onResponse(it)
                }, {
                    errorListener.onErrorResponse(it)
                })
            } else {
                listener.onResponse(categories)
                Log.d("APP-ACTION", "Successfully got category list from local database.\n$categories")
            }
        }
    }

    fun getIngredients(listener: Response.Listener<List<Ingredient>>, errorListener: Response.ErrorListener){
        GlobalScope.launch(Dispatchers.Main) {
            val ingredients = withContext(Dispatchers.IO){
                database.ingredientDao().getIngredients()
            }
            if (ingredients.isEmpty()){
                network.getIngredients({
                    GlobalScope.launch {
                        database.ingredientDao().insertIngredient(it as ArrayList<Ingredient>)
                    }
                    listener.onResponse(it)
                    Log.d("APP-ACTION", "Successfully got ingredient list from the internet.\n$it")
                }, {
                    errorListener.onErrorResponse(it)
                })
            } else {
                listener.onResponse(ingredients)
                Log.d("APP-ACTION", "Successfully got category list from local database.\n$ingredients")
            }
        }
    }

    fun getDrinksByCategory(listener: Response.Listener<List<String>>, errorListener: Response.ErrorListener, category: String){
        network.getDrinksByCategory(listener, errorListener, category)
    }
}