package hackman.trevor.myapplication.service

import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

object ServiceFactory {
    private val client: OkHttpClient =
        OkHttpClient().newBuilder().connectTimeout(60, TimeUnit.SECONDS).build()

    private val retrofit: Retrofit = Retrofit.Builder()
        .client(client)
        .baseUrl("https://api.flickr.com/services/feeds/")
        .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    val flickrService: FlickrService = retrofit.create(FlickrService::class.java)
}
