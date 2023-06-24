package com.kampus.merdeka.firebaserealtimedb

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class TireModel(
    var id: String? = null,
    var name: String? = null,
    var description: String? = null
) : Parcelable