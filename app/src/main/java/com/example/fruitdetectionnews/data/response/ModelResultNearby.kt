package com.example.fruitdetectionnews.data.response

import com.example.fruitdetectionnews.data.model.ModelResults
import com.google.gson.annotations.SerializedName

class ModelResultNearby {

    @SerializedName("results")
    val modelResults: List<ModelResults> = emptyList()
}