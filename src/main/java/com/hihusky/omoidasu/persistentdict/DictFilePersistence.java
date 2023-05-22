package com.hihusky.omoidasu.persistentdict;

import com.hihusky.omoidasu.entity.DictFileHash;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

// TODO: I think the Dict Persistence has a more clear method to express them
interface DictFilePersistence {
    void execute();
}
