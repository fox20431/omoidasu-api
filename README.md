# 思い出す

## Build

To ensure the environment is ready, please run docker and use the `docker-compose.yaml`.

The dictionary needs to be fetched because the dictionary files are big and uncomfortable to record into git.

So you can use the `fetch_dictionary.sh`(TODO) or `fetch_dictionary.ps1` to fetch the dictionary files.

The `entityExpansionLimit` VM option should be added.

## To Developer

### Integration Dictionary

It will be shown in YAML format to clarify the dictionary data construction.

**JMDict**

```yaml
JMdict:
  ent_seq: xxx
  k_ele:
    - keb: xxx
      ke_inf: xxx
      ke_pri: xxx
  r_ele:
    - reb: xxx
      re_nokanji: xxx
      re_restr: xxx
      re_inf: xxx
      re_pri: xxx
  sense:
    - stagk: xxx
      stagr: xxx
      pos: xxx
      xref: xxx
      ant: xxx
      field: xxx
      misc: xxx
      s_inf: xxx
      lsource:
        - lang: xxx
          ls_type: xxx
          ls_wasei: xxx
          ls_source: xxx
      dial: xxx
      gloss:
        - g_gend: xxx
          g_type: xxx
          g_lang: xxx
          g_xref: xxx
          g_pri: xxx
          g_gloss: xxx
```

## Q&A

Why is `fetch-dict.ps1` failed the error `7-Z is not installed or not added to the system PATH environment variable`?

> The error is caused by the 7-Zip is not installed or not added to the system PATH environment variable.
> The official JMdict source is gz format, so you need to install 7-Zip to decompress it.

Occur `columnNumber: 1; JAXP00010001: The parser has encountered more than "64000" entity expansions in this document; this is the limit imposed by the JDK.` 

> `-Djdk.xml.entityExpansionLimit=0` VM Option should be added.

## License

GNU v3

