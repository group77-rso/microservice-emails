package com.emails.api.v1.resources;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.kumuluz.ee.logs.cdi.Log;
import com.emails.lib.Price;
import com.emails.services.config.MicroserviceLocations;
import org.eclipse.microprofile.openapi.annotations.parameters.Parameter;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.io.*;
import java.net.HttpURLConnection;
import java.net.URI;
import java.net.URL;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.charset.StandardCharsets;
import java.time.Duration;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.logging.Level;
import java.util.logging.Logger;

import java.io.IOException;

@Log
@ApplicationScoped
@Path("/emails")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class EmailsResource {

    private Logger log = Logger.getLogger(EmailsResource.class.getName());

    @Inject
    private MicroserviceLocations microserviceLocations;
    private static HttpURLConnection conn;

    @GET
    @Path("scrape")
    public Response scrapeTest(@Parameter(hidden = true) @HeaderParam("requestId") String requestId) {

        requestId = requestId != null ? requestId : UUID.randomUUID().toString();

        ArrayList<Price> prices = new ArrayList<>();

        return Response.status(Response.Status.OK).header("requestId", requestId).entity(prices).build();
    }



}