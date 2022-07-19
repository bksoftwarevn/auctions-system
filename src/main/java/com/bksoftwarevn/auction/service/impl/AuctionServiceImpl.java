package com.bksoftwarevn.auction.service.impl;

import com.bksoftwarevn.auction.constant.*;
import com.bksoftwarevn.auction.dto.PaginationDTO;
import com.bksoftwarevn.auction.dto.SearchDTO;
import com.bksoftwarevn.auction.exception.AucException;
import com.bksoftwarevn.auction.mapper.AuctionMapper;
import com.bksoftwarevn.auction.mapper.CategoryMapper;
import com.bksoftwarevn.auction.mapper.GroupMapper;
import com.bksoftwarevn.auction.mapper.UserMapper;
import com.bksoftwarevn.auction.model.AuctionItem;
import com.bksoftwarevn.auction.model.AuctionsResponse;
import com.bksoftwarevn.auction.model.CategoryItem;
import com.bksoftwarevn.auction.model.CommonResponse;
import com.bksoftwarevn.auction.model.CreateAuctionRequest;
import com.bksoftwarevn.auction.model.CreateAuctionResponse;
import com.bksoftwarevn.auction.model.DetailAuctionItem;
import com.bksoftwarevn.auction.model.DetailAuctionResponse;
import com.bksoftwarevn.auction.model.FilterAuctionItem;
import com.bksoftwarevn.auction.model.FilterAuctionRequest;
import com.bksoftwarevn.auction.model.FilterAuctionResponse;
import com.bksoftwarevn.auction.model.GroupItem;
import com.bksoftwarevn.auction.model.InfoAuctionResponse;
import com.bksoftwarevn.auction.model.SearchAuctionItem;
import com.bksoftwarevn.auction.model.SearchAuctionRequest;
import com.bksoftwarevn.auction.model.SearchAuctionResponse;
import com.bksoftwarevn.auction.model.UpdateAuctionRequest;
import com.bksoftwarevn.auction.model.UpdateAuctionStatusRequest;
import com.bksoftwarevn.auction.model.UserDataItem;
import com.bksoftwarevn.auction.persistence.entity.*;
import com.bksoftwarevn.auction.persistence.filter.Condition;
import com.bksoftwarevn.auction.persistence.filter.Operator;
import com.bksoftwarevn.auction.persistence.filter.Order;
import com.bksoftwarevn.auction.persistence.filter.SortType;
import com.bksoftwarevn.auction.persistence.repository.*;
import com.bksoftwarevn.auction.service.CommonQueryService;
import com.bksoftwarevn.auction.service.AuctionService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.time.DateUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.*;

@Service
@Slf4j
@RequiredArgsConstructor
@Transactional
public class AuctionServiceImpl implements AuctionService {

    private final AuctionRepository auctionRepository;
    private final CategoryRepository categoryRepository;
    private final UserRepository userRepository;
    private final GroupRepository groupRepository;
    private final AuctionMapper mapper;
    private final UserMapper userMapper;
    private final CategoryMapper categoryMapper;
    private final GroupMapper groupMapper;
    private final CommonQueryService commonQueryService;

    @Override
    public CreateAuctionResponse create(CreateAuctionRequest createAuctionRequest, String userId) {
        CreateAuctionResponse response = new CreateAuctionResponse().code(AucMessage.CREATE_AUCTION_FAILED.getCode()).message(AucMessage.CREATE_AUCTION_FAILED.getMessage());

        try {

            if (validateAuctionData(createAuctionRequest)) {
                AuctionEntity entity = new AuctionEntity();
                entity.setId(UUID.randomUUID().toString());
                entity.setCategory(categoryRepository.findById(createAuctionRequest.getCategoryId()).orElseThrow(() -> new AucException(AucMessage.CATEGORY_NOT_FOUND.getCode(), AucMessage.CATEGORY_NOT_FOUND.getMessage())));
                UserEntity userEntity = userRepository.findById(userId).orElseThrow(() -> new AucException(AucMessage.USERNAME_NOT_FOUND.getCode(), AucMessage.USERNAME_NOT_FOUND.getMessage()));
                entity.setUser(userEntity);
                entity.setCreatedBy(userEntity.getUsername());
                entity.setTitle(createAuctionRequest.getTitle());
                entity.setCreatedDate(Instant.now());
                entity.setStartDate(DateUtils.parseDate(createAuctionRequest.getStartDate(), AucConstant.DATE_FORMAT).toInstant());
                entity.setEndDate(DateUtils.parseDate(createAuctionRequest.getEndDate(), AucConstant.DATE_FORMAT).toInstant());
                entity.setDecscriptions(createAuctionRequest.getDescriptions());
                entity.setStatus(AuctionStatus.PENDING.name());
                entity.setReason(ActionStatus.CREATED.name());
                entity = auctionRepository.save(entity);
                if (ObjectUtils.isNotEmpty(entity)) {
                    response.data(mapper.mappingEntityToItem(entity)).code(AucMessage.CREATE_AUCTION_SUCCESS.getCode()).message(AucMessage.CREATE_AUCTION_SUCCESS.getMessage());
                }
            } else {
                response.code(AucMessage.VALIDATE_AUCTION_FAILED.getCode()).message(AucMessage.VALIDATE_AUCTION_FAILED.getMessage());
            }
        } catch (Exception ex) {
            log.error("[AuctionServiceImpl.create] Exception when create auction: ", ex);
            response.message(ex.getMessage());
        }
        return response;
    }

