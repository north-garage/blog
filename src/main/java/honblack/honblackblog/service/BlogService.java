package honblack.honblackblog.service;

import java.time.LocalDateTime;
import java.util.List;

import honblack.honblackblog.exception.ResourceNotFoundException;
import honblack.honblackblog.exception.UnexpectedCountException;
import honblack.honblackblog.model.Blog;
import honblack.honblackblog.repository.BlogRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

@Service
@Transactional(readOnly = true)
public class BlogService {
    @Autowired
    private BlogRepository blogRepository;

    public List<Blog> fetchBlogs() {
        return blogRepository.findAll();
    }

    public Blog fetchById(Long blogId) {
        final Blog entity = blogRepository.findById(blogId);

        if (entity == null) {
            throw new ResourceNotFoundException();
        }
        return entity;
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
    public void update(Long blogId, String title, String content, Long userId) {
        Blog entity = blogRepository.findByBlogAndUserId(blogId, userId);

        if (entity == null) {
            throw new ResourceNotFoundException();
        }
        entity.setTitle(title);
        entity.setContent(content);
        entity.setUpdatedAt(LocalDateTime.now());

        final int count = blogRepository.update(entity);

        if (count != 1) {
            throw new UnexpectedCountException(1, count);
        }
    }

    public Blog show(Long blogId) {
        final Blog blog = blogRepository.findById(blogId);
        blog.setContent(StringUtils.replace(blog.getContent(), "\n", "<br/>"));

        return blog;
    }

    @Transactional
    public void delete(Long blogId, Long userId) {
        final int count = blogRepository.delete(blogId, userId);

        if (count != 1) {
            throw new UnexpectedCountException(1, count);
        }
    }
}
