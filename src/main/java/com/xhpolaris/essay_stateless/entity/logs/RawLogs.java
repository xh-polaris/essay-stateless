package com.xhpolaris.essay_stateless.entity.logs;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;

@Document(collection = "raw_logs")
@Data
public class RawLogs {
    @Id
    private String id;
    private String url;
    private String request;
    private String response;
    private LocalDateTime createTime;
}
