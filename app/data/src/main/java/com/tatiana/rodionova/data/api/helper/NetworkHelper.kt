package com.tatiana.rodionova.data.api.helper

import android.Manifest
import android.content.Context
import android.net.ConnectivityManager
import android.net.Network
import android.net.NetworkRequest
import androidx.annotation.RequiresPermission
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class NetworkHelper(context: Context) {
    private val _isNetworkConnected = MutableStateFlow(false)
    val isNetworkConnected: StateFlow<Boolean> = _isNetworkConnected

    private val connectivityManager: ConnectivityManager =
        context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
    private val connectivityManagerCallback: ConnectivityManager.NetworkCallback =
        object : ConnectivityManager.NetworkCallback() {

            private val activeNetworks: MutableList<Network> = mutableListOf()

            @RequiresPermission(Manifest.permission.ACCESS_NETWORK_STATE)
            override fun onAvailable(network: Network) {
                super.onAvailable(network)

                if (activeNetworks.none { activeNetwork -> activeNetwork == network }) {
                    activeNetworks.add(network)
                }
                _isNetworkConnected.value = activeNetworks.isNotEmpty()
            }

            @RequiresPermission(Manifest.permission.ACCESS_NETWORK_STATE)
            override fun onLost(network: Network) {
                super.onLost(network)

                activeNetworks.removeAll { activeNetwork -> activeNetwork == network }
                _isNetworkConnected.value = activeNetworks.isNotEmpty()
            }
        }

    init {
        connectivityManager.registerNetworkCallback(
            NetworkRequest.Builder().build(), connectivityManagerCallback
        )
    }
}