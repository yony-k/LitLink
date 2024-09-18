package com.yonyk.litlink.global.security.redis;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.redis.core.RedisHash;

@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Getter
@EqualsAndHashCode
@ToString
@RedisHash(value = "RefreshToken", timeToLive = 259200)
public class RefreshToken {
  @Id
  String refreshToken;
  String memberId;
}
