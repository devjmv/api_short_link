package dev.shortlink.link;

import dev.shortlink.link_status.LinkStatusMapper;
import dev.shortlink.link_status.LinkStatusDTO;

public class LinkMapper {

    public static LinkDTO toDTO(Link link) {
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
                .linkStatus(LinkStatusMapper.toDTO(link.getLinkStatus()))
                .build();
    }

    public static Link toEntity(LinkDTO dto) {
        if (dto == null) {
            return null;
        }

        Link link = Link.builder()
                .id(dto.getId())
                .originUrl(dto.getOriginUrl())
                .shortUrl(dto.getShortUrl())
                .clicks(dto.getClicks())
                .expirationDate(dto.getExpirationDate())
                //.link.setUser(userRepository.findById(dto.getUserId()).orElse(null));
                .build();
        return link;
    }
}