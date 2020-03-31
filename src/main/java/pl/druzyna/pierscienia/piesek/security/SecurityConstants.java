package pl.druzyna.pierscienia.piesek.security;

import com.auth0.jwt.algorithms.Algorithm;

public interface SecurityConstants {
    String JWT_HEADER_NAME = "Authorization";
    String JWT_HEADER_VALUE_PREFIX = "Bearer ";

    //864_000_00L = one day
    Long JWT_EXPIRATION_TIME_MILLIS = 864_000_00L;

    Algorithm JWT_ALGORITHM = Algorithm.HMAC512("asd'p5039tujg0djvxmz,.vnZ'asdpfkadf");
}
