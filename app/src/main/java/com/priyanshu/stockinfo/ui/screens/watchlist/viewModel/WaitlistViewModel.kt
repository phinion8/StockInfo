package com.priyanshu.stockinfo.ui.screens.watchlist.viewModel

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.priyanshu.stockinfo.domain.models.waitlist.WaitlistEntity
import com.priyanshu.stockinfo.domain.usecases.StockUseCase
import com.priyanshu.stockinfo.utils.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class WaitlistViewModel @Inject constructor(
    private val useCase: StockUseCase
): ViewModel() {

    private val _waitlistState: MutableStateFlow<List<WaitlistEntity>> = MutableStateFlow(emptyList())
    val waitListState = _waitlistState.asStateFlow()

    private val _isLoading: MutableStateFlow<Boolean> = MutableStateFlow(false)
    val isLoading = _isLoading.asStateFlow()

    private val _error: MutableStateFlow<String?> = MutableStateFlow(null)
    val error = _error.asStateFlow()

    private val _searchQuery: MutableState<String> = mutableStateOf("")
    val searchQuery: State<String> = _searchQuery

    fun updateSearchQuery(query: String) {
        _searchQuery.value = query
    }


    fun getWaitlistEntityList(){
        viewModelScope.launch {
            _error.emit(null)
        }
        viewModelScope.launch {
            useCase.getWaitlistEntityList().collect{result->

                when(result){
                    is Resource.Loading -> {
                        _isLoading.emit(true)
                    }
                    is Resource.Success -> {
                        _isLoading.emit(false)
                        result.data?.let { _waitlistState.emit(it) }
                    }
                    is Resource.Error -> {
                        _isLoading.emit(false)
                    }
                }

            }
        }
    }

    fun getLocalSearchWaitlist(){
        viewModelScope.launch {
            useCase.searchWaitlist(_searchQuery.value).collect{result->

                when(result){
                    is Resource.Loading -> {
                        _isLoading.value = true
                    }
                    is Resource.Success -> {
                        _isLoading.value = false
                        if (result.data != null) {
                            _waitlistState.emit(result.data)
                        }

                    }
                    is Resource.Error -> {
                        _isLoading.value = false
                    }
                }

            }
        }
    }

}