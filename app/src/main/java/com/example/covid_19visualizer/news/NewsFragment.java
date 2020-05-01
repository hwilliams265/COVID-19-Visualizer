package com.example.covid_19visualizer.news;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.covid_19visualizer.news.Adapter;
import com.example.covid_19visualizer.news.Client;
import com.example.covid_19visualizer.R;
import com.example.covid_19visualizer.news.parameter.Articles;
import com.example.covid_19visualizer.news.parameter.Headlines;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * File created by: Harry
 *
 * This class contains everything needed for the news_fragment.xml page.
 */
public class NewsFragment<floatingActionButton> extends Fragment {
    private Context context;
    // See InfoFragment for info about the newInstance() method
    /*@NotNull
    public static NewsFragment newInstance() {
        Bundle bundle = new Bundle();
       NewsFragment fragment = new NewsFragment();
        fragment.setArguments(bundle);
        return fragment;
   }*/

    // onCreateView() is automatically run after onCreate() when the fragment is called to view.
    // It returns a View object that displays the news interface.
    RecyclerView recyclerView;
    Adapter adapter;
    final String API_KEY = "c03631871fdb42dc933b8a948b1370ce";
    Button refreshButton;
    ImageButton floatingActionButton;
    List<Articles> articles = new ArrayList<>();


    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        this.context = context;
    }


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        return inflater.inflate(R.layout.news_fragment, container, false);
    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        recyclerView = view.findViewById(R.id.recycler);
        refreshButton= view.findViewById(R.id.refresh);
        recyclerView.setLayoutManager(new LinearLayoutManager(context));
        final String country = getCountry();
        final String category = getCategory();
        fetchJSON(country,category,API_KEY);

    }


    private void fetchJSON(String country,String category, String api_key) {
        Call<Headlines> call= Client.getInstance().getApi().getHeadlines(country,category,API_KEY);
        call.enqueue(new Callback<Headlines>() {
            @Override
            public void onResponse(Call<Headlines> call, Response<Headlines> response) {
                if(response.isSuccessful()&& response.body().getArticles() !=null)
                {
                    articles.clear();
                    articles=response.body().getArticles();
                    adapter =new Adapter(context, articles);
                    recyclerView.setAdapter(adapter);

                }
            }

            @Override
            public void onFailure(Call<Headlines> call, Throwable t) {
                Toast.makeText(context, "Check Your Internet Connection", Toast.LENGTH_SHORT).show();
            }
        });

    }

    private String getCountry() {
        Locale locale = Locale.getDefault();
        String country = locale.getCountry();
        return country.toLowerCase();
    }

    private String getCategory(){
        return "health";
    }
}
