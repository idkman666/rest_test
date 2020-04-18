package com.example.rest_test.repo;

import com.example.rest_test.entity.Actions;
import com.example.rest_test.entity.Logs;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IActionsRepo extends CrudRepository<Actions,String> {
    Iterable<Actions> findByUserId (String userId);
    Iterable<Actions> findByType (String type);
    Iterable<Actions> findByTime(String time);
    Iterable<Actions> findBySessionId(String sessionId);
}
