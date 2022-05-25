package me.dannly.mirainikki.data.entity

import android.net.Uri
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.Date

@Entity
data class NoteEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Int? = null,
    val title: String,
    val text: String,
    val attachments: List<Uri>,
    val from: Long,
    val to: Long? = null
)