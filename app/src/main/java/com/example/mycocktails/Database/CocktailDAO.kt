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
    fun insertIngredient(ingredient: Ingredient)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertCocktail(cocktail: Cocktail)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertCocktailIngredients(cocktailIngredients: CocktailIngredients)

    @Query("SELECT * FROM category ORDER BY name")
    fun getCategories() : List<Category>

    @Query("SELECT * FROM ingredient ORDER BY name")
    fun getIngredients() : List<Ingredient>

    @Query("SELECT * FROM cocktail WHERE category= :category ORDER BY name")
    fun getCocktailsByCategory(category: String) : List<Cocktail>

    @Query("SELECT cocktail.* FROM cocktail LEFT JOIN cocktailingredients ON cocktailIngredients.cocktailId = cocktail.id WHERE cocktailIngredients.ingredient = :ingredient GROUP BY cocktail.id")
    fun getCocktailsByIngredient(ingredient: String) : List<Cocktail>

    @Query("SELECT cocktailIngredients.* FROM cocktail LEFT JOIN cocktailingredients ON cocktail.id = cocktailIngredients.cocktailId WHERE cocktail.name = :cocktail")
    fun getCocktailIngredients(cocktail: String) : List<CocktailIngredients>
}
