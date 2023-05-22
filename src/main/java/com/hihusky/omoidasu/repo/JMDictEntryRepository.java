package com.hihusky.omoidasu.repo;

import com.hihusky.omoidasu.entity.JMDictEntry;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface JMDictEntryRepository extends CrudRepository<JMDictEntry, Long> {
    // @Query("SELECT jmd.sequence FROM JMDictEntry jmd WHERE j.kanjiList.kanji = :searchTerm OR j.kanaList.kana = :keyword")
    // List<JMDictEntry> search(@Param("keyword") String keyword);
}
