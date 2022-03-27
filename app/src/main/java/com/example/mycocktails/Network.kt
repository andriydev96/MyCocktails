package com.example.mycocktails

import android.content.Context
import android.graphics.Bitmap
import android.os.Parcel
import android.os.Parcelable
import android.util.Log
import android.widget.ImageView
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.VolleyError
import com.android.volley.toolbox.ImageRequest
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import com.example.mycocktails.Database.*
import org.json.JSONArray
import org.json.JSONObject

class Network private constructor(context: Context) {
    companion object : SingletonHolder<Network, Context>(::Network)

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
                    categoryList.sortBy { it.name }
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
                    val ingredientArray: JSONArray = response.getJSONArray("drinks")
                    for (i in 0 until ingredientArray.length()) {
                        val ingredientObject: JSONObject = ingredientArray[i] as JSONObject
                        val ingredientName = ingredientObject.getString("strIngredient1")
                        ingredientList.add(Ingredient(ingredientName))
                    }
                    ingredientList.sortBy { it.name }
                    listener.onResponse(ingredientList)
                },
                {
                    errorListener.onErrorResponse(VolleyError("Error: Ingredient list couldn't be retrieved from the internet."))
                }
        )
        queue.add(request)
    }

    fun getDrinksByCategory(listener: Response.Listener<List<Int>>, errorListener: Response.ErrorListener, categoryName: String) {
        val drinkList = ArrayList<Int>()
        val request = JsonObjectRequest(
                Request.Method.GET,
                "https://www.thecocktaildb.com/api/json/v1/1/filter.php?c=$categoryName",
                null,
                { response ->
                    val drinkArray: JSONArray = response.getJSONArray("drinks")
                    for (i in 0 until drinkArray.length()) {
                        val drinkObject: JSONObject = drinkArray[i] as JSONObject
                        val drinkName = drinkObject.getString("idDrink")
                        drinkList.add(drinkName.toInt())
                    }
                    drinkList.sort()
                    listener.onResponse(drinkList)
                    Log.d("APP-ACTION", "Successfully got $categoryName drink id list from the internet.\n$drinkList")
                },
                {
                    errorListener.onErrorResponse(VolleyError("Error: $categoryName drink id list couldn't be retrieved from the internet."))
                }
        )
        queue.add(request)
    }

    fun getDrinksByIngredient(listener: Response.Listener<List<Int>>, errorListener: Response.ErrorListener, ingredientName: String) {
        val drinkList = ArrayList<Int>()
        val request = JsonObjectRequest(
                Request.Method.GET,
                "https://www.thecocktaildb.com/api/json/v1/1/filter.php?i=$ingredientName",
                null,
                { response ->
                    val drinkArray: JSONArray = response.getJSONArray("drinks")
                    for (i in 0 until drinkArray.length()) {
                        val drinkObject: JSONObject = drinkArray[i] as JSONObject
                        val drinkName = drinkObject.getString("idDrink")
                        drinkList.add(drinkName.toInt())
                    }
                    drinkList.sort()
                    listener.onResponse(drinkList)
                    Log.d("APP-ACTION", "Successfully got drink id list containing $ingredientName from the internet.\n$drinkList")
                },
                {
                    errorListener.onErrorResponse(VolleyError("Error: Drink id list containing $ingredientName couldn't be retrieved from the internet."))
                }
        )
        queue.add(request)
    }

    fun getDrinkFullData(listener: Response.Listener<CocktailFullData>, errorListener: Response.ErrorListener, drinkId: Int) {
        val request = JsonObjectRequest(
                Request.Method.GET,
                "https://www.thecocktaildb.com/api/json/v1/1/lookup.php?i=$drinkId",
                null,
                { response ->
                    val drinkArray: JSONArray = response.getJSONArray("drinks")
                    val drinkObject: JSONObject = drinkArray[0] as JSONObject
                    val name = drinkObject.getString("strDrink")
                    val category = drinkObject.getString("strCategory")
                    val alcohol = drinkObject.getString("strAlcoholic")
                    val glass = drinkObject.getString("strGlass")
                    val instructions = drinkObject.getString("strInstructions")
                    val grade = 0
                    val image = drinkObject.getString("strDrinkThumb")

                    val cocktail = Cocktail(drinkId, name, category, alcohol, glass, instructions, grade, image)
                    val cocktailIngredients = ArrayList<CocktailIngredients>()

                    for (i in 1..15){
                        val ingredient = drinkObject.getString("strIngredient$i")
                        val measure = if (drinkObject.isNull("strMeasure$i")) "" else drinkObject.getString("strMeasure$i")
                        if (ingredient == "null") break
                        else cocktailIngredients.add(CocktailIngredients(drinkId, ingredient, measure))
                    }

                    val cocktailData = CocktailFullData(cocktail, cocktailIngredients)
                    listener.onResponse(cocktailData)
                },
                {
                    errorListener.onErrorResponse(VolleyError("Error: Something went wrong."))
                }
        )
        queue.add(request)
    }

    fun getImage(listener: Response.Listener<Bitmap>, errorListener: Response.ErrorListener, url: String){
        val request = ImageRequest(
            url,
            {
                listener.onResponse(it)
                Log.d("APP-ACTION", "Image recovered successfully.")
            },
            0,
            0,
            ImageView.ScaleType.CENTER,
            Bitmap.Config.ARGB_8888,
            {
                errorListener.onErrorResponse(VolleyError("Could not retrieve image from the internet."))
            }
        )
        queue.add(request)
    }
}