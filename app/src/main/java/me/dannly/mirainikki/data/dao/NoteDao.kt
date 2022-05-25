package me.dannly.mirainikki.data.dao

import androidx.room.*
import kotlinx.coroutines.flow.Flow
import me.dannly.mirainikki.data.entity.NoteEntity

@Dao
interface NoteDao {

    @Query("SELECT * FROM NoteEntity WHERE `from` / 86400000 <= :current / 86400000 AND (`to` IS NULL OR `to` / 86400000 >= :current / 86400000) ORDER BY `from` ASC")
    fun observeAllNotes(current: Long): Flow<List<NoteEntity>>

    @Query("SELECT * FROM NoteEntity WHERE id = :id")
    suspend fun getById(id: Int): NoteEntity?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(noteEntity: NoteEntity)

    @Query("DELETE FROM NoteEntity WHERE `from` < :current AND `to` IS NOT NULL AND `to` < :current")
    suspend fun clearPastNotes(current: Long)

    @Delete
    suspend fun delete(vararg noteEntities: NoteEntity)
}