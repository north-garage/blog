package honblack.honblackblog.repository;

import honblack.honblackblog.model.User;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface UserRepository {

    User findByEmail(String email);
}
