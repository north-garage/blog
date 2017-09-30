package honblack.honblackblog.service;

import honblack.honblackblog.model.Blog;
import honblack.honblackblog.repository.BlogRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.time.LocalDateTime;
import java.util.List;

@Service
@Transactional(readOnly = true)
public class BlogService {
    @Autowired
    private BlogRepository blogRepository;

    public List<Blog> fetchBlogs() {
        return blogRepository.findAll();
    }

    public Blog fetchById(Long blogId) {
        return blogRepository.findById(blogId);
    }

    @Transactional
    public void create(String title, String content, long userId) {
        final Blog blog = new Blog();
        blog.setUserId(userId);
        blog.setTitle(title);
        blog.setContent(content);
        blog.setCreatedAt(LocalDateTime.now());

        blogRepository.insert(blog);
    }

    @Transactional
    public void update(Long id, String title, String content, Long userId) {
        Blog blog = blogRepository.findById(id);
        if (!blog.getUserId().equals(userId)) {
            throw new IllegalStateException();
        }
        blog.setTitle(title);
        blog.setContent(content);
        blog.setUpdatedAt(LocalDateTime.now());

        blogRepository.update(blog);
    }

    public Blog show(Long blogId) {
        final Blog blog = blogRepository.findById(blogId);
        blog.setContent(StringUtils.replace(blog.getContent(), "\n", "<br/>"));

        return blog;
    }
}
