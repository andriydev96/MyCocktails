package com.example.mycocktails.Database

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = [Category::class, Ingredient::class, Cocktail::class, CocktailIngredients::class], version = 1)
abstract class CocktailDB : RoomDatabase() {
    abstract fun categoryDao() : CategoryDAO
    abstract fun ingredientDao() : IngredientDAO
    abstract fun cocktailDao() : CocktailDAO
    abstract fun cocktailIngredientsDao() : CocktailIngredientsDAO
}