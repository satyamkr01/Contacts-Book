package com.repo.contactbook

import com.google.gson.annotations.SerializedName

data class Contacts(
    @SerializedName("dataObject")
    val dataObject: List<DataObject>,
    @SerializedName("message")
    val message: String,
    @SerializedName("status")
    val status: Boolean,
    @SerializedName("statusCode")
    val statusCode: Int
)

data class DataObject(
    @SerializedName("myHierarchy")
    val myHierarchy: List<MyHierarchy>
)

data class MyHierarchy(
    @SerializedName("heirarchyList")
    val heirarchyList: List<Heirarchy>
)

data class Heirarchy(
    @SerializedName("contactName")
    val contactName: String,
    @SerializedName("contactNumber")
    val contactNumber: String,
    @SerializedName("designationName")
    val designationName: String
)