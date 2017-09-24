package com.octo.android.robospice.persistence.retrofit2;

import java.io.File;
import java.io.IOException;

import roboguice.util.temp.Ln;
import android.app.Application;

import com.octo.android.robospice.persistence.exception.CacheCreationException;
import com.octo.android.robospice.persistence.exception.CacheLoadingException;
import com.octo.android.robospice.persistence.exception.CacheSavingException;
import com.octo.android.robospice.persistence.file.InFileObjectPersister;
import java.io.Closeable;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import com.octo.android.robospice.persistence.retrofit2.converter.RetrofitResponseConverter;
import org.apache.commons.lang3.exception.ExceptionUtils;

public class RetrofitObjectPersister<T> extends InFileObjectPersister<T> {
    private static final String LOG_TAG = "robospice-retrofit2";
    private RetrofitResponseConverter converter;

    // ============================================================================================
    // CONSTRUCTOR
    // ============================================================================================
    public RetrofitObjectPersister(Application application, RetrofitResponseConverter converter, Class<T> clazz, File cacheFolder) throws CacheCreationException {
        super(application, clazz, cacheFolder);
        this.converter = converter;
    }

    public RetrofitObjectPersister(Application application, RetrofitResponseConverter converter, Class<T> clazz) throws CacheCreationException {
        this(application, converter, clazz, null);
    }

    // ============================================================================================
    // METHODS
    // ============================================================================================
    @Override
    public T saveDataToCacheAndReturnData(final T data, final Object cacheKey) throws CacheSavingException {
        try {
            if (isAsyncSaveEnabled()) {
                Thread t = new Thread() {
                    @Override
                    public void run() {
                        try {
                            saveData(data, cacheKey);
                        } catch (IOException e) {
                            Ln.e(e, "An error occured on saving request " + cacheKey + " data asynchronously");
                        } catch (CacheSavingException e) {
                            Ln.e(e, "An error occured on saving request " + cacheKey + " data asynchronously");
                        }
                    }
                };
                t.start();
            } else {
                saveData(data, cacheKey);
            }
        } catch (CacheSavingException e) {
            throw e;
        } catch (Exception e) {
            throw new CacheSavingException(e);
        }
        return data;
    }

    protected void saveData(T data, Object cacheKey) throws IOException, CacheSavingException {
        FileOutputStream outStream = null;
        try {
            outStream = new FileOutputStream(getCacheFile(cacheKey));
            converter.saveObject(data, getHandledClass(), outStream);
        } catch (IOException e) {
            throw e;
        } catch (Exception e) {
            throw new CacheSavingException(e);
        } finally {
            closeStreamQuietly(outStream);
        }
    }

    @Override
    protected T readCacheDataFromFile(File file) throws CacheLoadingException {
        if (!file.exists()) {
            Ln.w("file \"" + file.getAbsolutePath() + "\" does not exist");
            return null;
        }
        FileInputStream inStream = null;
        try {
            inStream = new FileInputStream(file);
            return (T) converter.restoreObject(inStream, getHandledClass());
        } catch (Exception e) {
            throw new CacheLoadingException(e);
        } finally {
            closeStreamQuietly(inStream);
        }
    }

    protected RetrofitResponseConverter getConverter() {
        return this.converter;
    }

    private void closeStreamQuietly(Closeable target) {
        if (target == null) {
            return;
        }
        try {
            target.close();
        } catch (Exception e) {
            Ln.d(LOG_TAG + ": " + getClass().getSimpleName() + " error closing a stream. "
                    + ExceptionUtils.getMessage(e));
        }
    }
}
