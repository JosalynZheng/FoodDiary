package com.example.fooddiary

import android.app.Activity
import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.view.View
import android.widget.TextView
import kotlinx.android.synthetic.main.activity_goodbye.*
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.enter_food.*

class Goodbye : AppCompatActivity(){  //, TextButton.OnFragmentInteractionListener {
    /*override fun onFragmentInteraction(name: Editable) {
        //textView.text = "Total Calories          " + name
    }*/
    companion object {
        val EXTRA1 = "cal"
        val EXTRA2 = "food"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.enter_food)
// After users click the submit button on Activity2, put the food items and calories the users entered into Intent()
        close2.setOnClickListener(object: View.OnClickListener {
            override fun onClick(view: View) {
                val cal = Calories.text.toString()
                val food = Food.text.toString()
                val res = Intent()
                res.putExtra(Goodbye.EXTRA1,cal)
                res.putExtra(Goodbye.EXTRA2,food)
                setResult(Activity.RESULT_OK, res)

                finish()


            }
        })


    }
}