    @Override
    public AuctionsResponse pull(String userId, long page, long size) {
        AuctionsResponse auctionsResponse = new AuctionsResponse().code(AucMessage.AUCTION_NOT_FOUND.getCode()).message(AucMessage.AUCTION_NOT_FOUND.getMessage());
        try {
            List<Order> orders = new ArrayList<>();
            List<Condition> conditions = new ArrayList<>();

            List<Object> statuses = new ArrayList<>();
            Arrays.stream(AuctionStatus.values()).parallel().forEach(status -> {
                if (!status.name().equalsIgnoreCase(AuctionStatus.PENDING.name())) {
                    statuses.add(status.name());
                }
            });
            conditions.add(Condition.builder()
                    .field(AuctionEntity_.STATUS)
                    .operator(Operator.IN)
                    .values(statuses)
                    .build());

            if (StringUtils.isNotEmpty(userId)) {
                UserEntity userEntity = userRepository.findById(userId).orElseThrow(() -> new AucException(AucMessage.USERNAME_NOT_FOUND.getCode(), AucMessage.USERNAME_NOT_FOUND.getMessage()));
                conditions.add(Condition.builder()
                        .field(AuctionEntity_.USER)
                        .operator(Operator.EQUALS)
                        .value(userEntity)
                        .build());
            }

            SearchDTO searchDTO = SearchDTO.builder()
                    .page(page)
                    .size(size)
                    .orders(orders)
                    .conditions(conditions)
                    .build();

            PaginationDTO<AuctionEntity> auctionItemPaginationDTO = commonQueryService.search(AuctionEntity.class, AuctionEntity.class, searchDTO, null);

            auctionsResponse.code(AucMessage.PULL_AUCTION_SUCCESS.getCode()).message(AucMessage.PULL_AUCTION_SUCCESS.getMessage())
                    .data(mapper.mappings(auctionItemPaginationDTO.getItems()));

        } catch (Exception ex) {
            log.error("[AuctionServiceImpl.filterAuctions] Filter auction have exception: ", ex);
            auctionsResponse.message(ex.getMessage());
        }
        return auctionsResponse;
    }


    @Override
    public CreateAuctionResponse update(UpdateAuctionRequest updateAuctionRequest) {
        CreateAuctionResponse response = new CreateAuctionResponse().code(AucMessage.CREATE_AUCTION_FAILED.getCode()).message(AucMessage.CREATE_AUCTION_FAILED.getMessage());
        try {

            if (validateAuctionData(updateAuctionRequest)) {
                AuctionEntity auctionEntity = auctionRepository.findById(updateAuctionRequest.getId()).orElseThrow(() -> new AucException(AucMessage.AUCTION_NOT_FOUND.getCode(), AucMessage.AUCTION_NOT_FOUND.getMessage()));
                if (auctionEntity.getStatus().equalsIgnoreCase(AuctionStatus.PENDING.name())) {
                    auctionEntity.setCategory(categoryRepository.findById(updateAuctionRequest.getCategoryId()).orElseThrow(() -> new AucException(AucMessage.CATEGORY_NOT_FOUND.getCode(), AucMessage.CATEGORY_NOT_FOUND.getMessage())));
                    auctionEntity.setTitle(updateAuctionRequest.getTitle());
                    auctionEntity.setStartDate(DateUtils.parseDate(updateAuctionRequest.getStartDate(), AucConstant.DATE_FORMAT).toInstant());
                    auctionEntity.setEndDate(DateUtils.parseDate(updateAuctionRequest.getEndDate(), AucConstant.DATE_FORMAT).toInstant());
                    auctionEntity.setDecscriptions(updateAuctionRequest.getDescriptions());
                    auctionEntity.setReason(updateAuctionRequest.getReason());
                    auctionEntity = auctionRepository.save(auctionEntity);
                    if (ObjectUtils.isNotEmpty(auctionEntity)) {
                        response.data(mapper.mappingEntityToItem(auctionEntity)).code(AucMessage.CREATE_AUCTION_SUCCESS.getCode()).message(AucMessage.CREATE_AUCTION_SUCCESS.getMessage());
                    }
                } else {
                    response.data(mapper.mappingEntityToItem(auctionEntity)).code(AucMessage.CANNOT_UPDATE_AUCTION.getCode()).message(AucMessage.CANNOT_UPDATE_AUCTION.getMessage());
                }
            } else {
                response.code(AucMessage.VALIDATE_AUCTION_FAILED.getCode()).message(AucMessage.VALIDATE_AUCTION_FAILED.getMessage());
            }
        } catch (Exception ex) {
            log.error("[AuctionServiceImpl.create] Exception when create auction: ", ex);
            response.message(ex.getMessage());
        }
        return response;
    }

