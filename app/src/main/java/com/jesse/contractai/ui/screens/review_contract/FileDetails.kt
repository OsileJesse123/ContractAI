package com.jesse.contractai.ui.screens.review_contract

import android.net.Uri
import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import java.io.File

@Parcelize
data class FileDetails(
    val fileName: String,
    val fileSize: String,
    val file: File?
): Parcelable{
    fun formatFileName() = if(fileName.length > 25) "${fileName.take(25)}..." else fileName
}
