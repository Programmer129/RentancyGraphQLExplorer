package com.rentancy.inventorygraphql;

import org.json.JSONException;
import org.json.JSONObject;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Map;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

@RunWith(SpringRunner.class)
@ContextConfiguration(classes = TestContext.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
public class GraphQLSchemaImplTest {

    private TestRestTemplate restTemplate;

    @Before
    public void setUp() {
        this.restTemplate = new TestRestTemplate();
    }

    @Test
    public void shouldGetInventory() throws JSONException {
        // given
        var query = "{\n " +
                "\"query\": \"# Write your query or mutation here\\n" +
                "{\\n  getInventory(agreementId: 1000) " +
                "{\\n    wallsAndCeilings\\n    " +
                "floorCoverings\\n    " +
                "windows\\n    " +
                "doors\\n    " +
                "lights\\n    " +
                "furniture\\n   " +
                " skirting\\n  " +
                "}\\n}\"\n}";

        var httpEntity = new HttpEntity<>(query);

        // when
        var result = restTemplate.exchange("http://localhost:8080/graphql", HttpMethod.POST, httpEntity, Map.class);

        // then
        var expected = "{\n" +
                "  \"data\": {\n" +
                "    \"getInventory\": {\n" +
                "      \"wallsAndCeilings\": 3,\n" +
                "      \"floorCoverings\": 5,\n" +
                "      \"windows\": 2,\n" +
                "      \"doors\": 4,\n" +
                "      \"lights\": 5,\n" +
                "      \"furniture\": 0,\n" +
                "      \"skirting\": 3\n" +
                "    }\n" +
                "  }\n" +
                "}";


        assertEquals(200, result.getStatusCodeValue());
        assertNotNull(result.getBody());
        assertEquals(new JSONObject(expected).toString(), new JSONObject(result.getBody()).toString());
    }
}
