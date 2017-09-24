package com.octo.android.robospice.retrofit2.test;

import com.octo.android.robospice.retrofit2.RetrofitJacksonSpiceService;
import java.io.File;

public class TestRetrofitJacksonSpiceService extends RetrofitJacksonSpiceService {

    @Override
    protected String getServerUrl() {
        return "http://non-blank.random.server/";
    }

    @Override
    public File getCacheFolder() {
        return new File("/");
    }

}
