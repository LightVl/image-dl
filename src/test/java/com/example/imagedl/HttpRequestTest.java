package com.example.imagedl;

import com.example.imagedl.controller.ImageController;
import com.example.imagedl.controller.XmlRiverClient;
import com.example.imagedl.model.Image;
import org.junit.jupiter.api.Test;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;

import lombok.SneakyThrows;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.assertj.core.api.Assertions.assertThat;

class HttpRequestTest extends AbstractTestClass {

  @LocalServerPort private int port;

  @Autowired private ImageController controller;

  @Autowired private TestRestTemplate restTemplate;

  @Autowired private XmlRiverClient xmlClient;

  @Test
  @SneakyThrows
  void contextLoads() {
    assertThat(controller).isNotNull();
  }

  @Test
  @SneakyThrows
  void greetingShouldReturnDefaultMessage() {
    final ResponseEntity<String> response =
        restTemplate.getForEntity(String.format("http://localhost:%d/image", port), String.class);
    assertThat(response.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
  }

  @Test
  @SneakyThrows
  void success() {
    final ResponseEntity<String> response =
        restTemplate.getForEntity(
            String.format("http://localhost:%d/image?name=tratata&qty=10", port), String.class);
    assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
  }

  @Test
  @SneakyThrows
  void badcheck1() {
    final ResponseEntity<String> response =
        restTemplate.getForEntity(
            String.format("http://localhost:%d/image?name=", port), String.class);
    assertThat(response.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
  }

  @Test
  @SneakyThrows
  void badcheck2() {
    final ResponseEntity<String> response =
        restTemplate.getForEntity(
            String.format(
                "http://localhost:%d/image?name=witcherwitcherwitcherwitcherwitcherwitcherwitcherwitcherwitcherwitcher&qty=10",
                port),
            String.class);
    assertThat(response.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
  }

  @Test
  @SneakyThrows
  void badcheck3() {
    final ResponseEntity<Image> response =
        restTemplate.getForEntity(
            String.format("http://localhost:%d/image?name=witcher&qty=0", port), Image.class);
    assertThat(response.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
  }

  @Test
  @SneakyThrows
  void badcheck4() {
    final ResponseEntity<Image> response =
        restTemplate.getForEntity(
            String.format("http://localhost:%d/image?name=witcher&qty=50", port), Image.class);
    assertThat(response.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
  }

  @Test
  @SneakyThrows
  void checkOKBody() {
    final ResponseEntity<String> response =
        restTemplate.getForEntity(
            String.format("http://localhost:%d/image?name=tratata&qty=10", port), String.class);
    assertThat(response.getBody()).contains("[{\"id\":1,\"url\":");
  }

  @Test
  @SneakyThrows
  void badBodyBelowZero() {
    final ResponseEntity<String> response =
        restTemplate.getForEntity(
            String.format("http://localhost:%d/image?name=witcher&qty=0", port), String.class);
    assertThat(response.getBody()).contains("qty: должно быть не меньше 1");
  }

  @Test
  @SneakyThrows
  void badBodyMoreThan20() {
    final ResponseEntity<String> response =
        restTemplate.getForEntity(
            String.format("http://localhost:%d/image?name=witcher&qty=50", port), String.class);
    assertThat(response.getBody()).contains("qty: должно быть не больше 20");
  }

  @Test
  @SneakyThrows
  void badBodyLongRequest() {
    final ResponseEntity<String> response =
        restTemplate.getForEntity(
            String.format(
                "http://localhost:%d/image?name=witcherwitcherwitcherwitcherwitcherwitcherwitcherwitcherwitcherwitcher&qty=10",
                port),
            String.class);
      assertThat(response.getBody()).contains("name: размер должен находиться в диапазоне от 1 до 40");
  }
  @Test
  @SneakyThrows
  void xmlRiverTest() {
    final ResponseEntity<String> response =
        restTemplate.getForEntity(
                "https://xmlriver.com/search/xml?setab=images&user=11971&key=f64f40381be005af50a5abf88508e9a7c51274ed&query=batman",
            String.class);
    assertThat(response.getBody())
        .contains("<?xml version=\"1.0\" encoding=\"UTF-8\" ?>");
  }
  @Test
  @SneakyThrows
  void checkXML() {
    assertThat(ImageController.jsonSerializer(xmlClient.getImages("batman")).contains("[{\"id\":1"));
  }

}