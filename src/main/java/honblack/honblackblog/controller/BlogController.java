package honblack.honblackblog.controller;

import honblack.honblackblog.repository.BlogRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping(value = "blog")
public class BlogController {

    @Autowired
    private BlogRepository blogRepository;

    @GetMapping
    public ModelAndView index() {
        ModelAndView mav = new ModelAndView("blog/index", "blogs", blogRepository.findAll());
        return mav;
    }
}
