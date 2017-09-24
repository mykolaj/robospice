package com.octo.android.robospice.retrofit2;

import java.io.File;

import retrofit2.Converter.Factory;
import android.app.Application;

import com.octo.android.robospice.persistence.CacheManager;
import com.octo.android.robospice.persistence.exception.CacheCreationException;
import com.octo.android.robospice.persistence.retrofit2.RetrofitObjectPersisterFactory;
import com.octo.android.robospice.persistence.retrofit2.converter.RetrofitGsonResponseConverter;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * A pre-set, easy to use, retrofit service. It will use retrofit for network
 * requests and both networking and caching will use Gson. To use it, make your
 * service to extend this service.
 */
public abstract class RetrofitGsonSpiceService extends RetrofitSpiceService {

    @Override
    public CacheManager createCacheManager(Application application) throws CacheCreationException {
        CacheManager cacheManager = new CacheManager();
        cacheManager.addPersister(new RetrofitObjectPersisterFactory(getApplication(), getRetrofitToCacheConverter(), getCacheFolder()));
        return cacheManager;
    }

    @Override
    protected Factory createConverterFactory() {
        return GsonConverterFactory.create();
    }

    public File getCacheFolder() {
        return null;
    }

    @Override
    protected RetrofitGsonResponseConverter createRetrofitToCacheConverter() {
        return new RetrofitGsonResponseConverter();
    }
}
