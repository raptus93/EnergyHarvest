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

    /* members */
	private boolean session = false;
    private User user;
	private Server(){}
	
	public boolean login(String email, String pw){
        if(!session){
            HashMap<String, Object> request = new HashMap<String, Object>();
            request.put("email", email);
            request.put("password", pw);

            boolean loginSuccessful = (Boolean) sendPackage(new Package(Package.Type.REQUEST_CHECK_LOGIN, request)).getContent().get("response");

            if(loginSuccessful){
                /* if the login was sucessful, we fetch the user data from the server */
                setUser(new User(0, "name", "email", new Clan(0, "name", "logo")));
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
		return new QuestionCatalog(new LinkedList<Question>());
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
        return user != null ? user : new User(0, "GUEST", "guest@no-reply.com", new Clan(0, "GUESTCLAN", "GUESTLOGO"));
    }

    /* PRIVATE */

    private void setUser(User user){
        this.user = user;
    }

    private Package sendPackage(Package p){
		/* request_package -> server | server -> response_package*/
        return null;
    }
}
