package com.hihusky.omoidasu.controller;

import com.hihusky.omoidasu.dto.SearchResultDTO;
import com.hihusky.omoidasu.service.JMDictService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/dicts/jmdict/")
public class JMDictController {

    JMDictService jmDictService;

    @Autowired
    public JMDictController(JMDictService jmDictService) {
        this.jmDictService = jmDictService;
    }

    @GetMapping("/search/{keyword}")
    public ResponseEntity<List<SearchResultDTO>> search(@PathVariable("keyword") String keyword) {
        List<SearchResultDTO> jmDictEntryDTOList = jmDictService.search(keyword);
        return ResponseEntity.ok(jmDictEntryDTOList);
    }
}
