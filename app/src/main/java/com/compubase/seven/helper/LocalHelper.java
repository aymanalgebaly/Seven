package com.compubase.seven.helper;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.Build;
import android.preference.PreferenceManager;

import java.util.Locale;

public class LocalHelper {
    private static final String SELECTED_LANGUAGE = "Local.Helper.Selected.Language";

    public static Context onAttach(Context context){
        String lang = getPersestedData(context, Locale.getDefault().getLanguage());

        return setLocale(context,lang);
    }

    public static Context onAttach(Context context, String defaultLanguage){
        String lang = getPersestedData(context, defaultLanguage);

        return setLocale(context,lang);
    }

    public static Context setLocale(Context context, String lang) {

        persis (context,lang);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N)
        return updateResources(context,lang);

        return updateResourcesLegacy(context,lang);
    }

    @TargetApi(Build.VERSION_CODES.N)
    private static Context updateResources(Context context, String lang) {

        Locale locale = new Locale(lang);
        Locale.setDefault(locale);

        Configuration configuration = context.getResources().getConfiguration();
        configuration.setLocale(locale);
        configuration.setLayoutDirection(locale);
        return context.createConfigurationContext(configuration);
    }

    @SuppressWarnings("deprecation")
    private static Context updateResourcesLegacy(Context context, String lang) {

        Locale locale = new Locale(lang);
        Locale.setDefault(locale);

        Resources resources = context.getResources();
        Configuration config = resources.getConfiguration();
        config.locale = locale;

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1)
            config.setLayoutDirection(locale);

        resources.updateConfiguration(config,resources.getDisplayMetrics());

        return context;
    }

    private static void persis(Context context, String lang) {
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
        SharedPreferences.Editor editor = sharedPreferences.edit();

        editor.putString(SELECTED_LANGUAGE,lang);
        editor.apply();
    }

    public static String getPersestedData(Context context, String language) {

        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);

        return preferences.getString(SELECTED_LANGUAGE,language);
    }
}
