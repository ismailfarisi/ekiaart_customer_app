package com.example.ekiaart.ui.home

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.ekiaart.adapters.ShopsAdapter
import com.example.ekiaart.data.FirestoreData
import com.example.ekiaart.databinding.FragmentShopListBinding
import com.example.ekiaart.ui.LocationPickerDialog
import com.example.ekiaart.util.TAG


class ShopListFragment : Fragment(), LocationPickerDialog.LocationDialogListener {

    companion object {
        const val LOCATION_FRAGMENT = "locationfragment"
    }

    private lateinit var binding: FragmentShopListBinding
    private lateinit var viewModel: ShopListViewModel
    private lateinit var factory: ShopListViewModelFactory


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentShopListBinding.inflate(inflater, container, false)
        factory = ShopListViewModelFactory(repository = FirestoreData())
        viewModel = ViewModelProvider(this, factory).get(ShopListViewModel::class.java)
        // Inflate the layout for this fragment
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.locationEditBtn.setOnClickListener {
            editLocation()

        }
        binding.searchViewMain.setOnClickListener { toggleVisibility() }


        val adapter = ShopsAdapter()
        binding.shopsRecyclerview.also {
            it.layoutManager = LinearLayoutManager(this.context)
            it.adapter = adapter
        }
        subscribeUI(adapter)
    }

    private fun editLocation() {
        val dialog = LocationPickerDialog()
        dialog.setTargetFragment(this, 12)
        dialog.show(parentFragmentManager, LOCATION_FRAGMENT)
    }


    private fun subscribeUI(adapter: ShopsAdapter) {
        Log.d(TAG, "subscribeUI: called")
        viewModel.shopsList.observe(viewLifecycleOwner, Observer {
            adapter.submitList(it)
        })

    }

    private fun toggleVisibility() {
        val b = binding.searchViewMain.isActivated
        Log.d(TAG, "toggleVisibility: $b")
        if (b) {
            binding.locationEditBtn.visibility = View.GONE
            binding.locationName.visibility = View.GONE
        } else {
            binding.locationEditBtn.visibility = View.VISIBLE
            binding.locationName.visibility = View.VISIBLE
        }
    }


    override fun onDialogPositiveClick(dialog: DialogFragment) {
        dialog.dismiss()
    }

    override fun onDialogNegativeClick(dialog: DialogFragment) {
        dialog.dismiss()
    }


}