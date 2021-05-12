package com.vision2022.mykotlinapp.ui

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.vision2022.mykotlinapp.R
import com.vision2022.mykotlinapp.model.DataModel


class DataAdpter(private var dataList: List<DataModel>, private val context: Context) : RecyclerView.Adapter<DataAdpter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(LayoutInflater.from(context).inflate(R.layout.list_item_home, parent, false))
    }

    override fun getItemCount(): Int {
        return dataList.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val dataModel=dataList.get(position)
        holder.titleTextView.text= dataModel.title
        //holder.thumbnail.(dataModel.thumbnailurl)
    }

    class ViewHolder(itemLayoutView: View) : RecyclerView.ViewHolder(itemLayoutView) {
        lateinit var titleTextView:TextView
        lateinit var thumbnail:ImageView

        init {
            titleTextView=itemLayoutView.findViewById(R.id.title)
            thumbnail=itemLayoutView.findViewById((R.id.thumbnail))
        }
    }

}
