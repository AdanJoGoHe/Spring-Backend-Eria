package com.eria.EriaWebBackend.impl;

import com.eria.EriaWebBackend.service.StorageService;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import lombok.extern.log4j.Log4j2;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
@Log4j2
public class StorageServiceImpl implements StorageService {
  private final Path root = Paths.get("Test");
  public final String THUMBNAIL_PREFIX = "Thumbnail-";

  @Override
  public void init() {}

  @Override
  public void store(MultipartFile multipartFile) {
    File saveFile = new File("C:\\Test\\", multipartFile.getOriginalFilename());

    try (OutputStream os = new FileOutputStream(saveFile)) {
      os.write(multipartFile.getBytes());
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  @Override
  public void storeThumbnail(MultipartFile multipartFile, String name) {
    File saveFile = new File("C:\\Test\\Thumbnail", THUMBNAIL_PREFIX + name);
    try (OutputStream os = new FileOutputStream(saveFile)) {
      os.write(multipartFile.getBytes());
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  @Override
  public Stream<Path> loadAll() {
    List<File> files;
    List<Path> pathes = null;
    try {
      files = Files.list(Paths.get("C:\\Test\\"))
          .map(Path::toFile)
          .filter(File::isFile)
          .collect(Collectors.toList());
      files.forEach(System.out::println);
      pathes = files.stream().map(File::toPath).collect(Collectors.toList());
    } catch (IOException e) {
      e.printStackTrace();
    }

    return pathes.stream();
  }

  @Override
  public Resource load(String filename) {
    try {
      Path file = root.resolve("C:\\Test\\" + filename);
      log.debug("Debug Point "+file.toString());
      Resource resource = new UrlResource(file.toUri());
      if (resource.exists() || resource.isReadable()) {
        return resource;
      } else {
        throw new RuntimeException("Could not read the file!");
      }
    } catch (MalformedURLException e) {
      throw new RuntimeException("Error: " + e.getMessage());
    }
  }

  @Override
  public Resource loadAsResource(String filename) {
    return null;
  }

  @Override
  public void deleteAll() {

  }
}
