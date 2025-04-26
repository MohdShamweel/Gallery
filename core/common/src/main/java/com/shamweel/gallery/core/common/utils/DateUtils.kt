package com.shamweel.gallery.core.common.utils

import com.shamweel.gallery.core.common.utils.DateUtils.Format.DAY_MONTHNAME_YEAR_TIME_12
import java.text.SimpleDateFormat
import java.util.Locale

object DateUtils {

    object Format {
        const val DAY_MONTHNAME_YEAR_TIME_24 = "dd MMMM yyyy | HH:mm "
        const val DAY_MONTHNAME_YEAR_TIME_12 = "dd MMMM yyyy | h:mm a "
    }

    fun getDateForPattern(
        timeStamp: Long,
        pattern: String = DAY_MONTHNAME_YEAR_TIME_12,
        locale: Locale = Locale.ENGLISH
    ): String? {
        return try {
            SimpleDateFormat(
                pattern,
                locale
            ).format(timeStamp)
        } catch (e: Exception) {
            null
        }
    }
}