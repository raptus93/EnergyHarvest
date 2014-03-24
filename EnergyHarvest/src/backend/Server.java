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

	private boolean session = false;

	private Server(){}
	
	public Package sendPackage(Package p){
		/* request_package -> server | server -> response_package*/
		return null;
	}
	
	public boolean login(String email, String pw){
		HashMap<String, Object> request = new HashMap<String, Object>();
		request.put("email", email); 
		request.put("password", pw);

        /* checks if a session is set, if not, we try to check login data on the server */
		return !session ? (Boolean) sendPackage(new Package(Package.Type.REQUEST_CHECK_LOGIN, request)).getContent().get("response") : session;
	}
	
	public boolean checkAnswer(Question question, Question.Answer answer){
		return true;
	}
	
	public QuestionCatalog getRandomQuestions(int amount){
        /* hier fragen vom server ziehen & in die liste packen*/
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
}
