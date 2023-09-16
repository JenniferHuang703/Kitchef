package com.app.kitchef.domain.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Ingredient(
    val id: Int,
    val name: String,
    val image: String,
): Parcelable