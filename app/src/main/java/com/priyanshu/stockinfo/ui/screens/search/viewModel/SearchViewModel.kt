package com.priyanshu.stockinfo.ui.screens.search.viewModel

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.compose.viewModel
import com.priyanshu.stockinfo.data.local.entities.SearchEntity
import com.priyanshu.stockinfo.domain.models.search.BestMatche
import com.priyanshu.stockinfo.domain.models.search.SearchItem
import com.priyanshu.stockinfo.domain.usecases.StockUseCase
import com.priyanshu.stockinfo.utils.Constants
import com.priyanshu.stockinfo.utils.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SearchViewModel @Inject constructor(
    private val stockUseCase: StockUseCase
) : ViewModel() {
    private val _searchQuery: MutableState<String> = mutableStateOf("")
    val searchQuery: State<String> = _searchQuery

    fun updateSearchQuery(query: String) {
        _searchQuery.value = query
    }

    private val _searchListState: MutableStateFlow<SearchItem?> = MutableStateFlow(null)
    val searchListState = _searchListState.asStateFlow()

    private val _searchTypeListState: MutableStateFlow<ArrayList<BestMatche>> = MutableStateFlow(
        ArrayList()
    )
    val searchTypeListState = _searchTypeListState.asStateFlow()

    private val searchList = mutableListOf<BestMatche>()

    private val _isLoading: MutableStateFlow<Boolean> = MutableStateFlow(false)
    val isLoading = _isLoading.asStateFlow()

    private val _localSearchListState: MutableStateFlow<List<SearchEntity>> = MutableStateFlow(
        emptyList()
    )

    val localSearchListState = _localSearchListState.asStateFlow()

    private val _isError: MutableStateFlow<String?> = MutableStateFlow(null)
    val isError = _isError.asStateFlow()

    private val _searchTypeState: MutableStateFlow<SearchType> = MutableStateFlow(SearchType.All)
    val searchTypeState = _searchTypeState.asStateFlow()


    fun updateSearchType(type: SearchType) {
        _searchTypeState.value = type
    }

    private fun resetError() {
        viewModelScope.launch {
            _isError.emit(null)
        }
    }

    private fun clearList() {
        viewModelScope.launch {
            _searchListState.emit(null)
        }
    }

    init {
        getSearchEntityList()
    }

    fun filterListByType(type: SearchType) {
        when (type) {
            SearchType.All -> {
                _searchTypeListState.value = ArrayList()
                viewModelScope.launch {
                    searchList.map {
                        _searchTypeListState.value.add(it)
                    }
                }
            }

            SearchType.Equity -> {
                _searchTypeListState.value = ArrayList()
                viewModelScope.launch {
                    searchList.map {
                        if (it.type == "Equity") {
                            _searchTypeListState.value.add(it)
                        }
                    }
                }
            }

            SearchType.ETF -> {
                _searchTypeListState.value = ArrayList()
                viewModelScope.launch {
                    searchList.map {
                        if (it.type == "ETF") {
                            _searchTypeListState.value.add(it)
                        }
                    }
                }
            }

            SearchType.MutualFunds -> {
                _searchTypeListState.value = ArrayList()
                viewModelScope.launch {
                    searchList.map {
                        if (it.type == "Mutual Fund") {
                            _searchTypeListState.value.add(it)
                        }
                    }
                }
            }
        }
    }

    fun searchTicker(keyword: String) {
        viewModelScope.launch {
            resetError()
            clearList()
            searchList.clear()
            stockUseCase.searchTicker(keyword).collect { result ->
                when (result) {
                    is Resource.Loading -> {
                        _isLoading.emit(true)
                    }

                    is Resource.Success -> {
                        _isLoading.emit(false)
                        result.data?.let {
                            _searchListState.emit(it)
                            searchList.addAll(it.bestMatches)
                            filterListByType(searchTypeState.value)
                        }

                    }

                    is Resource.Error -> {
                        _isLoading.emit(false)
                        _isError.emit(result.message)
                    }
                }
            }
        }
    }

    fun addSearchEntity(
        symbol: String,
        name: String,
        type: String
    ) {
        viewModelScope.launch(Dispatchers.IO) {
            stockUseCase.addSearchEntity(
                SearchEntity(
                    symbol = symbol,
                    name = name,
                    type = type,
                    createdAt = System.currentTimeMillis()
                )
            )
            if (localSearchListState.value.size >= Constants.MINIMUM_LOCAL_SEARCH_ITEM_SIZE) {
                deleteFirstSearchEntity()
            }
        }
    }

    private fun deleteFirstSearchEntity() {
        viewModelScope.launch {
            stockUseCase.deleteFirstSearchEntity()
        }
    }

    private fun getSearchEntityList() {
        viewModelScope.launch {
            stockUseCase.getSearchEntityList().collect { result ->

                when (result) {
                    is Resource.Loading -> {
                        _isLoading.emit(true)
                    }

                    is Resource.Success -> {
                        _isLoading.emit(false)
                        result.data?.let { _localSearchListState.emit(it) }
                    }

                    is Resource.Error -> {
                        _isLoading.emit(false)
                        _isError.emit(result.message)
                    }
                }

            }
        }
    }

}

enum class SearchType {
    All,
    Equity,
    ETF,
    MutualFunds,
}

