package com.androiddevs.Sprint.ui.fragments

import android.os.Build
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.androiddevs.Sprint.R
import com.androiddevs.Sprint.adapters.RunAdapter
import com.androiddevs.Sprint.other.Constants.REQUEST_CODE_LOCATION_PERMISSION
import com.androiddevs.Sprint.other.SortType
import com.androiddevs.Sprint.other.TrackingUtility
import com.androiddevs.Sprint.ui.viewmodels.MainViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.fragment_run.*
import pub.devrel.easypermissions.AppSettingsDialog
import pub.devrel.easypermissions.EasyPermissions

@AndroidEntryPoint
class RunFragment : Fragment(R.layout.fragment_run) ,EasyPermissions.PermissionCallbacks {

    private val viewModel: MainViewModel by viewModels()

    private lateinit var runAdapter: RunAdapter

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        requestPermission()
        setupRecyclerView()

        when(viewModel.sortType){
            SortType.DATE -> spFilter.setSelection(0)
            SortType.RUNNING_TIME -> spFilter.setSelection(1)
            SortType.DISTANCE -> spFilter.setSelection(2)
            SortType.AVG_SPEED -> spFilter.setSelection(3)
            SortType.CALORIES_BURNED -> spFilter.setSelection(4)
        }

        spFilter.onItemSelectedListener = object : AdapterView.OnItemSelectedListener{
            override fun onNothingSelected(parent: AdapterView<*>?) {
            }

            override fun onItemSelected(adapterView: AdapterView<*>?, view: View?, pos: Int, id: Long) {
                when(pos){
                    0 ->viewModel.sortRuns(SortType.DATE)
                    1 ->viewModel.sortRuns(SortType.RUNNING_TIME)
                    2 ->viewModel.sortRuns(SortType.DISTANCE)
                    3 ->viewModel.sortRuns(SortType.AVG_SPEED)
                    4 ->viewModel.sortRuns(SortType.CALORIES_BURNED)
                }
            }

        }

        viewModel.runs.observe(viewLifecycleOwner, Observer {
            runAdapter.submitList(it)
        })


        fab.setOnClickListener {
            findNavController().navigate(R.id.action_runFragment_to_trackingFragment)
        }
    }

    private fun setupRecyclerView() = rvRuns.apply {
        runAdapter = RunAdapter()
        adapter = runAdapter
        layoutManager = LinearLayoutManager(requireContext())

    }
    private fun requestPermission(){
        if(TrackingUtility.hasLocationPermission(requireContext())){
            return
        }
        if(Build.VERSION.SDK_INT < Build.VERSION_CODES.Q){
             EasyPermissions.requestPermissions(
                 this,"You need to accept location permission to use this app!",
                 REQUEST_CODE_LOCATION_PERMISSION,
                 android.Manifest.permission.ACCESS_COARSE_LOCATION,
                 android.Manifest.permission.ACCESS_FINE_LOCATION
             )
        }
        else{
            EasyPermissions.requestPermissions(
                this,"You need to accept location permission to use this app!",
                REQUEST_CODE_LOCATION_PERMISSION,
                android.Manifest.permission.ACCESS_COARSE_LOCATION,
                android.Manifest.permission.ACCESS_FINE_LOCATION,
                android.Manifest.permission.ACCESS_BACKGROUND_LOCATION
            )
        }
    }

    override fun onPermissionsGranted(requestCode: Int, perms: MutableList<String>) {}


    override fun onPermissionsDenied(requestCode: Int, perms: MutableList<String>) {
        if(EasyPermissions.somePermissionPermanentlyDenied(this,perms)){
            AppSettingsDialog.Builder(this).build().show()
        }
        else{
            requestPermission()
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        EasyPermissions.onRequestPermissionsResult(requestCode,permissions,grantResults,this)
    }
}