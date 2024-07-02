package com.example.travelapp.retrofit

class SearchResponse {
    //No need to map all keys, only those the elements you need @SerializedName("items")
    var items: List<Items>? = null
}
