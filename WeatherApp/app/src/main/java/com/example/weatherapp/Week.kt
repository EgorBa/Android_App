package com.example.weatherapp

import android.os.Parcelable
import androidx.room.*
import com.squareup.moshi.Json
import kotlinx.android.parcel.Parcelize


@Entity
@Parcelize
data class Week(

    @PrimaryKey
    val id: Long = 0,

    @ColumnInfo
    @TypeConverters(Converter::class)
    val data: List<Day>,

    @ColumnInfo
    @Json(name = "city_name") val cityName: String
) : Parcelable

@Dao
interface WeekDao {

    @Query("SELECT * FROM week WHERE id == :id")
    fun getById(id: Long): Week

    @Insert
    fun insert(employee: Week)

    @Update
    fun update(employee: Week)

    @Delete
    fun delete(employee: Week)

}

@Parcelize
data class Day(
    val temp: String,
    val datetime: String,
    val weather: Description,
    val pres: String,
    @Json(name = "wind_spd") val windSpd: String
) : Parcelable

@Parcelize
data class Description(
    val icon: String,
    val description: String
) : Parcelable