package com.octo.android.robospice.persistence.retrofit2;

import android.app.Application;
import com.octo.android.robospice.persistence.exception.CacheCreationException;
import com.octo.android.robospice.persistence.retrofit2.converter.RetrofitJacksonResponseConverter;


public class InFileWeatherPersisterJacksonTest extends JsonObjectPersisterFactoryTest {

    @Override
    protected RetrofitObjectPersisterFactory getRetrofitObjectPersisterFactory(Application application) throws CacheCreationException {
        return new RetrofitObjectPersisterFactory(application, new RetrofitJacksonResponseConverter());

    }

}
