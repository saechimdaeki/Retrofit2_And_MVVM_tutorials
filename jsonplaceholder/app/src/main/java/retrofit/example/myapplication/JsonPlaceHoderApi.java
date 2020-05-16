package retrofit.example.myapplication;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

public interface JsonPlaceHoderApi {
   @GET("posts")
    Call<List<Post>> getPosts();
}
