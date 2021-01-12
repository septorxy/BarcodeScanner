/*
 * Copyright 2019, The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.example.barcodescanner.database

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update


@Dao
interface LinkDatabaseDao {
    @Insert
    suspend fun insert(link: LinkSave)

    @Update
    suspend fun update(link: LinkSave)

    @Query("SELECT * from links_table WHERE ID = :key")
    suspend fun get(key: Long): LinkSave?

    @Query("DELETE FROM links_table")
    suspend fun clear()

    @Query("SELECT * FROM links_table ORDER BY ID DESC")
    fun getAllLinks(): LiveData<List<LinkSave>>
}