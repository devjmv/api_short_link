package dev.shortlink.link;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import dev.shortlink.user.User;
import jakarta.validation.Valid;

import java.security.Principal;

@RestController
@RequestMapping("${api-endpoint}/link")
public class LinkController {

    private final LinkService linkService;

    public LinkController(LinkService linkService) {
        this.linkService = linkService;
    }

    @PostMapping
    public ResponseEntity<LinkDTO> addLink(Principal connectedUser, @Valid @RequestHeader String originUrl) {
        User user = (User) ((UsernamePasswordAuthenticationToken) connectedUser).getPrincipal();
        LinkDTO addLink = linkService.addLink(user, originUrl);
        return ResponseEntity.status(HttpStatus.CREATED).body(addLink);
    }
}

