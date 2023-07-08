package com.sw.sw_api_kotlin_project.screen.planet.list

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import androidx.paging.LoadState
import com.google.android.material.appbar.MaterialToolbar
import com.sw.sw_api_kotlin_project.R
import com.sw.sw_api_kotlin_project.databinding.FragmentPlanetListBinding
import com.sw.sw_api_kotlin_project.screen.base.BaseFragment
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

/**
 * 惑星一覧画面
 */
@AndroidEntryPoint
class PlanetListFragment : BaseFragment() {
    private val viewModel: PlanetViewModel by viewModels()
    private var _binding: FragmentPlanetListBinding? = null
    private val binding get() = checkNotNull(_binding)

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentPlanetListBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun initView() {
        super.initView()
        binding.planetListAppbar.findViewById<MaterialToolbar>(R.id.toolbar).apply {
            setOnClickListener {
                findNavController().popBackStack()
            }
            title = getString(R.string.planet_title)
        }
    }

    override fun addObservers() {
        super.addObservers()

        val adapter = PlanetListAdapter {
            val action = PlanetListFragmentDirections.actionNavPlanetToNavPlanetDetail(it)
            findNavController().navigate(action)
        }

        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.planetItems.collect {
                    adapter.submitData(it)
                }
            }
        }

        lifecycleScope.launch {
            adapter.loadStateFlow.collect { loadStates ->
                binding.appendProgress.isVisible = loadStates.source.append is LoadState.Loading
                val loadStateRefresh: LoadState = loadStates.refresh
                binding.progressBar.isVisible = loadStateRefresh is LoadState.Loading
                binding.errorText.isVisible = loadStateRefresh is LoadState.Error
                binding.retryButton.isVisible = loadStateRefresh is LoadState.Error
                if (loadStateRefresh is LoadState.Error)
                    binding.errorText.text = loadStateRefresh.error.localizedMessage
            }
        }

        binding.retryButton.setOnClickListener {
            adapter.retry()
        }

        binding.planetRecyclerView.adapter = adapter

    }

    override fun onDestroy() {
        _binding = null
        super.onDestroy()
    }
}
