package com.bksoftwarevn.auction.service.impl;

import com.bksoftwarevn.auction.constant.ActionStatus;
import com.bksoftwarevn.auction.constant.AucMessage;
import com.bksoftwarevn.auction.constant.AuctionStatus;
import com.bksoftwarevn.auction.dto.PaginationDTO;
import com.bksoftwarevn.auction.dto.SearchDTO;
import com.bksoftwarevn.auction.exception.AucException;
import com.bksoftwarevn.auction.mapper.*;
import com.bksoftwarevn.auction.model.AcceptBidItem;
import com.bksoftwarevn.auction.model.AcceptBidRequest;
import com.bksoftwarevn.auction.model.AcceptBidResponse;
import com.bksoftwarevn.auction.model.AcceptProductItem;
import com.bksoftwarevn.auction.model.BidsResponse;
import com.bksoftwarevn.auction.model.CreateBidRequest;
import com.bksoftwarevn.auction.model.CreateBidResponse;
import com.bksoftwarevn.auction.model.SearchBidRequest;
import com.bksoftwarevn.auction.persistence.entity.*;
import com.bksoftwarevn.auction.persistence.filter.Condition;
import com.bksoftwarevn.auction.persistence.filter.Operator;
import com.bksoftwarevn.auction.persistence.filter.Order;
import com.bksoftwarevn.auction.persistence.filter.SortType;
import com.bksoftwarevn.auction.persistence.repository.AuctionRepository;
import com.bksoftwarevn.auction.persistence.repository.BidRepository;
import com.bksoftwarevn.auction.persistence.repository.ProductRepository;
import com.bksoftwarevn.auction.persistence.repository.UserRepository;
import com.bksoftwarevn.auction.service.BidService;
import com.bksoftwarevn.auction.service.CommonQueryService;
import com.bksoftwarevn.auction.service.EmailService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.math.NumberUtils;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.math.BigDecimal;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;


@Service
@Slf4j
@RequiredArgsConstructor
@Transactional
public class BidServiceImpl implements BidService {

    private final BidRepository repository;
    private final BidMapper mapper;
    private final AuctionMapper auctionMapper;
    private final BrandMapper brandMapper;
    private final UserMapper userMapper;

    private final ProductRepository productRepository;
    private final UserRepository userRepository;
    private final AuctionRepository auctionRepository;
    private final CommonQueryService commonQueryService;
    private final EmailService emailService;



    @Override
    public BidsResponse search(SearchBidRequest searchBidRequest) {
        BidsResponse response = new BidsResponse().code(AucMessage.BID_NOT_FOUND.getCode()).message(AucMessage.BID_NOT_FOUND.getMessage());
        try {
            ProductEntity productEntity = productRepository.findById(searchBidRequest.getProductId()).orElseThrow(() -> new AucException(AucMessage.PRODUCT_NOT_FOUND.getCode(), AucMessage.PRODUCT_NOT_FOUND.getMessage()));
            if (!productEntity.getAuction().getId().equals(searchBidRequest.getAuctionId())) {
                throw new AucException(AucMessage.PRODUCT_NOT_IN_AUCTION.getCode(), AucMessage.PRODUCT_NOT_IN_AUCTION.getMessage());
            }

            List<Order> orders = new ArrayList<>();
            Order order = Order.builder()
                    .field(BidEntity_.PRICE)
                    .sortType(SortType.ASC).build();
            orders.add(order);

            List<Condition> conditions = new ArrayList<>();


            conditions.add(Condition.builder()
                    .field(BidEntity_.PRODUCT)
                    .operator(Operator.EQUALS)
                    .value(productEntity)
                    .build());

            SearchDTO searchDTO = SearchDTO.builder()
                    .page(searchBidRequest.getPage())
                    .size(searchBidRequest.getSize())
                    .orders(orders)
                    .conditions(conditions)
                    .build();

            PaginationDTO<BidEntity> auctionItemPaginationDTO = commonQueryService.search(BidEntity.class, BidEntity.class, searchDTO, null);

            response.code(AucMessage.PULL_AUCTION_SUCCESS.getCode()).message(AucMessage.PULL_GROUP_SUCCESS.getMessage())
                    .data(mapper.mappingEntitiesToItems(auctionItemPaginationDTO.getItems())).total(auctionItemPaginationDTO.getTotal());

        } catch (Exception ex) {
            log.error("[BIDServiceImpl.search] search BID have exception: ", ex);
            response.message(ex.getMessage());
        }
        return response;
    }

