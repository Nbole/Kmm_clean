package com.example.basekmm_003.data

import com.example.basekmm_003.data.remote.KtorResponse
import com.example.basekmm_003.data.remote.WResponse
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.emitAll
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map

/**
 * This method is responsible for manage and fetch the queries into the localDB.
 */
inline fun <ResultType, RequestType> networkBoundResource(
    crossinline loadFromDb: () -> Flow<ResultType>,
    crossinline netWorkRequest: suspend () -> KtorResponse<RequestType>,
    crossinline saveCall: suspend (KtorResponse<RequestType>) -> Unit
): Flow<WResponse<ResultType>> = flow {
    emit(WResponse.Loading(loadFromDb().firstOrNull()))
    val netWorkKtorResponse: KtorResponse<RequestType> = netWorkRequest()
    emitAll(
        if (netWorkKtorResponse is KtorResponse.Success) {
            saveCall(netWorkKtorResponse)
            loadFromDb().map { WResponse.Success(it) }
        } else {
            val error = netWorkKtorResponse as KtorResponse.Error
            loadFromDb().map { WResponse.Error(error.message, it) }
        }
    )
}
