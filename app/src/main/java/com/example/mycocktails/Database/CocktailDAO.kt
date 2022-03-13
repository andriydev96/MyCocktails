package com.example.mycocktails.Database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface CategoryDAO {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertCategory(categories: ArrayList<Category>)

    @Query("SELECT * FROM category ORDER BY name")
    fun getCategories() : List<Category>
}

@Dao
interface IngredientDAO {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertIngredient(ingredients: ArrayList<Ingredient>)

    @Query("SELECT * FROM ingredient ORDER BY name")
    fun getIngredients() : List<Ingredient>
}

@Dao
interface CocktailDAO {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertCocktail(cocktail: Cocktail)
}

@Dao
interface CocktailIngredientsDAO {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertCocktailIngredients(cocktailIngredients: CocktailIngredients)
}