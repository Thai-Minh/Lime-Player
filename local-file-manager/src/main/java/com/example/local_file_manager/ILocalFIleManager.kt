package com.example.local_file_manager

import java.io.File

interface ILocalFIleManager {
    fun getRootFolder(): File

    suspend fun listChildFile(parent: File): List<File>

    suspend fun getVideoFiles(): List<File>

    suspend fun getAudioFiles(): List<File>

    suspend fun getImageFiles(): List<File>

    suspend fun deleteFiles(paths: String): Boolean
}