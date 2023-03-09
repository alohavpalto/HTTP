package org.example;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.IOException;
import java.util.List;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;

public class Main {

  public static final ObjectMapper objectMapper = new ObjectMapper();
  public static final String URL = "https://raw.githubusercontent.com/netology-code/jd-homeworks/master/http/task1/cats";

  public static void main(String[] args) throws IOException, NullPointerException {
    CloseableHttpClient httpClient = HttpClientBuilder.create()
        .setDefaultRequestConfig(RequestConfig.custom()
            .setConnectTimeout(5000)    //максимальное время ожидание подключения к серверу
            .setSocketTimeout(30000)    //максимальное время ожидания получения данных
            .setRedirectsEnabled(false) //возможность следовать редиректу в ответе
            .build())
        .build();
    HttpGet request = new HttpGet(URL);
    CloseableHttpResponse response = httpClient.execute(request);
    List<Cats> info = objectMapper.readValue(response.getEntity().getContent(),
        new TypeReference<>() {
        });
    info.stream()
        .filter(o -> o.getUpvotes() != null)
        .filter(o -> o.getUpvotes() > 0)
        .forEach(System.out::println);

  }
}
