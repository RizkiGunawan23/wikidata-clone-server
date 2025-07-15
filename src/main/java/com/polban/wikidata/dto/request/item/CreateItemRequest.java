package com.polban.wikidata.dto.request.item;

import com.polban.wikidata.validation.AtLeastOneNotEmpty;

import jakarta.validation.constraints.NotBlank;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@AtLeastOneNotEmpty(fields = { "label", "description",
        "alias" }, fieldName = "content", message = "Minimal salah satu dari label, description, atau alias harus diisi")
public class CreateItemRequest {
    @NotBlank(message = "Kode bahasa tidak boleh kosong")
    private String languageCode;

    private String label;
    private String description;
    private String alias;
}
