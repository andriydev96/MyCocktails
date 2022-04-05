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

    fun getDrinksByCategoryInet(listener: Response.Listener<List<Int>>, errorListener: Response.ErrorListener, category: String){
        network.getDrinksByCategory(listener, errorListener, category)
    }

    fun getDrinksByIngredientInet(listener: Response.Listener<List<Int>>, errorListener: Response.ErrorListener, ingredient: String){
        network.getDrinksByIngredient(listener, errorListener, ingredient)
    }

    fun getDrinkFullDataInet(listener: Response.Listener<CocktailFullData>, errorListener: Response.ErrorListener, drinkId : Int){
        network.getDrinkFullData(listener, errorListener, drinkId)
    }

    fun insertCocktail(cocktail: Cocktail, ingredientList: ArrayList<CocktailIngredients>){
        GlobalScope.launch(Dispatchers.Main) {
            withContext(Dispatchers.IO) {
                database.cocktailDao().insertCocktail(cocktail)
            }
            for (ingredient in ingredientList) {
                Log.d("APP-ACTION", "LISTA ENTERA: $ingredientList")
                withContext(Dispatchers.IO) {
                    database.cocktailDao().insertIngredient(Ingredient(ingredient.ingredient))
                    database.cocktailDao().insertCocktailIngredients(ingredient)
                }
            }
        }
    }

    fun getDrinksByCategoryDB(listener: Response.Listener<ArrayList<CocktailFullData>>, errorListener: Response.ErrorListener, category: String){
        GlobalScope.launch(Dispatchers.Main) {
            val cocktailDataList = ArrayList<CocktailFullData>()
            val cocktails = withContext(Dispatchers.IO){
                database.cocktailDao().getCocktailsByCategory(category)
            }
            for (cocktail in cocktails) {
                withContext(Dispatchers.IO) {
                    val ingredients = database.cocktailDao().getCocktailIngredients(cocktail.name) as  ArrayList<CocktailIngredients>
                    cocktailDataList.add(CocktailFullData(cocktail, ingredients))
                }
            }
            listener.onResponse(cocktailDataList)
        }
    }

    fun getDrinksByIngredientDB(listener: Response.Listener<ArrayList<CocktailFullData>>, errorListener: Response.ErrorListener, ingredient: String){
        GlobalScope.launch(Dispatchers.Main) {
            val cocktailDataList = ArrayList<CocktailFullData>()
            val cocktails = withContext(Dispatchers.IO){
                database.cocktailDao().getCocktailsByIngredient(ingredient)
            }
            Log.d("APP-ACTION", "GOT THIS COCKTAILS: $cocktails")
            for (cocktail in cocktails) {
                withContext(Dispatchers.IO) {
                    val ingredients = database.cocktailDao().getCocktailIngredients(cocktail.name) as  ArrayList<CocktailIngredients>
                    cocktailDataList.add(CocktailFullData(cocktail, ingredients))
                }
            }
            listener.onResponse(cocktailDataList)
        }
    }

    fun getImage(listener: Response.Listener<Bitmap>, errorListener: Response.ErrorListener, url : String){
        network.getImage(listener, errorListener, url)
    }
}