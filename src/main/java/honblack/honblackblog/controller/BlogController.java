package honblack.honblackblog.controller;

import honblack.honblackblog.service.BlogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

import java.security.Principal;
import java.util.HashMap;

@Controller
public class BlogController {

    @Autowired
    private BlogService blogService;

    @GetMapping
    public ModelAndView index(Principal principal) {
        return new ModelAndView(
                "blog/index",
                new HashMap<String, Object>() {{
                    put("blogs", blogService.fetchBlogs());
                    put("username", principal.getName());
                }}
        );
    }
}
