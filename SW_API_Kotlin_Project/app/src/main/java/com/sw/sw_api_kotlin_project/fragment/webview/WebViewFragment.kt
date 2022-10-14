package com.sw.sw_api_kotlin_project.fragment.webview

import android.os.Bundle
import android.view.KeyEvent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.findNavController
import androidx.navigation.fragment.navArgs
import com.google.android.material.appbar.MaterialToolbar
import com.sw.sw_api_kotlin_project.R
import com.sw.sw_api_kotlin_project.base.BaseFragment
import com.sw.sw_api_kotlin_project.databinding.FragmentWebViewBinding
import dagger.hilt.android.AndroidEntryPoint

/**
 * WebView
 */
@AndroidEntryPoint
class WebViewFragment : BaseFragment() {
    private var _binding: FragmentWebViewBinding? = null
    private val binding get() = checkNotNull(_binding)
    private val args: WebViewFragmentArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentWebViewBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun initView() {
        super.initView()
        val webViewInfo = args.info
        binding.webViewAppbar.findViewById<MaterialToolbar>(R.id.toolbar).apply {
            setOnClickListener { view ->
                view.findNavController().navigateUp()
            }
            title = webViewInfo.title
        }
        binding.webView.loadUrl(webViewInfo.url)
        binding.webView.setOnKeyListener { _, keyCode, event ->
            (keyCode == KeyEvent.KEYCODE_BACK && event.action == KeyEvent.ACTION_DOWN).apply {
                binding.webView.goBack()
            }
        }
        binding.webView.isFocusableInTouchMode = true
        binding.webView.requestFocus()
    }

    override fun onDestroy() {
        _binding = null
        super.onDestroy()
    }
}