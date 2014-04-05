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

        task.execute(email, password);

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

        task.execute(amount);

        try {
            return task.get();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }

        /* empty list ! */
        return new QuestionCatalog(new LinkedList<Question>());
    }

    public static ErrorCode createClan(String name){

        AsyncTask<String, Void, ErrorCode> task = new AsyncTask<String, Void, ErrorCode>() {

            private Exception exception;

            @Override
            protected ErrorCode doInBackground(String... strings) {
                try {
                    return Server.getInstance().createClan(strings[0]);
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

        task.execute(name);

        try {
            return task.get();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }

        return ErrorCode.ERROR;
    }

    public static Clan getClanByID(int id){

        AsyncTask<Integer, Void, Clan> task = new AsyncTask<Integer, Void, Clan>() {
            private Exception exception;

            @Override
            protected Clan doInBackground(Integer... integers) {
                try {
                    return Server.getInstance().getClanByID(integers[0]);
                } catch (Exception e) {
                    this.exception = e;
                    return null;
                }
            }

            protected void onPostExecute(ErrorCode error) {
                if(this.exception != null)
                    Log.i("error", this.exception.toString() + "  this is the exception");
            }
        };

        task.execute(id);

        try {
            return task.get();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }

        return new Clan(0, "No Clan", "No Logo", 0);
    }

    public static boolean checkAnswer(int questionID, Question.Answer answer){

        AsyncTask<Integer, Void, Boolean> task = new AsyncTask<Integer, Void, Boolean>() {

            private Exception exception;

            @Override
            protected Boolean doInBackground(Integer... integers) {
                try {
                    return Server.getInstance().checkAnswerOrdinal(integers[0], integers[1]);
                } catch (Exception e) {
                    this.exception = e;
                    return null;
                }
            }

            protected void onPostExecute(ErrorCode error) {
                if(this.exception != null)
                    Log.i("error", this.exception.toString() + "  this is the exception");
            }
        };

        task.execute(questionID, answer.ordinal());

        try {
            return task.get();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }

        return false;
    }

    public static ErrorCode register(String name, String email, String pw){

        AsyncTask<String, Void, ErrorCode> task = new AsyncTask<String, Void, ErrorCode>() {

            private Exception exception;

            @Override
            protected ErrorCode doInBackground(String... strings) {
                try {
                    return Server.getInstance().register(strings[0], strings[1], strings[2]);
                } catch (Exception e) {
                    this.exception = e;
                    return null;
                }
            }

            protected void onPostExecute(ErrorCode error) {
                if(this.exception != null)
                    Log.i("error", this.exception.toString() + "  this is the exception");
            }
        };

        task.execute(name, email, pw);

        try {
            return task.get();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }

        return ErrorCode.ERROR;
    }

    public static boolean leaveClan(){

        AsyncTask<Integer, Void, Boolean> task = new AsyncTask<Integer, Void, Boolean>() {

            private Exception exception;

            @Override
            protected Boolean doInBackground(Integer... integers) {
                try {
                    return Server.getInstance().leaveClan();
                } catch (Exception e) {
                    this.exception = e;
                    return null;
                }
            }

            protected void onPostExecute(ErrorCode error) {
                if(this.exception != null)
                    Log.i("error", this.exception.toString() + "  this is the exception");
            }
        };

        task.execute(1);

        try {
            return task.get();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }

        return false;
    }

}
