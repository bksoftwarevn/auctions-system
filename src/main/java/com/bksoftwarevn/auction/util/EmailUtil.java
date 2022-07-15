package com.bksoftwarevn.auction.util;

import com.bksoftwarevn.auction.model.Attachment;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.FileUtils;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import org.springframework.util.Base64Utils;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.*;

@RequiredArgsConstructor
@Component
@Slf4j
public class EmailUtil {

    @Qualifier("htmlTemplateEngine")
    private final TemplateEngine htmlTemplateEngine;


    public String generateHTMLContent(String templateName, Map<String, Object> models, final Locale locale) {
        final Context context = new Context(locale);
        context.setVariables(models);
        return Base64Utils.encodeToString(htmlTemplateEngine.process(templateName, context).getBytes(StandardCharsets.UTF_8));
    }

    public List<Attachment> loadAttachments(Set<String> paths) {
        List<Attachment> attachments = new ArrayList<>();

        paths.stream().parallel().forEach(path -> {
            try {
                File file = new File(path);
                byte[] fileContent = FileUtils.readFileToByteArray(file);
                Attachment attachment = Attachment.builder()
                        .content(Base64Utils.encodeToString(fileContent))
                        .fileName(file.getName())
                        .build();
                attachments.add(attachment);
            } catch (IOException e) {
                log.error("EmailUtil.loadAttachments] exception when read file: {} ", path, e);
            }
        });
        return attachments;
    }
}
