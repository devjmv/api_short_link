package dev.shortlink.access_log;

import dev.shortlink.link.Link;
import dev.shortlink.link.LinkRepository;
import jakarta.servlet.http.HttpServletRequest;
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
public class AccessLogController {

    private final LinkRepository linkRepository;
    public final AccessLogService accessLogService;

    public AccessLogController(LinkRepository linkRepository, AccessLogService accessLogService) {
        this.accessLogService = accessLogService;
        this.linkRepository = linkRepository;
    }

    @GetMapping("/{shortUrl}")
    public void redirectLink(@PathVariable String shortUrl, HttpServletRequest request, HttpServletResponse response)
            throws IOException {
        Optional<Link> linkOptional = linkRepository.findByShortUrl(shortUrl);

        if (linkOptional.isPresent()) {
            Link link = linkOptional.get();

            accessLogService.addAccessLog(link, request, response);

            response.sendRedirect(link.getOriginUrl());
        } else {
            response.sendError(HttpStatus.NOT_FOUND.value(), "Link not found");
        }
    }
}