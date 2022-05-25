package me.dannly.mirainikki.domain.model

import android.net.Uri

data class Note(
    val id: Int? = null,
    val title: String,
    val text: String,
    val attachments: List<Uri> = emptyList(),
    val from: Long,
    val to: Long? = null
)
