package org.upc.trabajo_aplicaciones_web.securities;

import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;

import java.io.Serializable;

public class JwtRequest implements Serializable {
    private static final long serialVersionUID = -7858869558953243875L;
    private String username;
    private String password;

    public JwtRequest() {
        super();
    }

    public JwtRequest(String username, String password) {
        super();
        this.username = username;
        this.password = password;
    }

    public static long getSerialVersionUID() {return serialVersionUID;}
    public String getUsername() {return username;}
    public void setUsername(String username) {this.username = username;}
    public String getpassword() {return password;}
    public void setPassword(String password) {this.password = password;}
}
