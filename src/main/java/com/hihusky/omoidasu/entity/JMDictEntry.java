package com.hihusky.omoidasu.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
@Entity
@Table(name = "jmdict_entries")

public class JMDictEntry {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "sequence", unique = true, nullable = false)
    private Long sequence;

    @OneToMany(mappedBy = "entry", cascade = CascadeType.PERSIST)
    private List<JMDictKanji> kanjiList;

    @OneToMany(mappedBy = "entry", cascade = CascadeType.PERSIST)
    private List<JMDictKana> kanaList;

    @OneToMany(mappedBy = "entry", cascade = CascadeType.PERSIST)
    private List<JMDictSense> senses;
}
