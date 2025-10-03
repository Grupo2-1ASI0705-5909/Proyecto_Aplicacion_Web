package org.upc.trabajo_aplicaciones_web.securities;

import java.io.Serializable;

public class JwtResponse implements Serializable {
    private static final long serialVersionUID = -7858869558953243875L;
    private final String jwttoken;

    public String getJwttoken() {return jwttoken;}
    public JwtResponse(String jwttoken) {this.jwttoken = jwttoken;}
}
