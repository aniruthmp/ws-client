package io.pivotal.wsclient;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.oorsprong.websamples.FullCountryInfoResponse;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.ws.client.core.WebServiceTemplate;

import javax.xml.bind.JAXB;
import java.io.StringWriter;

import static net.javacrumbs.smock.common.client.ClientTestHelper.response;
import static org.junit.Assert.assertEquals;
import static org.mockito.Matchers.anyObject;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@RunWith(SpringRunner.class)
@SpringBootTest
public class WsClientApplicationTests {

    private WebServiceTemplate webServiceTemplate;
    private CountryApi countryApi;

    @Before
    public void createMock() throws Exception {
        countryApi = new CountryApi();
        webServiceTemplate = mock(WebServiceTemplate.class);
        countryApi.setWebServiceTemplate(webServiceTemplate);
    }

    @Test
    public void getCountryInfo() throws Exception {
        when(webServiceTemplate.marshalSendAndReceive(anyObject())).thenReturn(response("getCountryInfoResponse.xml", FullCountryInfoResponse.class));
        FullCountryInfoResponse response = countryApi.getCountryInfo("USA");
        StringWriter sw = new StringWriter();
        JAXB.marshal(response, sw);
        System.out.println("sw = " + sw.toString());

        assertEquals("GB", response.getFullCountryInfoResult().getSISOCode());
    }

}
