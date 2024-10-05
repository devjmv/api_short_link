package dev.shortlink.access;

import dev.shortlink.link.Link;
import dev.shortlink.link.LinkRepository;
import jakarta.servlet.http.HttpServletResponse;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import java.io.IOException;
import java.util.Optional;

@RestController
@RequestMapping("/")
public class AccessController {

    private final LinkRepository linkRepository;

    public AccessController(LinkRepository linkRepository) {
        this.linkRepository = linkRepository;
    }

    @GetMapping("/{id}")
    public void redirectLink(@PathVariable String shortUrl, HttpServletResponse response) throws IOException {
        Optional<Link> linkOptional = linkRepository.findByShortUrl(shortUrl);

        if (linkOptional.isPresent()) {
            Link link = linkOptional.get();
            response.sendRedirect(link.getOriginUrl());
        } else {
            response.sendError(HttpStatus.NOT_FOUND.value(), "Link not found");
        }
    }
}