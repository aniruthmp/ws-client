package io.pivotal.wsclient;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.oxm.jaxb.Jaxb2Marshaller;
import org.springframework.ws.client.core.WebServiceTemplate;
import org.springframework.ws.client.core.support.WebServiceGatewaySupport;
import org.springframework.ws.transport.WebServiceMessageSender;
import org.springframework.ws.transport.http.HttpComponentsMessageSender;

@Configuration
public class WebServiceConfig extends WebServiceGatewaySupport {

    @Value("${client.default-uri}")
    private String defaultUri;

    @Value("${client.timeout}")
    private int timeout;

    @Bean
    Jaxb2Marshaller jaxb2Marshaller() {
        Jaxb2Marshaller jaxb2Marshaller = new Jaxb2Marshaller();
        jaxb2Marshaller.setContextPath("org.oorsprong.websamples");

        return jaxb2Marshaller;
    }

    @Bean
    public WebServiceTemplate webServiceTemplate() {
        WebServiceTemplate webServiceTemplate = new WebServiceTemplate();
        webServiceTemplate.setMarshaller(jaxb2Marshaller());
        webServiceTemplate.setUnmarshaller(jaxb2Marshaller());
        webServiceTemplate.setDefaultUri(defaultUri);
        webServiceTemplate.setMessageSender(webServiceMessageSender());

        return webServiceTemplate;
    }

    @Bean
    public WebServiceMessageSender webServiceMessageSender() {
        HttpComponentsMessageSender httpComponentsMessageSender = new HttpComponentsMessageSender();
        // timeout for creating a connection
        httpComponentsMessageSender.setConnectionTimeout(timeout);
        // when you have a connection, timeout the read blocks for
        httpComponentsMessageSender.setReadTimeout(timeout);

        return httpComponentsMessageSender;
    }
}
