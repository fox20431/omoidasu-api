package com.hihusky.omoidasu.dto;

import lombok.*;

import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
@ToString
public class SearchResultResponse {
    private Long sequence;
    private List<String> kanjiList;
    private List<String> kanaList;
}
