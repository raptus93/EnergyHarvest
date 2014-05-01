package node;

import android.util.Log;
import io.socket.IOAcknowledge;
import io.socket.IOCallback;
import io.socket.SocketIO;
import io.socket.SocketIOException;
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

                    //TODO: WENN CHALLENGE_START -> schalte in die quiz gui
                    //TODO: WENN CHALLENGE_QUESTION -> packe die frage irgendwo hin

                    if(event.equals("NOTIFICATION")){
                        try {
                            JSONObject notification = new JSONObject(args[0].toString());
                            NotificationCenter.getInstance().show(notification.getString("response"));

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

    public void makeChallege(final Callback success, final Callback fail, LinkedList<User> users){
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
                }, new JSONObject("{name :"+ getActiveUser().getName() +", clanid : " + getActiveUser().getClan().getId() + ", id : "+ getActiveUser().getId() + "," +
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


    /**
     * CHALLENGE_STARTED -> Opens the Quiz-GUI! Quiz-GUI waits for a question to come in
     * CHALLENGE_QUESTION -> Notification Center -> Put Question into the list
     * **/


    public void challengeResponse(final boolean accept, final Callback success, final Callback fail){
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

    /** STUB **/
    public void login(String email, String password, IOAcknowledge callback){
        try {
            send().emit("CHECK_LOGIN", callback, new JSONObject("{password : " + password + ", email : "+ email +"}"));
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

}