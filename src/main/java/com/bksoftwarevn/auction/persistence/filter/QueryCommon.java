package com.bksoftwarevn.auction.persistence.filter;

import com.bksoftwarevn.auction.constant.AucConstant;
import com.bksoftwarevn.auction.constant.AucMessage;
import com.bksoftwarevn.auction.exception.AucException;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.time.DateUtils;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.math.BigDecimal;
import java.text.ParseException;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class QueryCommon {

    public static String convertWildCardCharacter(String value) {
        if (StringUtils.isNotBlank(value)) {
            value = value.replace("_", "\\_");
            value = value.replace("%", "\\%");
        }
        return value;
    }

    public static String likeFirst(Object value) {
        value = convertWildCardCharacter((String) value);
        return "%" + value;
    }

    public static String likeLast(Object value) {
        value = convertWildCardCharacter((String) value);
        return value + "%";
    }

    public static String like(Object value) {
        value = convertWildCardCharacter((String) value);
        return "%" + value + "%";
    }

    public static List<Predicate> buildPredicate(List<Condition> conditions, CriteriaBuilder criteriaBuilder, Root<?> root) {
        List<Predicate> predicates = new ArrayList<>();
        for (Condition condition : conditions) {
            switch (condition.getOperator()) {
                case IS_NOT_NULL:
                    predicates.add(criteriaBuilder.isNotNull(root.get(condition.getField())));
                    break;
                case IS_NULL:
                    predicates.add(criteriaBuilder.isNull(root.get(condition.getField())));
                    break;
                case LIKE:
                    predicates.add(criteriaBuilder.like(root.get(condition.getField()), like(condition.getValue())));
                    break;
                case EQUALS:
                    predicates.add(criteriaBuilder.equal(root.get(condition.getField()),
                            castToRequiredType(root.get(condition.getField()).getJavaType(), condition.getValue())));
                    break;
                case NOT_EQUALS:
                    predicates.add(criteriaBuilder.notEqual(root.get(condition.getField()),
                            castToRequiredType(root.get(condition.getField()).getJavaType(), condition.getValue())));
                    break;
                case IN:
                    predicates.add(criteriaBuilder.in(root.get(condition.getField()))
                            .value(castToRequiredType(root.get(condition.getField()).getJavaType(), condition.getValues())));
                    break;
                case GREATER_THAN:
                    predicates.add(criteriaBuilder.gt(root.get(condition.getField()),
                            (Number) castToRequiredType(root.get(condition.getField()).getJavaType(), condition.getValue())));
                    break;
                case LESS_THAN:
                    predicates.add(criteriaBuilder.lt(root.get(condition.getField()),
                            (Number) castToRequiredType(root.get(condition.getField()).getJavaType(), condition.getValue())));
                    break;
                case LTI:
                    predicates.add(criteriaBuilder.lessThan(root.get(condition.getField()),
                            (Instant) castToRequiredType(root.get(condition.getField()).getJavaType(), condition.getValue())));
                    break;
                case GTI:
                    predicates.add(criteriaBuilder.greaterThan(root.get(condition.getField()),
                            (Instant) castToRequiredType(root.get(condition.getField()).getJavaType(), condition.getValue())));
                    break;
                default:
                    throw new AucException(AucMessage.OPERATION_NOT_SUPPORT.getCode(), AucMessage.OPERATION_NOT_SUPPORT.getMessage());
            }
        }
        return predicates;
    }

    private static Object castToRequiredType(Class fieldType, Object value) {
        if (fieldType.isAssignableFrom(Double.class)) {
            return Double.valueOf((Double) value);
        } else if (fieldType.isAssignableFrom(Integer.class)) {
            return Integer.valueOf((Integer) value);
        } else if (Enum.class.isAssignableFrom(fieldType)) {
            return Enum.valueOf(fieldType, (String) value);
        } else if (BigDecimal.class.isAssignableFrom(fieldType)) {
            return new BigDecimal((String) value);
        } else if (boolean.class.isAssignableFrom(fieldType)) {
            return Boolean.valueOf((Boolean) value);
        } else if (Instant.class.isAssignableFrom(fieldType)) {
            try {
                return DateUtils.parseDate((String) value, AucConstant.DATE_FORMAT).toInstant();
            } catch (ParseException e) {
                throw new AucException(AucMessage.INTERNAL_SERVER_ERROR.getCode(), e.getMessage());
            }
        } else if (Long.class.isAssignableFrom(fieldType)) {
            return Long.valueOf((String) value);
        } else if (BigDecimal.class.isAssignableFrom(fieldType)) {
            return BigDecimal.valueOf(Double.valueOf((Double) value));
        }
        return value;
    }

    private static Object castToRequiredType(Class fieldType, List<String> values) {
        return values.stream().map(value -> castToRequiredType(fieldType, value)).collect(Collectors.toList());
    }

    public static List<javax.persistence.criteria.Order> buildOrderList(List<Order> orderFields, CriteriaBuilder criteriaBuilder, Root<?> root) {
        List<javax.persistence.criteria.Order> orders = new ArrayList<>();
        for (Order order : orderFields) {
            switch (order.getSortType()) {
                case ASC:
                    orders.add(criteriaBuilder.asc(root.get(order.getField())));
                    break;
                case DESC:
                    orders.add(criteriaBuilder.desc(root.get(order.getField())));
                    break;
                default:
                    throw new AucException(AucMessage.OPERATION_NOT_SUPPORT.getCode(), AucMessage.OPERATION_NOT_SUPPORT.getMessage());
            }
        }
        return orders;
    }
}
