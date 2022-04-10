package com.example.mycocktails.dataActivity

import com.example.mycocktails.CocktailModel
import com.example.mycocktails.Database.Cocktail
import com.example.mycocktails.Database.CocktailIngredients
import com.example.mycocktails.R

class DataPresenter(private val view: DataActivity, private val model: CocktailModel) {
    init {
        view.buttonAdd.isEnabled = false
        retrieveCocktailData(view.cocktail, view.ingredients)
    }

    //Formats the cocktail data and passes it to the view
    private fun retrieveCocktailData(cocktail: Cocktail, ingredientList: ArrayList<CocktailIngredients>){
        val name: String = cocktail.name
        val alcohol: String = cocktail.alcohol
        val servedIn: String = cocktail.glass
        val category: String = cocktail.category
        val score: String = "${cocktail.grade} out of 10"
        val preparation: String = cocktail.instructions
        var ingredients: String = ""
        for (i in 0 until ingredientList.size){
            ingredients += if (i == 0){
                "${ingredientList[i].measure} ${ingredientList[i].ingredient}"
            } else
                ", ${ingredientList[i].measure} ${ingredientList[i].ingredient}"
        }
        ingredients = ingredients.replace("\\s+".toRegex(), " ")
        view.displayData(name, alcohol, servedIn, category, score, preparation, ingredients)
        model.getImage({ view.imageView.setImageBitmap(it)}, {view.imageView.setImageResource(R.drawable.no_image)}, cocktail.image)
    }

    //Changes the grade of the cocktail and enables the Add button
    fun gradeCocktail(grade: Int){
        view.cocktail.grade = grade
        view.textViewScore.text = "$grade out of 10"
        view.buttonAdd.isEnabled = true
    }

    //Stores the cocktail in the local database
    fun saveCocktailToDB(cocktail: Cocktail, ingredientList: ArrayList<CocktailIngredients>){
        model.insertCocktail(cocktail, ingredientList)
    }
}