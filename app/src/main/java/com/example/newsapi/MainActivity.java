package com.example.newsapi;

import android.app.PendingIntent;
import android.content.ComponentName;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.support.customtabs.CustomTabsCallback;
import android.support.customtabs.CustomTabsClient;
import android.support.customtabs.CustomTabsIntent;
import android.support.customtabs.CustomTabsServiceConnection;
import android.support.customtabs.CustomTabsSession;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.example.newsapi.api.ArticlesItem;
import com.example.newsapi.api.ResponseApi;

import java.lang.reflect.Method;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {
    RecyclerView rvNews;
    NewsAdapter newsAdapter;
    List<ArticlesItem> data;
    CustomTabsClient myCustomTabsClient;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_main );

        rvNews = findViewById( R.id.rv_news );
        newsAdapter = new NewsAdapter( this, data );

        getDataNationalGeographic();
        Intent intent = new Intent( this,WebCustom.class );


        CustomTabsServiceConnection tabsServiceConnection = new CustomTabsServiceConnection() {
            @Override
            public void onCustomTabsServiceConnected(ComponentName componentName, CustomTabsClient customTabsClient) {
                myCustomTabsClient = customTabsClient;
            }

            @Override
            public void onServiceDisconnected(ComponentName name) {
                myCustomTabsClient = null;
            }
        };
        String packageName = "com.android.chrome";
        CustomTabsClient.bindCustomTabsService( this,packageName,tabsServiceConnection );
    }

//    public void customService(View view) {
//        CustomTabsIntent.Builder builder = new CustomTabsIntent.Builder( getSession() );
//
//        builder.setToolbarColor( ContextCompat.getColor( this, R.color.browser_actions_title_color ) );
//
//        builder.setCloseButtonIcon( BitmapFactory.decodeResource( getResources(), R.mipmap.ic_arrow_back_white_24dp ) );
//        builder.setActionButton( BitmapFactory.decodeResource( getResources(),R.mipmap.ic_file_download_white_24dp ),"Material Color Picker",actionButton() );
//
//
//        builder.setStartAnimations( this, R.anim.slide_in_left, R.anim.slide_in_left );
//        builder.setExitAnimations( this, R.anim.slide_out_left, R.anim.slide_out_left );
////        CustomTabsIntent tabsIntent = builder.build();
////        tabsIntent.launchUrl( this, Uri.parse( getIntent().getStringExtra( "url" ) ) );
//
//        //menambahkan menu
//        builder.addMenuItem( "Share",setMenuItem());
//
//    }


    private PendingIntent setMenuItem() {
        Intent intentShare = new Intent( Intent.ACTION_SEND );
        intentShare.setType( "text/plain" );
        intentShare.putExtra( Intent.EXTRA_SUBJECT,"Github Site" );
        intentShare.putExtra( Intent.EXTRA_TEXT,getIntent().getStringExtra( "url" ) );
        return PendingIntent.getActivity( this,0,intentShare,0 );
    }

    private PendingIntent actionButton() {
        Intent intentPlayStore = new Intent( Intent.ACTION_VIEW,Uri.parse( "https://play.google.com/store/apps/details?id=com.swifty.mygithub&rdid=com.swifty.mygithub" ) );
        return PendingIntent.getActivity( this,0,intentPlayStore,0 );
    }


    private CustomTabsSession getSession() {
        return myCustomTabsClient.newSession( new CustomTabsCallback() {
            @Override
            public void onNavigationEvent(int navigationEvent, Bundle extras) {
                super.onNavigationEvent( navigationEvent, extras );
            }
        } );
    }


    private void getDataScientist() {
        ConfigRetrofit.getInstance().getDataScientist().enqueue( new Callback<ResponseApi>() {
            @Override
            public void onResponse(Call<ResponseApi> call, Response<ResponseApi> response) {
                data = response.body().getArticles();
                newsAdapter = new NewsAdapter( MainActivity.this, data );
                rvNews.setLayoutManager( new LinearLayoutManager( MainActivity.this ) );
                rvNews.setAdapter( newsAdapter );
            }

            @Override
            public void onFailure(Call<ResponseApi> call, Throwable t) {

            }
        } );
    }

    private void getDataM() {
        ConfigRetrofit.getInstance().getDataM().enqueue( new Callback<ResponseApi>() {
            @Override
            public void onResponse(Call<ResponseApi> call, Response<ResponseApi> response) {
                data = response.body().getArticles();
                newsAdapter = new NewsAdapter( MainActivity.this, data );
                rvNews.setLayoutManager( new LinearLayoutManager( MainActivity.this ) );
                rvNews.setAdapter( newsAdapter );
            }

            @Override
            public void onFailure(Call<ResponseApi> call, Throwable t) {

            }
        } );
    }

    private void getDataNationalGeographic() {
        ConfigRetrofit.getInstance().getDataNational().enqueue( new Callback<ResponseApi>() {
            @Override
            public void onResponse(Call<ResponseApi> call, Response<ResponseApi> response) {
                data = response.body().getArticles();
                newsAdapter = new NewsAdapter( MainActivity.this, data );
                rvNews.setLayoutManager( new LinearLayoutManager( MainActivity.this ) );
                rvNews.setAdapter( newsAdapter );
            }

            @Override
            public void onFailure(Call<ResponseApi> call, Throwable t) {
                Toast.makeText( MainActivity.this, t.getMessage(), Toast.LENGTH_SHORT ).show();
            }
        } );

    }

    private void getDataTech() {
        ConfigRetrofit.getInstance().getDataTech().enqueue( new Callback<ResponseApi>() {
            @Override
            public void onResponse(Call<ResponseApi> call, Response<ResponseApi> response) {
                data = response.body().getArticles();
                newsAdapter = new NewsAdapter( MainActivity.this, data );
                rvNews.setLayoutManager( new LinearLayoutManager( MainActivity.this ) );
                rvNews.setAdapter( newsAdapter );
            }

            @Override
            public void onFailure(Call<ResponseApi> call, Throwable t) {

            }
        } );
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate( R.menu.menu, menu );
        return super.onCreateOptionsMenu( menu );
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        Method destinationMethod = null;

        switch (item.getItemId()) {
            case R.id.mn_national:
                getDataNationalGeographic();
                break;
            case R.id.mn_tech:
                getDataTech();
                break;
            case R.id.mn_mtv:
                getDataM();
                break;
            case R.id.mn_scientist:
                getDataScientist();
                break;

        }
        assert destinationMethod != null;
        return super.onOptionsItemSelected( item );
    }



}
