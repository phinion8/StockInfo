package com.priyanshu.stockinfo.data.csv

import android.util.Log
import com.opencsv.CSVReader
import com.priyanshu.stockinfo.domain.models.IntraDayInfo
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.io.InputStream
import java.io.InputStreamReader
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.util.Locale
import javax.inject.Inject

class IntraDayParser @Inject constructor(): CSVParser<IntraDayInfo> {
    override suspend fun parse(stream: InputStream): List<IntraDayInfo> {
        val csvFileReader = CSVReader(InputStreamReader(stream))
        val list =  withContext(Dispatchers.IO) {
            csvFileReader
                .readAll()
                .drop(1)
                .mapNotNull { line ->
                    val timestamp = line.getOrNull(0) ?: return@mapNotNull null
                    val close = line.getOrNull(4) ?: return@mapNotNull null
                    val pattern = "yyyy-MM-dd HH:mm:ss"
                    val formatter = DateTimeFormatter.ofPattern(pattern, Locale.getDefault())
                    val localDateTime = LocalDateTime.parse(timestamp, formatter)
                    IntraDayInfo(
                        date = localDateTime,
                        close = close.toDouble()
                    )
                }
                .filter {
                    it.date.dayOfMonth == LocalDate.now().minusDays(4).dayOfMonth
                }
                .sortedBy {
                    it.date.hour
                }
                .also {
                    csvFileReader.close()
                }
        }
        Log.d("CSVRESULT", "parse: ${list}")
        return list
    }
}