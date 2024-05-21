package com.example.pizza.ui

import android.view.View
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.AsyncListDiffer
import com.example.pizza.data.vo.Resource
import com.example.pizza.data.vo.Status
import com.google.android.material.snackbar.Snackbar

open class BaseFragment: Fragment() {

    fun showMessageSnackbar(msg: String) {
        Snackbar.make(requireView(), msg, Snackbar.LENGTH_SHORT)
            .show()
    }

    fun <R, D> handleResponseResult(
        resource: Resource<R>,
        list: List<D>,
        differ: AsyncListDiffer<D>,
        loading: View?
    ) {
        resource.data?.let { }
        when (resource.status) {
            Status.SUCCESS -> {
                loading?.visibility = View.GONE
                resource.data?.let {
                    differ.submitList(list)
                }
            }
            Status.ERROR -> {
                loading?.visibility = View.GONE
                showMessageSnackbar(resource.message!!)
            }
            Status.LOADING -> {
                loading?.visibility = View.VISIBLE
            }
        }
    }
}