package dev.shortlink.link_status;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;
import dev.shortlink.user.User;

@RestController
@RequestMapping("${api-endpoint}/state")
public class LinkStatusController {

    private final LinkStatusService linkStatusService;

    public LinkStatusController(LinkStatusService linkStatusService) {
        this.linkStatusService = linkStatusService;
    }

    @PutMapping("/{stateId}")
    public ResponseEntity<LinkStatusDTO> updateState(
            Principal connectedUser,
            @PathVariable Long stateId,
            @RequestBody(required = false) String reason) {

        User user = (User) ((UsernamePasswordAuthenticationToken) connectedUser).getPrincipal();

        LinkStatusDTO updatedState = linkStatusService.updateState(user, stateId, reason);

        return ResponseEntity.status(HttpStatus.OK).body(updatedState);
    }
}
