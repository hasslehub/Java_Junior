package org.example.Task3.models;

import org.example.Task3.Column;

import java.util.UUID;

@org.example.Task3.Entity
public class Entity {

    @Column(name = "id", primaryKey = true)
    private UUID id;

}
