package com.yonyk.litlink.domain.member.entity;

import com.yonyk.litlink.domain.bookmark.entity.BookMark;
import com.yonyk.litlink.domain.member.entity.enums.MemberRole;
import com.yonyk.litlink.global.common.BaseEntity;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.SQLRestriction;

import java.util.List;

@Builder
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PROTECTED)
@ToString
@SQLDelete(sql = "UPDATE member SET deleted_at = CURRENT_TIMESTAMP WHERE member_id = ?")
@SQLRestriction("deleted_at is NULL")
@Entity
@Table(name = "member")
public class Member extends BaseEntity {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long memberId;

  private String authId;

  @Column(nullable = false)
  private String memberName;
  
  @Column(nullable = false)
  private String email;

  @Column(nullable = false)
  private String imageUrl;

  @Column(nullable = false)
  private String provider;

  @Enumerated(EnumType.STRING)
  @Column(nullable = false)
  private MemberRole memberRole;

  @OneToMany(mappedBy = "member", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
  private List<BookMark> bookMarks;
}
