a943060c (Fatimasoho        2020-04-29 14:28:40 -0500  1) package com.example.covid_19visualizer;
a943060c (Fatimasoho        2020-04-29 14:28:40 -0500  2) 
a943060c (Fatimasoho        2020-04-29 14:28:40 -0500  3) import android.content.Context;
a943060c (Fatimasoho        2020-04-29 14:28:40 -0500  4) import android.content.Intent;
a943060c (Fatimasoho        2020-04-29 14:28:40 -0500  5) import android.view.LayoutInflater;
a943060c (Fatimasoho        2020-04-29 14:28:40 -0500  6) import android.view.View;
a943060c (Fatimasoho        2020-04-29 14:28:40 -0500  7) import android.view.ViewGroup;
a943060c (Fatimasoho        2020-04-29 14:28:40 -0500  8) import android.widget.AdapterView;
a943060c (Fatimasoho        2020-04-29 14:28:40 -0500  9) import android.widget.ImageView;
a943060c (Fatimasoho        2020-04-29 14:28:40 -0500 10) import android.widget.TextView;
a943060c (Fatimasoho        2020-04-29 14:28:40 -0500 11) 
a943060c (Fatimasoho        2020-04-29 14:28:40 -0500 12) import androidx.annotation.NonNull;
a943060c (Fatimasoho        2020-04-29 14:28:40 -0500 13) import androidx.cardview.widget.CardView;
a943060c (Fatimasoho        2020-04-29 14:28:40 -0500 14) import androidx.recyclerview.widget.RecyclerView;
a943060c (Fatimasoho        2020-04-29 14:28:40 -0500 15) 
00000000 (Not Committed Yet 2020-05-01 15:30:04 -0400 16) import com.example.covid_19visualizer.news.parameter.Articles;
a943060c (Fatimasoho        2020-04-29 14:28:40 -0500 17) import com.squareup.picasso.Picasso;
a943060c (Fatimasoho        2020-04-29 14:28:40 -0500 18) 
a943060c (Fatimasoho        2020-04-29 14:28:40 -0500 19) import java.util.List;
a943060c (Fatimasoho        2020-04-29 14:28:40 -0500 20) import java.util.Locale;
a943060c (Fatimasoho        2020-04-29 14:28:40 -0500 21) 
a943060c (Fatimasoho        2020-04-29 14:28:40 -0500 22) public class Adapter extends RecyclerView.Adapter<Adapter.ViewHolder> {
a943060c (Fatimasoho        2020-04-29 14:28:40 -0500 23)     Context context;
a943060c (Fatimasoho        2020-04-29 14:28:40 -0500 24)     AdapterView.OnItemClickListener onItemClickListener;
a943060c (Fatimasoho        2020-04-29 14:28:40 -0500 25)     List<Articles> articles;
a943060c (Fatimasoho        2020-04-29 14:28:40 -0500 26)     public Adapter(Context context, List<Articles> articles){
a943060c (Fatimasoho        2020-04-29 14:28:40 -0500 27)         this.context = context;
a943060c (Fatimasoho        2020-04-29 14:28:40 -0500 28)         this.articles= articles;
a943060c (Fatimasoho        2020-04-29 14:28:40 -0500 29)     }
a943060c (Fatimasoho        2020-04-29 14:28:40 -0500 30)     @NonNull
a943060c (Fatimasoho        2020-04-29 14:28:40 -0500 31)     @Override
a943060c (Fatimasoho        2020-04-29 14:28:40 -0500 32)     public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
a943060c (Fatimasoho        2020-04-29 14:28:40 -0500 33)         View view = (View) LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item, parent, false);
a943060c (Fatimasoho        2020-04-29 14:28:40 -0500 34) 
a943060c (Fatimasoho        2020-04-29 14:28:40 -0500 35)         return new ViewHolder(view);
a943060c (Fatimasoho        2020-04-29 14:28:40 -0500 36)     }
a943060c (Fatimasoho        2020-04-29 14:28:40 -0500 37) 
a943060c (Fatimasoho        2020-04-29 14:28:40 -0500 38)     @Override
a943060c (Fatimasoho        2020-04-29 14:28:40 -0500 39)     public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
a943060c (Fatimasoho        2020-04-29 14:28:40 -0500 40)         final Articles art = articles.get(position);
a943060c (Fatimasoho        2020-04-29 14:28:40 -0500 41)         String url = art.getUrl();
a943060c (Fatimasoho        2020-04-29 14:28:40 -0500 42)         holder.newsTitle.setText(art.getTitle());
cf6eac1c (Fatimasoho        2020-04-30 14:14:00 -0500 43)         //holder.newsDate.setText(art.getPublishedAt());
a943060c (Fatimasoho        2020-04-29 14:28:40 -0500 44)         String imageUrl= art.getUrlToImage();
a943060c (Fatimasoho        2020-04-29 14:28:40 -0500 45)             Picasso.get().load(imageUrl).into(holder.imageView);
a943060c (Fatimasoho        2020-04-29 14:28:40 -0500 46)             holder.cardView.setOnClickListener(new View.OnClickListener() {
a943060c (Fatimasoho        2020-04-29 14:28:40 -0500 47)                 @Override
a943060c (Fatimasoho        2020-04-29 14:28:40 -0500 48)                 public void onClick(View v) {
a943060c (Fatimasoho        2020-04-29 14:28:40 -0500 49)                     Intent intent = new Intent(context, NewsInDetails.class);
a943060c (Fatimasoho        2020-04-29 14:28:40 -0500 50)                     intent.putExtra("url", art.getUrl());
a943060c (Fatimasoho        2020-04-29 14:28:40 -0500 51)                     intent.putExtra("title",art.getTitle());
a943060c (Fatimasoho        2020-04-29 14:28:40 -0500 52)                     intent.putExtra("source",art.getSource().getName());
a943060c (Fatimasoho        2020-04-29 14:28:40 -0500 53)                     intent.putExtra("time",art.getPublishedAt());
a943060c (Fatimasoho        2020-04-29 14:28:40 -0500 54)                     intent.putExtra("imageUrl",art.getUrlToImage());
a943060c (Fatimasoho        2020-04-29 14:28:40 -0500 55)                     intent.putExtra("decs",art.getDescription());
a943060c (Fatimasoho        2020-04-29 14:28:40 -0500 56)                     context.startActivity(intent);
a943060c (Fatimasoho        2020-04-29 14:28:40 -0500 57)                 }
a943060c (Fatimasoho        2020-04-29 14:28:40 -0500 58)             });
a943060c (Fatimasoho        2020-04-29 14:28:40 -0500 59)     }
a943060c (Fatimasoho        2020-04-29 14:28:40 -0500 60) 
a943060c (Fatimasoho        2020-04-29 14:28:40 -0500 61)     @Override
a943060c (Fatimasoho        2020-04-29 14:28:40 -0500 62)     public int getItemCount() {
a943060c (Fatimasoho        2020-04-29 14:28:40 -0500 63)         return articles.size();
a943060c (Fatimasoho        2020-04-29 14:28:40 -0500 64)     }
a943060c (Fatimasoho        2020-04-29 14:28:40 -0500 65) 
a943060c (Fatimasoho        2020-04-29 14:28:40 -0500 66)     public class ViewHolder extends RecyclerView.ViewHolder {
cf6eac1c (Fatimasoho        2020-04-30 14:14:00 -0500 67)         TextView newsTitle;
a943060c (Fatimasoho        2020-04-29 14:28:40 -0500 68)         ImageView imageView;
a943060c (Fatimasoho        2020-04-29 14:28:40 -0500 69)         CardView cardView;
a943060c (Fatimasoho        2020-04-29 14:28:40 -0500 70)         public ViewHolder(@NonNull View itemView) {
a943060c (Fatimasoho        2020-04-29 14:28:40 -0500 71)             super(itemView);
cf6eac1c (Fatimasoho        2020-04-30 14:14:00 -0500 72)             //newsDate = itemView.findViewById(R.id.newsDate);
a943060c (Fatimasoho        2020-04-29 14:28:40 -0500 73)             newsTitle = itemView.findViewById(R.id.newsTitle);
a943060c (Fatimasoho        2020-04-29 14:28:40 -0500 74)             imageView = itemView.findViewById(R.id.image);
a943060c (Fatimasoho        2020-04-29 14:28:40 -0500 75)             cardView = itemView.findViewById(R.id.cardView);
a943060c (Fatimasoho        2020-04-29 14:28:40 -0500 76)         }
a943060c (Fatimasoho        2020-04-29 14:28:40 -0500 77)     }
a943060c (Fatimasoho        2020-04-29 14:28:40 -0500 78)     public String getCountry()
a943060c (Fatimasoho        2020-04-29 14:28:40 -0500 79)     {
a943060c (Fatimasoho        2020-04-29 14:28:40 -0500 80)         Locale locale=Locale.getDefault();
a943060c (Fatimasoho        2020-04-29 14:28:40 -0500 81)         String country= locale.getCountry();
a943060c (Fatimasoho        2020-04-29 14:28:40 -0500 82)         return country.toLowerCase();
a943060c (Fatimasoho        2020-04-29 14:28:40 -0500 83)     }
a943060c (Fatimasoho        2020-04-29 14:28:40 -0500 84) 
a943060c (Fatimasoho        2020-04-29 14:28:40 -0500 85)     public String getCategory()
a943060c (Fatimasoho        2020-04-29 14:28:40 -0500 86)     {
a943060c (Fatimasoho        2020-04-29 14:28:40 -0500 87)         return "health";
a943060c (Fatimasoho        2020-04-29 14:28:40 -0500 88)     }
a943060c (Fatimasoho        2020-04-29 14:28:40 -0500 89) }
