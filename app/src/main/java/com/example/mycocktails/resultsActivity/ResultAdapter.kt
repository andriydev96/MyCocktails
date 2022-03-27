package com.example.mycocktails.resultsActivity

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.example.mycocktails.Database.CocktailFullData
import com.example.mycocktails.R

class ResultAdapter(val cocktailDataList : ArrayList<CocktailFullData>):RecyclerView.Adapter<ResultAdapter.ViewHolder>() {

    private lateinit var itemListener : onItemClickListener

    interface onItemClickListener{
        fun onItemClick(position: Int)
    }

    fun setOnItemClickListener(listener: onItemClickListener){
        itemListener = listener
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_drink, parent, false)
        return ViewHolder(view, itemListener)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        var ingredientString = ""
        for (i in 0 until cocktailDataList[position].cocktailIngredients.size){
            if (i == 0)
                ingredientString = cocktailDataList[position].cocktailIngredients[i].ingredient
            else
                ingredientString += ", " + cocktailDataList[position].cocktailIngredients[i].ingredient
        }

        holder.textViewDrinkName.text = cocktailDataList[position].cocktail.name
        holder.textViewDrinkCategory.text = cocktailDataList[position].cocktail.category
        holder.textViewDrinkAlcohol.text = cocktailDataList[position].cocktail.alcohol
        holder.textViewDrinkIngredients.text = ingredientString
    }

    override fun getItemCount(): Int = cocktailDataList.size

    class ViewHolder(view: View, listener: onItemClickListener):RecyclerView.ViewHolder(view){
        val textViewDrinkName : TextView = view.findViewById(R.id.textViewDrinkName)
        val textViewDrinkCategory : TextView = view.findViewById(R.id.textViewDrinkCategory)
        val textViewDrinkAlcohol : TextView = view.findViewById(R.id.textViewDrinkAlcohol)
        val textViewDrinkIngredients : TextView = view.findViewById(R.id.textViewDrinkIngredients)
        init {
            view.setOnClickListener {
                listener.onItemClick(adapterPosition)
            }
        }
    }
}