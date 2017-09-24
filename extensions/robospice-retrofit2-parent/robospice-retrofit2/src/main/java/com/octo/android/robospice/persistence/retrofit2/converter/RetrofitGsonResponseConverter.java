package com.octo.android.robospice.persistence.retrofit2.converter;

import com.google.gson.Gson;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import roboguice.util.temp.Ln;

/**
 * Saves and restores a data received by Retrofit into/from a file.
 * Uses Gson for the data conversion.
 */
public class RetrofitGsonResponseConverter implements RetrofitResponseConverter {

    private static final String LOG_TAG = "robospice-retrofit2";
    private final Gson gson;

    public RetrofitGsonResponseConverter() {
        this(new Gson());
    }

    public RetrofitGsonResponseConverter(Gson gson) {
        if (gson != null) {
            this.gson = gson;
        } else {
            this.gson = new Gson();
        }
    }

    @Override
    public void saveObject(Object object, Class<?> clzz, OutputStream out) throws Exception {
        if (object == null) {
            Ln.d(LOG_TAG + ": " + getClass().getSimpleName() + " not saving a 'null' object");
            return;
        }
        if (out == null) {
            Ln.d(LOG_TAG + ": " + getClass().getSimpleName() + " not saving '" + clzz.getSimpleName()
                    + "'. Provided output is 'null'");
            return;
        }
        OutputStreamWriter writer = new OutputStreamWriter(out, DEFAULT_CHARSET);
        this.gson.toJson(object, writer);
        writer.close();
    }

    @Override
    public Object restoreObject(InputStream in, Class<?> clzz) throws Exception {
        if (in == null) {
            Ln.d(LOG_TAG + ": " + getClass().getSimpleName() + " can not restore '"
                    + clzz.getSimpleName() + "' from a 'null' input");
            return null;
        }
        return this.gson.fromJson(new InputStreamReader(in, DEFAULT_CHARSET), clzz);
    }

}
