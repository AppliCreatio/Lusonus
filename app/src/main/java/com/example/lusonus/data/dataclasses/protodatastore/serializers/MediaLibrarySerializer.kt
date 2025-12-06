package com.example.lusonus.data.dataclasses.protodatastore.serializers

import androidx.datastore.core.Serializer
import com.example.lusonus.data.dataclasses.proto.MediaLibraryProto
import java.io.InputStream
import java.io.OutputStream

object MediaLibrarySerializer : Serializer<MediaLibraryProto> {
    override val defaultValue: MediaLibraryProto = MediaLibraryProto.getDefaultInstance()

    override suspend fun readFrom(input: InputStream): MediaLibraryProto =
        try {
            MediaLibraryProto.parseFrom(input)
        } catch (e: Exception) {
            defaultValue
        }

    override suspend fun writeTo(
        t: MediaLibraryProto,
        output: OutputStream
    ) {
        t.writeTo(output)
    }
}
