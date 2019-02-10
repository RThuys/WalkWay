/*
 * Copyright (C) 2016 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.rt.walkway.utils;

import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;

import com.rt.walkway.classes.Path;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.net.HttpURLConnection;

/**
 * Utility functions to handle OpenWeatherMap JSON data.
 */
public final class OpenPathJsonUtils {


    public static Path[] getSimplePathStringsFromJson(String pathJsonStr)
            throws JSONException {
        final String SET_NAME = "cities";

        final String ID = "id";
        final String CITY_NAME = "cityName";
        final String DISTANCE = "distance";
        final String DESCRIPTION = "description";
        final String DIFFICULTY = "difficulty";

        String[] parsedPathData = null;
        Path[] parsedPaths = null;
        JSONObject pathJson = new JSONObject(pathJsonStr);
        JSONArray pathArray = pathJson.getJSONArray(SET_NAME);

        parsedPaths = new Path[pathArray.length()];

        for (int i = 0; i < pathArray.length(); i++) {

            JSONObject cityPath = pathArray.getJSONObject(i);
            //parsedPathData[i] = cityPath.get(CITY_NAME).toString();
            parsedPaths[i] = new Path(
                    Integer.parseInt(cityPath.get(ID).toString()),
                    cityPath.get(CITY_NAME).toString(),
                    Double.parseDouble(cityPath.get(DISTANCE).toString()),
                    cityPath.get(DESCRIPTION).toString(),
                    cityPath.get(DIFFICULTY).toString());
        }
        return parsedPaths;
    }

    public static ContentValues[] getFullWeatherDataFromJson(Context context, String forecastJsonStr) {
        /** This will be implemented in a future lesson **/
        return null;
    }
}