package com.eria.EriaWebBackend.controller;

import com.eria.EriaWebBackend.models.FilesModel;
import com.eria.EriaWebBackend.models.UserInfo;
import com.eria.EriaWebBackend.service.RestService;
import java.io.File;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import lombok.extern.log4j.Log4j2;
import net.minidev.json.JSONObject;
import org.apache.catalina.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClient;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClientService;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.util.UriComponentsBuilder;

@Log4j2
@RestController
@RequestMapping("")
public class OAuth2Controller {

  @Autowired
  private OAuth2AuthorizedClientService authorizedClientService;
  @Autowired
  private RestService restService;


  @GetMapping()
  public ResponseEntity<String> getLoginInfo(Model model, OAuth2AuthenticationToken authentication)
      throws URISyntaxException {
    OAuth2AuthorizedClient client = authorizedClientService
        .loadAuthorizedClient(
            authentication.getAuthorizedClientRegistrationId(),
            authentication.getName());

    URI Eria = new URI("http://localhost:4200");
    OAuth2User user = authentication.getPrincipal();
    HttpHeaders headers = new HttpHeaders();
    headers.set(HttpHeaders.ACCEPT, MediaType.APPLICATION_JSON_VALUE);
    HttpEntity<?> entity = new HttpEntity<>(headers);
    String urlTemplate = UriComponentsBuilder.fromHttpUrl(Eria.toString())
        .queryParam("user", "{user}")
        .encode()
        .toUriString();
    Map<String, OAuth2User> params = new HashMap<>();
    params.put("user", user);
    headers.setLocation(Eria);
    return new ResponseEntity<>(headers, HttpStatus.SEE_OTHER);
  }

  @GetMapping("/user")
  public ResponseEntity<UserInfo> user(@AuthenticationPrincipal OAuth2User principal) {

    UserInfo userInfo = new UserInfo();
    userInfo.setId(principal.getAttributes().get("id").toString());
    userInfo.setUsername(principal.getAttributes().get("username").toString());
    userInfo.setProfilePicture(principal.getAttributes().get("avatar").toString());
    try {
      return ResponseEntity.status(HttpStatus.OK).body(userInfo);
    }catch (Exception e)
    { e.toString(); }
    return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).body(null);
  }

  @RequestMapping("/accessdenied")
  public ResponseEntity<String> accessdenied() {
    return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).body("Omegalul");
  }

}