    @Override
    public FilterAuctionResponse filterAuctions(FilterAuctionRequest filterAuctionRequest) {
        FilterAuctionItem filterAuctionItem = new FilterAuctionItem().total(0L);
        FilterAuctionResponse filterAuctionResponse = new FilterAuctionResponse().code(AucMessage.AUCTION_NOT_FOUND.getCode()).message(AucMessage.AUCTION_NOT_FOUND.getMessage()).data(filterAuctionItem);
        CategoryItem categoryItem = new CategoryItem();
        GroupItem groupItem = new GroupItem();
        UserDataItem userDataItem = new UserDataItem();
        try {
            List<Order> orders = new ArrayList<>();
            List<Condition> conditions = new ArrayList<>();

            if (StringUtils.isNotEmpty(filterAuctionRequest.getStatus()) && Arrays.stream(AuctionStatus.values()).anyMatch(as -> as.name().equalsIgnoreCase(filterAuctionRequest.getStatus()))) {
                conditions.add(Condition.builder()
                        .field(AuctionEntity_.STATUS)
                        .operator(Operator.EQUALS)
                        .value(filterAuctionRequest.getStatus())
                        .build());
            }

            if (StringUtils.isNotEmpty(filterAuctionRequest.getUserId())) {
                UserEntity userEntity = userRepository.findById(filterAuctionRequest.getUserId()).orElseThrow(() -> new AucException(AucMessage.USERNAME_NOT_FOUND.getCode(), AucMessage.USERNAME_NOT_FOUND.getMessage()));
                conditions.add(Condition.builder()
                        .field(AuctionEntity_.USER)
                        .operator(Operator.EQUALS)
                        .value(userEntity)
                        .build());
                userDataItem = userMapper.mappingEntity(userEntity);
            }

            Set<CategoryEntity> categoryEntities = new HashSet<>();

            if (StringUtils.isNotEmpty(filterAuctionRequest.getType())) {
                GroupEntity entity = groupRepository.findByType(filterAuctionRequest.getType());
                if (entity == null) {
                    throw new AucException(AucMessage.GROUP_NOT_FOUND.getCode(), AucMessage.GROUP_NOT_FOUND.getMessage());
                }
                categoryEntities = entity.getCategories();
                groupItem = groupMapper.mappingEntityToItem(entity);
            }

            if (StringUtils.isNotEmpty(filterAuctionRequest.getCategoryId())) {
                CategoryEntity entity = categoryRepository.findById(filterAuctionRequest.getCategoryId()).orElseThrow(() -> new AucException(AucMessage.CATEGORY_NOT_FOUND.getCode(), AucMessage.CATEGORY_NOT_FOUND.getMessage()));
                categoryEntities.add(entity);
                categoryItem = categoryMapper.mappingEntityToItem(entity);

            }

            if (CollectionUtils.isNotEmpty(categoryEntities)) {
                conditions.add(Condition.builder()
                        .field(AuctionEntity_.CATEGORY)
                        .operator(Operator.IN)
                        .values(Arrays.asList(categoryEntities.toArray()))
                        .build());
            }


            SearchDTO searchDTO = SearchDTO.builder()
                    .page(filterAuctionRequest.getIndex())
                    .size(filterAuctionRequest.getCount())
                    .orders(orders)
                    .conditions(conditions)
                    .build();

            PaginationDTO<AuctionEntity> auctionItemPaginationDTO = commonQueryService.search(AuctionEntity.class, AuctionEntity.class, searchDTO, null);


            List<AuctionItem> auctionItems = mapper.mappings(auctionItemPaginationDTO.getItems());
            filterAuctionItem.auctions(auctionItems)
                    .category(categoryItem)
                    .group(groupItem)
                    .user(userDataItem)
                    .total(auctionItemPaginationDTO.getTotal());
            filterAuctionResponse.code(AucMessage.PULL_AUCTION_SUCCESS.getCode()).message(AucMessage.PULL_GROUP_SUCCESS.getMessage())
                    .data(filterAuctionItem);

        } catch (Exception ex) {
            log.error("[AuctionServiceImpl.filterAuctions] search auction have exception: ", ex);
            filterAuctionResponse.message(ex.getMessage());
        }

        return filterAuctionResponse;
    }

