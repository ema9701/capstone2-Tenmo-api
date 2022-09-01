package com.techelevator.tenmo.model;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

public class User {

   private Long id;
   private String username;

   private String password;
   private boolean activated;
   private Set<Authority> authorities = new HashSet<>();

   public User() {
   }

   public User(Long id, String username, String password, String authorities) {
      this.id = id;
      this.username = username;
      this.password = password;
      this.activated = true;
   }

   public Long getId() {
      return id;
   }

   public void setId(Long id) {
      this.id = id;
   }

   public String getUsername() {
      return username;
   }

   public void setUsername(String username) {
      this.username = username;
   }

   @JsonIgnore
   public String getPassword() {
      return password;
   }

   @JsonProperty
   public void setPassword(String password) {
      this.password = password;
   }

   @JsonIgnore
   public boolean isActivated() {
      return activated;
   }

   @JsonProperty
   public void setActivated(boolean activated) {
      this.activated = activated;
   }

   @JsonIgnore
   public Set<Authority> getAuthorities() {
      return authorities;
   }

   @JsonProperty
   public void setAuthorities(Set<Authority> authorities) {
      this.authorities = authorities;
   }

   @JsonProperty
   public void setAuthorities(String authorities) {
      String[] roles = authorities.split(",");
      for (String role : roles) {
         this.authorities.add(new Authority("ROLE_" + role));
      }
   }

   @Override
   public boolean equals(Object o) {
      if (this == o)
         return true;
      if (o == null || getClass() != o.getClass())
         return false;
      User user = (User) o;
      return id == user.id &&
            activated == user.activated &&
            Objects.equals(username, user.username) &&
            Objects.equals(password, user.password) &&
            Objects.equals(authorities, user.authorities);
   }

   @Override
   public int hashCode() {
      return Objects.hash(id, username, password, activated, authorities);
   }

   @Override
   public String toString() {
      return "User{" +
            "id=" + id +
            ", username='" + username + '\'' +
            ", activated=" + activated +
            ", authorities=" + authorities +
            '}';
   }
}
