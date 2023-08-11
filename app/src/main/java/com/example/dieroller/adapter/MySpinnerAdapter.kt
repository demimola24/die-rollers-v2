package com.example.dieroller.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.TextView
import com.example.dieroller.R

//Spinner Adapter
class MySpinnerAdapter (ctx: Context,
                        list: List<Int>) :
    ArrayAdapter<Int>(ctx, 0, list) {
    override fun getView(position: Int, recycledView: View?, parent: ViewGroup): View {
        return this.createView(position, recycledView, parent)
    }
    override fun getDropDownView(position: Int, recycledView: View?, parent: ViewGroup): View {
        return this.createView(position, recycledView, parent)
    }
    private fun createView(position: Int, recycledView: View?, parent: ViewGroup): View {
        val data = getItem(position)

        val view = recycledView ?: LayoutInflater.from(context).inflate(
            R.layout.general_spinner_layout,
            parent,
            false
        )
        val textView = view.findViewById(R.id.value_name) as TextView
        if(data==-1){
            textView.text = "Use custom value"
        }else{
            textView.text = "$data sides"
        }
        return view
    }
}