package com.jesse.contractai.data.repository.file_processing

import java.io.File

interface FileProcessingRepository {

    suspend fun extractTextFromPdfWithIText(pdfFile: File): String?
}