package backend;

import android.os.AsyncTask;
import android.util.Log;

/**
 * Created by scarlet on 4/5/14.
 */
public class ServerAsyncErrocodeCallback extends AsyncTask<String, Void, ErrorCode> {

    private Exception exception;

    @Override
    protected ErrorCode doInBackground(String... strings) {
        try {
            ErrorCode err = Server.getInstance().login("sergej@admin.de", "123456");
            Log.i("error", err.toString());
            return err;
        } catch (Exception e) {
            this.exception = e;
            return null;
        }
    }

    protected void onPostExecute(ErrorCode error) {
        if(this.exception != null)
            Log.i("error", this.exception.toString() + "  this is the exception");

        // TODO: check this.exception
        // TODO: do something with the feed
    }
}
