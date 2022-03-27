package com.example.mycocktails.dataActivity

import android.graphics.Bitmap
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.FrameLayout
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.constraintlayout.widget.ConstraintLayout
import com.example.mycocktails.CocktailModel
import com.example.mycocktails.Database.Cocktail
import com.example.mycocktails.Database.CocktailIngredients
import com.example.mycocktails.R

class DataActivity : AppCompatActivity() {
    lateinit var cocktail: Cocktail
    lateinit var ingredients: ArrayList<CocktailIngredients>

    lateinit var textViewCocktailName: TextView
    lateinit var textViewCocktailAlcohol: TextView
    lateinit var textViewServedIn: TextView
    lateinit var textViewCategory: TextView
    lateinit var textViewScore: TextView
    lateinit var textViewPreparation: TextView
    lateinit var textViewIngredients: TextView
    lateinit var imageView: ImageView
    lateinit var presenter : DataPresenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_data)

        cocktail = intent.getParcelableExtra("COCKTAIL")!!
        ingredients = intent.getParcelableArrayListExtra<CocktailIngredients>("INGREDIENTS")!!

        textViewCocktailName = findViewById(R.id.textViewCocktailName)
        textViewCocktailAlcohol = findViewById(R.id.textViewCocktailAlcohol)
        textViewServedIn = findViewById(R.id.textViewCocktailServedIn)
        textViewCategory = findViewById(R.id.textViewCocktailCategory)
        textViewScore = findViewById(R.id.textViewCocktailScore)
        textViewPreparation = findViewById(R.id.textViewCocktailPreparation)
        textViewIngredients = findViewById(R.id.textViewCocktailIngredients)
        imageView = findViewById(R.id.imageView)
        presenter = DataPresenter(this, CocktailModel(this))
    }

    fun displayData(name: String, alcohol: String, servedIn: String, category: String, score: String, preparation: String, ingredients: String){
        textViewCocktailName.text = name
        textViewCocktailAlcohol.text = alcohol
        textViewServedIn.text = servedIn
        textViewCategory.text = category
        textViewScore.text = score
        textViewPreparation.text = preparation
        textViewIngredients.text = ingredients
    }
}