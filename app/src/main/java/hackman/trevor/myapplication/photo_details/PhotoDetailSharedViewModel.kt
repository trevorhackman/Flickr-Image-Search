package hackman.trevor.myapplication.photo_details

import androidx.lifecycle.ViewModel
import hackman.trevor.myapplication.service.Item

class PhotoDetailSharedViewModel : ViewModel() {
    var itemToDisplay: Item? = null
}
