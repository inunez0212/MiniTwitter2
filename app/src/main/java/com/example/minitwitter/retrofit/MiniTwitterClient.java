package com.example.minitwitter.retrofit;

import com.example.minitwitter.common.Constants;

import java.security.cert.CertificateException;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MiniTwitterClient {

    private static MiniTwitterClient instancia = null;

    private MiniTwitterService miniTwitterService;
    private MiniTwitterService miniTwitterServiceAuth;

    private Retrofit retrofit;
    private Retrofit retrofitAuth;

    private MiniTwitterClient(){

        retrofitAuth = new Retrofit.Builder()
                .baseUrl(Constants.URL_BASE_MINITWITTER)
                .addConverterFactory(GsonConverterFactory.create())
                .client(getUnsafeOkHttpClient(true))
                .build();

        retrofit = new Retrofit.Builder()
                .baseUrl(Constants.URL_BASE_MINITWITTER)
                .addConverterFactory(GsonConverterFactory.create())
                .client(getUnsafeOkHttpClient(false))
                .build();

        miniTwitterService = retrofit.create(MiniTwitterService.class);
        miniTwitterServiceAuth = retrofitAuth.create(MiniTwitterService.class);
    }

    public static MiniTwitterClient getInstance() {
        if(instancia == null){
            instancia = new MiniTwitterClient();
        }
        return instancia;
    }

    public MiniTwitterService getMiniTwitterService() {
        return miniTwitterService;
    }
    public MiniTwitterService getMiniTwitterServiceAuth() {
        return miniTwitterServiceAuth;
    }


    public static OkHttpClient getUnsafeOkHttpClient(boolean authorizedRequired) {
        try {
            // Create a trust manager that does not validate certificate chains
            final TrustManager[] trustAllCerts = new TrustManager[] {
                    new X509TrustManager() {
                        @Override
                        public void checkClientTrusted(java.security.cert.X509Certificate[] chain, String authType) throws CertificateException {
                        }

                        @Override
                        public void checkServerTrusted(java.security.cert.X509Certificate[] chain, String authType) throws CertificateException {
                        }

                        @Override
                        public java.security.cert.X509Certificate[] getAcceptedIssuers() {
                            return new java.security.cert.X509Certificate[]{};
                        }
                    }
            };

            // Install the all-trusting trust manager
            final SSLContext sslContext = SSLContext.getInstance("SSL");
            sslContext.init(null, trustAllCerts, new java.security.SecureRandom());

            // Create an ssl socket factory with our all-trusting manager
            final SSLSocketFactory sslSocketFactory = sslContext.getSocketFactory();

            OkHttpClient.Builder builder = new OkHttpClient.Builder();
            builder.sslSocketFactory(sslSocketFactory, (X509TrustManager)trustAllCerts[0]);
            builder.hostnameVerifier(new HostnameVerifier() {
                @Override
                public boolean verify(String hostname, SSLSession session) {
                    return true;
                }
            });
            if(authorizedRequired){
                builder.addInterceptor(new AuthInterceptor());
            }
            OkHttpClient okHttpClient = builder.build();

            return okHttpClient;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

}
