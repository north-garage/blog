package io.north.garage.blog.controller;

import io.north.garage.blog.dto.BlogImageDto;
import io.north.garage.blog.service.BlogImageService;
import org.apache.tomcat.util.http.fileupload.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.method.annotation.MvcUriComponentsBuilder;
import org.springframework.web.util.UriComponents;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@RestController
@Secured("ROLE_USER")
public class BlogImageController {

    @Autowired
    private BlogImageService blogImageService;

    @GetMapping(value = "blog-images/{fileName}")
    public void fetchImage(@PathVariable final String fileName,
                           final HttpServletResponse response) throws IOException {

        blogImageService.fetchImage(fileName, in -> {
            // HACK: できれば `HttpMessageConverter` とかにしたほうがいいかも
            response.setContentType(MediaType.IMAGE_JPEG_VALUE);

            IOUtils.copy(in, response.getOutputStream());
        });
    }

    // TODO: response型はdtoに切り出す
    @PostMapping(value = "blog-images", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public Map<String, String> uploadImage(
        @RequestParam("imageFile") final MultipartFile imageFile) throws IOException {

        final BlogImageDto dto = blogImageService.upload(imageFile.getInputStream());

        final UriComponents uriComponents = MvcUriComponentsBuilder
            .fromMethodName(BlogImageController.class, "fetchImage", dto.getFileName(), null)
            .build();

        final Map<String, String> response = new HashMap<>();
        response.put("imagePath", uriComponents.getPath());

        return response;
    }
}
