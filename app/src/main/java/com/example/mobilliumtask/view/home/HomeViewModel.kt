package com.example.mobilliumtask.view.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mobilliumtask.model.ErrorModel
import com.example.mobilliumtask.model.ListData
import com.example.mobilliumtask.model.NetworkResponse
import com.example.mobilliumtask.model.UIState
import com.example.mobilliumtask.repository.MainRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import retrofit2.Response
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val repository: MainRepository
) : ViewModel() {

    private val _listDataLiveData: MutableLiveData<List<ListData>> = MutableLiveData()
    val listDataLiveData: LiveData<List<ListData>> = _listDataLiveData

    private val _errorLiveData: MutableLiveData<ErrorModel> = MutableLiveData()
    val errorLiveData: LiveData<ErrorModel> = _errorLiveData

    private val _uiStateLiveData: MutableLiveData<UIState> = MutableLiveData()
    val uistateLiveData: LiveData<UIState> = _uiStateLiveData

    init {
        getDataFromRemote()
        collectFromFlowData()

    }

    fun getDataFromRemote() {
        viewModelScope.launch(Dispatchers.IO) {
            _uiStateLiveData.postValue(UIState.LOADING)
            repository.getDataFromRemote()
        }
    }

    fun collectFromFlowData() {
        viewModelScope.launch(Dispatchers.IO) {
            repository.flowListData.collect { networkResponse ->
                when (networkResponse) {
                    is NetworkResponse.Data -> handleListData(networkResponse.data)
                    is NetworkResponse.Error -> handleError(networkResponse.error)
                }
                _uiStateLiveData.postValue(UIState.LIVE)
            }
        }
    }

    fun handleError(errorModel : ErrorModel) {
        _errorLiveData.postValue(errorModel)
    }

    fun handleListData(listData: List<ListData>) {
        _listDataLiveData.postValue(listData)
    }



}