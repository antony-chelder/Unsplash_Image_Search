package com.ui.gallery

import androidx.hilt.Assisted
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.*
import androidx.paging.cachedIn
import com.data.UnSplahRepository

class GalleryViewModel @ViewModelInject constructor(
        private val repository: UnSplahRepository,
        @Assisted state: SavedStateHandle
 )
    :ViewModel() {
     private val currentquery = state.getLiveData(CURRENT_QUERY, DEFAULT_QUERY)

        val photos = currentquery.switchMap { queryString ->
            repository.getSearchResult(queryString).cachedIn(viewModelScope)
        }


    fun searchPhotos(query:String){
        currentquery.value = query
    }

    companion object {
        private const val DEFAULT_QUERY = "cats"
        private const val CURRENT_QUERY = "current_query"
    }
}