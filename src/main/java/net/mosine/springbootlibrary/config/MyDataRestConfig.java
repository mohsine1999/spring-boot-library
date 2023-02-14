package net.mosine.springbootlibrary.config;

import net.mosine.springbootlibrary.entities.Book;
import net.mosine.springbootlibrary.entities.Review;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.rest.core.config.RepositoryRestConfiguration;
import org.springframework.data.rest.webmvc.config.RepositoryRestConfigurer;
import org.springframework.http.HttpMethod;
import org.springframework.web.servlet.config.annotation.CorsRegistry;

@Configuration
public class MyDataRestConfig implements RepositoryRestConfigurer {

    private String theAllowedOrigins = "http://localhost:3000";

    public void configureRepositoryRestConfiguration (RepositoryRestConfiguration configuration, CorsRegistry cors){
        HttpMethod [] theUnsupporttedActions = {
                HttpMethod.POST,
                HttpMethod.PATCH,
                HttpMethod.DELETE,
                HttpMethod.PUT
        };
    configuration.exposeIdsFor(Book.class);
        configuration.exposeIdsFor(Review.class);

    disableHttpMethods(Book.class, configuration, theUnsupporttedActions);
    disableHttpMethods(Review.class, configuration, theUnsupporttedActions);


    /* Configure CORS Mapping */
        cors.addMapping(configuration.getBasePath() + "/**")
                .allowedOrigins(theAllowedOrigins);
    }

    private void disableHttpMethods(Class bookClass, RepositoryRestConfiguration configuration, HttpMethod[] theUnsupporttedActions) {
        configuration.getExposureConfiguration()
                     .forDomainType(bookClass)
                     .withItemExposure(((metdata, httpMethods) -> httpMethods.disable(theUnsupporttedActions)))
                     .withCollectionExposure((metdata, httpMethods) -> httpMethods.disable(theUnsupporttedActions));
    }
}
