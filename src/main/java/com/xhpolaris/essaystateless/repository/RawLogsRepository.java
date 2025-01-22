package com.xhpolaris.essaystateless.repository;

import com.xhpolaris.essaystateless.entity.logs.RawLogs;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface RawLogsRepository extends MongoRepository<RawLogs,String> {

}
