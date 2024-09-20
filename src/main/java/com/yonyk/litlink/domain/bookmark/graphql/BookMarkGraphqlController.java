package com.yonyk.litlink.domain.bookmark.graphql;

import java.util.List;

import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.MutationMapping;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;

import com.yonyk.litlink.domain.bookmark.dto.response.BookMarkDTO;
import com.yonyk.litlink.domain.bookmark.service.BookMarkService;
import com.yonyk.litlink.global.security.details.PrincipalDetails;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
@Controller
public class BookMarkGraphqlController {

  private final BookMarkService bookMarkService;

  // 책 저장
  @MutationMapping
  public String saveBookMark(
      @AuthenticationPrincipal PrincipalDetails principalDetails, @Argument String isbn) {
    bookMarkService.saveBookMark(principalDetails.getMember(), isbn);
    return "저장이 성공적으로 완료되었습니다.";
  }

  // 북마크 목록 조회
  @QueryMapping
  public List<BookMarkDTO> getBookMarks(
      @AuthenticationPrincipal PrincipalDetails principalDetails) {
    return bookMarkService.getBookMarks(principalDetails.getMember().getMemberId());
  }

  // 북마크 상세 조회
  @QueryMapping
  public BookMarkDTO getBookMark(
      @AuthenticationPrincipal PrincipalDetails principalDetails, @Argument long bookMarkId) {
    return bookMarkService.getBookMark(principalDetails.getMember().getMemberId(), bookMarkId);
  }

  // 북마크 좋아요 표시
  @MutationMapping
  public String likeBookMark(
      @AuthenticationPrincipal PrincipalDetails principalDetails, @Argument long bookMarkId) {
    bookMarkService.likeBookMark(principalDetails.getMember().getMemberId(), bookMarkId);
    return "좋아요 완료";
  }

  // 북마크 삭제
  @MutationMapping
  public String deleteBookMark(
      @AuthenticationPrincipal PrincipalDetails principalDetails, @Argument long bookMarkId) {
    bookMarkService.deleteBookMark(principalDetails.getMember().getMemberId(), bookMarkId);
    return "삭제가 완료되었습니다.";
  }
}
