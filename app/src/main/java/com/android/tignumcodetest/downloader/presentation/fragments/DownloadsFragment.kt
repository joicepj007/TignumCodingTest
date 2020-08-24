package com.android.tignumcodetest.downloader.presentation.fragments

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.android.tignumcodetest.R
import com.android.tignumcodetest.core.utils.AppUtils
import com.android.tignumcodetest.downloader.domain.model.Files
import com.android.tignumcodetest.downloader.presentation.viewmodel.FileViewModel
import com.android.tignumcodetest.downloader.presentation.activities.VideoPlayerActivity
import com.android.tignumcodetest.downloader.presentation.adapter.FileListAdapter
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.file_item_row.view.*
import kotlinx.android.synthetic.main.fragment_list_downloads.*
import java.io.File


class DownloadsFragment : Fragment(),
    FileListAdapter.Interaction {


    private lateinit var fileListAdapter: FileListAdapter
    private var model: FileViewModel?=null
    lateinit var allFiles: List<Files>

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        allFiles = mutableListOf()
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_list_downloads, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        model= ViewModelProvider(this, ViewModelProvider.NewInstanceFactory()).get(
            FileViewModel::class.java)
        observerLiveData()
    }

    private fun observerLiveData() {
        model?.getAllFiles()?.observe(viewLifecycleOwner, Observer { lisOfNotes ->
            lisOfNotes?.let {
                allFiles = it
                if (allFiles.size!=0)
                { initRecyclerView()
                } else{
                    noList.visibility=View.VISIBLE }

            }
        })
    }


    private fun initRecyclerView() {
        recyclerView.apply {
            fileListAdapter =
                FileListAdapter(
                    allFiles,
                    this@DownloadsFragment,
                    activity
                )
            layoutManager = LinearLayoutManager(this@DownloadsFragment.context)
            adapter = fileListAdapter
            val swipe = ItemTouchHelper(initSwipeToDelete())
            swipe.attachToRecyclerView(recyclerView)
        }
    }
    private fun initSwipeToDelete(): ItemTouchHelper.SimpleCallback {
        //Swipe recycler view items on RIGHT
        return object : ItemTouchHelper.SimpleCallback(
            0, ItemTouchHelper.RIGHT
        ) {
            override fun onMove(
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder,
                target: RecyclerView.ViewHolder
            ): Boolean {
                return true
            }

            //swipe to delete function
            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                val position = viewHolder.adapterPosition
                model?.delete(allFiles.get(position))
                if (allFiles.size==0)
                { noList.visibility=View.VISIBLE
                }
                val someDir = File(allFiles.get(position).dirName.toString())
                someDir.deleteRecursively()
                Toast.makeText(context, getString(R.string.str_note_deleted), Toast.LENGTH_SHORT).show();
            }
        }
    }

    override fun onItemSelected(position: Int, item: Files) {
        val fileType =
            AppUtils.getFileTypeFromURL(item.url.toString())
        if (fileType == "video") {
            val intent = Intent(context, VideoPlayerActivity::class.java)
            intent.putExtra("filePath", item.dirName.toString())
            startActivity(intent)
        } else if(fileType == "photo") {
            Toast.makeText(context, getString(R.string.str_image_detected), Toast.LENGTH_SHORT).show();
        } else
        { val intent = Intent(context, VideoPlayerActivity::class.java)
            intent.putExtra("filePath", item.dirName.toString())
            startActivity(intent)
        }
    }



}