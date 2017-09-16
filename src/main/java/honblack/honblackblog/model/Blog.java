package honblack.honblackblog.model;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class Blog {
    private long id;
    private long userId;
    private String title;
    private String content;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
