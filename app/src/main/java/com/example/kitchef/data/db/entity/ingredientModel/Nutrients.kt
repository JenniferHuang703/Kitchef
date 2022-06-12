package com.example.kitchef.data.db.entity.ingredientModel


import com.google.gson.annotations.SerializedName

data class Nutrients(
    @SerializedName("CHOCDF")
    val cHOCDF: Double,
    @SerializedName("ENERC_KCAL")
    val eNERCKCAL: Double,
    @SerializedName("FAT")
    val fAT: Double,
    @SerializedName("FIBTG")
    val fIBTG: Double,
    @SerializedName("PROCNT")
    val pROCNT: Double
)