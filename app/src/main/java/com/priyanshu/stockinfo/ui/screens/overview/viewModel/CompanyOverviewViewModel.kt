package com.priyanshu.stockinfo.ui.screens.overview.viewModel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.priyanshu.stockinfo.domain.models.CompanyOverview
import com.priyanshu.stockinfo.domain.models.IntraDayInfo
import com.priyanshu.stockinfo.domain.models.IntraDayInfoEntity
import com.priyanshu.stockinfo.domain.models.TopGainerAndLosers
import com.priyanshu.stockinfo.domain.models.waitlist.WaitlistEntity
import com.priyanshu.stockinfo.domain.usecases.StockUseCase
import com.priyanshu.stockinfo.utils.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import okhttp3.internal.userAgent
import javax.inject.Inject

@HiltViewModel
class CompanyOverviewViewModel @Inject constructor(
    private val useCase: StockUseCase
) : ViewModel() {

    private val _companyOverviewState = MutableStateFlow<CompanyOverview?>(null)
    val companyOverviewState = _companyOverviewState.asStateFlow()

    private val _intraDayInfoListState: MutableStateFlow<List<IntraDayInfoEntity>> = MutableStateFlow(
        emptyList()
    )
    val intraDayInfoListState = _intraDayInfoListState.asStateFlow()

    private val _isLoading: MutableStateFlow<Boolean> = MutableStateFlow(false)
    val isLoading = _isLoading.asStateFlow()

    private val _error: MutableStateFlow<String?> = MutableStateFlow(null)
    val error = _error.asStateFlow()

    private val _graphError: MutableStateFlow<String?> = MutableStateFlow(null)
    val graphError = _graphError.asStateFlow()

    private val _isGraphLoading: MutableStateFlow<Boolean> = MutableStateFlow(false)
    val isGraphLoading = _isGraphLoading.asStateFlow()

    private val _isItemInWaitlist: MutableStateFlow<Boolean> = MutableStateFlow(false)
    val isItemInWaitlist = _isItemInWaitlist.asStateFlow()

    fun getCompanyOverview(ticker: String) {
        viewModelScope.launch(Dispatchers.IO) {
            _error.emit(null)
        }
        viewModelScope.launch(Dispatchers.IO) {
            useCase.getCompanyOverview(ticker).collect { result ->
                when (result) {
                    is Resource.Loading -> {
                        _isLoading.emit(true)
                    }

                    is Resource.Success -> {
                        _isLoading.emit(false)
                        _companyOverviewState.emit(result.data)

                    }

                    is Resource.Error -> {
                        _isLoading.emit(false)
                        _error.emit(result.message)
                    }
                }
            }
        }
    }

    fun getIntraDayInfoList(ticker: String) {
        viewModelScope.launch(Dispatchers.IO) {
            useCase.getIntraDayInfoList(ticker)
                .collect { result ->
                    when (result) {
                        is Resource.Loading -> {
                            _isGraphLoading.emit(true)
                        }

                        is Resource.Success -> {
                            _isGraphLoading.emit(false)
                            result.data?.let { _intraDayInfoListState.emit(it) }
                        }

                        is Resource.Error -> {
                            _isGraphLoading.emit(false)
                            _graphError.emit(result.message)
                        }
                    }
                }
        }
    }

    fun isItemInWaitlist(symbol: String) {
        viewModelScope.launch(Dispatchers.IO) {
            useCase.isItemInWaitlist(symbol).collect { result ->
                when (result) {
                    is Resource.Loading -> {}

                    is Resource.Success -> {
                        result.data?.let { _isItemInWaitlist.emit(it) }
                    }

                    is Resource.Error -> {
                        _graphError.emit(result.message)
                    }
                }
            }
        }
    }

    fun saveItemInWaitlist(symbol: String) {
        viewModelScope.launch(Dispatchers.IO) {
            companyOverviewState.value?.Name?.let {
                companyOverviewState.value?.AssetType?.let { it1 ->
                    WaitlistEntity(
                        symbol = symbol,
                        name = it,
                        type = it1
                    )
                }
            }?.let {
                useCase.addWaitListEntity(
                    it
                )
            }
        }
    }

    fun deleteWaitlistEntity(symbol: String) {
        viewModelScope.launch(Dispatchers.IO) {
            useCase.deleteWaitlistEntity(symbol)
        }
    }

    fun updateWaitlistState(value: Boolean){
        viewModelScope.launch(Dispatchers.IO) {
            _isItemInWaitlist.emit(value)
        }
    }

}