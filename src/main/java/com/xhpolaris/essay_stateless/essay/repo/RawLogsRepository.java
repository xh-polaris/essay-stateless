package com.xhpolaris.essay_stateless.essay.repo;

import com.xhpolaris.essay_stateless.essay.entity.logs.RawLogs;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface RawLogsRepository extends MongoRepository<RawLogs,String> {

}
