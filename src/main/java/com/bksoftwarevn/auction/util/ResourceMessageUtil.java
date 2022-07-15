package com.bksoftwarevn.auction.util;

import com.bksoftwarevn.auction.constant.AucConstant;
import com.bksoftwarevn.auction.constant.AucMessage;
import com.bksoftwarevn.auction.constant.Languages;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.LocaleUtils;
import org.apache.commons.lang3.StringUtils;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URLConnection;
import java.nio.charset.StandardCharsets;
import java.util.*;

@Slf4j
public class ResourceMessageUtil {

    private ResourceMessageUtil() {
    }

    public static String getMessage(String code, Locale locale) {
        var resourceBundle = ResourceBundle.getBundle(AucConstant.BASE_NAME, locale, new UTF8Control());
        var message = resourceBundle.getString(AucMessage.UNKNOWN_ERROR.getCode());

        try {
            message = resourceBundle.getString(code);
        } catch (MissingResourceException e) {
            log.error("[ResourceMessageUtil.getErrorMessage] Can not find resource {}, key {}, locale {}", AucConstant.BASE_NAME, code, locale);
        }

        return message;
    }

    public static String getUTF8Message(String code, Locale locale) {
        var resourceBundle = ResourceBundle.getBundle(AucConstant.BASE_NAME, locale);
        var message = resourceBundle.getString(AucMessage.UNKNOWN_ERROR.getCode());

        try {
            message = new String(resourceBundle.getString(code).getBytes(StandardCharsets.UTF_8), StandardCharsets.UTF_8);
        } catch (MissingResourceException e) {
            log.error("[ResourceMessageUtil.getErrorMessageUTF8] Can not find resource {}, key {}, locale {}", AucConstant.BASE_NAME, code, locale);
        }

        return message;
    }

    public static String getMessage(String code, String lang) {
        String message;
        try {
            if (lang != null && Languages.EN.getLanguage().equalsIgnoreCase(lang.trim())) {
                lang = Languages.EN.getLanguage();
            } else if (lang != null && Languages.JA.getLanguage().equalsIgnoreCase(lang.trim())) {
                lang = Languages.JA.getLanguage();
            } else {
                lang = Languages.VI.getLanguage();
            }

            var locale = Optional.of(lang.trim().toLowerCase()).map(Locale::new).orElse(Locale.forLanguageTag(Languages.VI.getLanguage()));
            message = ResourceMessageUtil.getMessage(code, locale);

        } catch (Exception ex) {
            message = ResourceMessageUtil.getMessage(AucMessage.UNKNOWN_ERROR.getCode(), lang);
            log.error("[ResourceMessageUtil.getErrorMessage] Can not find resource {}, businessCode {}, lang {}", AucConstant.BASE_NAME, code, lang);
        }
        return message;
    }

    public static String getMessageDefault(String lang) {
        var locale = getLocale(lang);
        return getMessage(AucMessage.UNKNOWN_ERROR.getCode(), locale);
    }

    public static Locale getLocale(String lang) {
        var locale = LocaleUtils.toLocale(Languages.VI.getLanguage());
        if (StringUtils.isEmpty(lang) || lang.trim().equalsIgnoreCase(Languages.VI.getLanguage())) {
            lang = Languages.VI.getLanguage();
        }
        try {
            locale = LocaleUtils.toLocale(lang.trim().toLowerCase());
        } catch (Exception ex) {
            log.error("[ResourceMessageUtil.getLocale] illegal locale string format, falling back to default locale");
        }
        return locale;
    }


    public static class UTF8Control extends ResourceBundle.Control {
        public ResourceBundle newBundle(String baseName, Locale locale, String format, ClassLoader loader, boolean reload)//NOSONAR
                throws IOException {
            // The below is a copy of the default implementation.
            String bundleName = toBundleName(baseName, locale);
            String resourceName = toResourceName(bundleName, "properties");
            ResourceBundle bundle = null;
            InputStream stream = null;
            if (reload) {
                var url = loader.getResource(resourceName);
                if (url != null) {
                    URLConnection connection = url.openConnection();
                    if (connection != null) {
                        connection.setUseCaches(false);
                        stream = connection.getInputStream();
                    }
                }
            } else {
                stream = loader.getResourceAsStream(resourceName);
            }
            if (stream != null) {
                try (var inputStreamReader = new InputStreamReader(stream, StandardCharsets.UTF_8)) {
                    // Only this line is changed to make it to read properties files as UTF-8.
                    bundle = new PropertyResourceBundle(inputStreamReader);
                } finally {
                    stream.close();
                }
            }
            return bundle;
        }
    }
}
