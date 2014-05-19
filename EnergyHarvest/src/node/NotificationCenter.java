/**
 Copyright (c) 2013, Sergej Schefer
 All rights reserved.

 Redistribution and use in source and binary forms, with or without
 modification, are permitted provided that the following conditions are met:
 1. Redistributions of source code must retain the above copyright
 notice, this list of conditions and the following disclaimer.
 2. Redistributions in binary form must reproduce the above copyright
 notice, this list of conditions and the following disclaimer in the
 documentation and/or other materials provided with the distribution.
 3. All advertising materials mentioning features or use of this software
 must display the following acknowledgement:
 This product includes software developed by Sergej Schefer.
 4. Neither the name of Sergej Schefer nor the
 names of its contributors may be used to endorse or promote products
 derived from this software without specific prior written permission.

 THIS SOFTWARE IS PROVIDED BY SERGEJ SCHEFER ''AS IS'' AND ANY
 EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED
 WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE
 DISCLAIMED. IN NO EVENT SHALL SERGEJ SCHEFER BE LIABLE FOR ANY
 DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES
 (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES;
 LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND
 ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT
 (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS
 SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 */
package node;

import android.app.Activity;
import android.util.Log;
import android.widget.Toast;
import org.json.JSONException;
import org.json.JSONObject;

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
        challengeDeclined = Toast.makeText(activity, "Challenge abgelehnt!", Toast.LENGTH_SHORT);
        challengeEnded = Toast.makeText(activity, "Challenge wurde beendet!", Toast.LENGTH_SHORT);
        challengeUserJoined = Toast.makeText(activity, "User joined!", Toast.LENGTH_SHORT);
        challengeUserLeft = Toast.makeText(activity, "User left!", Toast.LENGTH_SHORT);
    }

    /** class members **/
    private Activity activity;
    private Toast clanInviteToast;
    private Toast challengeInviteToast;
    private Toast challengeStartedToast;
    private Toast challengeNewQuestion;
    private Toast challengeAccepted;
    private Toast challengeDeclined;
    private Toast challengeEnded;
    private Toast challengeUserJoined;
    private Toast challengeUserLeft;

    public void show(String notification, JSONObject json){

        Log.e("SOCKETIO", notification);

        if(notification.equals("CLAN_INVITE")){

            try {
                int memberCount = json.getInt("clanMembercount");
                int clanID = json.getInt("clanID");
                String clanName = json.getString("clanName");
                String clanLogo = json.getString("clanLogo");

                Server.getInstance().getActiveUser().setClan(new Clan(clanID, clanName, clanLogo, memberCount));

            } catch (JSONException e) {
                e.printStackTrace();
            }

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
        }else if(notification.equals("CHALLENGE_END")){
            challengeEnded.show();
        }else if(notification.equals("CHALLENGE_REMOVED_USER")){
            challengeUserLeft.show();
        }else if(notification.equals("CHALLENGE_ADDED_USER")){
            challengeUserJoined.show();
        }
    }



}
