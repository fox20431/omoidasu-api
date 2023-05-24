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
@Table(name = "jmdict_kana_elements")
public class JMDictKanaElement {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "kana")
    private String kana;
    @Column(name = "no_kanji")
    private boolean noKanji;
    @ElementCollection
    @CollectionTable(
            name = "jmdict_kana_element_restrictions",
            joinColumns = @JoinColumn(name = "kana_id"))
    @Column(name = "restriction")
    private List<String> restrictions;
    @ElementCollection
    @CollectionTable(
            name = "jmdict_kana_element_information_list",
            joinColumns = @JoinColumn(name = "kana_id"))
    @Column(name = "information")
    private List<String> informationList;
    @ElementCollection
    @CollectionTable(
            name = "jmdict_kana_element_priorities",
            joinColumns = @JoinColumn(name = "kana_id"))
    @Column(name = "priority")
    private List<String> priorities;


    // foreign key
    @ManyToOne(cascade = CascadeType.PERSIST)
    @JoinColumn(name = "entry_id")
    private JMDictEntry entry;
}
