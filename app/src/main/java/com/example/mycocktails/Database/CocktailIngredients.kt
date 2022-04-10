package com.example.mycocktails.Database

import android.os.Parcel
import android.os.Parcelable
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index

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
            )],
        primaryKeys = ["cocktailId", "ingredient", "measure"]
)
data class CocktailIngredients(
        val cocktailId: Int,
        val ingredient: String,
        val measure: String
) : Parcelable {
    constructor(parcel: Parcel) : this(
            parcel.readInt(),
            parcel.readString()!!,
            parcel.readString()!!) {
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeInt(cocktailId)
        parcel.writeString(ingredient)
        parcel.writeString(measure)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<CocktailIngredients> {
        override fun createFromParcel(parcel: Parcel): CocktailIngredients {
            return CocktailIngredients(parcel)
        }

        override fun newArray(size: Int): Array<CocktailIngredients?> {
            return arrayOfNulls(size)
        }
    }
}