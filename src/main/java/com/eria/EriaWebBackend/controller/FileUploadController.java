package com.eria.EriaWebBackend.controller;

import com.eria.EriaWebBackend.models.FilesModel;
import java.io.File;
import java.io.IOException;
import java.net.http.HttpResponse;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@Log4j2
public class FileUploadController
{

  private final StorageService storageService;


  @Autowired
  public FileUploadController(StorageService storageService)
  {
    this.storageService = storageService;
  }

  @GetMapping("/files")
  public ResponseEntity<List> listUploadedFiles(Model model) throws IOException
  {
    List fileInfos = storageService.loadAll().map(path -> {
      String filename = path.getFileName().toString();
      /*String url = MvcUriComponentsBuilder
          .fromMethodName(FileUploadController.class, "getFile", path.getFileName().toString()).build().toString();*/
      String url = path.toAbsolutePath().toString();
      return new FilesModel(filename, new File(url), new File(url));
    }).toList();
    return ResponseEntity.status(HttpStatus.OK).body(fileInfos);
  }

  @GetMapping("/files/{filename:.+}")
  @ResponseBody
  public ResponseEntity<Resource> getFile(@PathVariable String filename) {
    Resource file = storageService.load(filename);
    return ResponseEntity.ok()
        .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + file.getFilename() + "\"").body(file);
  }

  @PostMapping("/upload")
  public ResponseEntity<String> handleFileUpload(@RequestParam("file") MultipartFile file,
                                                 @RequestParam("thumbnail") MultipartFile thumbnail,
                                                 @RequestParam("name") String name,
                                                 RedirectAttributes redirectAttributes)
  {
    storageService.store(file);
    storageService.storeThumbnail(thumbnail, file.getOriginalFilename());
    log.error("Upload String Name is : " + name);
    redirectAttributes.addFlashAttribute("message",
        "You successfully uploaded " + file.getOriginalFilename() + "!");
    return ResponseEntity.status(HttpStatus.OK).body(file.getOriginalFilename());
  }

  @ExceptionHandler(StorageFileNotFoundException.class)
  public ResponseEntity<?> handleStorageFileNotFound(StorageFileNotFoundException exc) {
  return ResponseEntity.notFound().build();
  }
}
