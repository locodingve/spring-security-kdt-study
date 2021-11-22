package com.kdt.yun.configures;

import lombok.Getter;
import lombok.Setter;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * Created by yunyun on 2021/11/22.
 */
@Getter
@Setter
@Component
@ConfigurationProperties(prefix = "jwt")
public class JwtConfigure {
    private String header;
    private String issuer;
    private String clientSecret;
    private int expirySeconds;

    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE)
                .append("header", header)
                .append("issuer", issuer)
                .append("clientSecret", clientSecret)
                .append("expirySeconds", expirySeconds)
                .toString();
    }
}
