package hackman.trevor.myapplication.search

import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView

class SearchAdapter : ListAdapter<SearchData, SearchViewHolder>(Differ()) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SearchViewHolder =
        SearchViewHolder(SearchItemView(parent.context))

    override fun onBindViewHolder(holder: SearchViewHolder, position: Int) =
        holder.bind(getItem(position))

}

class SearchViewHolder(private val view: SearchItemView) : RecyclerView.ViewHolder(view) {
    fun bind(searchData: SearchData) = view.setData(searchData)
}

data class SearchData(val title: String, val imageUrl: String, val onImageClick: () -> Unit)

class Differ : DiffUtil.ItemCallback<SearchData>() {
    override fun areItemsTheSame(oldItem: SearchData, newItem: SearchData): Boolean =
        oldItem.imageUrl == newItem.imageUrl

    override fun areContentsTheSame(oldItem: SearchData, newItem: SearchData): Boolean =
        oldItem == newItem
}
