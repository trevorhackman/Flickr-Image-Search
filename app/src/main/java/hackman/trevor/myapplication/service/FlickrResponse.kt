package hackman.trevor.myapplication.service

data class SearchResponse(val items: List<Item>)

data class Item(
    val title: String,
    val media: ImageLink,
    val description: String
)

data class ImageLink(val m: String)
