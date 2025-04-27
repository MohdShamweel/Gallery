package com.shamweel.gallery.core.data.repository

import com.shamweel.core.datastore.AppPreferencesDatasource
import com.shamweel.gallery.core.common.MediaViewStyle
import com.shamweel.gallery.core.datastore_test.tesAppPreferencesDataStore
import com.shamweel.gallery.core.model.AppPrefs
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

class AppPreferencesRepositoryTest {

    @OptIn(ExperimentalCoroutinesApi::class)
    private val testScope = TestScope(UnconfinedTestDispatcher())

    private lateinit var subject : AppPreferencesRepositoryImpl

    private lateinit var appPreferencesDatasource: AppPreferencesDatasource

    @get:Rule
    val tmpFolder: TemporaryFolder = TemporaryFolder.builder().assureDeletion().build()

    @Before
    fun setup() {
        appPreferencesDatasource = AppPreferencesDatasource(
            tmpFolder.tesAppPreferencesDataStore()
        )

        subject = AppPreferencesRepositoryImpl(
            appPreferencesDataSource = appPreferencesDatasource
        )

    }

    @Test
    fun appPreferencesRepository_default_appPrefs_data_is_correct() =
        testScope.runTest {
            assertEquals(
                AppPrefs(
                   mediaViewStyleCode = MediaViewStyle.GRID.code
                ),
                subject.appPrefs.first()
            )
        }


}