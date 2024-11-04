package com.example.limeplayer.repository.local_file

import com.example.limeplayer.repository.local_file.data.LocalFileItem

interface ILocalFileRepository {

    fun getRootFolder(): LocalFileItem

    suspend fun listChildFile(parent: LocalFileItem): List<LocalFileItem>

    suspend fun getVideoFiles(): List<LocalFileItem.Media>

    suspend fun getAudioFiles(): List<LocalFileItem.Media>

    suspend fun getImageFiles(): List<LocalFileItem.Media>

    suspend fun deleteFiles(paths: String): Boolean
}