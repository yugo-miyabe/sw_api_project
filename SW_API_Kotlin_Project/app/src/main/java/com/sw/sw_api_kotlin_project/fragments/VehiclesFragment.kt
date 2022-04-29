package com.sw.sw_api_kotlin_project.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.sw.sw_api_kotlin_project.R
import com.sw.sw_api_kotlin_project.adapters.VehiclesAdapter
import com.sw.sw_api_kotlin_project.base.BaseFragment
import com.sw.sw_api_kotlin_project.databinding.FragmentVehiclesBinding
import com.sw.sw_api_kotlin_project.repository.APIRepository
import com.sw.sw_api_kotlin_project.viewmodels.VehiclesViewModel
import com.sw.sw_api_kotlin_project.viewmodels.VehiclesViewModelFactory

class VehiclesFragment : BaseFragment() {

    private lateinit var viewModel: VehiclesViewModel
    private var _binding: FragmentVehiclesBinding? = null
    private val binding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel = ViewModelProvider(
            this,
            VehiclesViewModelFactory(APIRepository())
        )[VehiclesViewModel::class.java]
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentVehiclesBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.getVehiclesAPI()
    }

    override fun addObservers() {
        super.addObservers()
        viewModel.vehicle.observe(viewLifecycleOwner) {
            val adapter = VehiclesAdapter(it.vehicles)
            val recyclerView = view?.findViewById<RecyclerView>(R.id.vehicles_recycler)
            recyclerView!!.adapter = adapter
            recyclerView.layoutManager = LinearLayoutManager(context)
        }
    }

    override fun onDestroy() {
        _binding = null
        super.onDestroy()
    }
}