package com.example.kitchef.domain.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Ingredient(
    var image: String,
    var title: String
): Parcelable