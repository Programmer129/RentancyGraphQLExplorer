package com.rentancy.inventorygraphql.graphql;

import com.rentancy.inventorygraphql.api.InventoryService;
import graphql.schema.GraphQLSchema;
import graphql.schema.idl.RuntimeWiring;
import graphql.schema.idl.SchemaGenerator;
import graphql.schema.idl.SchemaParser;
import org.springframework.context.annotation.Bean;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Component;

import java.io.IOException;

import static graphql.schema.idl.TypeRuntimeWiring.newTypeWiring;

@Component
class GraphQLSchemaImpl {

    private static final String GRAPHQL_SCHEMA = "graphql/schema.graphqls";

    private final InventoryService inventoryService;

    GraphQLSchemaImpl(InventoryService inventoryService) {
        this.inventoryService = inventoryService;
    }

    @Bean
    GraphQLSchema buildSchema() throws IOException {
        var url = new ClassPathResource(GRAPHQL_SCHEMA);
        var typeRegistry = new SchemaParser().parse(url.getFile());
        return new SchemaGenerator().makeExecutableSchema(typeRegistry, buildWiring());
    }

    private RuntimeWiring buildWiring() {
        return RuntimeWiring.newRuntimeWiring()
                .type(newTypeWiring("Query")
                        .dataFetcher("getInventory", query -> {
                            var agreementId = Long.parseLong(query.getArgument("agreementId"));
                            return inventoryService.getInventory(agreementId);
                        })
                        .dataFetcher("getInventories", query -> inventoryService.getInventories())
                ).build();
    }

}
