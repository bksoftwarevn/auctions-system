package com.bksoftwarevn.auction.api;

import com.bksoftwarevn.auction.model.CommonResponse;
import com.bksoftwarevn.auction.model.CreateNotificationsRequest;
import com.bksoftwarevn.auction.model.DetailNotificationsResponse;
import com.bksoftwarevn.auction.model.SearchNotificationsRequest;
import com.bksoftwarevn.auction.model.SearchNotificationsResponse;
import com.bksoftwarevn.auction.model.UpdateNotificationsRequest;
import com.bksoftwarevn.auction.security.util.SecurityUtils;
import com.bksoftwarevn.auction.service.NotificationsService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;


@RestController
@Slf4j
@RequiredArgsConstructor
public class NotificationsController implements com.bksoftwarevn.auction.api.v1.NotificationsApi {

    private final NotificationsService notificationsService;

    @Override
    public ResponseEntity<SearchNotificationsResponse> getListNotifications(SearchNotificationsRequest searchNotificationsRequest) {
        log.info("[NotificationsController.getListNotifications] Start get list notifications with data: {}", searchNotificationsRequest);
        return ResponseEntity.ok(notificationsService.search(searchNotificationsRequest));
    }

    @Override
    public ResponseEntity<DetailNotificationsResponse> getNotificationsDetail(String id) {
        log.info("[ClientController.getNotificationsDetail] User read notifications detail [{}] request", id);
        return ResponseEntity.ok(notificationsService.detail(SecurityUtils.getCurrentUserId(), id));
    }

    @Override
    public ResponseEntity<CommonResponse> postCreateNotifications(CreateNotificationsRequest createNotificationsRequest) {
        log.info("[NotificationsController.getListNotifications] Start create notifications with data: {}", createNotificationsRequest);
        return ResponseEntity.ok(notificationsService.create(createNotificationsRequest));
    }

    @Override
    public ResponseEntity<CommonResponse> putDeleteNotifications(String id) {
        log.info("[NotificationsController.putDeleteNotifications] Start delete notifications with data: {}", id);
        return ResponseEntity.ok(notificationsService.delete(SecurityUtils.getCurrentUserId(), id));
    }

    @Override
    public ResponseEntity<CommonResponse> putUpdateNotifications(UpdateNotificationsRequest updateNotificationsRequest) {
        log.info("[NotificationsController.putUpdateNotifications] Start update notifications with data: {}", updateNotificationsRequest);
        return ResponseEntity.ok(notificationsService.update(updateNotificationsRequest));
    }
}
