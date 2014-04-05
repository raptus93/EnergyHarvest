package backend;

import android.os.AsyncTask;
import android.util.Log;

import java.util.LinkedList;
import java.util.concurrent.ExecutionException;

public class AndroidServerInterface {


    public static ErrorCode login(String email, String password){

        /* anonymous inner class */
        AsyncTask<String, Void, ErrorCode> task = new AsyncTask<String, Void, ErrorCode>() {
            private Exception exception;

            @Override
            protected ErrorCode doInBackground(String... strings) {
                try {
                    return Server.getInstance().login(strings[0], strings[1]);
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
        };

        try {
            return task.get();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }

       return ErrorCode.ERROR;
    }

    public static void logout(){
        Server.getInstance().logout();
    }

    public User getActiveUser(){
        return Server.getInstance().getActiveUser();
    }

    public static QuestionCatalog getRandomQuestions(int amount){
        AsyncTask<Integer, Void, QuestionCatalog> task = new AsyncTask<Integer, Void, QuestionCatalog>() {
            private Exception exception;

            @Override
            protected QuestionCatalog doInBackground(Integer... integers) {
                try {
                    return Server.getInstance().getRandomQuestions(integers[0]);
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
        };

        /* empty list ! */
        return new QuestionCatalog(new LinkedList<Question>());
    }
}
