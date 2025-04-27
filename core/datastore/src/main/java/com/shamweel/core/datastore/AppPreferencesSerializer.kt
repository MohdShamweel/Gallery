package com.shamweel.core.datastore

import androidx.datastore.core.CorruptionException
import androidx.datastore.core.Serializer
import com.core.datastore.AppPreferences
import com.core.datastore.copy
import com.google.protobuf.InvalidProtocolBufferException
import com.shamweel.gallery.core.common.MediaViewStyle
import java.io.InputStream
import java.io.OutputStream
import javax.inject.Inject


class AppPreferencesSerializer @Inject constructor() : Serializer<AppPreferences> {

    override val defaultValue: AppPreferences
        get() = AppPreferences.getDefaultInstance().toDefaults()

    override suspend fun readFrom(input: InputStream): AppPreferences {
        try {
            return AppPreferences.parseFrom(input)
        } catch (exception: InvalidProtocolBufferException) {
            throw CorruptionException("Cannot read proto. ", exception)
        }
    }

    override suspend fun writeTo(t: AppPreferences, output: OutputStream) {
        t.writeTo(output)
    }

    private fun AppPreferences.toDefaults() =
        this.copy {
            this.mediaViewStyleCode = MediaViewStyle.GRID.code
        }

}
