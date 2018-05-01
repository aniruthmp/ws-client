package io.pivotal.wsclient;

import lombok.extern.slf4j.Slf4j;
import org.oorsprong.websamples.FullCountryInfo;
import org.oorsprong.websamples.FullCountryInfoResponse;
import org.oorsprong.websamples.ObjectFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.ws.client.core.WebServiceTemplate;

@RestController
@Slf4j
public class CountryApi {

    @Autowired
    private WebServiceTemplate webServiceTemplate;

    @GetMapping(value = "/countryInfo", produces = MediaType.APPLICATION_XML_VALUE)
    public FullCountryInfoResponse getCountryInfo(@RequestParam("countryIsoCode") String countryIsoCode) {
        log.info("Came inside getCountryInfo for countryIsoCode: " + countryIsoCode);
        FullCountryInfoResponse response = null;

        try {
            ObjectFactory factory = new ObjectFactory();
            FullCountryInfo fullCountryInfo = factory.createFullCountryInfo();
            fullCountryInfo.setSCountryISOCode(countryIsoCode);

            log.info("Requesting " + fullCountryInfo.toString());
            response = (FullCountryInfoResponse) webServiceTemplate.marshalSendAndReceive(fullCountryInfo);
            log.info("Returning " + response.toString());
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return response;
    }

    public void setWebServiceTemplate(WebServiceTemplate webServiceTemplate) {
        this.webServiceTemplate = webServiceTemplate;
    }
}