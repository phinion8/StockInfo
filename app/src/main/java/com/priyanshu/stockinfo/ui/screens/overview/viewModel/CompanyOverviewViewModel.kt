package com.priyanshu.stockinfo.ui.screens.overview.viewModel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.priyanshu.stockinfo.domain.models.CompanyOverview
import com.priyanshu.stockinfo.domain.models.TopGainerAndLosers
import com.priyanshu.stockinfo.domain.usecases.StockUseCase
import com.priyanshu.stockinfo.utils.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CompanyOverviewViewModel @Inject constructor(
    private val useCase: StockUseCase
) : ViewModel() {

    private val _companyOverviewState = MutableStateFlow<CompanyOverview?>(null)
    val companyOverviewState = _companyOverviewState.asStateFlow()

    private val _isLoading: MutableStateFlow<Boolean> = MutableStateFlow(false)
    val isLoading = _isLoading.asStateFlow()

    private val _error: MutableStateFlow<String?> = MutableStateFlow(null)
    val error = _error.asStateFlow()

    fun getCompanyOverview(ticker: String){
        viewModelScope.launch {
            useCase.getCompanyOverview(ticker).collect {result->
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

}