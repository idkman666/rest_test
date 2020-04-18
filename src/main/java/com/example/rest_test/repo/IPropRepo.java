package com.example.rest_test.repo;

import com.example.rest_test.entity.Logs;
import com.example.rest_test.entity.Properties;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.RequestMapping;

@Repository
public interface IPropRepo extends CrudRepository<Properties, Long> {
    Properties findByActId (String actId);
}
