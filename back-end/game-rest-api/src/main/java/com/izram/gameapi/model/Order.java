package com.izram.gameapi.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "orders")
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    int order_id;

    @Column(nullable = false)
    int user_id;

    @ManyToMany(fetch = FetchType.EAGER)
    @Column(nullable = false)
    List<Game> gameList = new ArrayList<>();

}
