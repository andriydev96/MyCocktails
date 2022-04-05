package com.example.mycocktails.dataActivity

import android.app.Activity
import android.app.AlertDialog
import android.app.Dialog
import android.os.Bundle
import android.view.ContextThemeWrapper
import android.view.View
import android.widget.RatingBar
import androidx.fragment.app.DialogFragment
import com.example.mycocktails.R

open class DataRateDialogFragment: DialogFragment() {
    interface GradeListener {
        fun onGradeAvailable(grade: Int)
    }

    private lateinit var ratingBar: RatingBar

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val activity: Activity = activity ?: throw IllegalStateException("Activity cannot be null")
        val view: View = activity.layoutInflater.inflate(R.layout.dialog_fragment_rate, null)
        with(view) {
            ratingBar = findViewById(R.id.ratingBar)
        }
        return AlertDialog.Builder(ContextThemeWrapper(activity, R.style.CustomAlertDialog)).run {
            setView(view)
            setNegativeButton(android.R.string.cancel, null)
            setPositiveButton(android.R.string.ok) { _, _ -> onDrinkGrade() }
            create()
        }
    }

    private fun onDrinkGrade() {
        val listener = activity as GradeListener?
        val grade: Int = ratingBar.rating.toInt()
        listener!!.onGradeAvailable(grade)
    }
}