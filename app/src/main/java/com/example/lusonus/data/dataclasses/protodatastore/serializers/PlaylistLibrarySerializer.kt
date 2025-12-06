package com.example.lusonus.data.dataclasses.protodatastore.serializers

import androidx.datastore.core.Serializer
import com.example.lusonus.data.dataclasses.proto.PlaylistLibraryProto
import java.io.InputStream
import java.io.OutputStream

object PlaylistLibrarySerializer : Serializer<PlaylistLibraryProto> {
    override val defaultValue: PlaylistLibraryProto = PlaylistLibraryProto.getDefaultInstance()

    override suspend fun readFrom(input: InputStream): PlaylistLibraryProto =
        try {
            PlaylistLibraryProto.parseFrom(input)
        } catch (e: Exception) {
            defaultValue
        }

    override suspend fun writeTo(
        t: PlaylistLibraryProto,
        output: OutputStream
    ) {
        t.writeTo(output)
    }
}
