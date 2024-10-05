package dev.shortlink.link;

import java.util.List;
import java.util.stream.Collectors;

import java.security.SecureRandom;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import dev.shortlink.link.link_exceptions.LinkException;
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

    public LinkDTO addLink(User user, String originUrl, String shortUrl) {
        if (linkRepository.existsByOriginUrl(originUrl)) {
            throw new LinkException("Email already in use");
        }

        if (shortUrl != null)
            if (linkRepository.existsByShortUrl(shortUrl)) {
                throw new LinkException("Short URL " + shortUrl + " already in use");
            }

        String randomShortUrl = new SecureRandom().ints(5, 0, 62)
                .mapToObj(i -> "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789".charAt(i) + "")
                .collect(Collectors.joining());

        Link link = new Link();
        link.setOriginUrl(originUrl);
        link.setShortUrl(shortUrl != null ? shortUrl : randomShortUrl);
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
