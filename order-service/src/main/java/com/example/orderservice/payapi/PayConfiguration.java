package com.example.orderservice.payapi;

import com.siot.IamportRestClient.IamportClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * 결재 API 설정 class
 * -> PortOne 사용중
 */
@Configuration
public class PayConfiguration {

    String apiKey = "1657551317134748";
    String secretKey = "x6fBJ6J5ObqjCRSRloJZSagnr2Z39lbKAOrjs8sDlwiGE0CoOrP968rHGmfxVpmHG1zGrto1ktWoTWaI";

    @Bean
    public IamportClient iamportClient() {
        return new IamportClient(apiKey, secretKey);
    }
}
