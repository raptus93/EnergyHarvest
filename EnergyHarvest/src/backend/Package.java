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

/* used across backend & client */

package backend;

import java.util.HashMap;

public class Package implements java.io.Serializable{

    private static final long serialVersionUID = 1L;

    public static enum Type{
        REQUEST_CHECK_LOGIN, REQUEST_FETCH_QUESTIONS, REQUEST_REGISTER_CLAN, REQUEST_INVITE_MEMBER,
        RESPONSE_CHECK_LOGIN, RESPONSE_FETCH_QUESTIONS, RESPONSE_REGISTER_CLAN, RESPONSE_INVITE_MEMBER,
        REQUEST_GET_USER_BY_EMAIL, RESPONSE_GET_USER_BY_EMAIL, REQUEST_CLAN_INFO_BY_ID, RESPONSE_CLAN_INFO_BY_ID,
        FAILED_TO_CONNECT_TO_SERVER
    }

    private HashMap<String, Object> content;
    private Type type;

    public Package(Type type, HashMap<String, Object> content){
        this.type = type;
        this.content = content;
    }

    public HashMap<String, Object> getContent(){
        return content;
    }

    public Type getType(){
        return type;
    }

    public Object getFromContent(String key){
        Object o = getContent().get(key);
        if(o == null)
            o = "EMPTY OBJECT";
        return o;
    }

}