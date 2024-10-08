package dev.shortlink.access_log;

import dev.shortlink.link.Link;
import dev.shortlink.link.LinkRepository;
import dev.shortlink.link_status.Status;
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
            if (link.getLinkStatus().getStatus() != Status.ACTIVE) {
                response.sendRedirect("http://localhost:8080/");
            }
            accessLogService.addAccessLog(link, request, response);

            response.sendRedirect(link.getOriginUrl());
        } else {
            response.sendError(HttpStatus.NOT_FOUND.value(), "Link not found");
        }
    }

    @GetMapping
    public String error() {
        return "<!DOCTYPE html>\r\n" +
                "<html lang=\"en\">\r\n" + 
                "<head>\r\n" + 
                "    <meta charset=\"UTF-8\">\r\n" +
                "    <meta name=\"viewport\" content=\"width=device-width, initial-scale=1.0\">\r\n" + 
                "    <title>404 - Page Not Found</title>\r\n" + 
                "</head>\r\n" + 
                "<body>\r\n" + 
                "    <h1>404 - Page Not Found</h1>\r\n" + 
                "    <p>The shortLink you are looking for is inactive.</p>\r\n" + 
                "</body>\r\n" + 
                "</html>";
    }

}