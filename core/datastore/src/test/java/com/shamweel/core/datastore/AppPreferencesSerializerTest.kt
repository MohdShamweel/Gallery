package com.shamweel.core.datastore

import androidx.datastore.core.CorruptionException
import com.core.datastore.appPreferences
import com.shamweel.gallery.core.common.MediaViewStyle
import junit.framework.TestCase.assertEquals
import kotlinx.coroutines.test.runTest
import org.junit.Test
import java.io.ByteArrayInputStream
import java.io.ByteArrayOutputStream

class AppPreferencesSerializerTest {

    private val appPreferencesSerializer = AppPreferencesSerializer()

    @Test
    fun defaultAppPreferences_mediaViewStyle() {
        assertEquals(
            appPreferences {
                this.mediaViewStyleCode = MediaViewStyle.GRID.code
            },
            appPreferencesSerializer.defaultValue
        )
    }

    @Test
    fun writingAndReadingAppPreferences_outputsCorrectValue() = runTest {
        val expectedAppPreferences = appPreferences {
            mediaViewStyleCode = MediaViewStyle.GRID.code
        }

        val outputStream = ByteArrayOutputStream()

        expectedAppPreferences.writeTo(outputStream)

        val inputStream = ByteArrayInputStream(outputStream.toByteArray())

        val actualAppPreferences = appPreferencesSerializer.readFrom(inputStream)

        assertEquals(
            expectedAppPreferences,
            actualAppPreferences
        )
    }

    @Test(expected = CorruptionException::class)
    fun readingInvalidUserPreferences_throwsCorruptionException() = runTest {
        appPreferencesSerializer.readFrom(ByteArrayInputStream(byteArrayOf(0)))
    }
}