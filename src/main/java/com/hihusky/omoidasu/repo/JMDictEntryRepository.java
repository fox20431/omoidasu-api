package com.hihusky.omoidasu.repo;

import com.hihusky.omoidasu.entity.JMDictEntry;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface JMDictEntryRepository extends CrudRepository<JMDictEntry, Long> {
    @Query(value = "SELECT entries.sequence, kanjiElementList.kanji, kanaElementList.kana " +
            "FROM JMDictEntry entries " +
            "JOIN entries.kanjiElements kanjiElementList " +
            "JOIN entries.kanaElements kanaElementList " +
            "WHERE :keyword IN (kanjiElementList.kanji, kanaElementList.kana)")
    List<Object[]> search(@Param("keyword") String keyword);

}
