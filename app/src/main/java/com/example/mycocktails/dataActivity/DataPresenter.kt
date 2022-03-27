package com.example.mycocktails.dataActivity

import com.example.mycocktails.CocktailModel
import com.example.mycocktails.Database.Cocktail
import com.example.mycocktails.Database.CocktailIngredients
import com.example.mycocktails.R

class DataPresenter(private val view: DataActivity, private val model: CocktailModel) {
    init {
        retrieveCocktailData(view.cocktail, view.ingredients)
    }

    private fun retrieveCocktailData(cocktail: Cocktail, ingredientList: ArrayList<CocktailIngredients>){
        val name: String = cocktail.name
        val alcohol: String = cocktail.alcohol
        val servedIn: String = cocktail.glass
        val category: String = cocktail.category
        val score: String = "${cocktail.grade} out of 10"
        val preparation: String = cocktail.instructions
        var ingredients: String = ""
        for (i in 0 until ingredientList.size){
            if (i == 0){
                ingredients += "${ingredientList[i].measure} ${ingredientList[i].ingredient}"
            } else
                ingredients += ", ${ingredientList[i].measure} ${ingredientList[i].ingredient}"
        }
        view.displayData(name, alcohol, servedIn, category, score, preparation, ingredients)
        model.getImage({ view.imageView.setImageBitmap(it)}, {view.imageView.setImageResource(R.drawable.no_image)}, cocktail.image)
    }
}