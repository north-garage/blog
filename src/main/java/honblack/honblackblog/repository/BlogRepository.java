package honblack.honblackblog.repository;

import honblack.honblackblog.model.Blog;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface BlogRepository {
    List<Blog> findAll();

    Blog findById(Long id);

    Blog findByBlogAndUserId(@Param("blogId") Long blogId, @Param("userId") Long userId);

    int insert(Blog blog);

    int update(Blog blog);

    int delete(@Param("blogId") Long blogId, @Param("userId") Long userId);
}
