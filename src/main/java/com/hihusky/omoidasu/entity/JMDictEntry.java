package com.hihusky.omoidasu.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
@ToString
@Entity
@Table(name = "jmdict_entries")
public class JMDictEntry {

    public JMDictEntry(Long sequence, List<JMDictKanjiElement> kanjiElements, List<JMDictKanaElement> kanaList) {
        this.sequence = sequence;
        this.kanjiElements = kanjiElements;
        this.kanaElements = kanaList;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "sequence", unique = true, nullable = false)
    private Long sequence;

    @OneToMany(mappedBy = "entry", cascade = CascadeType.PERSIST)
    private List<JMDictKanjiElement> kanjiElements;

    @OneToMany(mappedBy = "entry", cascade = CascadeType.PERSIST)
    private List<JMDictKanaElement> kanaElements;

    @OneToMany(mappedBy = "entry", cascade = CascadeType.PERSIST)
    private List<JMDictSense> senses;
}
