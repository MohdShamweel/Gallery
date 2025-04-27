package com.shamweel.gallery.core.testing.rules

import android.Manifest
import android.os.Build.VERSION.SDK_INT
import android.os.Build.VERSION_CODES.TIRAMISU
import androidx.test.rule.GrantPermissionRule.grant
import org.junit.rules.TestRule

class GrantMediaStoragePermissionRule : TestRule by if (SDK_INT >= TIRAMISU) grant(
    Manifest.permission.READ_MEDIA_VIDEO,
    Manifest.permission.READ_MEDIA_IMAGES,
) else
    grant(Manifest.permission.READ_EXTERNAL_STORAGE)