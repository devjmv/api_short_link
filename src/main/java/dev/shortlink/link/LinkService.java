package dev.shortlink.link;

import java.util.Calendar;
import java.util.Date;
import java.util.stream.Collectors;

import java.security.SecureRandom;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
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

    public Page<LinkDTO> findAllLinksByUser(User user, Pageable pageable) {
        return linkRepository.findByUser(user, pageable)
                .map(linkMapper::toDTO);
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

    public LinkDTO addLinkFree(String originUrl) {
        if (linkRepository.existsByOriginUrl(originUrl)) {
            throw new LinkException("Email already in use");
        }

        String randomShortUrl = new SecureRandom().ints(5, 0, 256)
                .mapToObj(i -> "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789".charAt(i) + "")
                .collect(Collectors.joining());

        Link link = new Link();
        link.setOriginUrl(originUrl);
        link.setShortUrl(randomShortUrl);
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(new Date());
        calendar.add(Calendar.DAY_OF_MONTH, 30);
        link.setExpirationDate(calendar.getTime());
        link.setUser(null);
        Link savedLink = linkRepository.save(link);

        LinkStatus linkStatus = new LinkStatus();
        linkStatus.setStatus(Status.ACTIVE);
        linkStatus.setReason("Create Link");
        linkStatus.setLink(savedLink);
        linkStatusRepository.save(linkStatus);

        return linkMapper.toDTO(savedLink);
    }

    public LinkDTO updatedLink(User user, LinkDTO linkDTO) {

        Link link = linkRepository.findById(linkDTO.getId()).get();

        if (link == null) {
            throw new LinkException("Link not exists");
        }

        if (null != linkDTO.getOriginUrl()
                && linkRepository.existsByOriginUrlAndNotId(linkDTO.getOriginUrl(), linkDTO.getId())) {
            throw new LinkException("Email already in use");
        }

        if (null != linkDTO.getShortUrl()
                && linkRepository.existsByShortUrlAndNotId(linkDTO.getShortUrl(), linkDTO.getId())) {
            throw new LinkException("Short link already in use");
        }

        link.setOriginUrl(null != linkDTO.getOriginUrl() ? linkDTO.getOriginUrl() : link.getOriginUrl());
        link.setShortUrl(null != linkDTO.getShortUrl() ? linkDTO.getShortUrl() : link.getShortUrl());
        link.setExpirationDate(
                null != linkDTO.getExpirationDate() ? linkDTO.getExpirationDate() : link.getExpirationDate());

        LinkDTO updatedLink = linkMapper.toDTO(linkRepository.save(link));

        return updatedLink;
    }
}
