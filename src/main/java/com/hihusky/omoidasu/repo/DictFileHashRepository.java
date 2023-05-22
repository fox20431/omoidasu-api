package com.hihusky.omoidasu.repo;

import com.hihusky.omoidasu.entity.DictFileHash;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface DictFileHashRepository extends CrudRepository<DictFileHash, Long> {
    Optional<DictFileHash> findByHash(String hash);
}
