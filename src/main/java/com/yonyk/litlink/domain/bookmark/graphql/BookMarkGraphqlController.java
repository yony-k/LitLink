package com.yonyk.litlink.domain.bookmark.graphql;

import com.yonyk.litlink.domain.bookmark.service.BookMarkService;
import com.yonyk.litlink.global.security.details.PrincipalDetails;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.MutationMapping;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;

@Slf4j
@RequiredArgsConstructor
@Controller
public class BookMarkGraphqlController {

  private final BookMarkService bookMarkService;

  @MutationMapping
  public String saveBookMark(@AuthenticationPrincipal PrincipalDetails principalDetails,
                             @Argument String isbn) {
    bookMarkService.saveBookMark(principalDetails.getMember(), isbn);
    return "저장이 성공적으로 완료되었습니다.";
  }
}
