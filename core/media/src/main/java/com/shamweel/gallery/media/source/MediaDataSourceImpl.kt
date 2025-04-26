package com.shamweel.gallery.media.source

import android.content.ContentResolver
import android.content.ContentUris
import android.net.Uri
import android.provider.MediaStore
import com.shamweel.gallery.core.common.FileProjection
import com.shamweel.gallery.core.common.ImageProjection
import com.shamweel.gallery.core.common.MediaProjection
import com.shamweel.gallery.core.common.MediaType
import com.shamweel.gallery.core.common.VideoProjection
import com.shamweel.gallery.media.MediaDataSource
import com.shamweel.gallery.media.model.MediaSourceFile
import javax.inject.Inject

class MediaDataSourceImpl @Inject constructor(
    private val contentResolver: ContentResolver
) : MediaDataSource {

    override suspend fun getAllMediaByType(type: MediaType): List<MediaSourceFile> {

        val projection = when (type) {
            MediaType.IMAGE -> ImageProjection()
            MediaType.VIDEO -> VideoProjection()
            MediaType.FILE -> FileProjection()
        }

        return getMediaFiles(
            type = type,
            queryUri = projection.query,
            projection = projection
        )
    }

    override suspend fun getAlbums(): List<MediaSourceFile> {
        val projection = FileProjection()

        return getMediaFiles(
            type = MediaType.FILE,
            queryUri = projection.query,
            projection = projection
        )
    }

    override suspend fun getAlbum(bucketId: Long): List<MediaSourceFile> {
        val projection = FileProjection()
        val selection = "${MediaStore.Images.ImageColumns.BUCKET_ID} = ?"
        val selectionArgs = arrayOf("$bucketId")

        return getMediaFiles(
            type = MediaType.FILE,
            queryUri = projection.query,
            projection = projection,
            selection = selection,
            selectionArgs = selectionArgs
        )

        return emptyList()
    }

    override suspend fun getMedia(mediaId: Long): List<MediaSourceFile> {
        return emptyList()
    }


    private fun getMediaFiles(
        type: MediaType,
        queryUri: Uri,
        projection: MediaProjection,
        selection: String? = null,
        selectionArgs: Array<String>? = null,
    ): List<MediaSourceFile> {
        val mediaFiles = mutableListOf<MediaSourceFile>()
        val orderBy = MediaStore.Video.Media.DATE_TAKEN

        val projectionArray = arrayOf(
            projection.id,
            projection.displayName,
            projection.bucketId,
            projection.bucketDisplayName,
            projection.dateAdded,
            projection.author,
            projection.size,
            projection.mimeType
        )

        contentResolver.query(
            queryUri,
            projectionArray,
            selection,
            selectionArgs,
            "$orderBy DESC"
        )?.use { cursor ->
            val idColumn = cursor.getColumnIndexOrThrow(projection.id)
            val nameColumn = cursor.getColumnIndexOrThrow(projection.displayName)
            val bucketId = cursor.getColumnIndexOrThrow(projection.bucketId)
            val bucketName = cursor.getColumnIndexOrThrow(projection.bucketDisplayName)
            val dateAddedColumn = cursor.getColumnIndexOrThrow(projection.dateAdded)
            val authorColumn = cursor.getColumnIndexOrThrow(projection.author)
            val sizeColumn = cursor.getColumnIndexOrThrow(projection.size)
            val mimeTypeColumn = cursor.getColumnIndexOrThrow(projection.mimeType)

            while (cursor.moveToNext()) {
                val id = cursor.getLong(idColumn)
                val name = cursor.getString(nameColumn)
                val bucketId = cursor.getLong(bucketId)
                val bucketName = cursor.getString(bucketName)
                val dateAdded = cursor.getLong(dateAddedColumn)
                val author = cursor.getString(authorColumn)
                val size = cursor.getLong(sizeColumn)
                val mimeType = cursor.getString(mimeTypeColumn)

                if (name != null && mimeType != null) {
                    val contentUri = ContentUris.withAppendedId(
                        queryUri,
                        id
                    )

                    val mediaType = if (type == MediaType.FILE) {
                        when {
                            mimeType.startsWith("image/") -> MediaType.IMAGE
                            mimeType.startsWith("video/") -> MediaType.VIDEO
                            else -> MediaType.FILE
                        }
                    } else type

                    mediaFiles.add(
                        MediaSourceFile(
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
                    )
                }
            }
        }

        return mediaFiles.toList()
    }
}