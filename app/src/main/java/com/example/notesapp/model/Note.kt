import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Note(
    val id: Int = 0,
    val noteTitle: String,
    val noteDesc: String
) : Parcelable

