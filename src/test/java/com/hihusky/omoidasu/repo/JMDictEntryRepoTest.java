package com.hihusky.omoidasu.repo;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.List;
import java.util.Objects;

// We can use the following annotations to replace `@SpringBootTest`
@ExtendWith(SpringExtension.class)
@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
// @SpringBootTest
@Slf4j
public class JMDictEntryRepoTest {
    @Autowired
    JMDictEntryRepository jmDictEntryRepository;

    @Test
    public void testSearch() {
        List<Object[]> resultList = jmDictEntryRepository.search("○");
        for (Object[] result : resultList) {
            log.info(Objects.toString(result));
            Long sequence = (Long) result[0];
            String kanji = (String) result[1];
            String kana = (String) result[2];
            assert sequence == 1000090;
            assert Objects.equals(kanji, "○");
            assert Objects.equals(kana, "まる");
        }
    }

}
