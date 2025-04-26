package com.shamweel.gallery.core.data.mapper

import com.shamweel.gallery.core.model.MediaSource
import com.shamweel.gallery.media.model.MediaSourceFile

fun MediaSourceFile.toExternalMediaSource() = MediaSource(
    id = id,
    name = name,
    bucketId = bucketId,
    bucketName = bucketName,
    dateAdded = dateAdded,
    author = author,
    size = size,
    mediaType = mediaType,
    mimeType = mimeType,
    contentUri = contentUri
)