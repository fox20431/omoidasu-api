package com.hihusky.omoidasu.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString(exclude = "entry")
@Entity
@Table(name = "jmdict_kanji")
public class JMDictKanji {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "kanji")
    private String kanji;
    @ElementCollection
    @CollectionTable(
            name = "jmdict_kanji_information",
            joinColumns = @JoinColumn(name = "kanji_id"))
    @Column(name = "information")
    private List<String> informationList;
    @ElementCollection
    @CollectionTable(
            name = "jmdict_kanji_priorities",
            joinColumns = @JoinColumn(name = "kanji_id")
    )
    @Column(name = "priority")
    private List<String> priorities;

    // foreign key
    @ManyToOne(cascade = CascadeType.PERSIST)
    @JoinColumn(name = "entry_id")
    private JMDictEntry entry;

}
