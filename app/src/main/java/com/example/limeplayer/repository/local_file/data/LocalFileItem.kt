package com.example.limeplayer.repository.local_file.data

sealed class LocalFileItem(
    open val path: String,
    open val fileName: String,
    open val dateModified: Long,
    open val fileSize: Long
) {
    data class Media(
        override val path: String,
        override val fileName: String,
        override val dateModified: Long,
        override val fileSize: Long,
        val type: LocalFileType,
        val duration: Long? = null
    ): LocalFileItem(
        path = path,
        fileName = fileName,
        dateModified = dateModified,
        fileSize = fileSize
    )

    data class Folder(
        override val path: String,
        override val fileName: String,
        override val dateModified: Long,
        override val fileSize: Long,
        val childCount: Int = 0,
    ): LocalFileItem(
        path = path,
        fileName = fileName,
        dateModified = dateModified,
        fileSize = fileSize
    )
}

enum class LocalFileType {
    Video, Audio, Image, Other
}