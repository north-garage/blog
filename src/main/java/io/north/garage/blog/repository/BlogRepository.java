package io.north.garage.blog.repository;

import java.util.List;

import io.north.garage.blog.model.Blog;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface BlogRepository {
    List<Blog> findAll();

    Blog findById(Long id);

    Blog findByBlogAndUserId(@Param("blogId") Long blogId, @Param("userId") Long userId);

    int insert(Blog blog);

    int update(Blog blog);

    int delete(@Param("blogId") Long blogId, @Param("userId") Long userId);
}
