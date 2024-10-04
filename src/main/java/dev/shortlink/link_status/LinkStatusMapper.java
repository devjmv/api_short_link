package dev.shortlink.link_status;

public class LinkStatusMapper {

    public static LinkStatusDTO toDTO(LinkStatus linkStatus) {
        if (linkStatus == null) {
            return null;
        }

        return LinkStatusDTO.builder()
                .id(linkStatus.getId())
                .status(linkStatus.getStatus())
                .reason(linkStatus.getReason())
                .statusUpdatedAt(linkStatus.getStatusUpdatedAt())
                .build();
    }
    
    public static LinkStatus toEntity(LinkStatusDTO dto) {
        if (dto == null) {
            return null;
        }

        return LinkStatus.builder()
                .id(dto.getId())
                .status(dto.getStatus())
                .reason(dto.getReason())
                .statusUpdatedAt(dto.getStatusUpdatedAt())
                .build();
    }
}
