package com.example.mycocktails.Database

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity
data class Category(
        @PrimaryKey
        val name: String
) {
    override fun toString(): String {
        return name
    }
}

@Entity
data class Ingredient(
        @PrimaryKey
        val name: String
) {
    override fun toString(): String {
        return name
    }
}

@Entity(
    indices = [Index(value = ["category"])],
    foreignKeys = [ForeignKey(
        entity = Category::class,
        parentColumns = ["name"],
        childColumns = ["category"]
    )]
)
data class Cocktail(
        @PrimaryKey
        val id: Int,
        val name: String,
        val category: String,
        val alcohol: String,
        val glass: String,
        val instructions: String,
        val grade: Int = 0,
        val image: String
)

@Entity(
        indices = [Index(value = ["cocktailId", "ingredient"])],
        foreignKeys = [
            ForeignKey(
                    entity = Cocktail::class,
                    parentColumns = ["id"],
                    childColumns = ["cocktailId"]
        ),
            ForeignKey(
                    entity = Ingredient::class,
                    parentColumns = ["name"],
                    childColumns = ["ingredient"]
            )]
)
data class CocktailIngredients(
        @PrimaryKey
        val cocktailId: Int,
        val ingredient: String,
        val measure: String
)