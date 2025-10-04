package org.upc.trabajo_aplicaciones_web.securities;

import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;

import java.io.Serializable;

public class JwtRequest implements Serializable {
    private static final long serialVersionUID = -7858869558953243875L;
    private String email;
    private String passwordHash;

    public JwtRequest() {
        super();
    }

    public JwtRequest(String email, String passwordHash) {
        this.email = email;
        this.passwordHash = passwordHash;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPasswordHash() {
        return passwordHash;
    }

    public void setPasswordHash(String passwordHash) {
        this.passwordHash = passwordHash;
    }
}
