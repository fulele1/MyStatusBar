package com.xaqb.policenw.Other;

import android.app.Application;
import android.content.Context;
import android.os.Build;
import android.os.StrictMode;

import com.tencent.bugly.crashreport.CrashReport;
import com.xaqb.policenw.Utils.ProcUnit;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.cookie.CookieJarImpl;
import com.zhy.http.okhttp.cookie.store.PersistentCookieStore;
import com.zhy.http.okhttp.log.LoggerInterceptor;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by fl on 2017/3/6.
 */

public class MyApplication  extends Application{
    public static String versionName;
    public static Context instance;
    @Override
    public void onCreate() {
        super.onCreate();
        //fl解决7.0以上版本我发安装本地安装包的问题
        StrictMode.VmPolicy.Builder builder = new StrictMode.VmPolicy.Builder();
        StrictMode.setVmPolicy(builder.build());
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR2) {
            builder.detectFileUriExposure();
        }
        versionName = ProcUnit.getVersionName(getApplicationContext());
        instance = getApplicationContext();
        CrashReport.initCrashReport(getApplicationContext());
        CookieJarImpl cookieJar = new CookieJarImpl(new PersistentCookieStore(getApplicationContext()));
        OkHttpClient okHttpClient = new OkHttpClient.Builder()
                .addInterceptor(new LoggerInterceptor("police"))
                .addInterceptor(new Interceptor() {
                    @Override
                    public Response intercept(Chain chain) throws IOException {
                        Request newRequest = chain.request().newBuilder()
                                .addHeader("User-Agent",String.format("XAQianbai Android policexinjinag %s","V"+MyApplication.versionName))
                                .build();
                        return chain.proceed(newRequest);
                    }
                })
                .connectTimeout(10000L, TimeUnit.MILLISECONDS)
                .readTimeout(10000L, TimeUnit.MILLISECONDS)
                //其他配置
                .cookieJar(cookieJar)
                .build();
        OkHttpUtils.initClient(okHttpClient);
    }
}