    @Override
    public DetailAuctionResponse detail(String id) {
        DetailAuctionResponse detailAuctionResponse = new DetailAuctionResponse().code(AucMessage.AUCTION_NOT_FOUND.getCode()).message(AucMessage.AUCTION_NOT_FOUND.getMessage());
        try {
            Optional<AuctionEntity> auctionEntity = auctionRepository.findById(id);
            if (auctionEntity.isPresent()) {
                DetailAuctionItem detailAuctionItem = new DetailAuctionItem().auctions(mapper.mappingEntityToItem(auctionEntity.get()))
                        .category(categoryMapper.mappingEntityToItem(auctionEntity.get().getCategory()))
                        .sellingUser(userMapper.mappingEntity(auctionEntity.get().getUser()));
                // need add detail product, buyer,...
                detailAuctionResponse.code(AucMessage.PULL_AUCTION_SUCCESS.getCode()).message(AucMessage.PULL_AUCTION_SUCCESS.getMessage()).data(detailAuctionItem);
            }
        } catch (Exception ex) {
            log.error("[AuctionController.detail] Get detail auction {}, have exception: ", id, ex);
        }
        return detailAuctionResponse;
    }

    @Override
    public CommonResponse delete(String currentUserId, String auctionId) {
        CommonResponse commonResponse = new CommonResponse().code(AucMessage.DELETE_AUCTION_FAILED.getCode()).message(AucMessage.DELETE_AUCTION_FAILED.getMessage());
        try {

            long rs = auctionRepository.deleteByIdAndUserIdAndStatus(auctionId, currentUserId, AuctionStatus.PENDING.name());
            if (rs > 0) {
                commonResponse.code(AucMessage.DELETE_AUCTION_SUCCESS.getCode()).message(AucMessage.DELETE_AUCTION_SUCCESS.getMessage());
            }
        } catch (Exception ex) {
            log.error("[AuctionServiceImpl.delete] Delete auction have exception: ", ex);
            commonResponse.message(ex.getMessage());
        }
        return commonResponse;
    }

    @Override
    public InfoAuctionResponse info(String currentUserId, String auctionId) {
        InfoAuctionResponse detailAuctionResponse = new InfoAuctionResponse().code(AucMessage.AUCTION_NOT_FOUND.getCode()).message(AucMessage.AUCTION_NOT_FOUND.getMessage());
        try {
            AuctionEntity auctionEntity = auctionRepository.findByIdAndUserId(auctionId, currentUserId);
            if (ObjectUtils.isNotEmpty(auctionEntity)) {
                detailAuctionResponse.data(mapper.mappingEntityToItem(auctionEntity))
                        .code(AucMessage.PULL_AUCTION_SUCCESS.getCode()).message(AucMessage.PULL_AUCTION_SUCCESS.getMessage());
            }
        } catch (Exception ex) {
            log.error("[AuctionController.detail] Get info auction {}, have exception: ", auctionId, ex);
        }
        return detailAuctionResponse;
    }

