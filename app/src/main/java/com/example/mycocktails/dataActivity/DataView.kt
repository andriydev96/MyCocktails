package com.example.mycocktails.dataActivity

interface DataView {
    fun displayData(name: String, alcohol: String, servedIn: String, category: String, score: String, preparation: String, ingredients: String)
    fun onGradeAvailable(grade: Int)
}