package org.d3if3045.convertsuhu.ui.penemu_suhu

import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.RecyclerView
import org.d3if3045.convertsuhu.databinding.FragmentPenemuSuhuBinding
import org.d3if3045.convertsuhu.network.PenemuSuhuApi

class PenemuSuhuFragment : Fragment() {
    private val viewModel: PenemuSuhuViewModel by lazy {
        ViewModelProvider(this)[PenemuSuhuViewModel::class.java]
    }
    private lateinit var binding: FragmentPenemuSuhuBinding
    private lateinit var myAdapter: PenemuSuhuAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentPenemuSuhuBinding.inflate(layoutInflater, container, false)
        myAdapter = PenemuSuhuAdapter()
        with(binding.recyclerView) {
            addItemDecoration(
                DividerItemDecoration(
                    context,
                    RecyclerView.VERTICAL
                )
            )
            adapter = myAdapter
            setHasFixedSize(true)
        }
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.getData().observe(viewLifecycleOwner) {
            myAdapter.updateData(it)
        }
        viewModel.getStatus().observe(viewLifecycleOwner) {
            updateProgress(it)
        }

    }

    private fun updateProgress(status: PenemuSuhuApi.ApiStatus) {
        when (status) {
            PenemuSuhuApi.ApiStatus.LOADING -> {
                binding.progressBar.visibility = View.VISIBLE
            }
            PenemuSuhuApi.ApiStatus.SUCCESS -> {
                binding.progressBar.visibility = View.GONE
            }
            PenemuSuhuApi.ApiStatus.FAILED -> {
                binding.progressBar.visibility = View.GONE
                binding.networkError.visibility = View.VISIBLE
            }
        }
    }
}