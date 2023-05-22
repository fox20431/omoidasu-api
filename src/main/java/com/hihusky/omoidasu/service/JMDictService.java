package com.hihusky.omoidasu.service;

import com.hihusky.omoidasu.dto.SearchResultDTO;
import com.hihusky.omoidasu.entity.JMDictEntry;
import com.hihusky.omoidasu.entity.JMDictKana;
import com.hihusky.omoidasu.entity.JMDictKanji;
import com.hihusky.omoidasu.repo.JMDictEntryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class JMDictService {

    private JMDictEntryRepository jmDictEntryRepository;

    @Autowired
    public JMDictService(JMDictEntryRepository jmDictEntryRepository) {
        this.jmDictEntryRepository = jmDictEntryRepository;
    }

    public List<SearchResultDTO> search(String keyword) {
        return null;
    }

}
