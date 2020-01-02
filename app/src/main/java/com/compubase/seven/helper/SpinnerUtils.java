package com.compubase.seven.helper;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import androidx.annotation.NonNull;

import java.util.List;

public class SpinnerUtils {

    public static void SetSpinnerAdapter(Context context, Spinner spinner, List<String> serviceName, int view){

        final ArrayAdapter<String> adapter = new ArrayAdapter<String>(context,view,serviceName){

            @NonNull
            public View getView(int position, View convertView, @NonNull ViewGroup parent) {
                View v = super.getView(position, convertView, parent);

//                Typeface externalFont = Typeface.createFromAsset(context.getAssets(), "fonts/dinnext.otf");
//                ((TextView) v).setTypeface(externalFont);

                return v;
            }



            public View getDropDownView(int position, View convertView, @NonNull ViewGroup parent) {
                View v = super.getDropDownView(position, convertView, parent);

//                Typeface externalFont = Typeface.createFromAsset(context.getAssets(), "fonts/dinnext.otf");
//                ((TextView) v).setTypeface(externalFont);

                return v;
            }

        };

        spinner.setAdapter(adapter);
    }
}
