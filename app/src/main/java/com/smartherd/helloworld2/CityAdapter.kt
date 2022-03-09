package com.smartherd.helloworld2

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.activity_row.view.*

class CityAdapter(val arrayList: ArrayList<CityModel>, val context: Context) :
    RecyclerView.Adapter<CityAdapter.ViewHolder>() {

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bindItems(model:CityModel) {
            itemView.titleTv.text = model.title
            itemView.descriptionTv.text = model.des
            itemView.descTv.text = model.descr
            itemView.imageTv.setImageResource(model.image)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {

        val v = LayoutInflater.from(parent.context).inflate(R.layout.activity_row, parent, false)

        return ViewHolder(v)
    }

    override fun getItemCount(): Int {
        return arrayList.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bindItems(arrayList[position])

        holder.itemView.setOnClickListener {

           /* if (position == 0) {
                Toast.makeText(
                    context,
                    "You clicked over Newsfeed",
                    Toast.LENGTH_LONG
                ).show()
            }

            if (position == 1) {
                Toast.makeText(
                    context,
                    "You clicked over B",
                    Toast.LENGTH_LONG
                ).show()
            }

            if (position == 2) {
                Toast.makeText(
                    context,
                    "You clicked over P",
                    Toast.LENGTH_LONG
                ).show()
            }

            if (position == 3) {
                Toast.makeText(
                    context,
                    "You clicked over N",
                    Toast.LENGTH_LONG
                ).show()
            }

            if (position == 4) {
                Toast.makeText(
                    context,
                    "You clicked over F",
                    Toast.LENGTH_LONG
                ).show()
            }*/

            //get position of selected item
            val model = arrayList[position]
            //get title and description of selected item with intent
            var gTitle:String = model.title
            var gDescription : String = model.des
            var gDescr : String = model.descr
            // get image with intent, which position is selected
            var gImageView : Int = model.image

            // create intent in Kotlin
            val intent = Intent(context, City::class.java)
            // now put all these items with putExtra intent
            intent.putExtra("iTitle", gTitle)
            intent.putExtra("iDescription", gDescription)
            intent.putExtra("iDescr", gDescr)
            intent.putExtra("iImageView", gImageView)
            // start another activity
            context.startActivity(intent)
        }
    }


}