package com.example.mycocktails.searchActivity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.widget.*
import android.widget.Toast.LENGTH_SHORT
import androidx.core.widget.addTextChangedListener
import com.example.mycocktails.CocktailModel
import com.example.mycocktails.Database.Category
import com.example.mycocktails.Database.Ingredient
import com.example.mycocktails.R

class SearchActivity : AppCompatActivity() {
    lateinit var spinner: Spinner
    lateinit var autoCompleteTextView: AutoCompleteTextView
    lateinit var buttonSearchByCategory: Button
    lateinit var buttonSearchByIngredient : Button
    lateinit var radioButtonLocalSearch: RadioButton
    lateinit var radioButtonInetSearch: RadioButton
    lateinit var presenter: SearchPresenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_search)

        spinner = findViewById(R.id.spinner)
        autoCompleteTextView = findViewById(R.id.autoCompleteTextView)
        buttonSearchByCategory = findViewById(R.id.buttonSearchByCategory)
        buttonSearchByIngredient = findViewById(R.id.buttonSearchByIngredient)
        radioButtonLocalSearch = findViewById(R.id.radioButtonLocalSearch)
        radioButtonInetSearch = findViewById(R.id.radioButtonInetSearch)
        presenter = SearchPresenter(this, CocktailModel(this))

        //TODO: Hide the ingredient button ONLY if the text input is equal to a value from the list
        autoCompleteTextView.addTextChangedListener(){
            if (autoCompleteTextView.text.toString() == ""){
                manageButton(buttonSearchByCategory, true)
                manageButton(buttonSearchByIngredient, false)
            } else {
                manageButton(buttonSearchByCategory, false)
                manageButton(buttonSearchByIngredient, true)
            }
        }
    }

    fun showCategories(list: ArrayList<Category>){
        val arrayAdapter = ArrayAdapter(this, android.R.layout.simple_dropdown_item_1line, list)
        spinner.apply {
            adapter = arrayAdapter
            onItemSelectedListener = object : AdapterView.OnItemSelectedListener{
                override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                    //TODO
                }

                override fun onNothingSelected(parent: AdapterView<*>?) {}
            }
        }
    }

    fun showIngredients(list: ArrayList<Ingredient>){
        val arrayAdapter = ArrayAdapter(this,android.R.layout.simple_dropdown_item_1line, list)
        autoCompleteTextView.apply {
            setAdapter(arrayAdapter)
            addTextChangedListener(object: TextWatcher{
                override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
                override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}
                override fun afterTextChanged(s: Editable?) {
                    //TODO
                }
            })
        }
    }

    fun showError(message: String){
        Toast.makeText(this, message, LENGTH_SHORT).show()
    }


    fun manageButton(button: Button, boolean: Boolean){
        button.isEnabled = boolean
    }
}