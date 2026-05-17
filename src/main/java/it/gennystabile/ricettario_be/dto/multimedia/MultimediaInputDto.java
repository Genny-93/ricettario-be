package it.gennystabile.ricettario_be.dto.multimedia;

import jakarta.validation.constraints.NotBlank;

public class MultimediaInputDto {

    @NotBlank
    private String url;

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
