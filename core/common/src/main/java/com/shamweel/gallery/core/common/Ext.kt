package com.shamweel.gallery.core.common

import com.shamweel.gallery.core.common.utils.ConversionUtil
import com.shamweel.gallery.core.common.utils.DateUtils
import java.util.concurrent.TimeUnit


fun Double?.orZero(): Double = this ?: 0.0

fun Long?.orZero(): Long = this ?: 0L

fun Int?.orZero(): Int = this ?: 0

fun Long?.secondsToFormatterDate() =
    DateUtils.getDateForPattern(this.orZero().times(TimeUnit.SECONDS.toMillis(1)))

fun Long?.toReadableSize() = ConversionUtil.bytesToHumanReadableSize(this?.toDouble().orZero())

