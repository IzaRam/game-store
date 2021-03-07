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
public class Sale {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    int sale_id;

    @Column(nullable = false)
    int user_id;

    @ManyToMany(fetch = FetchType.LAZY)
    @Column(nullable = false)
    List<Game> gameList = new ArrayList<>();

}
