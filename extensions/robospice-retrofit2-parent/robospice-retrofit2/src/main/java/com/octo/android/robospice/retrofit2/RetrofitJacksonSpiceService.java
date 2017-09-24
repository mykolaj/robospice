package com.octo.android.robospice.retrofit2;

import java.io.File;

import retrofit2.Converter.Factory;
import android.app.Application;

import com.octo.android.robospice.persistence.CacheManager;
import com.octo.android.robospice.persistence.exception.CacheCreationException;
import com.octo.android.robospice.persistence.retrofit2.RetrofitObjectPersisterFactory;
import com.octo.android.robospice.persistence.retrofit2.converter.RetrofitJacksonResponseConverter;
import retrofit2.converter.jackson.JacksonConverterFactory;

/**
 * A pre-set, easy to use, retrofit service. It will use retrofit for network
 * requests and both networking and caching will use Jackson. To use it, make your
 * service to extend this service.
 */
public abstract class RetrofitJacksonSpiceService extends RetrofitSpiceService {

    @Override
    public CacheManager createCacheManager(Application application) throws CacheCreationException {
        CacheManager cacheManager = new CacheManager();
        cacheManager.addPersister(new RetrofitObjectPersisterFactory(getApplication(), getRetrofitToCacheConverter(), getCacheFolder()));
        return cacheManager;
    }

    @Override
    protected Factory createConverterFactory() {
        return JacksonConverterFactory.create();
    }

    public File getCacheFolder() {
        return null;
    }

    @Override
    protected RetrofitJacksonResponseConverter createRetrofitToCacheConverter() {
        return new RetrofitJacksonResponseConverter();
    }
}
