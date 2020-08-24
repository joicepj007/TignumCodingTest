package com.android.tignumcodetest.downloader.presentation.fragments

import android.app.Dialog
import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import android.widget.Toast
import androidx.fragment.app.DialogFragment
import androidx.lifecycle.ViewModelProvider
import com.android.tignumcodetest.R
import com.android.tignumcodetest.downloader.data.interfaces.DownloadInterface
import com.android.tignumcodetest.downloader.domain.model.Files
import com.android.tignumcodetest.downloader.domain.presenter.DownloadPresenterImpl
import com.android.tignumcodetest.core.utils.Utils
import com.android.tignumcodetest.downloader.presentation.model.DownloadModelImpl
import com.android.tignumcodetest.downloader.presentation.viewmodel.FileViewModel
import com.downloader.Error
import com.downloader.PRDownloader
import com.downloader.Progress
import com.downloader.Status
import kotlinx.android.synthetic.main.fragment_custom_dialog.*
import kotlinx.android.synthetic.main.fragment_custom_dialog.view.*
import kotlinx.android.synthetic.main.fragment_custom_dialog.view.buttonCancelOne
import kotlinx.android.synthetic.main.fragment_custom_dialog.view.buttonOne
import kotlinx.android.synthetic.main.fragment_custom_dialog.view.progressBarOne
import kotlinx.android.synthetic.main.fragment_custom_dialog.view.textViewProgressOne


class CustomDialogFragment : DialogFragment(), DownloadInterface.downloadViewPresenter {


    private var dirPath: String?=null
    private var fileDirPath: String?=null
    private var filelastName: String?=null
    private var downloadUrl: String?=null
    private var downloadIdValue: Int = 0
    private var model: FileViewModel?=null

    var presenterImplementation: DownloadPresenterImpl? = null

        companion object {

            private const val KEY_TITLE = "KEY_TITLE"
            private const val KEY_SUBTITLE = "KEY_SUBTITLE"

            fun newInstance(title: String?,subtitle: String?): CustomDialogFragment {
                val args = Bundle()
                args.putString(KEY_TITLE, title)
                args.putString(KEY_SUBTITLE, subtitle)
                val fragment =
                    CustomDialogFragment()
                fragment.arguments = args
                return fragment
            }

        }

        override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
        ): View? {
            return inflater.inflate(R.layout.fragment_custom_dialog, container, false)
        }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val dialog = super.onCreateDialog(savedInstanceState)
        dialog.setCancelable(false)
        dialog.setCanceledOnTouchOutside(false)
        return dialog
    }

        override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
            super.onViewCreated(view, savedInstanceState)

            model= ViewModelProvider(this, ViewModelProvider.NewInstanceFactory()).get(
                FileViewModel::class.java)
            dirPath = Utils.getRootDirPath(requireContext())
            downloadUrl=arguments?.getString(KEY_TITLE)
            filelastName=arguments?.getString(KEY_SUBTITLE)
            fileDirPath =
                Utils.getFilePath(requireContext(),filelastName.toString())
            setupView(view)
            setupClickListeners(view)
            presenterImplementation=
                DownloadPresenterImpl(
                    this,
                    DownloadModelImpl()
                )
            presenterImplementation?.fileDownload(downloadUrl.toString(),dirPath.toString(),filelastName.toString())

        }

        override fun onStart() {
            super.onStart()
            dialog?.window?.setLayout(
                WindowManager.LayoutParams.MATCH_PARENT,
                WindowManager.LayoutParams.WRAP_CONTENT
            )
        }

        private fun setupView(view: View) {
            if (Status.RUNNING == PRDownloader.getStatus(downloadIdValue)) {
                PRDownloader.pause(downloadIdValue);
                return;
            }

            view.buttonOne.setEnabled(false);
            view.progressBarOne.setIndeterminate(true);
            view.progressBarOne.getIndeterminateDrawable().setColorFilter(
                Color.BLUE, android.graphics.PorterDuff.Mode.SRC_IN);

            if (Status.PAUSED == PRDownloader.getStatus(downloadIdValue)) {
                PRDownloader.resume(downloadIdValue);
                return;
            }
        }

        private fun setupClickListeners(view: View) {
            view.buttonOne.setOnClickListener {
                setupView(view)
            }

            view.buttonCancelOne.setOnClickListener {
                Toast.makeText(context, getString(R.string.str_Cancel_check), Toast.LENGTH_SHORT).show();
                PRDownloader.cancel(downloadIdValue);
                 dismiss()
            }
        }

    override fun setOnStartOrResumeListener(downloadId: Int) {
        progressBarOne?.setIndeterminate(false);
        buttonOne?.setEnabled(true);
        buttonOne?.setText(R.string.pause);
        buttonCancelOne?.setEnabled(true);
        downloadIdValue=downloadId
    }

    override fun setOnPauseListener() {
        buttonOne?.setText(R.string.resume);
    }

    override fun setOnCancelListener() {
        buttonOne?.setText(R.string.start);
        buttonCancelOne?.setEnabled(false);
        progressBarOne?.setProgress(0);
        textViewProgressOne?.setText("");
        downloadIdValue=0
        progressBarOne?.setIndeterminate(false);
    }

    override fun setOnProgressListener(progress: Progress) {
         val progressPercent =
                progress.currentBytes * 100 / progress.totalBytes
            progressBarOne?.progress = progressPercent.toInt()
            textViewProgressOne?.text = Utils.getProgressDisplayLine(
                progress.currentBytes,
                progress.totalBytes
            )
            progressBarOne?.isIndeterminate = false


    }

    override fun OnDownloadListener() {
        buttonOne?.setEnabled(false);
        buttonCancelOne?.setEnabled(false);
        buttonOne?.setText(R.string.completed);
        val file = Files(
            0,
            downloadUrl.toString(),
            dirPath.toString(),
            fileDirPath.toString()
        )
        model?.insert(file)
        Toast.makeText(context, getString(R.string.str_download_completed), Toast.LENGTH_SHORT).show();
        dismiss()
    }

    override fun onDownloadError(error: Error?) {
        Toast.makeText(context, getString(R.string.some_error_occurred), Toast.LENGTH_SHORT).show();
        downloadIdValue=0
        PRDownloader.cancel(downloadIdValue);
        dismiss()
    }
    override fun onPause() {
        super.onPause()
        PRDownloader.cancel(downloadIdValue);
        dismiss()
    }
    override fun onStop() {
        super.onStop()
        PRDownloader.cancel(downloadIdValue);
        dismiss()
    }
    override fun onDestroy() {
        super.onDestroy()
        PRDownloader.cancel(downloadIdValue);
        dismiss()
    }

}