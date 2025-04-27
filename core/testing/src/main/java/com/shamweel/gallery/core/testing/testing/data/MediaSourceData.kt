package com.shamweel.gallery.core.testing.testing.data

import android.net.Uri
import com.shamweel.gallery.core.common.MediaType
import com.shamweel.gallery.core.model.MediaSource

val imageMediaSourceData = MediaSource(
    id = 1000045274,
    name = "Screenshot_20250426-004757.png,",
    bucketId = 1028075469,
    bucketName = "Screenshots",
    dateAdded = 1745614077,
    author = null,
    size = 64521,
    mediaType = MediaType.IMAGE,
    mimeType = "image/png",
    contentUri = Uri.parse("content://media/external/file/1000045274")
)

val videoMediaSourceData= MediaSource(
    id = 1000045274,
    name = "Video_20250426-004757.mp4,",
    bucketId = 1028075469,
    bucketName = "Screenshots",
    dateAdded = 1745614077,
    author = null,
    size = 645212,
    mediaType = MediaType.VIDEO,
    mimeType = "video/mp4",
    contentUri = Uri.parse("content://media/external/file/1000045274")
)