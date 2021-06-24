package com.example.mobilliumtask.repository

import com.example.mobilliumtask.model.ErrorModel
import com.example.mobilliumtask.model.ListData
import com.example.mobilliumtask.model.NetworkResponse
import com.example.mobilliumtask.network.ApiInterface
import kotlinx.coroutines.Dispatchers
import javax.inject.Inject
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.withContext

class MainRepository @Inject constructor(
    private val apiInterface: ApiInterface
) {

    private val channelListData = Channel<NetworkResponse<List<ListData>, ErrorModel>> { }
    var flowListData: Flow<NetworkResponse<List<ListData>, ErrorModel>> = channelListData.receiveAsFlow()

    suspend fun getDataFromRemote() {
        withContext(Dispatchers.IO) {
            val networkResult = apiInterface.getAllData()
            if (networkResult.isSuccessful && networkResult.body() != null) {
                channelListData.send(NetworkResponse.Data(networkResult.body()!!))
            }else{
                channelListData.send(NetworkResponse.Error(ErrorModel(networkResult.message())))
            }
        }
    }

}