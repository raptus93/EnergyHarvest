package node;

import android.app.Activity;
import android.util.Log;
import android.widget.Toast;

public class NotificationCenter {

    private static NotificationCenter instance;

    public static NotificationCenter getInstance(){
        return instance;
    }

    public static void init(Activity a){
        if(instance == null){
            instance = new NotificationCenter(a);
        }
    }

    private NotificationCenter(Activity a){
        activity = a;
        clanInviteToast = Toast.makeText(activity, "Clan-Einladung!", Toast.LENGTH_SHORT);
        challengeInviteToast = Toast.makeText(activity, "Challenge-Einladung!", Toast.LENGTH_SHORT);
        challengeStartedToast = Toast.makeText(activity, "Challenge wurde gestartet!", Toast.LENGTH_SHORT);
        challengeNewQuestion = Toast.makeText(activity, "Neue Quiz-Frage erhalten!", Toast.LENGTH_SHORT);
        challengeAccepted = Toast.makeText(activity, "Challenge akzeptiert!", Toast.LENGTH_SHORT);
        challengeDeclined =  Toast.makeText(activity, "Challenge abgelehnt!", Toast.LENGTH_SHORT);
    }

    /** class members **/
    private Activity activity;
    private Toast clanInviteToast;
    private Toast challengeInviteToast;
    private Toast challengeStartedToast;
    private Toast challengeNewQuestion;
    private Toast challengeAccepted;
    private Toast challengeDeclined;

    public void show(String notification){

        Log.e("SOCKETIO", notification);

        if(notification.equals("CLAN_INVITE")){

            /*
            * TODO: send the clanid from the server to the client & fetch the clan from the DB
            * TODO: OR -> send the clan info from the server to the client & just set it here
            * */

            clanInviteToast.show();
        }else if(notification.equals("CHALLENGE_INVITE")){
            challengeInviteToast.show();
        }else if(notification.equals("CHALLENGE_STARTED")){
            challengeStartedToast.show();
        }else if(notification.equals("CHALLENGE_QUESTION")){
            challengeNewQuestion.show();
        }else if(notification.equals("CHALLENGE_DECLINED")){
            challengeDeclined.show();
        }else if(notification.equals("CHALLENGE_ACCEPTED")){
            challengeAccepted.show();
        }
    }



}
