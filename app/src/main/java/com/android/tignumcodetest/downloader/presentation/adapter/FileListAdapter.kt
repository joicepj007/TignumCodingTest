package com.android.tignumcodetest.downloader.presentation.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.android.tignumcodetest.R
import com.android.tignumcodetest.core.utils.AppUtils
import com.android.tignumcodetest.downloader.domain.model.Files
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.file_item_row.view.*
import xyz.neocrux.suziloader.SuziLoader


class FileListAdapter(
    fileList: List<Files>,
    private val interaction: Interaction? = null,
    private var context: Context? = null
) : RecyclerView.Adapter<FileListAdapter.ViewHolder>() {

    private val files = mutableListOf<Files>()


    init {
        files.addAll(fileList)
    }

    // Method #1
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val view = inflater.inflate(R.layout.file_item_row, parent, false)
        context = parent.getContext();
        return ViewHolder(
            view,
            interaction,
            context
        )
    }
    

    // Method #2
    override fun getItemCount() = files.size


    // Method #3
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(item = files[position])
    }

    // Method #4
    class ViewHolder(
        itemView: View,
        private val interaction: Interaction?,
        private var context: Context?
    ) : RecyclerView.ViewHolder(itemView) {

        // Method #5
        fun bind(item: Files) {
            itemView.url.text = item.url

            val fileType =
                AppUtils.getFileTypeFromURL(item.url.toString())
            if (fileType == "video") {
                // Do your task here
                val loader = SuziLoader() //Create it for once

                loader.with(context) //Context
                    .load(item.dirName) //Video path
                    .into(itemView.profile_pic) // imageview to load the thumbnail
                    .type("mini") // mini or micro
                    .show() // to show the thumbnail
            }
            else if(fileType == "photo") {
                context?.let { Glide.with(it).load(item.url).into(itemView.profile_pic) }
            }
            else{
                val loader = SuziLoader() //Create it for once

                loader.with(context) //Context
                    .load(item.dirName) //Video path
                    .into(itemView.profile_pic) // imageview to load the thumbnail
                    .type("mini") // mini or micro
                    .show() // to show the thumbnail
            }

            //Handle item click
            itemView.setOnClickListener {
                interaction?.onItemSelected(adapterPosition,item)
            }
        }


    }
    
    

    // Method #6
    interface Interaction {
        fun onItemSelected(position: Int, item: Files)
    }
}