package dev.shortlink.link;

import org.springframework.stereotype.Component;
import dev.shortlink.user.UserRepository;

@Component
public class LinkMapper {

    private final UserRepository userRepository;

    public LinkMapper(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public LinkDTO toDTO(Link link) {
        if (link == null) {
            return null;
        }

        return LinkDTO.builder()
                .id(link.getId())
                .originUrl(link.getOriginUrl())
                .shortUrl(link.getShortUrl())
                .clicks(link.getClicks())
                .expirationDate(link.getExpirationDate())
                .userId(link.getUser() != null ? link.getUser().getId() : null)
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
                .clicks(dto.getClicks())
                .expirationDate(dto.getExpirationDate())
                .user(userRepository.findById(dto.getUserId()).orElse(null))
                .build();
    }
}