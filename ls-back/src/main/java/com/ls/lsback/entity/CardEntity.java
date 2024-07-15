package com.ls.lsback.entity;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "carte")
@Getter
@Setter
@NoArgsConstructor
public class CardEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "recto", nullable = false)
    private String recto;

    @Column(name = "verso", nullable = false)
    private String verso;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "memo_id", nullable = false)
    @JsonProperty("memo_id")
    private CardMemoEntity memoId;

}
