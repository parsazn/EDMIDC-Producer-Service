package com.edmidcproducerservicesource.config;

import org.hibernate.boot.model.naming.Identifier;
import org.hibernate.boot.model.naming.PhysicalNamingStrategyStandardImpl;
import org.hibernate.engine.jdbc.env.spi.JdbcEnvironment;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Arrays;

@Configuration
public class DynamicTableNameProvider {

    @Value("${spring.application.name}")
    private String name;

    private static String NAME_STATIC;

    @Value("${spring.application.name}")
    public void setNameStatic(String name){
        DynamicTableNameProvider.NAME_STATIC = name;
    }

    public static String toCamelCase(String input) {
        return Arrays.stream(input.split("-"))
                .map(part -> part.substring(0, 1).toUpperCase() + part.substring(1).toLowerCase())
                .reduce("", String::concat);
    }


    @Bean
    public PhysicalNamingStrategyStandardImpl physicalNamingStrategyStandard() {
        return new PhysicalNamingImpl();
    }

    static class PhysicalNamingImpl extends PhysicalNamingStrategyStandardImpl {
        @Override
        public Identifier toPhysicalTableName(Identifier name, JdbcEnvironment context) {
            return switch (name.getText()) {
                case "SpringApplicationName" -> new Identifier(toCamelCase(NAME_STATIC) + "_failedkafkamessages", name.isQuoted());
                default -> super.toPhysicalTableName(name, context);
            };
        }
    }

}

