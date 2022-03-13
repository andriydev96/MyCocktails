package com.example.mycocktails

import android.content.Context
import android.util.Log
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.VolleyError
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import com.example.mycocktails.Database.Category
import com.example.mycocktails.Database.Ingredient
import org.json.JSONArray
import org.json.JSONObject

class Network private constructor(context: Context) {
    companion object: SingletonHolder<Network, Context>(::Network)

    open class SingletonHolder<out T, in A>
    (private val constructor: (A) -> T) {
        @Volatile
        private var instance: T? = null
        fun getInstance(arg: A): T =
                instance ?: synchronized(this) {
                    instance ?: constructor(arg).also { instance = it }
                }
    }

    private val queue = Volley.newRequestQueue(context)

    fun getCategories(listener: Response.Listener<List<Category>>, errorListener: Response.ErrorListener) {
        val categoryList = ArrayList<Category>()
        val request = JsonObjectRequest(
                Request.Method.GET,
                "https://www.thecocktaildb.com/api/json/v1/1/list.php?c=list",
                null,
                { response ->
                    val categoryArray: JSONArray = response.getJSONArray("drinks")
                    for (i in 0 until categoryArray.length()) {
                        val categoryObject: JSONObject = categoryArray[i] as JSONObject
                        val categoryName = categoryObject.getString("strCategory")
                        categoryList.add(Category(categoryName))
                    }
                    listener.onResponse(categoryList)
                },
                {
                    errorListener.onErrorResponse(VolleyError("Error: Category list couldn't be retrieved from the internet."))
                }
        )
        queue.add(request)
    }

    fun getIngredients(listener: Response.Listener<List<Ingredient>>, errorListener: Response.ErrorListener) {
        val ingredientList = ArrayList<Ingredient>()
        val request = JsonObjectRequest(
                Request.Method.GET,
                "https://www.thecocktaildb.com/api/json/v1/1/list.php?i=list",
                null,
                { response ->
                    val ingredientArray : JSONArray = response.getJSONArray("drinks")
                    for (i in 0 until ingredientArray.length()){
                        val ingredientObject : JSONObject = ingredientArray[i] as JSONObject
                        val ingredientName = ingredientObject.getString("strIngredient1")
                        ingredientList.add(Ingredient(ingredientName))
                    }
                    listener.onResponse(ingredientList)
                }
                ,
                {
                    errorListener.onErrorResponse(VolleyError("Error: Ingredient list couldn't be retrieved from the internet."))
                }
        )
        queue.add(request)
    }

    fun getDrinksByCategory(listener: Response.Listener<List<String>>, errorListener: Response.ErrorListener, categoryName: String){
        val drinkList = ArrayList<String>()
        Log.d("APP-ACTION", "Buscando: http://www.thecocktaildb.com/api/json/v1/1/filter.php?c=$categoryName")
        val request = JsonObjectRequest(
                Request.Method.GET,
                "https://www.thecocktaildb.com/api/json/v1/1/filter.php?c=Shake",
                null,
                { response ->
                    drinkList.add("drinkName")
                    listener.onResponse(drinkList)
                    listener.onResponse(drinkList)
                    val drinkArray : JSONArray = response.getJSONArray("drinks")
                    Log.d("APP-ACTION", "Got: $drinkArray")
                    for (i in 0 until drinkArray.length()){
                        val drinkObject : JSONObject = drinkArray[i] as JSONObject
                        val drinkName = drinkObject.getString("strDrink")
                        Log.d("APP-ACTION", "Got: $drinkName")
                        drinkList.add(drinkName)
                    }
                    drinkList.sort()
                    listener.onResponse(drinkList)
                    Log.d("APP-ACTION", "Successfully got drink list from the internet.\n$drinkList")
                }
                ,
                {
                    errorListener.onErrorResponse(VolleyError("Error: Drink list couldn't be retrieved from the internet."))
                }
        )
        queue.add(request)
    }
}