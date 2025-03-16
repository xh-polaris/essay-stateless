package com.xhpolaris.essay_stateless.essay.repository;

import com.xhpolaris.essay_stateless.entity.logs.RawLogs;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface RawLogsRepository extends MongoRepository<RawLogs,String> {

}
