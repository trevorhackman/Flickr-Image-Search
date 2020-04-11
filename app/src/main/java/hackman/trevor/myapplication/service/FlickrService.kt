package hackman.trevor.myapplication.service

import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Query

interface FlickrService {
    @GET("photos_public.gne?nojsoncallback=1&tagmode=any&format=json")
    fun search(
        @Query("tags") tags: List<String>
    ): Single<SearchResponse>
}
