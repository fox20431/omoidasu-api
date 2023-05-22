package com.hihusky.omoidasu;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.ComponentScan;

import javax.sql.DataSource;

@Disabled
@JdbcTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class DBTest {
    @Autowired
    private DataSource dataSource;

    @Test
    public void testDataSource() {
        assertNotNull(dataSource);
        try {
            dataSource.getConnection().isValid(1);
        } catch (Exception e) {
            fail(e);
        }
    }
}
