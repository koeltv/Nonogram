package fr.utt.if26.nonogram.model;

import androidx.room.TypeConverter;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;

public class GridArrayConverter {
    @TypeConverter
    public static boolean[][] fromString(String string) {
        Type arrayType = new TypeToken<Boolean[][]>() {}.getType();
        return new Gson().fromJson(string, arrayType);
    }

    @TypeConverter
    public static String fromDoubleArray(boolean[][] array) {
        return new Gson().toJson(array);
    }
}
