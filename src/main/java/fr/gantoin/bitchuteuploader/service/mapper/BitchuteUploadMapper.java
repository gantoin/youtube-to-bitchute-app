package fr.gantoin.bitchuteuploader.service.mapper;

import org.springframework.stereotype.Service;

import domain.BitchuteUpload;
import fr.gantoin.bitchuteuploader.api.dto.BitchuteUploadDto;
import io.github.bonigarcia.wdm.WebDriverManager;

@Service
public class BitchuteUploadMapper {

    public BitchuteUpload map(WebDriverManager chromedriver, BitchuteUploadDto dto) {
        return new BitchuteUpload(chromedriver.getDownloadedDriverPath(), dto.getUser(), dto.getPassword(), true);
    }

}
