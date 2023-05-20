package org.d3if3045.convertsuhu.db

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "suhu")
data class SuhuEntity(
    @PrimaryKey(autoGenerate = true)
    var id: Long = 0L,
    var tanggal: Long = System.currentTimeMillis(),
    var value: Float,
    var from: String,
    var to: String
)
