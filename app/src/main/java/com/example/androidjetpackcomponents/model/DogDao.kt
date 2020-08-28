package com.example.androidjetpackcomponents.model

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query

@Dao
interface DogDao {

    @Insert
    suspend fun insertAll(vararg dogs: DogBreed): List<Long>

    @Query("SELECT * FROM dogBreed")
    suspend fun getAllDogs(): List<DogBreed>

    @Query("SELECT * FROM dogbreed WHERE uuid = :dogID")
    suspend fun getDog(dogID:Int): DogBreed

    @Query("DELETE FROM dogbreed")
    suspend fun deleteAllDogs()
}