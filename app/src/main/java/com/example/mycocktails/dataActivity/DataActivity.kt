package com.example.mycocktails.dataActivity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.ContextThemeWrapper
import android.widget.*
import androidx.appcompat.app.AlertDialog
import com.example.mycocktails.CocktailModel
import com.example.mycocktails.Database.Cocktail
import com.example.mycocktails.Database.CocktailIngredients
import com.example.mycocktails.R

class DataActivity : AppCompatActivity(), DataView, DataRateDialogFragment.GradeListener {
    lateinit var cocktail: Cocktail
    lateinit var ingredients: ArrayList<CocktailIngredients>

    lateinit var textViewCocktailName: TextView
    lateinit var textViewCocktailAlcohol: TextView
    lateinit var textViewServedIn: TextView
    lateinit var textViewCategory: TextView
    lateinit var textViewScore: TextView
    lateinit var buttonRate: Button
    lateinit var buttonAdd: Button
    lateinit var textViewPreparation: TextView
    lateinit var textViewIngredients: TextView
    lateinit var imageView: ImageView
    lateinit var presenter : DataPresenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_data)

        cocktail = intent.getParcelableExtra(COCKTAIL)!!
        ingredients = intent.getParcelableArrayListExtra<CocktailIngredients>(INGREDIENTS)!!

        textViewCocktailName = findViewById(R.id.textViewCocktailName)
        textViewCocktailAlcohol = findViewById(R.id.textViewCocktailAlcohol)
        textViewServedIn = findViewById(R.id.textViewCocktailServedIn)
        textViewCategory = findViewById(R.id.textViewCocktailCategory)
        textViewScore = findViewById(R.id.textViewCocktailScore)
        buttonRate = findViewById(R.id.buttonRate)
        buttonAdd = findViewById(R.id.buttonAdd)
        textViewPreparation = findViewById(R.id.textViewCocktailPreparation)
        textViewIngredients = findViewById(R.id.textViewCocktailIngredients)
        imageView = findViewById(R.id.imageView)
        presenter = DataPresenter(this, CocktailModel(this))

        supportActionBar!!.hide()

        buttonRate.setOnClickListener {
            val dialog = DataRateDialogFragment()
            dialog.show(supportFragmentManager, "DataRateDialogFragment")
        }

        buttonAdd.setOnClickListener {
            val alertDialog: AlertDialog? = this.let {
                val builder = AlertDialog.Builder(ContextThemeWrapper(it, R.style.CustomAlertDialog))
                builder.apply {
                    setTitle("Database")
                    setMessage("Cocktail and ingredients inserted in local database.")
                    setPositiveButton("OK"
                    ) { _, _ ->
                        presenter.saveCocktailToDB(cocktail, ingredients)
                        it.finish()
                    }
                }
                builder.create()
            }
            alertDialog!!.show()
        }
    }

    //Displays the cocktail data
    override fun displayData(name: String, alcohol: String, servedIn: String, category: String, score: String, preparation: String, ingredients: String){
        textViewCocktailName.text = name
        textViewCocktailAlcohol.text = alcohol
        textViewServedIn.text = servedIn
        textViewCategory.text = category
        textViewScore.text = score
        textViewPreparation.text = preparation
        textViewIngredients.text = ingredients
    }

    //Tells the presenter the new grade of the drink
    override fun onGradeAvailable(grade: Int) {
        presenter.gradeCocktail(grade)
    }

    companion object{
        private const val COCKTAIL = "COCKTAIL"
        private const val INGREDIENTS = "INGREDIENTS"
    }
}