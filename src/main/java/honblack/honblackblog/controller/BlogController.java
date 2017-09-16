package honblack.honblackblog.controller;

import honblack.honblackblog.form.blog.BlogForm;
import honblack.honblackblog.model.Blog;
import honblack.honblackblog.service.BlogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.Valid;
import java.security.Principal;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

@Controller
public class BlogController {

    @Autowired
    private BlogService blogService;

    @GetMapping("/")
    public ModelAndView index(Principal principal) {
        Map<String, Object> params = new HashMap<>();
        params.put("blogs", blogService.fetchBlogs());
        if (principal != null) {
            params.put("username", principal.getName());
        }

        return new ModelAndView("blog/index", params);
    }

    @GetMapping("blogs/input")
    public ModelAndView input() {
        return new ModelAndView("blog/input");
    }

    @PostMapping("blogs/create")
    public String create(@Valid @ModelAttribute BlogForm form, Principal principal) {
        blogService.create(
                form.getTitle(), form.getContent(), Long.parseLong(principal.getName()));
        return "redirect:/";
    }

    @GetMapping("blogs/{blogId}/edit")
    public ModelAndView edit(@PathVariable Long blogId) {
        Blog entity = blogService.fetchById(blogId);
        BlogForm form = new BlogForm(entity.getTitle(), entity.getContent());
        Map<String, Object> params = new HashMap<>();
        params.put("form", form);
        params.put("blogId", blogId);
        return new ModelAndView("blog/edit", params);
    }

    @PutMapping("blogs/{blogId}/update")
    public String update(@PathVariable Long blogId, @Valid @ModelAttribute BlogForm form, Principal principal) {
        blogService.update(blogId, form.getTitle(), form.getContent(), Long.parseLong(principal.getName()));
        return "redirect:/";
    }
}
