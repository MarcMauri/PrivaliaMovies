package es.marcmauri.privaliamovies.utils;

import android.content.Context;

import java.util.Objects;

public class Util {

    /* VARIABLES */
    public static final String TAG_MOVIE_TYPE_POPULAR = "popular";

    /* URL PATHs */
    public static final String IMG_BASE_URL_LOW_RES = "https://image.tmdb.org/t/p/w300";
    public static final String IMG_BASE_URL_MED_RES = "https://image.tmdb.org/t/p/w500";

    /* GET DEVICE LANGUAGE; Any Spain region returns "es" */
    public static String getDeviceLanguage(final Context context) {
        String lang = context.getResources().getConfiguration().locale.toString().split("_")[0];
        if (Objects.equals(lang, "an")) lang = "es";
        if (Objects.equals(lang, "ca")) lang = "es";
        if (Objects.equals(lang, "eu")) lang = "es";
        if (Objects.equals(lang, "gl")) lang = "es";
        return lang;
    }
}
