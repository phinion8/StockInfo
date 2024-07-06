package com.priyanshu.stockinfo.ui.screens.home.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.priyanshu.stockinfo.domain.models.TopGainerAndLosers
import com.priyanshu.stockinfo.domain.usecases.StockUseCase
import com.priyanshu.stockinfo.utils.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val useCase: StockUseCase
) : ViewModel() {

    private val _topGainersAndLosers = MutableStateFlow<TopGainerAndLosers?>(null)
    val topGainersAndLosers = _topGainersAndLosers.asStateFlow()

    private val _isLoading: MutableStateFlow<Boolean> = MutableStateFlow(false)
    val isLoading = _isLoading.asStateFlow()

    init {
        getTopGainersAndLosers()
    }

    private fun getTopGainersAndLosers() {
        viewModelScope.launch(Dispatchers.IO) {
            useCase.getTopGainersAndLosers().collect { result ->

                when (result) {
                    is Resource.Loading -> {
                        _isLoading.emit(true)
                    }

                    is Resource.Success -> {
                        _isLoading.emit(false)
                        _topGainersAndLosers.emit(result.data)
                    }

                    is Resource.Error -> {
                        _isLoading.emit(false)
                    }
                }

            }
        }
    }


}