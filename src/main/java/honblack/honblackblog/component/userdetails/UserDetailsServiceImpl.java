package honblack.honblackblog.component.userdetails;

import honblack.honblackblog.model.User;
import honblack.honblackblog.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import java.util.Arrays;

@Component
public class UserDetailsServiceImpl implements UserDetailsService {
    @Autowired
    UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        final User user = userRepository.findByEmail(email);

        if (user == null) {
            throw new UsernameNotFoundException("User not found for email: " + email);
        }

        // ユーザー情報が取得できたらSpring Securityで認証できる形で戻す
        return new org.springframework.security.core.userdetails.User(
                user.getEmail(),
                user.getPasswordHash(),
                Arrays.asList(new SimpleGrantedAuthority("USER"))
        );
    }
}