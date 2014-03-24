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
package backend;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.HashMap;
import java.util.LinkedList;

/**
 * 
 * @author Sergej Schefer
 */
public class Server {

    /* singleton */
    private static Server instance;
    public static Server getInstance(){
        if(instance == null)
            instance = new Server();
        return instance;
    }

    /* config */
    private final String SERVER_IP = "caramelswirl.myqnapcloud.com";
    private final int SERVER_PORT = 8888;

    /* members */
	private boolean session = false;
    private User user = new User(0, "GUEST", "guest@no-reply.com", 0, new Clan(0, "GUESTCLAN", "GUESTLOGO"));
	private Server(){}
	
	public boolean login(String email, String pw){
        if(!session){
            HashMap<String, Object> request = new HashMap<String, Object>();
            request.put("email", email);
            request.put("password", pw);

            boolean loginSuccessful = (Boolean) sendPackage(new Package(Package.Type.REQUEST_CHECK_LOGIN, request)).getFromContent("response");

            if(loginSuccessful){
                /* if the login was sucessful, we fetch the user data from the server */
                HashMap<String, Object> map = new HashMap<String, Object>();
                map.put("email", email);

                Package response = sendPackage(new Package(Package.Type.REQUEST_GET_USER_BY_EMAIL, map));

                String name = (String) response.getFromContent("name");
                String response_email = (String) response.getFromContent("email");
                int id = (Integer) response.getFromContent("id");
                int score = (Integer) response.getFromContent("score");
                int clanID = (Integer) response.getFromContent("clanid");


                /* need to fetch clan infos */
                setUser(new User(id, name, response_email, score, new Clan(clanID, "name", "logo")));

                System.out.println("Debug login print");
                getActiveUser().print();

                session = true;
            }

        }
		return session;
	}
	
	public boolean checkAnswer(int questionID, Question.Answer answer){
		return true;
	}
	
	public QuestionCatalog getRandomQuestions(int amount){
        /* fetch questions from server & put them in a list (questioncatalog) */

        HashMap<String, Object> map = new HashMap<String, Object>();
        map.put("amount", amount);

        Package response = sendPackage(new Package(Package.Type.REQUEST_FETCH_QUESTIONS, map));
		return new QuestionCatalog((LinkedList<Question>) response.getFromContent("response"));
	}
	
	public boolean register(String name, String email, String pw){
		return true;
	}
	
	public boolean createClan(String name){
		return true;
	}
	
	public boolean inviteMember(int id){
		return true;
	}

    public User getActiveUser(){
        return user;
    }

    /* PRIVATE */

    private void setUser(User user){
        this.user = user;
    }

    private Package sendPackage(Package p){

        Socket clientSocket;

        try {
            clientSocket = new Socket(SERVER_IP, SERVER_PORT);
            ObjectOutputStream outToServer = new ObjectOutputStream(clientSocket.getOutputStream());
            outToServer.writeObject(p);
            outToServer.flush();
            clientSocket.shutdownOutput();

            ObjectInputStream inFromServer = new ObjectInputStream(clientSocket.getInputStream());
            Package reponse = (Package) inFromServer.readObject();

            outToServer.close();
            inFromServer.close();
            clientSocket.close();

            return reponse;

        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

        HashMap<String, Object> map = new HashMap<String, Object>();
        map.put("response", false);
        return new Package(Package.Type.FAILED_TO_CONNECT_TO_SERVER, map);
    }
}
