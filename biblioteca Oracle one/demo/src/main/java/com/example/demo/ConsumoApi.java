package com.example.demo;
import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public class ConsumoApi {

    public String obterDados(String endereco) {
        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(endereco))
                .build();

        HttpRequest request = HttpRequest.newBuilder()
        .uri(URI.create(endereco))
        .timeout(Duration.ofSeconds(10)) // Importar java.time.Duration
        .build();

        try {
            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

            if (response.statusCode() != 200) {
                throw new RuntimeException("Erro na API: Status " + response.statusCode());
}

return response.body();
        } catch (IOException e) {
            throw new RuntimeException("Erro de conexão ao acessar a API: " + e.getMessage());
        } catch (InterruptedException e) {
        
            throw new RuntimeException("A requisição foi interrompida: " + e.getMessage());
        }
    }
}
