package dev.shortlink.link_status;

import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;

import dev.shortlink.user.User;

@Service
public class LinkStatusService {

    @Autowired
    private LinkStatusRepository linkStatusRepository;

    @Autowired
    private LinkStatusMapper linkStatusMapper;

    public LinkStatusDTO updateState(User user, Long stateId, String reason) {
        LinkStatus linkStatus = linkStatusRepository.findByLinkId(stateId).get();

        linkStatus.setStatus(linkStatus.getStatus() == Status.ACTIVE ? Status.INACTIVE : Status.ACTIVE);
        if (reason != null) {
            linkStatus.setReason(reason);
        }

        LinkStatus updatedLinkStatus = linkStatusRepository.save(linkStatus);

        return linkStatusMapper.toDTO(updatedLinkStatus);
    }
}