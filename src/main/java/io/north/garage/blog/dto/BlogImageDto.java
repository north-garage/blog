package io.north.garage.blog.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.io.Serializable;

@Data
@AllArgsConstructor
public class BlogImageDto implements Serializable {

    private static final long serialVersionUID = 8801731397199098658L;

    private String directoryPath;

    private String fileName;

    private String extension;

    public String getImagePath() {
        return directoryPath + fileName + "." + extension;
    }
}
