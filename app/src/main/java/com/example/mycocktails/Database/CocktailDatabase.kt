package com.example.mycocktails.Database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [Category::class, Ingredient::class, Cocktail::class, CocktailIngredients::class], version = 1)
abstract class CocktailDB : RoomDatabase() {
    abstract fun cocktailDao() : CocktailDAO
}

class CocktailDatabase private constructor(context: Context){
    companion object: CocktailDatabase.SingletonHolder<CocktailDatabase, Context>(::CocktailDatabase)

    open class SingletonHolder<out T, in A>(private val constructor: (A) -> T) {
        @Volatile
        private var instance: T? = null
        fun getInstance(arg: A): T =
                instance ?: synchronized(this) {
                    instance ?: constructor(arg).also { instance = it}
                }
    }

    val database = Room.databaseBuilder(
            context,
            CocktailDB::class.java,
            "CocktailDB"
    ).build()
}