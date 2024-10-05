package dev.shortlink.link;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import dev.shortlink.link_status.LinkStatus;
import dev.shortlink.link_status.LinkStatusRepository;
import dev.shortlink.link_status.Status;
import dev.shortlink.user.User;

@Service
@Transactional
public class LinkService {

    private final LinkRepository linkRepository;
    private final LinkStatusRepository linkStatusRepository;
    private final LinkMapper linkMapper;

    public LinkService(LinkRepository linkRepository, LinkMapper linkMapper,
            LinkStatusRepository linkStatusRepository) {
        this.linkRepository = linkRepository;
        this.linkMapper = linkMapper;
        this.linkStatusRepository = linkStatusRepository;
    }

    public List<LinkDTO> findAllByUser(User user) {
        List<Link> links = linkRepository.findByUser(user);
        return links.stream()
                .map(linkMapper::toDTO)
                .collect(Collectors.toList());
    }

    public LinkDTO addLink(User user, String originUrl) {
        if (linkRepository.existsByOriginUrl(originUrl)) {
            throw new IllegalArgumentException("Email already in use");
        }
        Link link = new Link();
        link.setOriginUrl(originUrl);
        link.setShortUrl("https://shortlink.dev/" + Math.random());
        link.setClicks(0);
        link.setExpirationDate(null);
        link.setUser(user);
        Link savedLink = linkRepository.save(link);

        LinkStatus linkStatus = new LinkStatus();
        linkStatus.setStatus(Status.ACTIVE);
        linkStatus.setReason("Create Link");
        linkStatus.setLink(savedLink);
        linkStatusRepository.save(linkStatus);

        return linkMapper.toDTO(savedLink);
    }

}
