package edu.miu.TradingPlatform.domain;

import jakarta.persistence.*;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Entity
@Data
public class WatchList {
  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  private Long watchListId;

  @OneToOne
  private User user;
  @ManyToMany
  private List<Coins> coins = new ArrayList<>();
}
