package com.hihusky.omoidasu.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
@ToString(exclude = "entry")
@Entity
@Table(name = "jmdict_kanji_elements")
public class JMDictKanjiElement {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @Column(name = "kanji")
    private String kanji;
    @ElementCollection
    @CollectionTable(
            name = "jmdict_kanji_element_information_list",
            joinColumns = @JoinColumn(name = "kanji_id"))
    @Column(name = "information")
    private List<String> informationList;
    @ElementCollection
    @CollectionTable(
            name = "jmdict_kanji_element_priorities",
            joinColumns = @JoinColumn(name = "kanji_id")
    )
    @Column(name = "priority")
    private List<String> priorities;

    // foreign key
    @ManyToOne(cascade = CascadeType.PERSIST)
    @JoinColumn(name = "entry_id")
    private JMDictEntry entry;

}
