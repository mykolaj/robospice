package com.octo.android.robospice.retrofit2.test.stub;

import com.octo.android.robospice.request.retrofit2.RetrofitSpiceRequest;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

import com.octo.android.robospice.retrofit2.test.model.WeatherResult;

public class RetrofitSpiceRequestStub extends RetrofitSpiceRequest<WeatherResult, FakeWeatherService> {
    private ReentrantLock reentrantLock = new ReentrantLock();
    private Condition loadDataFromNetworkHasBeenExecuted = reentrantLock.newCondition();

    public RetrofitSpiceRequestStub(Class<WeatherResult> clazz) {
        super(clazz, FakeWeatherService.class);
    }

    @Override
    public WeatherResult loadDataFromNetwork() throws Exception {
        reentrantLock.lock();
        try {
            loadDataFromNetworkHasBeenExecuted.signal();
        } finally {
            reentrantLock.unlock();
        }
        return new WeatherResult();
    }

    public void await(long timeout) throws InterruptedException {
        reentrantLock.lock();
        try {
            loadDataFromNetworkHasBeenExecuted.await(timeout, TimeUnit.MILLISECONDS);
        } finally {
            reentrantLock.unlock();
        }
    }

}
