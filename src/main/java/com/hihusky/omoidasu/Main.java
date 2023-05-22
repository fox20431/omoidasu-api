package com.hihusky.omoidasu;

import com.hihusky.omoidasu.persistentdict.JMDictXMLPersistence;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.context.ConfigurableApplicationContext;

@SpringBootApplication
public class Main {
    public static void main(String[] args) throws Exception {
        ConfigurableApplicationContext context = SpringApplication.run(Main.class, args);

        JMDictXMLPersistence jmDictDBLoader = context.getBean(JMDictXMLPersistence.class);
        jmDictDBLoader.execute();
    }
}
