package honblack.honblackblog.repository;

import honblack.honblackblog.model.Blog;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface BlogRepository {
    List<Blog> findAll();

    int insert(Blog blog);
}
