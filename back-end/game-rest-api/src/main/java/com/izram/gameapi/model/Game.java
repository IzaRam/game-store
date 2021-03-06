package com.izram.gameapi.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "games")
public class Game {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    int id;

    @Column(nullable = false)
    String name;

    @Column(nullable = false)
    int year;

    @Column(nullable = false)
    String description;

    @Column(nullable = false)
    String image_url;

    @Column(nullable = false)
    int quantity;

    @Column(nullable = false)
    double price;

}
