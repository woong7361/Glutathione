package com.example.productservice.performance;

import com.example.productservice.dto.product.ProductCreateRequestDto;
import com.example.productservice.dto.product.ProductSearchRequestDto;
import com.example.productservice.dto.type.ProductTypeCreateRequestDto;
import com.example.productservice.repository.ProductRepository;
import com.example.productservice.service.ProductService;
import org.apache.commons.lang.RandomStringUtils;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.util.List;
import java.util.Random;

@SpringBootTest
@ActiveProfiles("local")
public class PerformanceTest {

    @Autowired
    ProductService productService;
    @Autowired
    ProductRepository productRepository;

    Random random = new Random();

    //
    private final List<String> styleList = List.of("hot", "cool", "winter", "form", "puff", "steam", "cold", "warm", "pink", "red",
            "green", "blue", "smart", "bad", "not", "null", "purify", "gradle", "boss", "body", "leg", "modal", "swim",
            "bath", "room", "outdoor", "inner", "pants", "one", "two", "mobile", "hair", "head", "abg", "tennis",
            "ball", "bat", "pig", "dig", "dog", "cat", "live", "liver", "pool", "soft", "flower", "soap", "wash");

    //34개
    private final List<String> types = List.of("phants", "shirt", "top", "blouse", "sweater", "hoodie", "dress shirt", "tank top",
            "cardigan", "vest", "jacket", "blazer", "coat", "trench coat", "rain coat", "windbreaker", "parka", "anorak",
            "overcoat", "T-shirt", "long goose down", "hawaiian shirt", "waist coat", "puffer vest", "cargo pants",
            "jeans", "caprants", "capri pants", "slacks", "sweatpants", "trousers", "harem pants", "wide pants", "roll up pants");


    @Test
    public void loopTest() {
        for (int i = 0; i < 100000; i++) {
            createDummyData();
        }
    }

    @Test
    public void createType() {
        for (String type : types) {
            ProductTypeCreateRequestDto requestDto = new ProductTypeCreateRequestDto(type);
            productService.createProductType(requestDto);
        }
    }


    public void createDummyData() {
        long typeId = random.nextInt(33) + 1;
        String style = styleList.get(random.nextInt(48));
        String name = RandomStringUtils.random(10, true, false);
        String desc = RandomStringUtils.random(10, true, true);
        String content = RandomStringUtils.random(20, true, true);
        int unitPrice = random.nextInt(1000) + 1;
        int quantity = random.nextInt(1000) + 1;


        // random으로 하는게
        ProductCreateRequestDto req = ProductCreateRequestDto.builder()
                .name(name)
                .description(desc)
                .content(content)
                .productTypeId(typeId)
                .productStyles(List.of(style))
                .unitPrice(unitPrice)
                .quantity(quantity)
                .thumbnailImageId(1L)
                .build();

        productService.createProduct(req);
    }

    @Test
    public void test() throws Exception{
        for (int i = 0; i < 60000; i++) {
            long memberId= random.nextInt(1000) + 1;
            long productId= random.nextInt(100000) + 1;

            try {
                productService.createFavorProduct(productId, memberId);
            } catch (Exception e) {
                System.out.println("fail!!! = " + e);
            }
        }

    }

    @Test
    public void searchTest() throws Exception{
        productRepository.search(new ProductSearchRequestDto("tvc", null, null, null, null, 1, 5), 74L);

    }
}
