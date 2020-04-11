package hackman.trevor.myapplication.search

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import hackman.trevor.myapplication.service.Item
import hackman.trevor.myapplication.service.SearchResponse
import hackman.trevor.myapplication.service.ServiceFactory
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers

class SearchViewModel : ViewModel() {
    private var searchCall: Disposable? = null

    private val _searchResponse = MutableLiveData<List<SearchData>>()
    val searchResponse: LiveData<List<SearchData>> = _searchResponse

    private val _searchError = MutableLiveData<String>()
    val searchError: LiveData<String> = _searchError

    private val _noSearchResults = MutableLiveData<Nothing?>()
    val noSearchResults: LiveData<Nothing?> = _noSearchResults

    private val _navigateToPhotoDetails = MutableLiveData<Item>()
    val navigateToPhotoDetails: LiveData<Item> = _navigateToPhotoDetails

    fun search(query: String) {
        searchCall = ServiceFactory.flickrService.search(tags = query.split(" "))
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                { result -> onSuccess(result) },
                { error -> onError(error.message) }
            )
    }

    private fun onSuccess(response: SearchResponse) {
        _searchResponse.value = response.items.map {
            SearchData(
                it.title,
                it.media.m
            ) { onImageClickListener(it) }
        }.also {
            if (it.isEmpty()) _noSearchResults.value = null
        }
    }

    private fun onError(message: String?) {
        _searchError.value = message
    }

    private fun onImageClickListener(item: Item) {
        _navigateToPhotoDetails.value = item
        _navigateToPhotoDetails.value = null
    }

    override fun onCleared() {
        super.onCleared()
        searchCall?.dispose()
    }
}
