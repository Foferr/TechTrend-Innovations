package org.acme.DTO.CompanyNewsDTOs;

import org.eclipse.microprofile.openapi.annotations.media.Schema;

public class CompanyNewsPostDTO {

    @Schema(example = "Your title here")
    private String title;

    @Schema(example = "Your news content here")
    private String newsContent;

    @Schema(example = "published/drafted")
    private String status;
}
