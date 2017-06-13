package sixtus.com.homenavigation;


/**
 * Created by Dakurah Sixtus on 6/6/2017.
 */
        import android.content.Context;
        import android.net.ConnectivityManager;
        import android.net.NetworkInfo;
        import android.util.Log;
        import android.widget.Toast;

class NetworkStatus {
    Context context;
    public NetworkStatus(Context context){
        this.context= context;
    }
    public boolean isNetworkAvailable() {

        ConnectivityManager connectivityManager
                = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnectedOrConnecting();
    }
}