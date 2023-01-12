package com.emails.api.v1.resources;

import com.emails.services.config.RestProperties;
import com.kumuluz.ee.logs.cdi.Log;
import org.eclipse.microprofile.openapi.annotations.Operation;
import org.eclipse.microprofile.openapi.annotations.parameters.Parameter;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.Duration;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;
import java.util.logging.Level;
import java.util.logging.Logger;


@Log
@ApplicationScoped
@Path("/emails")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class EmailsResource {

    private Logger log = Logger.getLogger(EmailsResource.class.getName());

    @Inject
    private RestProperties restProperties;

    @GET
    @Operation(description = "Sends an email.", summary = "Asynchronously sends an email")
    @Path("/send")
    public Response sendEmail(@Parameter(hidden = true) @HeaderParam("requestId") String requestId) {

        requestId = requestId != null ? requestId : UUID.randomUUID().toString();
        log.log(Level.INFO,
                        "Received a request to send an email!"
                                + " - requestId: " + requestId);

        String email = restProperties.getMail();

        String api = "https://api.sendgrid.com/v3/mail/send";
        String requestBody = String.format("""
                {
                    "personalizations": [
                        {
                            "to": [
                                {
                                    "email": "%s"
                                }
                            ]
                        }
                    ],
                    "from": {
                        "email": "bl8031@student.uni-lj.si"
                    },
                    "subject": "Scraping done.",
                    "content": [
                        {
                            "type": "text/plain",
                            "value": "Prices for products have been scraped for each merchant and are saved to the database."
                        }
                    ]
                }""", email);
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(api))
                .timeout(Duration.ofMinutes(2))
                .header("Content-Type", "application/json")
                .header("Authorization", "Bearer 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")
                .header("Content-Type", "keep-alive")
                .header("Accept-Encoding", "gzip, deflate, br")
                .POST(HttpRequest.BodyPublishers.ofString(requestBody))
                .build();

        String finalRequestId = requestId;
        CompletableFuture<Void> response = HttpClient.newBuilder()
                .build()
                .sendAsync(request, HttpResponse.BodyHandlers.ofString())
                .thenApply(HttpResponse::body)
                .thenAccept(s -> log.log(Level.INFO,
                        String.format("Email to %s sent!", email)
                                + " - requestId: " + finalRequestId));

        return Response.status(Response.Status.OK).header("requestId", requestId).build();
    }


}
