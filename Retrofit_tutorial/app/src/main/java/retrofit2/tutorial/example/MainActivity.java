package retrofit2.tutorial.example;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.tutorial.example.api.ApiClient;
import retrofit2.tutorial.example.api.ApiInterface;
import retrofit2.tutorial.example.model.Article;
import retrofit2.tutorial.example.model.News;

public class MainActivity extends AppCompatActivity {
    public static final String API_KEY="ebbc3f52dc294e72aa888b8fe88aa962";
    private RecyclerView recyclerView;
    private RecyclerView.LayoutManager layoutManager;
    private List<Article> articles=new ArrayList<>();
    private Adapter adpater;
    private String TAG=MainActivity.class.getSimpleName();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        recyclerView=findViewById(R.id.recyclerView);
        layoutManager=new LinearLayoutManager(MainActivity.this);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setNestedScrollingEnabled(false);

        LoadJson();


    }
    public void LoadJson(){
        ApiInterface apiInterface= ApiClient.getApiClient().create(ApiInterface.class);
        String country=Utils.getCountry();
        Call<News> call;
        call=apiInterface.getNews(country,API_KEY);
        call.enqueue(new Callback<News>() {
            @Override
            public void onResponse(Call<News> call, Response<News> response) {
                if(response.isSuccessful()&& response.body().getArticle()!=null){
                    if(!articles.isEmpty()){
                        articles.clear();
                    }
                    articles=response.body().getArticle();
                    adpater=new Adapter(articles,MainActivity.this);
                    recyclerView.setAdapter(adpater);
                    adpater.notifyDataSetChanged();
                }else{
                    Toast.makeText(MainActivity.this, "NO Result", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<News> call, Throwable t) {

            }
        });
    }
}
