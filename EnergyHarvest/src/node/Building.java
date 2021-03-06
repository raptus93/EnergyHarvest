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

public class Building {

    private int id;

    private Clan cat0_clan;
    private Clan cat1_clan;
    private Clan cat2_clan;
    private Clan cat3_clan;

    private String cat0;
    private String cat1;
    private String cat2;
    private String cat3;

    private int cat0_unlockTime;
    private int cat1_unlockTime;
    private int cat2_unlockTime;
    private int cat3_unlockTime;


    public Building(){}

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Clan getCat0_clan() {
        return cat0_clan;
    }

    public void setCat0_clan(Clan cat0_clan) {
        this.cat0_clan = cat0_clan;
    }

    public Clan getCat1_clan() {
        return cat1_clan;
    }

    public void setCat1_clan(Clan cat1_clan) {
        this.cat1_clan = cat1_clan;
    }

    public Clan getCat2_clan() {
        return cat2_clan;
    }

    public void setCat2_clan(Clan cat2_clan) {
        this.cat2_clan = cat2_clan;
    }

    public Clan getCat3_clan() {
        return cat3_clan;
    }

    public void setCat3_clan(Clan cat3_clan) {
        this.cat3_clan = cat3_clan;
    }

    public String getCat0() {
        return cat0;
    }

    public void setCat0(String cat0) {
        this.cat0 = cat0;
    }

    public String getCat1() {
        return cat1;
    }

    public void setCat1(String cat1) {
        this.cat1 = cat1;
    }

    public String getCat2() {
        return cat2;
    }

    public void setCat2(String cat2) {
        this.cat2 = cat2;
    }

    public String getCat3() {
        return cat3;
    }

    public void setCat3(String cat3) {
        this.cat3 = cat3;
    }

    public int getCat0_unlockTime() {
        return cat0_unlockTime;
    }

    public void setCat0_unlockTime(int cat0_unlockTime) {
        this.cat0_unlockTime = cat0_unlockTime;
    }

    public int getCat1_unlockTime() {
        return cat1_unlockTime;
    }

    public void setCat1_unlockTime(int cat1_unlockTime) {
        this.cat1_unlockTime = cat1_unlockTime;
    }

    public int getCat2_unlockTime() {
        return cat2_unlockTime;
    }

    public void setCat2_unlockTime(int cat2_unlockTime) {
        this.cat2_unlockTime = cat2_unlockTime;
    }

    public int getCat3_unlockTime() {
        return cat3_unlockTime;
    }

    public void setCat3_unlockTime(int cat3_unlockTime) {
        this.cat3_unlockTime = cat3_unlockTime;
    }

    public void print(){
        cat0_clan.print();
        cat1_clan.print();
        cat2_clan.print();
        cat3_clan.print();

        System.out.println(cat0);
        System.out.println(cat1);
        System.out.println(cat2);
        System.out.println(cat3);
    }
}
