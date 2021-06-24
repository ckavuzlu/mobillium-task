package com.example.mobilliumtask.model

sealed class NetworkResponse<out R : Any, out E : ErrorModel> {
    class Data<out R : Any>(val data: R) : NetworkResponse<R, Nothing>()
    class Error<out E : ErrorModel>(val error: E) : NetworkResponse<Nothing, E>()
}
