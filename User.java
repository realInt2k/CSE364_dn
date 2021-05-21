package com.unist;

import java.util.Objects;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
class User {

    private @Id @GeneratedValue Long id;
    private String name;
    private String film;

    User() {}

    User(String name, String film) {

        this.name = name;
        this.film = film;
    }

    public Long getId() {
        return this.id;
    }

    public String getName() {
        return this.name;
    }

    public String getFilm() {
        return this.film;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setFilm(String film) {
        this.film = film;
    }

    @Override
    public boolean equals(Object o) {

        if (this == o)
            return true;
        if (!(o instanceof User))
            return false;
        User user = (User) o;
        return Objects.equals(this.id, user.id) && Objects.equals(this.name, user.name)
                && Objects.equals(this.film, user.film);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id, this.name, this.film);
    }

    @Override
    public String toString() {
        return "Employee{" + "id=" + this.id + ", name='" + this.name + '\'' + ", film='" + this.film + '\'' + '}';
    }
}