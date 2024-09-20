package com.yonyk.litlink.domain.bookmark.redis;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.redis.core.RedisHash;

@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Getter
@EqualsAndHashCode
@ToString
@RedisHash(value = "ShareToken", timeToLive = 600)
public class ShareToken {
  @Id
  String shareToken;
  long bookMarkId;
}
