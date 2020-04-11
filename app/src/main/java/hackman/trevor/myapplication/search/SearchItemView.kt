package hackman.trevor.myapplication.search

import android.content.Context
import android.util.AttributeSet
import android.view.View
import android.widget.LinearLayout
import com.squareup.picasso.Callback
import com.squareup.picasso.Picasso
import hackman.trevor.myapplication.R
import kotlinx.android.synthetic.main.search_item.view.*

class SearchItemView @JvmOverloads constructor(
    context: Context,
    attributeSet: AttributeSet? = null
) : LinearLayout(context, attributeSet) {
    private lateinit var searchData: SearchData

    init {
        View.inflate(context,
            R.layout.search_item, this)
    }

    fun setData(searchData: SearchData) {
        this.searchData = searchData
        setTextAndImage()
        setOnImageClickListener()
    }

    private fun setTextAndImage() {
        search_item_text.text = null

        Picasso.get().load(searchData.imageUrl)
            .placeholder(R.drawable.ic_launcher_background).into(search_item_image,
                object : Callback {
                    override fun onSuccess() {
                        search_item_text.text = searchData.title
                    }

                    override fun onError(e: Exception?) {
                        search_item_text.text = context.getString(R.string.main_image_load_error)
                        search_item_image.setImageDrawable(null)
                        search_item_image.background = context.getDrawable(R.drawable.error_icon)
                    }
                })
    }

    private fun setOnImageClickListener() =
        search_item_image.setOnClickListener { searchData.onImageClick() }
}
