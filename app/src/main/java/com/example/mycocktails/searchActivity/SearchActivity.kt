package com.example.mycocktails.searchActivity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.widget.*
import android.widget.Toast.LENGTH_SHORT
import androidx.core.view.isVisible
import androidx.core.widget.addTextChangedListener
import com.example.mycocktails.CocktailModel
import com.example.mycocktails.Database.Category
import com.example.mycocktails.Database.CocktailFullData
import com.example.mycocktails.Database.Ingredient
import com.example.mycocktails.R
import com.example.mycocktails.resultsActivity.ResultsActivity

class SearchActivity : AppCompatActivity() {
    lateinit var textViewCategory: TextView
    lateinit var textViewIngredient: TextView
    lateinit var spinner: Spinner
    lateinit var autoCompleteTextView: AutoCompleteTextView
    lateinit var buttonSearchByCategory: Button
    lateinit var buttonSearchByIngredient : Button
    lateinit var radioButtonLocalSearch: RadioButton
    lateinit var radioButtonInetSearch: RadioButton
    lateinit var progressBar: ProgressBar
    lateinit var presenter: SearchPresenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_search)

        textViewCategory = findViewById(R.id.textViewCategory)
        textViewIngredient = findViewById(R.id.textViewIngredient)
        spinner = findViewById(R.id.spinner)
        autoCompleteTextView = findViewById(R.id.autoCompleteTextView)
        buttonSearchByCategory = findViewById(R.id.buttonSearchByCategory)
        buttonSearchByIngredient = findViewById(R.id.buttonSearchByIngredient)
        radioButtonLocalSearch = findViewById(R.id.radioButtonLocalSearch)
        radioButtonInetSearch = findViewById(R.id.radioButtonInetSearch)
        progressBar = findViewById(R.id.progressBar)
        presenter = SearchPresenter(this, CocktailModel(this))

        buttonSearchByCategory.setOnClickListener {
            launchResultsActivity(presenter.chosenCtgr, radioButtonLocalSearch.isChecked)
        }

        buttonSearchByIngredient.setOnClickListener {
            launchResultsActivity(presenter.chosenIngr, radioButtonLocalSearch.isChecked)
        }
    }

    override fun onResume() {
        super.onResume()
        showInterface(false)
        presenter.getCategoryList()
        presenter.getIngredientList()
    }

    fun showCategories(list: ArrayList<Category>){
        val arrayAdapter = ArrayAdapter(this, android.R.layout.simple_dropdown_item_1line, list)
        spinner.apply {
            adapter = arrayAdapter
            onItemSelectedListener = object : AdapterView.OnItemSelectedListener{
                override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                    val category : Category = spinner.getItemAtPosition(position) as Category
                    presenter.setChosenCategory(category)
                }

                override fun onNothingSelected(parent: AdapterView<*>?) {}
            }.also { spinner.onItemSelectedListener = it }
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
                    val ingredient = s.toString()
                    list.binarySearch { it.name.compareTo(ingredient) }.let {
                        if (it >= 0)
                            presenter.setChosenIngredient(list[it])
                    }
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

    fun showInterface(visible: Boolean){
        textViewCategory.isVisible = visible
        textViewIngredient.isVisible = visible
        spinner.isVisible = visible
        autoCompleteTextView.isVisible = visible
        buttonSearchByCategory.isVisible = visible
        buttonSearchByIngredient.isVisible = visible
        radioButtonLocalSearch.isEnabled = visible
        radioButtonInetSearch.isEnabled = visible
        progressBar.isVisible = !visible
    }

    fun launchResultsActivity(category: Category, localSearch: Boolean){
        val intent = Intent(this, ResultsActivity::class.java).also {
            it.putExtra("CRITERION", category.name)
            it.putExtra("SEARCH_TYPE", "Category")
            it.putExtra("LOCAL_SEARCH", localSearch)
        }
        startActivity(intent)
    }

    fun launchResultsActivity(ingredient: Ingredient, localSearch: Boolean){
        val intent = Intent(this, ResultsActivity::class.java).also {
            it.putExtra("CRITERION", ingredient.name)
            it.putExtra("SEARCH_TYPE", "Ingredient")
            it.putExtra("LOCAL_SEARCH", localSearch)
        }
        startActivity(intent)
    }
}