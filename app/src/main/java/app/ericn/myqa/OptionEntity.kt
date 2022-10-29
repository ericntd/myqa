package app.ericn.myqa

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "option")
data class OptionEntity(
    @PrimaryKey @ColumnInfo(name = "option_id") val id: String,
    @ColumnInfo(name = "question_id") val questionId: String,
    val text: String,
    val humanId: String?
)