package com.example.fooddiary

import android.app.ActionBar
import android.app.Dialog
import android.content.Context
import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.app.AlertDialog
import android.text.Editable
import android.util.Log
import android.view.View
import android.view.ViewGroup
import android.widget.*
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.enter_food.*
import kotlinx.android.synthetic.main.enter_total_cal.*
import android.R.attr.data
import android.app.Activity
import android.content.DialogInterface
import android.graphics.Color
import java.lang.NumberFormatException


data class FoodCal constructor(
    val Food : String,
    val Cal : String
)


class MainActivity : AppCompatActivity(), TextButton.OnFragmentInteractionListener {
    private  var foodList = ArrayList<FoodCal>()
    private  var totalCal = 0
    private  var adapter : FoodAdapter?=null
    private  var recode = 1
    private  var goal = 0
    override fun onFragmentInteraction(name: Editable) {

    }
// Using onActivityResult and startActivityForResult to communicate between MainActivity and Goodbye
    //Using onActivityResult to receive the data from the activity2
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (requestCode == recode) {
            val cal  = data?.getStringExtra(Goodbye.EXTRA1)
            val food = data?.getStringExtra(Goodbye.EXTRA2)
            cal?.let {
                foodList.add(FoodCal(food.toString(), cal))
                try {
                    totalCal += cal.toInt()
                } catch (e: Exception) {//Using AlertDialog to display error message
                    AlertDialog.Builder(this)
                        .setTitle("Error!Please type a number")
                        .show()
                }
// Implement calculation to make the color turns red when calorie count becomes negative
                if (goal - totalCal >= 0) {
                    findViewById<TextView>(R.id.calories).text =  (goal - totalCal).toString()
                    calories.setTextColor(Color.rgb(0,0,0))
                } else {
                    findViewById<TextView>(R.id.calories).text =  (goal - totalCal).toString()

                    calories.setTextColor(Color.rgb(255,0,0))//Make the number red when calories exceed the total calories
                }
                findViewById<TextView>(R.id.totalCal).text = "Total Calories   " + totalCal.toString()
                adapter = FoodAdapter(foodList, this)
                list.adapter = adapter

            }

        }
    }
// In onCreate, using displayDialog function to let users enter the total calories they plan to intake
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        displayDialog(R.layout.enter_total_cal)
//Using startActivityForResult to start the activity2 named Goodbye
        //addFood is the id of the button called Add Food Item. Use setOnClickListener to start communicating to activity2
        addFood.setOnClickListener(object: View.OnClickListener {
            override fun onClick(view: View) {
                val intent = Intent(this@MainActivity, Goodbye::class.java)
                startActivityForResult(intent,recode)

            }
        })
        // Add one button named Change Total Calories to Change the total calories the users plan to intake
        Changecalories.setOnClickListener(object: View.OnClickListener {
            override fun onClick(view: View) {
                displayDialog(R.layout.enter_total_cal)
            }
        })
        // Add the users daily weight
        //weight.setOnClickListener(object:View.OnClickListener{

            //override fun onClick(view: View) {
               // displayDialog(R.layout.enter_total_cal)
            //}
        //})
    }

// Using FoodAdapter and the function getView to make a view list
    inner class FoodAdapter(val list: ArrayList<FoodCal>, val context: Context) : BaseAdapter() {

        override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View? {

            val view: View?
            val vh: ViewHolder
// Make sure if list is null
            if (convertView == null) {
                view = layoutInflater.inflate(R.layout.foodlist, parent, false)
                vh = ViewHolder(view)
                view.tag = vh
            } else {
                view = convertView
                vh = view.tag as ViewHolder
            }
// put the food and the matching calories to the ViewHolder
            vh.food.text = foodList[position].Food
            vh.cal.text = foodList[position].Cal

            return view
        }

        override fun getItem(position: Int): Any {
            return foodList[position]
        }

        override fun getItemId(position: Int): Long {
            return position.toLong()
        }

        override fun getCount(): Int {
            return foodList.size
        }
    }
// Make a new class called ViewHolder
    private class ViewHolder(view: View?) {
        val food: TextView
        val cal: TextView

        init {
            this.food = view?.findViewById(R.id.food) as TextView
            this.cal = view?.findViewById(R.id.cal) as TextView
        }
    }
// Create displayDialog function
    private fun displayDialog(layout: Int) {
        val dialog = Dialog(this)
        dialog.setContentView(layout)

        val window = dialog.window
        window?.setLayout(ActionBar.LayoutParams.MATCH_PARENT, ActionBar.LayoutParams.WRAP_CONTENT)

        dialog.findViewById<Button>(R.id.close).setOnClickListener {
            //if (dialog.Foods.text.toString().toInt().)
            try {
                goal = dialog.Foods.text.toString().toInt()
            } catch (e: Exception) {//Using AlertDialog to show the error message and remind users to enter new total calories number
                AlertDialog.Builder(this)
                    .setTitle("Error!Please type a number")
                    .setPositiveButton("Please Enter Your Total Calories Number"){//Add button to let users enter the number of calories once their inputs are wrong
                        dialog, which ->
                        displayDialog(R.layout.enter_total_cal)
                    }
                    .show()


                print("error")
            }
            if (goal - totalCal >= 0) {
                findViewById<TextView>(R.id.calories).text =  (goal - totalCal).toString()
                calories.setTextColor(Color.rgb(0,0,0))
            } else {
                findViewById<TextView>(R.id.calories).text =  (goal - totalCal).toString()

                calories.setTextColor(Color.rgb(255,0,0))//Make the number red when calories exceed the total calories
            }
            findViewById<TextView>(R.id.totalCal).text = "Total Calories   " + totalCal.toString()
            dialog.dismiss()
        }

        dialog.show()
    }


    override fun onStart() {
        super.onStart()
        Log.d("Android:", "onStart()")
    }

    override fun onResume() {
        super.onResume()
        Log.d("Android:", "onResume()")
    }

    override fun onPause() {
        super.onPause()
        Log.d("Android:", "onPause()")
    }

    override fun onStop() {
        super.onStop()
        Log.d("Android:", "onStop()")
    }
}
