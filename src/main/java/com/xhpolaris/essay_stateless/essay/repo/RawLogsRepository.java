package com.xhpolaris.essay_stateless.essay.repo;

import com.xhpolaris.essay_stateless.essay.entity.RawLogs;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Component;

@Component
public interface RawLogsRepository extends MongoRepository<RawLogs,String> {

}
