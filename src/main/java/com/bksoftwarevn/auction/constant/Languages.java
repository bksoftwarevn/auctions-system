package com.bksoftwarevn.auction.constant;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Collections;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

@Getter
@AllArgsConstructor
public enum Languages {
    EN("en"),
    JA("ja"),
    VI("vi");

    static final Map<String, Languages> ENUM_MAP;
    String language;

    static {
        Map<String, Languages> map = new ConcurrentHashMap<>();
        for (Languages instance : Languages.values()) {
            map.put(instance.getLanguage().toLowerCase(), instance);
        }
        ENUM_MAP = Collections.unmodifiableMap(map);
    }

    public static Languages get(String name) {
        return Optional.ofNullable(ENUM_MAP.get(name.toLowerCase())).orElse(VI);
    }
}
