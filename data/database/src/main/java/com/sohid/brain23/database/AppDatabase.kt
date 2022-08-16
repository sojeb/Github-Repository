package com.sohid.brain23.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.sohid.brain23.database.dao.RepositoryDao
import com.sohid.brain23.database.entity.RepositoryEntity

@Database(entities = [RepositoryEntity::class], version = 1, exportSchema = false)
abstract class AppDatabase : RoomDatabase() {

  abstract val repositoryDao: RepositoryDao
}
