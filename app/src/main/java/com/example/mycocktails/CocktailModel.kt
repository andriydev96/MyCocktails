package com.example.mycocktails

import android.content.Context
import android.graphics.Bitmap
import android.util.Log
import androidx.room.Database
import androidx.room.Room
import com.android.volley.Response
import com.example.mycocktails.Database.*
import kotlinx.coroutines.*

class CocktailModel(context: Context) {
    private val network = Network.getInstance(context)
    private val database = CocktailDatabase.getInstance(context).database

    fun getCategories(listener: Response.Listener<List<Category>>, errorListener: Response.ErrorListener){
        GlobalScope.launch(Dispatchers.Main) {
            val categories = withContext(Dispatchers.IO){
                database.cocktailDao().getCategories()
            }
            if (categories.isEmpty()){
                network.getCategories({
                    GlobalScope.launch {
                        database.cocktailDao().insertCategory(it as ArrayList<Category>)
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
                database.cocktailDao().getIngredients()
            }
            if (ingredients.isEmpty()){
                network.getIngredients({
                    GlobalScope.launch {
                        database.cocktailDao().insertIngredient(it as ArrayList<Ingredient>)
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

    fun getDrinksByCategory(listener: Response.Listener<List<Int>>, errorListener: Response.ErrorListener, category: String){
        network.getDrinksByCategory(listener, errorListener, category)
    }

    fun getDrinksByIngredient(listener: Response.Listener<List<Int>>, errorListener: Response.ErrorListener, ingredient: String){
        network.getDrinksByIngredient(listener, errorListener, ingredient)
    }

    fun getDrinkFullData(listener: Response.Listener<CocktailFullData>, errorListener: Response.ErrorListener, drinkId : Int){
        network.getDrinkFullData(listener, errorListener, drinkId)
    }

    fun getImage(listener: Response.Listener<Bitmap>, errorListener: Response.ErrorListener, url : String){
        network.getImage(listener, errorListener, url)
    }
}