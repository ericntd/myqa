package app.ericn.myqa

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "option")
data class OptionEntity(
    @PrimaryKey val id: Long,
    @ColumnInfo(name = "question_id") val questionId: String,
    val text: String,
    val humanId: String?
)