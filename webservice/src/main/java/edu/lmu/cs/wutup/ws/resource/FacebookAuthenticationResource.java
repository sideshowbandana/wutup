package edu.lmu.cs.wutup.ws.resource;

import java.net.URI;
import java.net.URLEncoder;
import java.util.Random;

import javax.ws.rs.DefaultValue;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Response;

import org.springframework.stereotype.Component;

@Component
@Path("/auth")
public class FacebookAuthenticationResource {
    
    @GET
    @Path("/facebook")
    public Response authenticate() {
        try {
            String redirectURI = "https://www.facebook.com/dialog/oauth?" +
                    "client_id=525740714109980" +
                    "&redirect_uri=" + URLEncoder.encode("http://localhost:8080/wutup/auth/landing", "ISO-8859-1") +
                    "&scope=user_events,create_event" +
                    "&state=" + Math.abs(new Random().nextInt());
            
            return Response
                    .seeOther(new URI(redirectURI))
                    .build();
        } catch (Exception e) {
            return Response
                    .serverError()
                    .build();
        }
        
    }
    
    @GET
    @Path("/landing")
    public Response handleFacebookAuthenticationResponse (
            @QueryParam("state") String state,
            @QueryParam("code") String code,
            @DefaultValue("") @QueryParam("error_reason") String errorReason,
            @DefaultValue("") @QueryParam("error") String error,
            @DefaultValue("") @QueryParam("error_description") String errorDescription
            ) {
        
        if (!error.equals("")) {
            return Response
                    .status(Response.Status.UNAUTHORIZED)
                    .build();
        }
        
        /*
         * We need to send a request to FB in order to exchange our code for an access token.
         * Once we have our access token, we will be golden.
         */
        
        return Response
                .ok(code)
                .build();
    }
}