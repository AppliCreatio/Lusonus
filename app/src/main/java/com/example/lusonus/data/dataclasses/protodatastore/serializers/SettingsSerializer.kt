import androidx.datastore.core.CorruptionException
import androidx.datastore.core.Serializer
import com.example.lusonus.data.dataclasses.proto.Settings
import java.io.InputStream
import java.io.OutputStream

object SettingsSerializer : Serializer<Settings> {

    override val defaultValue: Settings =
        Settings.newBuilder()
            .setProfileToggle(false)
            .setFileTypeRestriction(0)
            .build()

    override suspend fun readFrom(input: InputStream): Settings =
        try {
            Settings.parseFrom(input)
        } catch (e: Exception) {
            throw CorruptionException("Cannot read proto", e)
        }

    override suspend fun writeTo(t: Settings, output: OutputStream) = t.writeTo(output)
}