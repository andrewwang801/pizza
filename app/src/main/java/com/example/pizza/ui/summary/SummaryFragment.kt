package com.example.pizza.ui.summary

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.Toolbar
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.pizza.R
import com.example.pizza.databinding.ActivityMainBinding
import com.example.pizza.databinding.FragmentSummaryBinding
import com.example.pizza.ui.BaseFragment
import com.example.pizza.ui.flavors.FlavorsViewModel
import dagger.android.support.AndroidSupportInjection
import kotlinx.android.synthetic.main.fragment_flavors.*
import kotlinx.android.synthetic.main.fragment_summary.*
import javax.inject.Inject


/**
 * A simple [Fragment] subclass.
 * Use the [SummaryFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class SummaryFragment : BaseFragment() {

    lateinit var adapter: SummaryAdapter

    private var binding: FragmentSummaryBinding? = null

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory
    private val viewModel: FlavorsViewModel by activityViewModels() { viewModelFactory }

    override fun onCreate(savedInstanceState: Bundle?) {
        AndroidSupportInjection.inject(this)
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentSummaryBinding.inflate(inflater)
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupView()
    }

    private fun setupView() {
        setupRecyclerView()

        val totalPrice = if (viewModel.addedFlavors.size < 2) {
            viewModel.addedFlavors.sumOf { it.price }
        } else {
            viewModel.addedFlavors.sumOf { it.price / 2.0 }
        }
        binding?.tvTotalPrice?.text = totalPrice.toString()
    }

    private fun setupRecyclerView() {
        adapter = SummaryAdapter()

        binding?.apply {
            rv_summary_flavors.layoutManager = LinearLayoutManager(requireContext())
            rv_summary_flavors.adapter = adapter
            rv_summary_flavors.addItemDecoration(DividerItemDecoration(requireContext(), DividerItemDecoration.VERTICAL))
        }

        adapter.differ.submitList(viewModel.addedFlavors)
    }

    override fun onDestroy() {
        super.onDestroy()
        binding = null
    }
}