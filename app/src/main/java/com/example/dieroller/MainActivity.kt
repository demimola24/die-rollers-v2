package com.example.dieroller

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.AdapterView
import android.widget.Toast
import com.example.dieroller.adapter.MySpinnerAdapter
import com.example.dieroller.databinding.ActivityMainBinding
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import java.util.*

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private var selectedDieType = 4;
    private var rollType = 1;

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setSupportActionBar(binding.toolbar)
        val data  =  listOf(4, 6, 8, 10, 12, 20, -1)
        binding.tlDieTypes.adapter = MySpinnerAdapter(this,data)

        //listen for chances in the die type
        binding.tlDieTypes.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>,
                view: View?,
                position: Int,
                id: Long
            ) {
                selectedDieType = data[position]
                if(selectedDieType==-1){
                    binding.tlCustomDieValue.visibility = View.VISIBLE
                    binding.tlCustomDieLabel.visibility = View.VISIBLE

                }else{
                    binding.tlCustomDieValue.visibility = View.GONE
                    binding.tlCustomDieLabel.visibility = View.GONE
                }

            }

            override fun onNothingSelected(parent: AdapterView<*>?) {}
        }

        //Allows us to know the selection for roll type to be used
        binding.rollOptions.setOnCheckedChangeListener { _, checkedId ->

            // Check which radio button was clicked
            when (checkedId) {
                R.id.singleRadioButton ->{
                    rollType = 1
                    binding.tvDieTwo.visibility = View.GONE
                    binding.tvDieValueTwo.visibility = View.GONE
                }
                R.id.doubleRadioButton -> {
                    rollType = 2
                    binding.tvDieTwo.visibility = View.VISIBLE
                    binding.tvDieValueTwo.visibility = View.VISIBLE
                }
            }
        }

        binding.btnRoll.setOnClickListener {
            performRoll()
        }

    }

    private fun performRoll(){
        Log.d("performRoll","performRoll")

        //Get default or custom die type
        if(selectedDieType==-1){
            val customSelectedDieType  = binding.tlCustomDieValue.editText?.text.toString().toIntOrNull()
            if(customSelectedDieType==null){
                Toast.makeText(this@MainActivity, "Kindly input a valid custom die type", Toast.LENGTH_SHORT).show()
                return
            }else{
                selectedDieType = customSelectedDieType
            }
        }
        binding.tvDieValueOne.text = "."
        binding.tvDieValueTwo.text = "."

        GlobalScope.launch { // launch new coroutine in background and continue

            //Simulate an animation
            binding.tvDieValueOne.text = ".."
            binding.tvDieValueTwo.text = ".."
            delay(500L)
            binding.tvDieValueOne.text = "..."
            binding.tvDieValueTwo.text = "..."
            delay(500L)
            binding.tvDieValueOne.text = "...."
            binding.tvDieValueTwo.text = "...."
            delay(500L)

            //Random from 0 to selectedDieType-1
            var dieValue = Random().nextInt(selectedDieType-1)
            binding.tvDieValueOne.text = "$dieValue"

            Log.d("performRoll","performRolled => $dieValue")

            if(rollType==2){
                //Random from 0 to selectedDieType-1
                dieValue = Random().nextInt(selectedDieType-1)
                binding.tvDieValueTwo.text = "$dieValue"
            }

        }

    }
}