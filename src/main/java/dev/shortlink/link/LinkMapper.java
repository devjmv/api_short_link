package dev.shortlink.link;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import dev.shortlink.link_status.LinkStatusMapper;
import dev.shortlink.link_status.LinkStatusRepository;
import dev.shortlink.user.UserRepository;

@Component
public class LinkMapper {

    @Autowired
    private LinkStatusMapper linkStatusMapper;
    private final UserRepository userRepository;
    private final LinkStatusRepository linkStatusRepository;

    public LinkMapper(UserRepository userRepository, LinkStatusRepository linkStatusRepository) {
        this.userRepository = userRepository;
        this.linkStatusRepository = linkStatusRepository;
    }

    public LinkDTO toDTO(Link link) {
        if (link == null) {
            return null;
        }

        return LinkDTO.builder()
                .id(link.getId())
                .originUrl(link.getOriginUrl())
                .shortUrl(link.getShortUrl())
                .expirationDate(link.getExpirationDate())
                .userId(link.getUser() != null ? link.getUser().getId() : null)
                .status(linkStatusMapper.toDTO(linkStatusRepository.findById(link.getId()).get()))
                .build();
    }

    public Link toEntity(LinkDTO dto) {
        if (dto == null) {
            return null;
        }

        return Link.builder()
                .id(dto.getId())
                .originUrl(dto.getOriginUrl())
                .shortUrl(dto.getShortUrl())
                .expirationDate(dto.getExpirationDate())
                .user(userRepository.findById(dto.getUserId()).orElse(null))
                .build();
    }
}