package hackman.trevor.myapplication.search

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.GridLayoutManager
import hackman.trevor.myapplication.R
import hackman.trevor.myapplication.photo_details.PhotoDetailFragment
import hackman.trevor.myapplication.photo_details.PhotoDetailSharedViewModel
import kotlinx.android.synthetic.main.search_fragment.*

class SearchFragment : Fragment() {
    private val searchViewModel: SearchViewModel by viewModels()
    private val photoDetailSharedViewModel: PhotoDetailSharedViewModel by activityViewModels()

    private val searchAdapter: SearchAdapter = SearchAdapter()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.search_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        main_recycler_view.adapter = searchAdapter
        main_recycler_view.layoutManager = GridLayoutManager(context, 2)
        setOnSearchClickListener()
        observeSearchResponse()
        observeSearchError()
        observeNoSearchResults()
        observeNavigateToPhotoDetails()
    }

    private fun setOnSearchClickListener() =
        main_search_button.setOnClickListener {
            searchViewModel.search(main_search_input_text.text.toString())
        }

    private fun observeSearchResponse() =
        searchViewModel.searchResponse.observe(viewLifecycleOwner, Observer {
            it ?: return@Observer
            searchAdapter.submitList(it)
        })

    private fun observeSearchError() =
        searchViewModel.searchError.observe(viewLifecycleOwner, Observer {
            it ?: return@Observer
            Toast.makeText(context, it, Toast.LENGTH_LONG).show()
        })

    private fun observeNoSearchResults() =
        searchViewModel.noSearchResults.observe(viewLifecycleOwner, Observer {
            Toast.makeText(context, getString(R.string.main_no_search_results), Toast.LENGTH_LONG)
                .show()
        })

    private fun observeNavigateToPhotoDetails() =
        searchViewModel.navigateToPhotoDetails.observe(viewLifecycleOwner, Observer {
            it ?: return@Observer
            photoDetailSharedViewModel.itemToDisplay = it
            navigateToPhotoDetails()
        })

    private fun navigateToPhotoDetails() =
        parentFragmentManager.beginTransaction()
            .replace(R.id.fragment_container, PhotoDetailFragment())
            .addToBackStack(null)
            .commit()
}
