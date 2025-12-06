package com.example.lusonus.data.dataclasses.protodatastore.serializers

import androidx.datastore.core.Serializer
import com.example.lusonus.data.dataclasses.proto.FolderLibraryProto
import java.io.InputStream
import java.io.OutputStream

object FolderLibrarySerializer : Serializer<FolderLibraryProto> {
    override val defaultValue: FolderLibraryProto = FolderLibraryProto.getDefaultInstance()

    override suspend fun readFrom(input: InputStream): FolderLibraryProto =
        try {
            FolderLibraryProto.parseFrom(input)
        } catch (e: Exception) {
            defaultValue
        }

    override suspend fun writeTo(
        t: FolderLibraryProto,
        output: OutputStream
    ) {
        t.writeTo(output)
    }
}
