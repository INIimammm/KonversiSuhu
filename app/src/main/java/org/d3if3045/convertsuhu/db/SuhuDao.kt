package org.d3if3045.convertsuhu.db

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query

@Dao
interface SuhuDao {
    @Insert
    fun insert(suhu: SuhuEntity)

    @Query("SELECT * FROM suhu ORDER BY id DESC")
    fun getLastSuhu(): LiveData<List<SuhuEntity>>

    @Query("DELETE FROM suhu")
    fun clearData()

}