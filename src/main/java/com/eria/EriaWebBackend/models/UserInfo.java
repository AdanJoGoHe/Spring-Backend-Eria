package com.eria.EriaWebBackend.models;

import java.io.File;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class UserInfo {
  String id;
  String username;
  String profilePicture;
}
