package com.example.basekmm_003.data

import com.example.basekmm_003.data.remote.SerialResponse
import com.example.basekmm_003.data.remote.WResponse
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.emitAll
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map

/**
 * This method is responsible for manage and fetch the queries into the localDB.
 */
inline fun <ResultType, RequestType> networkBoundResource(
    crossinline loadFromDb: () -> Flow<ResultType>,
    crossinline netWorkRequest: suspend () -> SerialResponse<RequestType>,
    crossinline saveCall: suspend (SerialResponse<RequestType>) -> Unit
): Flow<WResponse<ResultType>> = flow {
    emit(WResponse.Loading(loadFromDb().firstOrNull()))
    val netWorkSerialResponse: SerialResponse<RequestType> = netWorkRequest()
    emitAll(
        if (netWorkSerialResponse is SerialResponse.Success) {
            saveCall(netWorkSerialResponse)
            loadFromDb().map { WResponse.Success(it) }
        } else {
            val error = netWorkSerialResponse as SerialResponse.Error
            loadFromDb().map { WResponse.Error(error.message, it) }
        }
    )
}
