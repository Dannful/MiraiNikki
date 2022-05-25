package me.dannly.mirainikki.data

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import me.dannly.mirainikki.data.converters.Converters
import me.dannly.mirainikki.data.dao.NoteDao
import me.dannly.mirainikki.data.entity.NoteEntity

@Database(entities = [NoteEntity::class], version = 5)
@TypeConverters(Converters::class)
abstract class NoteDatabase : RoomDatabase() {

    abstract val dao: NoteDao
}