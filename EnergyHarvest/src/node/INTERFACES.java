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

import node.Callback;
import node.Server;
import node.User;

import java.util.LinkedList;

public class INTERFACES {

    public static void main(String[] args) {

        Server server = Server.getInstance();

        /****
         * LOGIN
         * ****/
        Server.getInstance().login("sergej@mega.de", "123",
        /** success [no input] **/
        new Callback() {
            @Override
            public void callback(Object... input) {
                Server.getInstance().getActiveUser().print();
            }
        },

        /** wrong password [no input] **/
        new Callback() {
            @Override
            public void callback(Object... input) {

            }
        },

        /** invalid email [no input] **/
        new Callback() {
            @Override
            public void callback(Object... input) {

            }
        });

        /****
         * REGISTER NEW USER
         * ****/
        Server.getInstance().register("sergej0", "sergej@mega.de", "123",
        /** success [no input] **/
        new Callback() {
            @Override
            public void callback(Object... input) {
            }
        },
        /** username taken [no input] **/
        new Callback() {
            @Override
            public void callback(Object... input) {

            }
        },
        /** email taken [no input] **/
        new Callback() {
            @Override
            public void callback(Object... input) {

            }
        });


        /****
         * INVITE CLAN MEMBER
         * ****/
        Server.getInstance().inviteMember(12,
        /** success [no input] **/
        new Callback() {
            @Override
            public void callback(Object... input) {

            }
        },
        /** user does not exist [no input] **/
        new Callback() {
            @Override
            public void callback(Object... input) {

            }
        },
        /** user is already in a clan [no input] **/
        new Callback() {
            @Override
            public void callback(Object... input) {
                System.out.println("USER IS ALREADY IN CLAN");
            }
        },
        /** active user is not in a clan [no input] **/
        new Callback() {
            @Override
            public void callback(Object... input) {
                System.out.println("ACTIVE USER NOT IN CLAN");
            }
        });

        /****
         * CREATE NEW CLAN
         * ****/
        Server.getInstance().createClan("Coffee",
        /** success [no input]**/
        new Callback() {
            @Override
            public void callback(Object... input) {

            }
        },
        /** clanname is taken [no input]**/
        new Callback() {
            @Override
            public void callback(Object... input) {

            }
        });

        /****
         * CHECK ANSWER
         * ****/
        Server.getInstance().checkAnswer(0, 0,
        /** correct [no input] **/
        new Callback() {
            @Override
            public void callback(Object... input) {

            }
        },
        /** false [no input] **/
        new Callback() {
            @Override
            public void callback(Object... input) {

            }
        });


        /***
         * GET ALL ONLINE MEMBERS FROM YOUR CLAN
         * **/
        Server.getInstance().getOnlineMembersFromClan(new Callback() {

            /** success [input[0] = LinkedList<User>] **/
            @Override
            public void callback(Object... input) {
                LinkedList<User> users = (LinkedList<User>) input[0];
                System.out.println(users.size() + " online members");
            }
        });


        /***
         * CREATE NEW CHALLENGE
         * **/
        LinkedList<User> users = new LinkedList<User>();
        users.add(Server.getInstance().getActiveUser());
        users.add(Server.getInstance().getActiveUser());

        Server.getInstance().makeChallege(
        /** success [no input] **/
        new Callback() {
            @Override
            public void callback(Object... input) {

            }
        },
        /** fail. challenge for clan already exists [no input] **/
        new Callback() {
            @Override
            public void callback(Object... input) {

            }
        }, users);

        /***
         * START THE CHALLENGE
         * **/
        Server.getInstance().startChallenge(
        /** success [no input] **/
        new Callback() {
            @Override
            public void callback(Object... input) {

            }
        },
        /** fail. you are not the challenge creator [no input] **/
        new Callback() {
            @Override
            public void callback(Object... input) {

            }
        });

        /***
         * END THE CHALLENGE
         * **/
        Server.getInstance().endChallenge(
        /** success [no input] **/
        new Callback() {
            @Override
            public void callback(Object... input) {
            }
        },
        /** fail. you are not the challenge creator [no input] **/
        new Callback() {
            @Override
            public void callback(Object... input) {
            }
        });




        /***
         * ACCEPT / DECLINE THE CHALLENGE
         * **/
        Server.getInstance().challengeResponse(true,
        /** success [no input] **/
        new Callback() {
            @Override
            public void callback(Object... input) {

            }
        },
        /** fail [no input] **/
        new Callback() {
            @Override
            public void callback(Object... input) {

            }
        });

        /***
         * GET BUILDING BY ID
         * ID = [1, 2, 3, 4]
         * **/

        final int buildingID = 1;
        Server.getInstance().getBuildingByID(buildingID,
                /**
                 * success
                 * input[0] = building
                 * **/
                new Callback() {
                    @Override
                    public void callback(Object... input) {
                        ((Building)input[0]).print();
                        System.out.println("SUCCESS");
                    }
                },
                /** fail [no input] **/
                new Callback() {
                    @Override
                    public void callback(Object... input) {
                        System.out.println("FAIL");
                    }
                }
        );
    }

}
