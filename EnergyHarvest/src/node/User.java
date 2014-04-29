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

/**
 * @author Sergej Schefer
 */
public class User {

    /* class members */
    private int id;
    private int score;
    private String name;
    private String email;
    private Clan clan;

    public User(int id, String name, String email, int score, Clan clan){
        this.id = id;
        this.score = score;
        this.name = name;
        this.email = email;
        setClan(clan);
    }

    public void setClan(Clan clan){
        this.clan = clan;
    }

    public void print(){
        System.out.println("\nUser");
        System.out.println("------");

        System.out.println("UserID: " + id);
        System.out.println("User: " + name);
        System.out.println("Email: " + email);
        System.out.println("ClanID: " + clan.getId());
        System.out.println("Score: " + score);

        System.out.println("\nClan");
        System.out.println("------");

        clan.print();
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Clan getClan() {
        return clan;
    }
}
