package com.example.local_file_manager

import java.io.File

class LocalFileManagerImpl: ILocalFIleManager {
    override fun getRootFolder(): File {
        TODO("Not yet implemented")
    }

    override suspend fun listChildFile(parent: File): List<File> {
        TODO("Not yet implemented")
    }

    override suspend fun getVideoFiles(): List<File> {
        TODO("Not yet implemented")
    }

    override suspend fun getAudioFiles(): List<File> {
        TODO("Not yet implemented")
    }

    override suspend fun getImageFiles(): List<File> {
        TODO("Not yet implemented")
    }

    override suspend fun deleteFiles(paths: String): Boolean {
        TODO("Not yet implemented")
    }
}