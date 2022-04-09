package com.eria.EriaWebBackend.service;

import java.nio.file.Path;
import java.util.stream.Stream;
import org.springframework.core.io.Resource;
import org.springframework.web.multipart.MultipartFile;

public interface StorageService {
  void init();

  public void storeThumbnail(MultipartFile multipartFile, String name);

  void store(MultipartFile file);

  Stream<Path> loadAll();

  Resource load(String filename);

  Resource loadAsResource(String filename);

  void deleteAll();
}
