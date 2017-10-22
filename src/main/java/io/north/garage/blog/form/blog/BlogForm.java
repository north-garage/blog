package io.north.garage.blog.form.blog;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotEmpty;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class BlogForm {
    @NotEmpty
    @Length(min = 1, max = 255)
    private String title;
    @NotEmpty
    @Length(min = 1, max = 4_000)
    private String content;
}
