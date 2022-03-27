package com.example.mycocktails.Database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface CocktailDAO {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertCategory(categories: ArrayList<Category>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertIngredient(ingredients: ArrayList<Ingredient>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertCocktail(cocktail: Cocktail)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertCocktailIngredients(cocktailIngredients: CocktailIngredients)

    @Query("SELECT * FROM category ORDER BY name")
    fun getCategories() : List<Category>

    @Query("SELECT * FROM ingredient ORDER BY name")
    fun getIngredients() : List<Ingredient>
}
