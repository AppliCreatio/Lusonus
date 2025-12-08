package com.example.lusonus.data.dataclasses.protodatastore.serializers

import android.net.Uri
import androidx.datastore.core.CorruptionException
import androidx.datastore.core.Serializer
import com.example.lusonus.data.dataclasses.proto.ProfileProto
import java.io.InputStream
import java.io.OutputStream

object ProfileSerializer : Serializer<ProfileProto> {

    override val defaultValue: ProfileProto =
        ProfileProto.newBuilder()
            .setName("[Username]")
            .setImage(Uri.EMPTY.toString())
            .build()

    override suspend fun readFrom(input: InputStream): ProfileProto =
        try {
            ProfileProto.parseFrom(input)
        } catch (e: Exception) {
            throw CorruptionException("Cannot read proto", e)
        }

    override suspend fun writeTo(t: ProfileProto, output: OutputStream) = t.writeTo(output)
}