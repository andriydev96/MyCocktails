package com.example.mycocktails.Database

data class CocktailFullData(
        val cocktail : Cocktail,
        val cocktailIngredients : ArrayList<CocktailIngredients>
)