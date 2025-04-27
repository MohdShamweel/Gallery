package com.shamweel.gallery.core.common

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.core.content.ContextCompat
import com.shamweel.gallery.core.common.utils.ConversionUtil
import com.shamweel.gallery.core.common.utils.DateUtils
import java.util.concurrent.TimeUnit


fun Double?.orZero(): Double = this ?: 0.0

fun Long?.orZero(): Long = this ?: 0L

fun Long?.secondsToFormatterDate() =
    DateUtils.getDateForPattern(this.orZero().times(TimeUnit.SECONDS.toMillis(1)))

fun Long?.toReadableSize() = ConversionUtil.bytesToHumanReadableSize(this?.toDouble().orZero())

