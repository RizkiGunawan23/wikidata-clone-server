package com.polban.wikidata.dto.response.reference;

import java.util.Set;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.polban.wikidata.dto.response.referenceinfo.ReferenceInfoResponse;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ReferenceResponse {
    private String id;
    private Set<ReferenceInfoResponse> referenceInfos;
}
