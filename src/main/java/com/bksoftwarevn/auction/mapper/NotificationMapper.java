package com.bksoftwarevn.auction.mapper;

import com.bksoftwarevn.auction.dto.NotificationDto;
import com.bksoftwarevn.auction.model.NotificationItem;
import com.bksoftwarevn.auction.persistence.entity.NotificationEntity;
import org.mapstruct.*;

import java.util.List;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = "spring", uses = {AuctionMapper.class, UserMapper.class})
public interface NotificationMapper {
    NotificationEntity notificationDtoToNotificationEntity(NotificationDto notificationDto);

    NotificationDto notificationEntityToNotificationDto(NotificationEntity notificationEntity);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void updateNotificationEntityFromNotificationDto(NotificationDto notificationDto, @MappingTarget NotificationEntity notificationEntity);


    @Mapping(source = "entity.user.username", target = "createdBy")
    NotificationItem mappingEntityToItem(NotificationEntity entity);

    List<NotificationItem> mappingEntitiesToItems(List<NotificationEntity> items);
}
