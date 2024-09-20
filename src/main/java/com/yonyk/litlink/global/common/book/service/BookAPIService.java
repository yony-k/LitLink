package com.yonyk.litlink.global.common.book.service;

import java.net.URI;
import java.util.List;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import com.yonyk.litlink.global.common.book.dto.request.BookSerchDTO;
import com.yonyk.litlink.global.common.book.dto.response.BookDTO;
import com.yonyk.litlink.global.common.book.dto.response.BookSerchResult;
import com.yonyk.litlink.global.error.CustomException;
import com.yonyk.litlink.global.error.exceptionType.BookExceptionType;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
@Service
public class BookAPIService {

  @Value("${naver.client-id}")
  String clientId;

  @Value("${naver.client-secret}")
  String clientSecret;

  // 책 목록 검색
  public List<BookDTO> serchBookList(BookSerchDTO bookSerchDTO) {

    // 요청 Url 생성
    URI uri =
        UriComponentsBuilder.fromUriString("https://openapi.naver.com")
            .path("/v1/search/book.json")
            .queryParam("query", bookSerchDTO.query())
            .queryParam("display", bookSerchDTO.display())
            .queryParam("start", bookSerchDTO.start())
            .queryParam("sort", bookSerchDTO.sort())
            .encode()
            .build()
            .toUri();

    // Request 객체 생성
    RequestEntity<Void> request =
        RequestEntity.get(uri)
            .header("X-Naver-Client-Id", clientId)
            .header("X-Naver-Client-Secret", clientSecret)
            .build();

    // RestTempleate
    RestTemplate restTemplate = new RestTemplate();
    ResponseEntity<BookSerchResult> response =
        restTemplate.exchange(request, BookSerchResult.class);

    BookSerchResult bookSerchResult = response.getBody();
    return bookSerchResult.items();
  }

  // 책 상세 검색
  public BookDTO serchBook(String isbn) {
    BookSerchDTO bookSerchDTO =
        BookSerchDTO.builder().query(isbn).display(10).start(1).sort("sim").build();
    List<BookDTO> bookDTOList = serchBookList(bookSerchDTO);
    // 검색 결과가 있는지 확인
    if (bookDTOList.isEmpty()) throw new CustomException(BookExceptionType.BOOK_NOT_FOUND);
    return bookDTOList.get(0);
  }
}
