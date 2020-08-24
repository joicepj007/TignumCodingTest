package com.android.tignumcodetest.downloader.presentation.fragments

import android.os.Bundle
import android.util.Patterns
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import com.android.tignumcodetest.R
import com.android.tignumcodetest.core.utils.AppUtils
import com.android.tignumcodetest.downloader.presentation.viewmodel.FileViewModel
import kotlinx.android.synthetic.main.fragment_file_download.*


class FileDownloaderFragment : Fragment(),View.OnClickListener {

    private var downloadUrl: String?=null
    private var model: FileViewModel?=null


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_file_download, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        model= ViewModelProvider(this, ViewModelProvider.NewInstanceFactory()).get(
            FileViewModel::class.java)

    }


    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        button.setOnClickListener(this)
    }

    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.button -> {
                validation()
            }
            else -> {
                // else condition
            }

        }
    }
    fun validation()
    {
        downloadUrl=search_edt.text.toString()
        if (AppUtils.isNetworkConnected(requireContext()))
        {
            if (!downloadUrl.isNullOrBlank() && Patterns.WEB_URL.matcher(downloadUrl.toString()).matches())
            {
                if (model?.isDataExistsinRoomDb(downloadUrl.toString())==0) {
                    val ft = activity?.getSupportFragmentManager()?.beginTransaction()
                    val newFragment = CustomDialogFragment.newInstance(downloadUrl.toString(),
                        downloadUrl?.lastIndexOf('/')?.plus(1)?.let { downloadUrl?.substring(it) })
                    newFragment.isCancelable=false
                    ft?.let { newFragment.show(it, "CustomDialogFragment") }
                } else {
                    Toast.makeText(activity, getString(R.string.str_file_check), Toast.LENGTH_SHORT).show()
                }
            }
            else
            {
                Toast.makeText(activity, getString(R.string.str_url_check), Toast.LENGTH_SHORT).show()
            }
        }
        else
        {
            Toast.makeText(activity, getString(R.string.network_error_text), Toast.LENGTH_SHORT).show()
        }

    }


}