    @Override
    @Transactional
    public CreateBidResponse create(String currentUserId, CreateBidRequest createBidRequest) {
        CreateBidResponse response = new CreateBidResponse().code(AucMessage.CREATE_BID_FAILED.getCode()).message(AucMessage.CREATE_BID_FAILED.getMessage());

        try {
            AuctionEntity auctionEntity = auctionRepository.findByIdAndStatus(createBidRequest.getAuctionId(), AuctionStatus.STARTING.name()).orElseThrow(() -> new AucException(AucMessage.CAN_NOT_BID.getCode(), AucMessage.CAN_NOT_BID.getMessage()));
            if(auctionEntity.getUser().getId().equals(currentUserId)){
                throw new AucException(AucMessage.BUYER_SAME_SELLER.getCode(),AucMessage.BUYER_SAME_SELLER.getMessage());
            }

            BidEntityId bidEntityId = new BidEntityId();
            bidEntityId.setUserId(currentUserId);
            bidEntityId.setProductId(createBidRequest.getProductId());

            BidEntity entity = repository.findById(bidEntityId).orElse(null);

            BigDecimal newPrice = new BigDecimal(createBidRequest.getPrice());


            if (entity == null) {
                entity = new BidEntity();
                ProductEntity productEntity = productRepository.findById(createBidRequest.getProductId()).orElseThrow(() -> new AucException(AucMessage.PRODUCT_NOT_FOUND.getCode(), AucMessage.PRODUCT_NOT_FOUND.getMessage()));

                if (NumberUtils.isCreatable(createBidRequest.getPrice()) && newPrice.compareTo(productEntity.getMaxBid()) > 0) {
                    entity.setPrice(new BigDecimal(createBidRequest.getPrice()));
                } else {
                    throw new AucException(AucMessage.BAD_REQUEST.getCode(), AucMessage.BAD_REQUEST.getMessage());
                }

                entity.setId(bidEntityId);
                entity.setProduct(productEntity);
                entity.setUser(userRepository.findById(currentUserId).orElseThrow(() -> new AucException(AucMessage.USERNAME_NOT_FOUND.getCode(), AucMessage.USERNAME_NOT_FOUND.getMessage())));
                entity.setCreatedDate(Instant.now());
                entity.setStatus(ActionStatus.CREATED.name());
                entity.setTotal(1);

            } else {
                BigDecimal oldPrice = entity.getPrice();

                if (NumberUtils.isCreatable(createBidRequest.getPrice()) && newPrice.compareTo(oldPrice) > 0 && newPrice.compareTo(entity.getProduct().getMaxBid()) > 0) {
                    entity.setPrice(new BigDecimal(createBidRequest.getPrice()));
                } else {
                    throw new AucException(AucMessage.BAD_REQUEST.getCode(), AucMessage.BAD_REQUEST.getMessage());
                }
                entity.setStatus(ActionStatus.UPDATED.name());
                entity.setUpdatedDate(Instant.now());
                int total = entity.getTotal() + 1;
                entity.setTotal(total);
            }

            if (auctionEntity.getProducts().stream().noneMatch(productEntity -> productEntity.getId().equals(createBidRequest.getProductId()))) {
                throw new AucException(AucMessage.PRODUCT_NOT_IN_AUCTION.getCode(), AucMessage.PRODUCT_NOT_IN_AUCTION.getMessage());
            }

            entity = repository.save(entity);

            ProductEntity productEntity = entity.getProduct();
            productEntity.setMaxBid(newPrice);
            productEntity.setBuyer(entity.getUser().getId());
            productRepository.save(productEntity);

            if (ObjectUtils.isNotEmpty(entity)) {
                response.data(mapper.mappingEntityToItem(entity)).code(AucMessage.CREATE_BID_SUCCESS.getCode()).message(AucMessage.CREATE_BID_SUCCESS.getMessage());
            }
        } catch (Exception ex) {
            log.error("[BidServiceImpl.create] Exception when create bid: ", ex);
            response.message(ex.getMessage());
        }
        return response;
    }

