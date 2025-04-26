package com.shamweel.gallery.core.common

import android.net.Uri
import android.os.Build
import android.provider.MediaStore

abstract class MediaProjection() {
    abstract val bucketId: String
    abstract val bucketDisplayName: String
    abstract val id: String
    abstract val displayName: String
    abstract val dateAdded: String
    abstract val author: String
    abstract val size: String
    abstract val mimeType: String
    abstract val query: Uri
}

class ImageProjection(
    override val bucketId: String = MediaStore.Images.ImageColumns.BUCKET_ID,
    override val bucketDisplayName: String = MediaStore.Images.ImageColumns.BUCKET_DISPLAY_NAME,
    override val id: String = MediaStore.Images.ImageColumns._ID,
    override val displayName: String = MediaStore.Images.ImageColumns.DISPLAY_NAME,
    override val dateAdded: String = MediaStore.Images.ImageColumns.DATE_ADDED,
    override val author: String = MediaStore.Images.ImageColumns.AUTHOR,
    override val size: String = MediaStore.Images.ImageColumns.SIZE,
    override val mimeType: String = MediaStore.Images.ImageColumns.MIME_TYPE,
    override val query: Uri = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
        MediaStore.Images.Media.getContentUri(MediaStore.VOLUME_EXTERNAL)
    } else MediaStore.Images.Media.EXTERNAL_CONTENT_URI
) : MediaProjection()

class VideoProjection(
    override val bucketId: String = MediaStore.Video.VideoColumns.BUCKET_ID,
    override val bucketDisplayName: String = MediaStore.Video.VideoColumns.BUCKET_DISPLAY_NAME,
    override val id: String = MediaStore.Video.VideoColumns._ID,
    override val displayName: String = MediaStore.Video.VideoColumns.DISPLAY_NAME,
    override val dateAdded: String = MediaStore.Video.VideoColumns.DATE_ADDED,
    override val author: String = MediaStore.Video.VideoColumns.AUTHOR,
    override val size: String = MediaStore.Video.VideoColumns.SIZE,
    override val mimeType: String = MediaStore.Video.VideoColumns.MIME_TYPE,
    override val query: Uri = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
        MediaStore.Video.Media.getContentUri(MediaStore.VOLUME_EXTERNAL)
    } else MediaStore.Video.Media.EXTERNAL_CONTENT_URI
) : MediaProjection()

class FileProjection(
    override val bucketId: String = MediaStore.Files.FileColumns.BUCKET_ID,
    override val bucketDisplayName: String = MediaStore.Files.FileColumns.BUCKET_DISPLAY_NAME,
    override val id: String = MediaStore.Files.FileColumns._ID,
    override val displayName: String = MediaStore.Files.FileColumns.DISPLAY_NAME,
    override val dateAdded: String = MediaStore.Files.FileColumns.DATE_ADDED,
    override val author: String = MediaStore.Files.FileColumns.AUTHOR,
    override val size: String = MediaStore.Files.FileColumns.SIZE,
    override val mimeType: String = MediaStore.Files.FileColumns.MIME_TYPE,
    override val query: Uri = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
        MediaStore.Files.getContentUri(MediaStore.VOLUME_EXTERNAL)
    } else MediaStore.Files.getContentUri("external")
) : MediaProjection()