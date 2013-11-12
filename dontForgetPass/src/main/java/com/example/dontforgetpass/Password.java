package com.example.dontforgetpass;

/**
 * Created by lhernandez on 11/11/13.
 */
public class Password {
    private String title, username, password;
    public Password(String title,String username,String password){
        this.title = title;
        this.username = username;
        this.password = password;
    }

    public String getTitle(){
        return this.title;
    }

    public String getUsername() {
        return this.username;
    }

    public String getPassword() {
        return this.password;
    }

    public void setTitle(String title){
        this.title = title;
    }

    public void setUsername(String username){
        this.username = username;
    }

    public void setPassword(String password){
        this.password = password;
    }

}
