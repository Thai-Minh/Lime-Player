package com.example.limeplayer.repository.local_file

import com.example.limeplayer.repository.local_file.data.LocalFileItem

class LocalFileRepositoryImpl : ILocalFileRepository {
    override fun getRootFolder(): LocalFileItem {
        TODO("Not yet implemented")
    }

    override suspend fun listChildFile(parent: LocalFileItem): List<LocalFileItem> {
        TODO("Not yet implemented")
    }

    override suspend fun getVideoFiles(): List<LocalFileItem.Media> {
        TODO("Not yet implemented")
    }

    override suspend fun getAudioFiles(): List<LocalFileItem.Media> {
        TODO("Not yet implemented")
    }

    override suspend fun getImageFiles(): List<LocalFileItem.Media> {
        TODO("Not yet implemented")
    }

    override suspend fun deleteFiles(paths: String): Boolean {
        TODO("Not yet implemented")
    }

}