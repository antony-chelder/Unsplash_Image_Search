package com.data

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize


@Parcelize
 data class UnSplashPhoto(
        val id:String,
        val description:String?,
        val urls: UnSplashPhotoUrls,
        val user : UnSplashUser,
 ) : Parcelable {

    
    @Parcelize
     data class UnSplashPhotoUrls(
         val raw: String,
         val thumb: String,
         val full: String,
         val small: String,
         val regular: String,
     ): Parcelable


    @Parcelize
     data class UnSplashUser(
         val name: String,
         val username: String
     ): Parcelable
     {
         val attributionUrl get() = "https://unsplash.com/$username?utm_source=ImageSearch&utm_medium=referral"
     }
}