package com.jesse.contractai.ui.screens.review_contract

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class FileDetails(
    val fileName: String,
    val fileSize: String
): Parcelable{
    fun formatFileName() = if(fileName.length > 25) "${fileName.take(25)}..." else fileName
}
