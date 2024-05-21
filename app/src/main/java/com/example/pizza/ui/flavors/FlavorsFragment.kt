package com.example.pizza.ui.flavors

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContentProviderCompat.requireContext
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.pizza.R
import com.example.pizza.data.remote.network.dto.FlavorDTO
import com.example.pizza.databinding.FragmentFlavorsBinding
import com.example.pizza.ui.BaseFragment
import com.example.pizza.ui.OnItemClicked

import dagger.android.support.AndroidSupportInjection
import kotlinx.android.synthetic.main.fragment_flavors.*
import javax.inject.Inject

class FlavorsFragment() : BaseFragment() {

    companion object {
        fun createInstance() = FlavorsFragment()
    }

    lateinit var adapter: FlavorsAdapter

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory
    private val viewModel: FlavorsViewModel by activityViewModels { viewModelFactory }

    private var binding: FragmentFlavorsBinding? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        AndroidSupportInjection.inject(this)
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentFlavorsBinding.inflate(inflater)
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupRecyclerView()
        observeData()
        fetchData()
    }

    private fun setupRecyclerView() {
        adapter = FlavorsAdapter(object: OnItemClicked<FlavorDTO> {
            override fun onItemClicked(t: FlavorDTO) {
                    if (viewModel.addedFlavors.contains(t))
                        viewModel.removeFlavor(t)
                    else {
                        if (viewModel.validateFlavors())
                            viewModel.addFlavor(t)
                        else
                            showMessageSnackbar("Flavors are more than 2")
                    }
            }
        }, viewModel.addedFlavors)

        binding?.apply {
            rv_flavors.layoutManager = LinearLayoutManager(requireContext())
            rv_flavors.adapter = adapter
            rv_flavors.addItemDecoration(DividerItemDecoration(requireContext(), DividerItemDecoration.VERTICAL))

                btnDone.setOnClickListener {
                    if (viewModel.addedFlavors.isNotEmpty())
                        findNavController().navigate(FlavorsFragmentDirections.actionFlavorsFragmentToSummaryFragment())
                }
        }
    }

    private fun observeData() {
        viewModel.flavors.observe(viewLifecycleOwner) { result ->
            result.data?.let { list ->
                handleResponseResult(
                    result,
                    list,
                    adapter.differ,
                    binding?.loading
                )
            }
        }
    }

    private fun fetchData() {
        viewModel.getFlavors()
    }

    override fun onDestroy() {
        super.onDestroy()

        binding = null
    }
}
