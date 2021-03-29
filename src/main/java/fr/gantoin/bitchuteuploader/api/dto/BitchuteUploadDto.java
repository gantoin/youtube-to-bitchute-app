package fr.gantoin.bitchuteuploader.api.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class BitchuteUploadDto {
    private String user;
    private String password;
    private String youtubePath;
}
