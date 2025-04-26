package com.shamweel.gallery.core.model

import android.net.Uri
import com.shamweel.gallery.core.common.MediaType

data class MediaSource(
    val id: Long?,
    val name: String?,
    val bucketId: Long?,
    val bucketName: String?,
    val dateAdded: Long?,
    val author: String?,
    val size: Long?,
    val mediaType: MediaType?,
    val mimeType: String?,
    val contentUri: Uri?
)
