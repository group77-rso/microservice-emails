package com.emails.api.v1;

import javax.ws.rs.ApplicationPath;
import javax.ws.rs.core.Application;


import org.eclipse.microprofile.openapi.annotations.OpenAPIDefinition;
import org.eclipse.microprofile.openapi.annotations.info.Contact;
import org.eclipse.microprofile.openapi.annotations.info.Info;
import org.eclipse.microprofile.openapi.annotations.info.License;
import org.eclipse.microprofile.openapi.annotations.servers.Server;

@OpenAPIDefinition(info = @Info(title = "emails API", version = "v1",
        contact = @Contact(email = "gg4792@student.uni-lj.si"),
        license = @License(name = "dev"), description = "API for managing emails."),
        servers = @Server(url = "http://localhost:8082/"))
@ApplicationPath("/v1")
public class EmailsApplication extends Application {

}
