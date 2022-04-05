package com.example.mycocktails.Database

import android.os.Parcel
import android.os.Parcelable
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
        var grade: Int = 0,
        val image: String
) : Parcelable {
    constructor(parcel: Parcel) : this(
            parcel.readInt(),
            parcel.readString()!!,
            parcel.readString()!!,
            parcel.readString()!!,
            parcel.readString()!!,
            parcel.readString()!!,
            parcel.readInt(),
            parcel.readString()!!) {
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeInt(id)
        parcel.writeString(name)
        parcel.writeString(category)
        parcel.writeString(alcohol)
        parcel.writeString(glass)
        parcel.writeString(instructions)
        parcel.writeInt(grade)
        parcel.writeString(image)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<Cocktail> {
        override fun createFromParcel(parcel: Parcel): Cocktail {
            return Cocktail(parcel)
        }

        override fun newArray(size: Int): Array<Cocktail?> {
            return arrayOfNulls(size)
        }
    }
}

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

data class CocktailFullData(
        val cocktail : Cocktail,
        val cocktailIngredients : ArrayList<CocktailIngredients>
)