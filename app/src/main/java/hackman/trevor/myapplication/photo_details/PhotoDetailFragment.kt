package hackman.trevor.myapplication.photo_details

import android.content.Context
import android.os.Bundle
import android.text.Html
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import com.squareup.picasso.Callback
import com.squareup.picasso.Picasso
import hackman.trevor.myapplication.R
import hackman.trevor.myapplication.service.Item
import kotlinx.android.synthetic.main.photo_detail_fragment.*

class PhotoDetailFragment : Fragment() {
    private val photoDetailSharedViewModel: PhotoDetailSharedViewModel by activityViewModels()

    private val requireContext: Context by lazy {
        requireContext()
    }

    private lateinit var item: Item

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.photo_detail_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        photoDetailSharedViewModel.itemToDisplay?.let {
            this.item = it
            bindViews()
        }
    }

    private fun bindViews() {
        bindTitle()
        bindImage()
        bindDescription()
    }

    private fun bindTitle() {
        photo_detail_title_text.text = item.title
    }

    private fun bindImage() =
        Picasso.Builder(requireContext).build().load(item.media.m)
            .placeholder(R.drawable.ic_launcher_background).into(photo_detail_image,
                object : Callback {
                    override fun onSuccess() {
                        bindImageDimensions()
                    }

                    override fun onError(e: Exception?) {
                        photo_detail_title_text.text =
                            requireContext.getString(R.string.main_image_load_error)
                        photo_detail_image.setImageDrawable(null)
                        photo_detail_image.background =
                            requireContext.getDrawable(R.drawable.error_icon)
                    }
                })

    private fun bindDescription() {
        photo_detail_description.text =
            if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.N)
                Html.fromHtml(item.description, Html.FROM_HTML_MODE_LEGACY)
            else
                Html.fromHtml(item.description)

    }

    private fun bindImageDimensions() {
        val width = photo_detail_image.drawable.intrinsicWidth.toString()
        val height = photo_detail_image.drawable.intrinsicHeight.toString()
        photo_detail_image_dimensions.text =
            requireContext().getString(R.string.photo_detail_image_dimensions, width, height)
    }
}
