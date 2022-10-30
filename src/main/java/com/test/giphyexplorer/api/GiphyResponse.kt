package com.test.giphyexplorer.api

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class GiphyResponse(
    @SerializedName("data") val data: List<Data>,
    @SerializedName("pagination") val pagination: Pagination?
) : Parcelable {

    @Parcelize
    data class Data(
        @SerializedName("username") val username: String?,
        @SerializedName("title") val title: String?,
        @SerializedName("images") val images: Images?,
    ) : Parcelable {

        @Parcelize
        data class Images(
            @SerializedName("original") val original: Original?,
            @SerializedName("fixed_width_downsampled") val fixedWidthDownSampled: Original?
        ) : Parcelable {

            @Parcelize
            data class Original(
                @SerializedName("height") val height: String?,
                @SerializedName("width") val width: String?,
                @SerializedName("size") val size: String?,
                @SerializedName("url") val url: String?,
                @SerializedName("webp") val webp: String?
            ) : Parcelable

            @Parcelize
            data class FixedWidthDownSampled(
                @SerializedName("height") val height: String?,
                @SerializedName("width") val width: String?,
                @SerializedName("webp") val webp: String?,
                @SerializedName("webp_size") val webpSize: String?
            ) : Parcelable
        }
    }

    @Parcelize
    data class Pagination(
        @SerializedName("total_count") val totalCount: Int?,
        @SerializedName("count") val count: Int?,
        @SerializedName("offset") val offset: Int?
    ) : Parcelable
}