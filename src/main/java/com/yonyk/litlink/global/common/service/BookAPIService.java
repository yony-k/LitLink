package com.yonyk.litlink.global.common.service;

import com.yonyk.litlink.global.common.dto.request.BookSerchDTO;
import com.yonyk.litlink.global.common.dto.response.BookDTO;
import com.yonyk.litlink.global.common.dto.response.BookSerchResult;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.List;

@Slf4j
@RequiredArgsConstructor
@Service
public class BookAPIService {

  @Value("${naver.client-id}")
  String clientId;

  @Value("${naver.client-secret}")
  String clientSecret;

  // 책 검색
  public List<BookDTO> serchBooks(BookSerchDTO bookSerchDTO) {

    // 요청 Url 생성
    URI uri = UriComponentsBuilder
            .fromUriString("https://openapi.naver.com")
            .path("/v1/search/book.json")
            .queryParam("query", bookSerchDTO.query())
            .queryParam("display", bookSerchDTO.display())
            .queryParam("start", bookSerchDTO.start())
            .queryParam("sort", bookSerchDTO.sort())
            .encode()
            .build()
            .toUri();

    // Request 객체 생성
    RequestEntity<Void> request = RequestEntity.get(uri)
            .header("X-Naver-Client-Id", clientId)
            .header("X-Naver-Client-Secret", clientSecret)
            .build();

    // RestTempleate
    RestTemplate restTemplate = new RestTemplate();
    ResponseEntity<BookSerchResult> response = restTemplate.exchange(request, BookSerchResult.class);

    BookSerchResult bookSerchResult = response.getBody();
    return bookSerchResult.items();
  }
}

