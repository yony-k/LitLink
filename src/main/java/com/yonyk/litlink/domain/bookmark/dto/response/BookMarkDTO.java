package com.yonyk.litlink.domain.bookmark.dto.response;

import com.yonyk.litlink.domain.bookmark.entity.BookMark;
import com.yonyk.litlink.domain.member.entity.Member;
import com.yonyk.litlink.global.common.book.entity.Book;
import lombok.Builder;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

@Builder
public record BookMarkDTO(
        long bookMarkId,
        long memberId,
        boolean liked,
        long bookId,
        String title,
        String link,
        String image,
        String author,
        int discount,
        String publisher,
        String isbn,
        String description,
        String pubdate,
        int likeCount
) {
  public static BookMarkDTO toBookMarkDTO(BookMark bookMark) {
    Member member = bookMark.getMember();
    Book book = bookMark.getBook();

    return BookMarkDTO.builder()
            .bookMarkId(bookMark.getBookmarkId())
            .memberId(member.getMemberId())
            .liked(bookMark.isLiked())
            .bookId(book.getBookId())
            .title(book.getTitle())
            .link(book.getLink())
            .image(book.getImage())
            .author(book.getAuthor())
            .discount(book.getDiscount())
            .publisher(book.getPublisher())
            .isbn(book.getIsbn())
            .description(book.getDescription())
            .pubdate(book.getPubdate().format(DateTimeFormatter.ISO_LOCAL_DATE))
            .likeCount(book.getLikeCount())
            .build();
  }
}