    @Override
    @Transactional
    public AcceptBidResponse accept(AcceptBidRequest acceptBidRequest) {
        AcceptBidResponse response = new AcceptBidResponse().code(AucMessage.ACCEPT_BID_FAILED.getCode()).message(AucMessage.ACCEPT_BID_FAILED.getMessage());

        try {
            AuctionEntity auctionEntity = auctionRepository.findByIdAndStatus(acceptBidRequest.getAuctionId(), AuctionStatus.STOPPED.name()).orElseThrow(() -> new AucException(AucMessage.CAN_NOT_ACCEPT_BID.getCode(), AucMessage.CAN_NOT_ACCEPT_BID.getMessage()));

            ProductEntity productEntity = productRepository.findById(acceptBidRequest.getProductId()).orElseThrow(() -> new AucException(AucMessage.PRODUCT_NOT_FOUND.getCode(), AucMessage.PRODUCT_NOT_FOUND.getMessage()));

            if(ActionStatus.ACCEPTED.name().equals(productEntity.getStatus())){
                throw new AucException(AucMessage.PRODUCT_HAS_ACCEPTED.getCode(), AucMessage.PRODUCT_HAS_ACCEPTED.getMessage());
            }

            if (productEntity.getBuyer() == null || productEntity.getMaxBid() == null) {
                throw new AucException(AucMessage.PRODUCT_HAS_NO_BID.getCode(), AucMessage.PRODUCT_HAS_NO_BID.getMessage());
            }

            productEntity.setAcceptedInfo(acceptBidRequest.getInfo());
            productEntity.setUpdatedDate(Instant.now());
            productEntity.setStatus(ActionStatus.ACCEPTED.name());
            productEntity = productRepository.save(productEntity);


            if (ObjectUtils.isNotEmpty(productEntity)) {
                AcceptBidItem acceptBidItem = new AcceptBidItem();
                acceptBidItem.auction(auctionMapper.mappingEntityToItem(auctionEntity));

                AcceptProductItem acceptProductItem = new AcceptProductItem();
                acceptProductItem.setAcceptedInfo(acceptBidRequest.getInfo());
                acceptProductItem.setName(productEntity.getName());
                acceptProductItem.setSeller(userMapper.mappingEntity(productEntity.getAuction().getUser()));
                acceptProductItem.setBuyer(userMapper.mappingEntity(userRepository.findById(productEntity.getBuyer()).orElse(new UserEntity())));
                acceptProductItem.setBrand(brandMapper.mappingEntityToItem(productEntity.getBrand()));
                acceptProductItem.setSeries(productEntity.getSeries());
                acceptProductItem.setStartPrice(productEntity.getStartPrice().toPlainString());
                acceptProductItem.setMaxBid(productEntity.getMaxBid().toPlainString());
                acceptProductItem.setId(productEntity.getId());
                acceptBidItem.setProduct(acceptProductItem);

                response.data(acceptBidItem).code(AucMessage.ACCEPT_BID_SUCCESS.getMessage()).message(AucMessage.ACCEPT_BID_SUCCESS.getMessage());
                //send mail
                emailService.sendEmail(emailService.buildEmailAcceptedBid(response));
            }
        } catch (Exception ex) {
            log.error("[BidServiceImpl.accept] Exception when accept bid: ", ex);
            response.message(ex.getMessage());
        }
        return response;
    }
}
