package com.shamweel.core.datastore

import com.shamweel.gallery.core.common.MediaViewStyle
import com.shamweel.gallery.core.datastore_test.tesAppPreferencesDataStore
import com.shamweel.gallery.core.model.AppPrefs
import junit.framework.Assert.assertTrue
import junit.framework.TestCase.assertEquals
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.TestScope
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.rules.TemporaryFolder

class AppPreferencesDatasourceTest {

    @OptIn(ExperimentalCoroutinesApi::class)
    private val testScope = TestScope(UnconfinedTestDispatcher())

    private lateinit var subject: AppPreferencesDatasource

    @get:Rule
    val tmpFolder: TemporaryFolder = TemporaryFolder.builder().assureDeletion().build()

    @Before
    fun setup() {
        subject = AppPreferencesDatasource(
            tmpFolder.tesAppPreferencesDataStore()
        )
    }

    @Test
    fun shouldMediaViewStyleBeGridByDefaultDefault() = runTest {
       assertTrue(subject.prefs.first().mediaViewStyleCode == MediaViewStyle.GRID.code)
    }

    @Test
    fun mediaViewStyleShouldChangedLaterWhenSet() = runTest {
        subject
            .updatePreferences(
                AppPrefs(
                    mediaViewStyleCode = MediaViewStyle.LINEAR.code,
                )
            )

        assertEquals(
            MediaViewStyle.LINEAR.code,
            subject.prefs.first().mediaViewStyleCode
        )
    }

}