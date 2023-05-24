package com.hihusky.omoidasu.persistentdict;

import com.hihusky.omoidasu.entity.*;
import com.hihusky.omoidasu.repo.DictFileHashRepository;
import com.hihusky.omoidasu.repo.JMDictEntryRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.stereotype.Component;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import java.io.*;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;

@Component
@Slf4j
public class JMDictXMLPersistence {

    private ResourceLoader resourceLoader;
    private DictFileHashRepository dictFileHashRepository;
    private JMDictEntryRepository jmDictEntryRepository;

    private Resource jmDict;
    private String hash;

    @Autowired
    public JMDictXMLPersistence(
            ResourceLoader resourceLoader,
            DictFileHashRepository dictFileHashRepository,
            JMDictEntryRepository jmDictEntryRepository
    ) {
        this.resourceLoader = resourceLoader;
        this.dictFileHashRepository = dictFileHashRepository;
        this.jmDictEntryRepository = jmDictEntryRepository;
        jmDict = resourceLoader.getResource("classpath:dicts/JMdict");
        hash = getFileHash();
    }

    private String getFileHash() {
        // create the input stream
        InputStream is;
        try {
            is = jmDict.getInputStream();
        } catch ( IOException e) {
            throw new RuntimeException(e);
        }
        // create the buffer
        byte[] buffer = new byte[1024];
        MessageDigest complete;
        try {
            complete = MessageDigest.getInstance("MD5");
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }
        int numRead;
        do {
            try {
                numRead = is.read(buffer);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            if (numRead > 0) {
                complete.update(buffer, 0, numRead);
            }
        } while (numRead != -1);
        try {
            is.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        byte[] b = complete.digest();
        String result = "";
        for (int i=0; i < b.length; i++) {
            result += Integer.toString( ( b[i] & 0xff ) + 0x100, 16).substring( 1 );
        }
        return result;
    }

    public void persistFileHash() {
        DictFileHash dictFileHash = new DictFileHash();
        dictFileHash.setHash(hash);
        dictFileHash.setName("JMdict");
        dictFileHashRepository.save(dictFileHash);
    }

    public void persistDict() {
        if (dictFileHashRepository.findByHash(hash).isEmpty()) {
            SAXParserFactory factory = SAXParserFactory.newInstance();
            SAXParser parser = null;
            try {
                parser = factory.newSAXParser();
            } catch (ParserConfigurationException | SAXException e) {
                throw new RuntimeException(e);
            }
            // 创建一个实现了DefaultHandler接口的类
            InputStream inputStream;
            try {
                inputStream = jmDict.getInputStream();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            try {
                parser.parse(inputStream, new JMDictXMLPersistenceHandler(jmDictEntryRepository));
            } catch (SAXException | IOException e) {
                throw new RuntimeException(e);
            }
        }
    }

    public void execute() {
        if (dictFileHashRepository.findByHash(hash).isEmpty()) {
            log.info("persisting dict file");
            persistDict();
            log.info("persisting dict file hash");
            persistFileHash();
        } else {
            log.info("dict file already persisted");
        }
    }

    public static class JMDictXMLPersistenceHandler extends DefaultHandler {

        private final JMDictEntryRepository repository;

        public JMDictXMLPersistenceHandler(JMDictEntryRepository repository) {
            this.repository = repository;
        }

        private JMDictEntry entry;
        private String currentElement;
        private Attributes currentAttributes;

        @Override
        public void startDocument() {
            log.info("start document");
        }

        @Override
        public void endDocument() {
            log.info("end document");
        }

        @Override
        public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException {
            currentElement = qName;
            currentAttributes = attributes;
            switch (currentElement) {
                case "entry":
                    entry = new JMDictEntry();
                    entry.setKanjiElements(new ArrayList<>());
                    entry.setKanaElements(new ArrayList<>());
                    entry.setSenses(new ArrayList<>());
                    break;
                case "k_ele":
                    JMDictKanjiElement kanji = new JMDictKanjiElement();
                    kanji.setEntry(entry);
                    entry.getKanjiElements().add(kanji);
                    break;
                case "r_ele":
                    JMDictKanaElement kana = new JMDictKanaElement();
                    kana.setEntry(entry);
                    entry.getKanaElements().add(kana);
                    break;
                case "sense":
                    JMDictSense sense = new JMDictSense();
                    sense.setEntry(entry);
                    sense.setGlosses(new ArrayList<>());
                    sense.setLanguageSources(new ArrayList<>());
                    entry.getSenses().add(sense);
                    break;
            }
        }

        @Override
        public void characters(char[] ch, int start, int length) {
            String value = new String(ch, start, length).trim();
            if (!value.isEmpty()) {
                switch (currentElement) {
                    // entry field
                    case "ent_seq":
                        entry.setSequence(Long.parseLong(value));
                        break;
                    // kanji field
                    case "keb":
                        entry.getKanjiElements().get(entry.getKanjiElements().size() - 1).setKanji(value);
                        break;
                    case "ke_inf":
                        JMDictKanjiElement jmDictKanjiElement = entry.getKanjiElements().get(entry.getKanjiElements().size() - 1);
                        jmDictKanjiElement.setInformationList(new ArrayList<>());
                        jmDictKanjiElement.getInformationList().add(value);
                        entry.getKanjiElements().get(entry.getKanjiElements().size() - 1).getInformationList().add(value);
                        break;
                    case "ke_pri":
                        entry.getKanjiElements().get(entry.getKanjiElements().size() - 1).setPriorities(new ArrayList<>());
                        entry.getKanjiElements().get(entry.getKanjiElements().size() - 1).getPriorities().add(value);
                        break;
                    // kana field
                    case "reb":
                        entry.getKanaElements().get(entry.getKanaElements().size() - 1).setKana(value);
                        break;
                    case "re_nokanji":
                        if ("".equals(value)) {
                            entry.getKanaElements().get(entry.getKanaElements().size() - 1).setNoKanji(false);
                        } else {
                            entry.getKanaElements().get(entry.getKanaElements().size() - 1).setNoKanji(true);
                        }
                        break;
                    case "re_restr":
                        entry.getKanaElements().get(entry.getKanaElements().size() - 1).setRestrictions(new ArrayList<>());
                        entry.getKanaElements().get(entry.getKanaElements().size() - 1).getRestrictions().add(value);
                        break;
                    case "re_inf":
                        entry.getKanaElements().get(entry.getKanaElements().size() - 1).setInformationList(new ArrayList<>());
                        entry.getKanaElements().get(entry.getKanaElements().size() - 1).getInformationList().add(value);
                        break;
                    case "re_pri":
                        entry.getKanaElements().get(entry.getKanaElements().size() - 1).setPriorities(new ArrayList<>());
                        entry.getKanaElements().get(entry.getKanaElements().size() - 1).getPriorities().add(value);
                        break;
                    // sense field
                    case "stagk":
                        entry.getSenses().get(entry.getSenses().size() - 1).setStagkList(new ArrayList<>());
                        entry.getSenses().get(entry.getSenses().size() - 1).getStagkList().add(value);
                        break;
                    case "stagr":
                        entry.getSenses().get(entry.getSenses().size() - 1).setStagrList(new ArrayList<>());
                        entry.getSenses().get(entry.getSenses().size() - 1).getStagrList().add(value);
                        break;
                    case "pos":
                        entry.getSenses().get(entry.getSenses().size() - 1).setPosList(new ArrayList<>());
                        entry.getSenses().get(entry.getSenses().size() - 1).getPosList().add(value);
                        break;
                    case "xref":
                        entry.getSenses().get(entry.getSenses().size() - 1).setCrossReferences(new ArrayList<>());
                        entry.getSenses().get(entry.getSenses().size() - 1).getCrossReferences().add(value);
                        break;
                    case "ant":
                        entry.getSenses().get(entry.getSenses().size() - 1).setAntonyms(new ArrayList<>());
                        entry.getSenses().get(entry.getSenses().size() - 1).getAntonyms().add(value);
                        break;
                    case "field":
                        entry.getSenses().get(entry.getSenses().size() - 1).setFields(new ArrayList<>());
                        entry.getSenses().get(entry.getSenses().size() - 1).getFields().add(value);
                        break;
                    case "misc":
                        entry.getSenses().get(entry.getSenses().size() - 1).setMiscList(new ArrayList<>());
                        entry.getSenses().get(entry.getSenses().size() - 1).getMiscList().add(value);
                        break;
                    case "s_inf":
                        entry.getSenses().get(entry.getSenses().size() - 1).setInformationList(new ArrayList<>());
                        entry.getSenses().get(entry.getSenses().size() - 1).getInformationList().add(value);
                        break;
                    case "lsource":
                        JMDictLanguageSource languageSource = new JMDictLanguageSource();
                        languageSource.setSense(entry.getSenses().get(entry.getSenses().size() - 1));
                        entry.getSenses().get(entry.getSenses().size() - 1).getLanguageSources().add(languageSource);
                        entry.getSenses().get(entry.getSenses().size() - 1).getLanguageSources().get(entry.getSenses().get(entry.getSenses().size() - 1).getLanguageSources().size() - 1).setValue(value);
                        if (currentAttributes.getValue("xml:lang") == null ) {
                            entry.getSenses().get(entry.getSenses().size() - 1).getLanguageSources().get(entry.getSenses().get(entry.getSenses().size() - 1).getLanguageSources().size() - 1).setLanguage("eng");
                        } else {
                            entry.getSenses().get(entry.getSenses().size() - 1).getLanguageSources().get(entry.getSenses().get(entry.getSenses().size() - 1).getLanguageSources().size() - 1).setLanguage(currentAttributes.getValue("xml:lang"));
                        }
                        if (currentAttributes.getValue("ls_type") == null) {
                            entry.getSenses().get(entry.getSenses().size() - 1).getLanguageSources().get(entry.getSenses().get(entry.getSenses().size() - 1).getLanguageSources().size() - 1).setType("full");
                        } else {
                            entry.getSenses().get(entry.getSenses().size() - 1).getLanguageSources().get(entry.getSenses().get(entry.getSenses().size() - 1).getLanguageSources().size() - 1).setType(currentAttributes.getValue("ls_type"));
                        }
                        if ("".equals(currentAttributes.getValue("ls_wasei"))) {
                            entry.getSenses().get(entry.getSenses().size() - 1).getLanguageSources().get(entry.getSenses().get(entry.getSenses().size() - 1).getLanguageSources().size() - 1).setWasei(false);
                        } else if ("y".equals(currentAttributes.getValue("ls_wasei"))){
                            entry.getSenses().get(entry.getSenses().size() - 1).getLanguageSources().get(entry.getSenses().get(entry.getSenses().size() - 1).getLanguageSources().size() - 1).setWasei(true);
                        }
                        break;
                    case "dial":
                        entry.getSenses().get(entry.getSenses().size() - 1).setDialects(new ArrayList<>());
                        entry.getSenses().get(entry.getSenses().size() - 1).getDialects().add(value);
                        break;
                    case "gloss":
                        JMDictGloss gloss = new JMDictGloss();
                        gloss.setSense(entry.getSenses().get(entry.getSenses().size() - 1));
                        entry.getSenses().get(entry.getSenses().size() - 1).getGlosses().add(gloss);
                        entry.getSenses().get(entry.getSenses().size() - 1).getGlosses().get(entry.getSenses().get(entry.getSenses().size() - 1).getGlosses().size() - 1).setGloss(value);
                        if (currentAttributes.getValue("xml:lang") == null ) {
                            entry.getSenses().get(entry.getSenses().size() - 1).getGlosses().get(entry.getSenses().get(entry.getSenses().size() - 1).getGlosses().size() - 1).setLanguage("eng");
                        } else {
                            entry.getSenses().get(entry.getSenses().size() - 1).getGlosses().get(entry.getSenses().get(entry.getSenses().size() - 1).getGlosses().size() - 1).setLanguage(currentAttributes.getValue("xml:lang"));
                        }
                        break;
                }
            }
        }

        @Override
        public void endElement(String uri, String localName, String qName) throws SAXException {
            if ("entry".equals(qName)) {
                log.info(entry.toString());
                repository.save(entry);
            }
        }
    }

}

