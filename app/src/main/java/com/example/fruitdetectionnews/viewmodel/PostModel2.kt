package com.example.fruitdetectionnews.viewmodel

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize
import kotlinx.android.parcel.RawValue


data class PostModel2(

    @field:SerializedName("totalResults")
	val totalResults: Int,

    @field:SerializedName("articles")
	val articles: List<ArticlesItem>,

    @field:SerializedName("status")
	val status: String
)

@Parcelize
data class ArticlesItem(

    @field:SerializedName("publishedAt")
	val publishedAt: String,

    @field:SerializedName("author")
	val author: String,

    @field:SerializedName("urlToImage")
	val urlToImage: @RawValue Any,

    @field:SerializedName("description")
	val description: String,

    @field:SerializedName("source")
	val source: @RawValue Source,

    @field:SerializedName("title")
	val title: String,

    @field:SerializedName("url")
	val url: String,

    @field:SerializedName("content")
	val content: String
):Parcelable

data class Source(

	@field:SerializedName("name")
	val name: String,

	@field:SerializedName("id")
	val id: Any
)
