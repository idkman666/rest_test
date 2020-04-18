package com.example.rest_test.repo;

import com.example.rest_test.entity.Logs;
import org.apache.juli.logging.Log;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface ILogsRepo extends CrudRepository<Logs, String> {
    Logs findByUserId (String userId);
    Iterable<Logs> findAllByUserId (String userId);
    Logs findBySessionId (String sessionId);
}
