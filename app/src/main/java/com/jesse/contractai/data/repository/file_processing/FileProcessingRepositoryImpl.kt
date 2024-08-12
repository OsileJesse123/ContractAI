package com.jesse.contractai.data.repository.file_processing

import com.itextpdf.kernel.pdf.PdfDocument
import com.itextpdf.kernel.pdf.PdfReader
import com.itextpdf.kernel.pdf.canvas.parser.PdfTextExtractor
import com.jesse.contractai.di.ContractAIDispatchers
import com.jesse.contractai.di.Dispatcher
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import java.io.File
import javax.inject.Inject

class FileProcessingRepositoryImpl @Inject constructor(
    @Dispatcher(ContractAIDispatchers.IO) private val ioDispatcher: CoroutineDispatcher,
): FileProcessingRepository {

    override suspend fun extractTextFromPdfWithIText(pdfFile: File): String? {
        return withContext(ioDispatcher){
            var extractedText: String? = null
            try {
                val pdfDocument = PdfDocument(PdfReader(pdfFile))
                val pageCount = pdfDocument.numberOfPages
                val text = StringBuilder()

                for (i in 1..pageCount) {
                    text.append(PdfTextExtractor.getTextFromPage(pdfDocument.getPage(i)))
                }
                extractedText = text.toString()
                pdfDocument.close()
            } catch (e: Exception) {
                e.printStackTrace()
            }
            extractedText
        }
    }

}