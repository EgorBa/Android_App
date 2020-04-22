package com.example.weatherapp;

import androidx.room.TypeConverter;

import java.util.ArrayList;
import java.util.List;

class Converter {

    @TypeConverter
    public static String toString(List<Day> data) {
        StringBuilder str = new StringBuilder();
        for (Day day : data) {
            str.append(day.getTemp()).append("Egor")
                    .append(day.getDatetime()).append("Egor")
                    .append(day.getWeather().getIcon()).append("Egor")
                    .append(day.getWeather().getDescription()).append("Egor")
                    .append(day.getPres()).append("Egor")
                    .append(day.getWindSpd()).append("Egor");
        }
        return str.toString();
    }

    @TypeConverter
    public static List<Day> fromString(String str) {
        String[] split = str.split("Egor");
        int i = 0;
        ArrayList<Day> list = new ArrayList<>();
        while (i < split.length ) {
            list.add(new Day(split[i], split[i + 1], new Description(split[i + 2], split[i + 3]), split[i + 4], split[i + 5]));
            i += 6;
        }
        return list;
    }

}
