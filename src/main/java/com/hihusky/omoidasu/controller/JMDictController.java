package com.hihusky.omoidasu.controller;

import com.hihusky.omoidasu.dto.SearchResultResponse;
import com.hihusky.omoidasu.service.JMDictService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/dicts/jmdict/")
public class JMDictController {

    private final JMDictService jmDictService;

    @GetMapping("/search/{keyword}")
    public ResponseEntity<List<SearchResultResponse>> search(@PathVariable("keyword") String keyword) {
        List<SearchResultResponse> jmDictEntryDTOList = jmDictService.search(keyword);
        return ResponseEntity.ok(jmDictEntryDTOList);
    }
}
