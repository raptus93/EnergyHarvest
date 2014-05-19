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

import android.util.Log;
import io.socket.IOAcknowledge;
import io.socket.IOCallback;
import io.socket.SocketIO;
import io.socket.SocketIOException;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.net.MalformedURLException;
import java.util.LinkedList;

public class Server {

    /** singleton stuff **/
    private static Server server = null;

    public static Server getInstance(){
        if(server == null)
            server = new Server();
        return server;
    }

    /** class members **/
    private SocketIO socket = null;
    private boolean session = false;
    private boolean challengeCreated = false;
    public final User GUEST_USER = new User(0, "GUEST", "guest@no-reply.com", 0, new Clan(0, "GUESTCLAN", "GUESTLOGO", 0));
    public final Clan GUEST_CLAN = new Clan(0, "GUESTCLAN", "GUESTLOGO", 0);
    private User user = GUEST_USER;

    /** config **/
    private final String SERVER_IP = "rawfood.no-ip.biz";
    private final String SERVER_PORT = "1337";

    private Server(){
        try {
            socket = new SocketIO("http://" + SERVER_IP + ":" + SERVER_PORT + "/");
            socket.connect(new IOCallback() {
                @Override
                public void onMessage(JSONObject json, IOAcknowledge ack) {
                    try {
                        System.out.println("Server said:" + json.toString(2));
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }

                @Override
                public void onMessage(String data, IOAcknowledge ack) {
                    System.out.println("Server said: " + data);
                }

                @Override
                public void onError(SocketIOException socketIOException) {
                    System.out.println("an Error occured");
                    socketIOException.printStackTrace();
                }

                @Override
                public void onDisconnect() {
                    System.out.println("Connection terminated.");
                }

                @Override
                public void onConnect() {
                    System.out.println("Connection established");
                }

                @Override
                public void on(String event, IOAcknowledge ack, Object... args) {
                    Log.e("SOCKET.IO", event + " & ack = " + (ack == null) + " OBJC = " + args[0].toString());

                    if(event.equals("NOTIFICATION")){
                        try {
                            JSONObject notification = new JSONObject(args[0].toString());
                            String message = notification.getString("response");

                            NotificationCenter.getInstance().show(message, notification);

                            if(message.contains("CHALLENGE")){
                                ChallengeBridge.getInstance().proccess(message, notification);
                            }

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }

                    if(ack != null){
                        ack.ack(args);
                    }

                }
            });

            socket.send("Hello Server!");



        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
    }

    public SocketIO send(){
        return socket;
    }

    public boolean logout(){
        try {
            send().emit("LOGOUT", new IOAcknowledge() {
                @Override
                public void ack(Object... args) {
                    /**
                     * nothing here.
                     * **/
                }
            }, new JSONObject("{clanid: " + getActiveUser().getClan().getId() + "}"));
        } catch (JSONException e) {
            e.printStackTrace();
        }
        this.user = GUEST_USER;
        this.session = false;
        this.challengeCreated = false;
        return session;
    }

    public User getActiveUser(){
        return user;
    }

    private void loginUser(User user){
        this.user = user;
        this.session = true;
    }

    public void login(final String email, String password, final Callback success, final Callback wrongPassword, final Callback invalidEmail){
        try {
            send().emit("CHECK_LOGIN", new IOAcknowledge() {
                @Override
                public void ack(Object... args) {

                    try {

                        JSONObject result = new JSONObject(args[1].toString());
                        String response = result.get("response").toString();

                        if(response.equals("SUCCESS")){
                            /** log the user in !**/

                            /** get user by email **/
                            send().emit("GET_USER_BY_EMAIL", new IOAcknowledge() {
                                @Override
                                public void ack(Object... args) {
                                    try {
                                        JSONObject result = new JSONObject(args[1].toString());
                                        final int id = result.getInt("id");
                                        final String email = result.getString("email");
                                        final String name = result.getString("name");
                                        final int score = result.getInt("score");
                                        final int clanid = result.getInt("clanid");

                                        /** set clan if > 0 **/
                                        if(clanid > 0){
                                            send().emit("GET_CLAN_BY_ID", new IOAcknowledge() {
                                                @Override
                                                public void ack(Object... args) {
                                                    try {
                                                        JSONObject jsonClan = new JSONObject(args[1].toString());
                                                        int clanid = jsonClan.getInt("id");
                                                        int memberCount = jsonClan.getInt("membercount");
                                                        String logo = jsonClan.getString("logo");
                                                        String clanname = jsonClan.getString("name");
                                                        loginUser(new User(id, name, email, score, new Clan(clanid, clanname, logo, memberCount)));
                                                        success.callback();
                                                    } catch (JSONException e) {
                                                        e.printStackTrace();
                                                    }
                                                }
                                            }, new JSONObject("{id : " + clanid + "}"));
                                        }else{
                                            loginUser(new User(id, name, email, score, new Clan(0, "No Clan", "No Logo", 0)));
                                            success.callback();
                                        }

                                    } catch (JSONException e) {
                                        e.printStackTrace();
                                    }
                                }
                            }, new JSONObject("{email : " + email +"}"));

                        }else if(response.equals("WRONG_PASSWORD")){
                            wrongPassword.callback();
                        }else if(response.equals("INVALID_EMAIL")){
                            invalidEmail.callback();
                        }

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                }
            }, new JSONObject("{password : " + password + ", email : "+ email +"}"));
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public void leaveClan(final Callback success){
        if(getActiveUser().getId() > 0 && getActiveUser().getClan().getId() > 0){
            try {
                send().emit("LEAVE_CLAN", new IOAcknowledge() {
                    @Override
                    public void ack(Object... args) {
                        success.callback();
                    }
                }, new JSONObject("{clanID : " + getActiveUser().getClan().getId() + ", userID : "+ getActiveUser().getId() +"}"));
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     use only if user is in no clan !
     **/
    public void createClan(String name, final Callback success, final Callback clanNameTaken){
        if(getActiveUser().getId() > 0 && getActiveUser().getClan().getId() == 0){
            try {
                send().emit("REGISTER_CLAN", new IOAcknowledge() {
                    @Override
                    public void ack(Object... args) {

                        try {
                            JSONObject result = new JSONObject(args[1].toString());
                            String response = result.get("response").toString();

                            if(response.equals("SUCCESS")){
                                /** change users clan **/
                                send().emit("GET_CLAN_BY_ID", new IOAcknowledge() {
                                    @Override
                                    public void ack(Object... args) {
                                        try {
                                            JSONObject jsonClan = new JSONObject(args[1].toString());
                                            int clanid = jsonClan.getInt("id");
                                            int memberCount = jsonClan.getInt("membercount");
                                            String logo = jsonClan.getString("logo");
                                            String clanname = jsonClan.getString("name");
                                            getActiveUser().setClan(new Clan(clanid, clanname, logo, memberCount));
                                            success.callback();
                                        } catch (JSONException e) {
                                            e.printStackTrace();
                                        }
                                    }
                                }, new JSONObject("{id : " + result.getInt("clanid") + "}"));
                            }else if(response.equals("CLANNAME_TAKEN")){
                                clanNameTaken.callback();
                            }

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, new JSONObject("{clanName : " + name + ", userID : "+ getActiveUser().getId() +"}"));
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }

    public void register(String name, String email, String pw, final Callback success, final Callback usernameTaken, final Callback emailTaken){
        try {
            send().emit("REGISTER_USER", new IOAcknowledge() {
                @Override
                public void ack(Object... args) {
                    try {
                        JSONObject result = new JSONObject(args[1].toString());
                        String response = result.get("response").toString();

                        if(response.equals("SUCCESS")){
                            success.callback();
                        }else if(response.equals("EMAIL_TAKEN")){
                            emailTaken.callback();
                        }else if(response.equals("USERNAME_TAKEN")){
                            usernameTaken.callback();
                        }

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }, new JSONObject("{name : " + name + ", password : " + pw + ", email : " + email + "}"));
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }


    public void inviteMember(int id, final Callback success, final Callback userDoesNotExist, final Callback userAlreadyInClan, final Callback activeUserNotInClan){
        if(getActiveUser().getClan().getId() > 0){
            try {
                send().emit("INVITE_MEMBER", new IOAcknowledge() {
                    @Override
                    public void ack(Object... args) {
                        try {
                            JSONObject result = new JSONObject(args[1].toString());
                            String response = result.get("response").toString();

                            if(response.equals("SUCCESS")){
                                success.callback();
                            }else if(response.equals("USER_ALREADY_IN_CLAN")){
                                userAlreadyInClan.callback();
                            }else if(response.equals("USER_DOES_NOT_EXIST")){
                                userDoesNotExist.callback();
                            }

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, new JSONObject("{clanID : " + getActiveUser().getClan().getId() + ", userID : "+ id +"}"));
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }else{
            activeUserNotInClan.callback();
        }
    }

    @Deprecated
    public void checkAnswer(int questionID, int answer, final Callback correctAnswer, final Callback falseAnswer){
        try {
            send().emit("CHECK_ANSWER", new IOAcknowledge() {
                @Override
                public void ack(Object... args) {
                    try {
                        JSONObject result = new JSONObject(args[1].toString());
                        String response = result.get("response").toString();

                        if(response.equals("true")){
                            correctAnswer.callback();
                        }else if(response.equals("false")){
                            falseAnswer.callback();
                        }

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }, new JSONObject("{id : " + questionID + ", answer : "+ answer +"}"));
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public void getOnlineMembersFromClan(final Callback success){
        if(getActiveUser().getId() > 0 && getActiveUser().getClan().getId() > 0){
            try {
                send().emit("GET_ONLINE_MEMBERS", new IOAcknowledge() {
                    @Override
                    public void ack(Object... args) {
                        try {
                            JSONObject result = new JSONObject(args[1].toString());
                            String response = result.get("response").toString();
                            JSONObject members = new JSONObject(response);
                            LinkedList<User> users = new LinkedList<User>();

                            for(int i = 0; i < members.length(); i++){
                                JSONObject user = members.getJSONObject("" + i);
                                users.add(new User(user.getInt("id"), user.getString("name"), "HIDDEN", user.getInt("score"), getActiveUser().getClan()));
                            }

                            success.callback(users);

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, new JSONObject("{clanid : " + getActiveUser().getClan().getId() + ", id : "+ getActiveUser().getId() +"}"));
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }

    public void makeChallege(final Callback success, final Callback fail, LinkedList<User> users, int buildingID, int categoryID){
        if(getActiveUser().getId() > 0 && getActiveUser().getClan().getId() > 0){

            /** prepare userlist **/
            String userlist = "[";

            for(int i = 0; i < users.size(); i++){
                if(i < users.size() - 1){
                    userlist += users.get(i).getId() + ",";
                }else{
                    userlist += users.get(i).getId();
                }
            }

            userlist += "]";

            try {
                send().emit("MAKE_CHALLENGE", new IOAcknowledge() {
                    @Override
                    public void ack(Object... args) {
                        try {
                            JSONObject result = new JSONObject(args[1].toString());
                            String response = result.get("response").toString();

                            if(response.equals("CHALLENGE_FOR_YOUR_CLAN_ALREADY_EXISTS")){
                                fail.callback();
                            }else if(response.equals("CHALLENGE_CREATED")){
                                /*
                                * TODO: don't forget, to change this, after we end the challenge!
                                * OR if the client disconnects, or leaves the challenge
                                * */
                                challengeCreated = true;
                                success.callback();
                            }

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, new JSONObject("{name :"+ getActiveUser().getName() +", clanid : " + getActiveUser().getClan().getId() + ", catID : " + categoryID +
                        ", buildingID : " + buildingID +
                        ", id : "+ getActiveUser().getId() + "," +
                        " userlist: " + userlist + "}"));
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }

    public void endChallenge(final Callback success, final Callback fail){
        if(getActiveUser().getId() > 0 && getActiveUser().getClan().getId() > 0 && challengeCreated){
            try {
                send().emit("CHALLENGE_END", new IOAcknowledge() {
                    @Override
                    public void ack(Object... args) {
                        try {
                            JSONObject result = new JSONObject(args[1].toString());
                            String response = result.get("response").toString();

                            if(response.equals("SUCCESS")){
                                success.callback();
                            }else if(response.equals("YOU_ARENT_THE_CHALLENGE_CREATOR")){
                                fail.callback();
                            }

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, new JSONObject("{id : " + getActiveUser().getId() + ", challengeID : "+ getActiveUser().getClan().getId() +"}"));
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }

    public void startChallenge(final Callback success, final Callback fail){
        /** if master -> start challenge auf dem server **/
        if(getActiveUser().getId() > 0 && getActiveUser().getClan().getId() > 0){
            try {
                send().emit("CHALLENGE_START", new IOAcknowledge() {
                    @Override
                    public void ack(Object... args) {
                        try {
                            JSONObject result = new JSONObject(args[1].toString());
                            String response = result.get("response").toString();

                            if(response.equals("SUCCESS")){
                                success.callback();
                            }else if(response.equals("YOU_ARENT_THE_CHALLENGE_CREATOR")){
                                fail.callback();
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }
                }, new JSONObject("{challengeID : " + getActiveUser().getClan().getId() + ", id : "+ getActiveUser().getId() +"}"));
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }

    public void challengeResponse(final boolean accept, final Callback success, final Callback fail){
        if(getActiveUser().getId() > 0 && getActiveUser().getClan().getId() > 0){
            try {
                send().emit("CHALLENGE_RESPONSE", new IOAcknowledge() {
                    @Override
                    public void ack(Object... args) {

                        try {
                            JSONObject result = new JSONObject(args[1].toString());
                            String response = result.get("response").toString();

                            if(response.equals("CHALLENGE_ACCEPTED")){
                                success.callback();
                            }else if(response.equals("CHALLENGE_DECLINED")){
                                fail.callback();
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, new JSONObject("{response : " + accept + ", challengeID : " + getActiveUser().getClan().getId() + ", name : " + getActiveUser().getName() + ", id: " + getActiveUser().getId() + "}"));
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }

    public void sendAnswer(final int questionID, final int answer, final int challengeID){
        if(getActiveUser().getId() > 0 && getActiveUser().getClan().getId() > 0){
            try {
                send().emit("SEND_ANSWER", new IOAcknowledge() {
                    @Override
                    public void ack(Object... args) {
                        /** no response **/
                    }
                }, new JSONObject("{questionID : " + questionID + ", answer : "+ answer + ", challengeID : "+ getActiveUser().getClan().getId() +"}"));
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }

    public void getBuildingByID(final int ID, final Callback success, final Callback fail){
        try {
            send().emit("GET_BUILDING_BY_ID", new IOAcknowledge() {
                @Override
                public void ack(Object... args) {
                    try {
                        JSONObject result = new JSONObject(args[1].toString());
                        String response = result.get("response").toString();

                        if(response.equals("SUCCESS")){
                            System.out.println(result.getJSONObject("building"));

                            JSONObject building = result.getJSONObject("building");

                            int id = building.getInt("id");

                            String cat0_name = building.getString("c0");
                            String cat1_name = building.getString("c1");
                            String cat2_name = building.getString("c2");
                            String cat3_name = building.getString("c3");

                            int c0_holding_id = building.getInt("c0_holding_ID");
                            int c1_holding_id = building.getInt("c1_holding_ID");
                            int c2_holding_id = building.getInt("c2_holding_ID");
                            int c3_holding_id = building.getInt("c3_holding_ID");

                            int c0_locktime = building.getInt("c0_locktime");
                            int c1_locktime = building.getInt("c1_locktime");
                            int c2_locktime = building.getInt("c2_locktime");
                            int c3_locktime = building.getInt("c3_locktime");

                            JSONArray holdingClans = building.getJSONArray("holding_clans");
                            LinkedList<Clan> clans = new LinkedList<Clan>();

                            for(int i = 0; i < holdingClans.length(); i++){
                                JSONObject clan = holdingClans.getJSONObject(i);

                                int clanID = clan.getInt("id");
                                int memberCount = clan.getInt("membercount");
                                String logo = clan.getString("logo");
                                String clanname = clan.getString("name");

                                clans.add(new Clan(clanID, clanname, logo, memberCount));
                            }

                            /**
                             * FILL THE BUILDING OBJECT
                             * **/

                            Building resultBuilding = new Building();

                            /**
                             * Set clans!
                             * **/
                            for(int i = 0; i < clans.size(); i++){
                                if(clans.get(i).getId() == c0_holding_id){
                                    resultBuilding.setCat0_clan(clans.get(i));
                                }

                                if(clans.get(i).getId() == c1_holding_id){
                                    resultBuilding.setCat1_clan(clans.get(i));
                                }

                                if(clans.get(i).getId() == c2_holding_id){
                                    resultBuilding.setCat2_clan(clans.get(i));
                                }

                                if(clans.get(i).getId() == c3_holding_id){
                                    resultBuilding.setCat3_clan(clans.get(i));
                                }
                            }

                            /**
                             * Set unlock times
                             * **/
                            resultBuilding.setCat0_unlockTime(c0_locktime);
                            resultBuilding.setCat1_unlockTime(c1_locktime);
                            resultBuilding.setCat2_unlockTime(c2_locktime);
                            resultBuilding.setCat3_unlockTime(c3_locktime);

                            //set ID
                            resultBuilding.setId(id);

                            /**
                             * Set Category-Names
                             * **/
                            resultBuilding.setCat0(cat0_name);
                            resultBuilding.setCat1(cat1_name);
                            resultBuilding.setCat2(cat2_name);
                            resultBuilding.setCat3(cat3_name);

                            success.callback(resultBuilding);
                        }else if(response.equals("BUILDING_DOES_NOT_EXIST")){
                            fail.callback();
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }, new JSONObject("{id : " + ID + "}"));
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    /** STUB **/
    public void login(String email, String password, IOAcknowledge callback){
        try {
            send().emit("CHECK_LOGIN", callback, new JSONObject("{password : " + password + ", email : "+ email +"}"));
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

}