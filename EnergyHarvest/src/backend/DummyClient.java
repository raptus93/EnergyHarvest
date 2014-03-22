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
import java.net.UnknownHostException;
import java.util.HashMap;

public class DummyClient {

    public static void main(String[] args) {
        Socket clientSocket;
        try {
            clientSocket = new Socket("caramelswirl.myqnapcloud.com", 8888);
            ObjectOutputStream outToServer = new ObjectOutputStream(clientSocket.getOutputStream());
            System.out.println(clientSocket.isConnected());

            /* prepare package */
            HashMap<String, Object> map = new HashMap<String, Object>();
            map.put("email", "sergej@admin.de");
            map.put("password", "123456");

            /* write package */
            outToServer.writeObject(new backend.Package(backend.Package.Type.REQUEST_CHECK_LOGIN, map));
            outToServer.flush();
            clientSocket.shutdownOutput();

            /* open stream for server reponse */
            ObjectInputStream inFromServer = new ObjectInputStream(clientSocket.getInputStream());

            /* read reponse from server */
            try {
                backend.Package reponse = (backend.Package) inFromServer.readObject();
                System.out.println(reponse.getType() + " && " + reponse.getContent().get("response"));
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }

            /* close stuff */
            outToServer.close();
            inFromServer.close();
            clientSocket.close();

        } catch (UnknownHostException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
