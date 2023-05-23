package com.hihusky.omoidasu.service;

import com.hihusky.omoidasu.dto.SearchResultDTO;
import com.hihusky.omoidasu.repo.JMDictEntryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class JMDictService {

    @Autowired
    private JMDictEntryRepository jmDictEntryRepository;

    public List<SearchResultDTO> search(String keyword) {
        List<Object[]> resultList = jmDictEntryRepository.search(keyword);
        Map<Long, SearchResultDTO> resultMap = new HashMap<>();
        for (Object[] result : resultList) {
            Long sequence = (Long) result[0];
            String kanji = (String) result[1];
            String kana = (String) result[2];
            SearchResultDTO searchResultDTO = resultMap.get(sequence);
            if (searchResultDTO == null) {
                searchResultDTO = SearchResultDTO.builder()
                        .sequence(sequence)
                        .kanjiList(new ArrayList<>() {{
                            add(kanji);
                        }})
                        .kanaList(new ArrayList<>() {{
                            add(kana);
                        }})
                        .build();
                resultMap.put(sequence, searchResultDTO);
            } else {
                if (!searchResultDTO.getKanjiList().contains(kanji)){
                    searchResultDTO.getKanjiList().add(kanji);
                }
                if (!searchResultDTO.getKanaList().contains(kana)){
                    searchResultDTO.getKanaList().add(kana);
                }
            }
        }
        return new ArrayList<>(resultMap.values());
    }

}
