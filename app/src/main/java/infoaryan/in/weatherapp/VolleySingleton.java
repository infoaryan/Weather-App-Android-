package infoaryan.in.weatherapp;
import android.app.DownloadManager;
import android.content.Context;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;

public class VolleySingleton {

    //getting instance 
    private static VolleySingleton mInstance;
    private RequestQueue mRequestQueue;

    VolleySingleton(Context context){
        mRequestQueue= Volley.newRequestQueue(context.getApplicationContext());

    }

    public static synchronized  VolleySingleton getInstance(Context context){
        if(mInstance==null){
            mInstance=new VolleySingleton(context);
        }
        return mInstance;
    }
    public RequestQueue getmRequestQueue(){
        return mRequestQueue;
    }
    public void addTorequestque(Request request){
        mRequestQueue.add(request);
    }
}
