package honblack.honblackblog.service;

import honblack.honblackblog.model.Blog;
import honblack.honblackblog.repository.BlogRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true)
public class BlogService {
    @Autowired
    private BlogRepository blogRepository;

    public List<Blog> fetchBlogs(){
        return blogRepository.findAll();
    }
}