    @Override
    public SearchAuctionResponse search(SearchAuctionRequest searchAuctionRequest) {

        SearchAuctionResponse searchAuctionResponse = new SearchAuctionResponse().code(AucMessage.AUCTION_NOT_FOUND.getCode()).message(AucMessage.AUCTION_NOT_FOUND.getMessage());
        SearchAuctionItem searchAuctionItem = new SearchAuctionItem();
        try {
            List<Order> orders = new ArrayList<>();
            Order order = Order.builder()
                    .field(AuctionEntity_.CREATED_DATE)
                    .sortType(SortType.DESC)
                    .build();
            orders.add(order);

            List<Condition> conditions = new ArrayList<>();

            int searchType = searchAuctionRequest.getType();
            if (SearchType.START_PRICE.getType() == searchType) {
                Condition condition = Condition.builder()
                        .operator(Operator.EQUALS)
                        .field(AuctionEntity_.START_PRICE)
                        .value(new BigDecimal(searchAuctionRequest.getKeyword()))
                        .build();
                conditions.add(condition);
            } else if (SearchType.START_TIME.getType() == searchType) {
                Condition condition = Condition.builder()
                        .operator(Operator.EQUALS)
                        .field(AuctionEntity_.START_DATE)
                        .value(DateUtils.parseDate(searchAuctionRequest.getKeyword(), AucConstant.DATE_FORMAT).toInstant())
                        .build();
                conditions.add(condition);
            } else if (SearchType.END_TIME.getType() == searchType) {
                Condition condition = Condition.builder()
                        .operator(Operator.EQUALS)
                        .field(AuctionEntity_.END_DATE)
                        .value(DateUtils.parseDate(searchAuctionRequest.getKeyword(), AucConstant.DATE_FORMAT).toInstant())
                        .build();
                conditions.add(condition);
            } else {
                Condition condition = Condition.builder()
                        .operator(Operator.LIKE)
                        .field(AuctionEntity_.TITLE)
                        .value(searchAuctionRequest.getKeyword())
                        .build();
                conditions.add(condition);
            }

            SearchDTO searchDTO = SearchDTO.builder()
                    .page(searchAuctionRequest.getPage() == null ? 0 : searchAuctionRequest.getPage())
                    .size(searchAuctionRequest.getSize() == null ? 10 : searchAuctionRequest.getSize())
                    .orders(orders)
                    .conditions(conditions)
                    .build();
            PaginationDTO<AuctionEntity> auctionItemPaginationDTO = commonQueryService.search(AuctionEntity.class, AuctionEntity.class, searchDTO, null);
            searchAuctionItem.setTotal(auctionItemPaginationDTO.getTotal());
            searchAuctionItem.setAuctions(mapper.mappings(auctionItemPaginationDTO.getItems()));

            searchAuctionResponse.code(AucMessage.PULL_AUCTION_SUCCESS.getCode()).message(AucMessage.PULL_GROUP_SUCCESS.getMessage()).data(searchAuctionItem);

        } catch (Exception ex) {
            log.error("[AuctionServiceImpl.filterAuctions] Filter auction have exception: ", ex);
            searchAuctionResponse.message(ex.getMessage());
        }

        return searchAuctionResponse;
    }

    @Override
    public CommonResponse updateStatus(UpdateAuctionStatusRequest updateAuctionStatusRequest) {
        CommonResponse commonResponse = new CommonResponse().code(AucMessage.UPDATE_AUCTION_FAILED.getCode()).message(AucMessage.UPDATE_AUCTION_FAILED.getMessage());
        try{
            AuctionEntity auctionEntity = auctionRepository.findById(updateAuctionStatusRequest.getAuctionId()).orElseThrow(()-> new AucException(AucMessage.AUCTION_NOT_FOUND.getCode(), AucMessage.AUCTION_NOT_FOUND.getMessage()));
            if(AuctionStatus.valueOf(updateAuctionStatusRequest.getStatus()).getStatus() > AuctionStatus.valueOf(auctionEntity.getStatus()).getStatus()){
                auctionEntity.setStatus(updateAuctionStatusRequest.getStatus());
                auctionRepository.save(auctionEntity);
            }else {
                throw new AucException(AucMessage.CANNOT_UPDATE_AUCTION.getCode(), AucMessage.CANNOT_UPDATE_AUCTION.getMessage());
            }
        }catch (Exception ex){
            log.error("[AuctionServiceImpl.updateStatus] update status auction have exception: ", ex);
            commonResponse.message(ex.getMessage());
        }
        return commonResponse;
    }

    private boolean validateAuctionData(UpdateAuctionRequest updateAuctionRequest) {

        return com.bksoftwarevn.auction.util.DateUtils.validate(updateAuctionRequest.getStartDate(), updateAuctionRequest.getEndDate());
    }

    private boolean validateAuctionData(CreateAuctionRequest createAuctionRequest) {

        return com.bksoftwarevn.auction.util.DateUtils.validate(createAuctionRequest.getStartDate(), createAuctionRequest.getEndDate());
    }
}
