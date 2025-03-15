package com.example.imagedl.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "logs")
public class Log {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(name = "request")
  private String request;

  @Column(name = "results")
  private int results;

  @Column(name = "urls", length = 10485760)
  private String urls;

  public Log(String name, Integer qty, String output) {
    this.request = name;
    this.results = qty;
    this.urls = output;
  }
}