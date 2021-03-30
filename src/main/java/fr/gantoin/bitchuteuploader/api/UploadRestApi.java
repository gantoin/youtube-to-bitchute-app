package fr.gantoin.bitchuteuploader.api;

import java.util.concurrent.CompletableFuture;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;

import fr.gantoin.bitchuteuploader.api.dto.BitchuteUploadDto;
import fr.gantoin.bitchuteuploader.service.UploadService;
import fr.gantoin.bitchuteuploader.service.YoutubeDownloaderService;
import fr.gantoin.bitchuteuploader.service.mapper.BitchuteUploadMapper;

@RestController
@RequiredArgsConstructor
public class UploadRestApi {

    private final BitchuteUploadMapper bitchuteUploadMapper;

    private final YoutubeDownloaderService youtubeDownloaderService;

    private final UploadService uploadService;

    @PostMapping("/upload/without-ui")
    public void upload(@RequestBody BitchuteUploadDto dto) {
        CompletableFuture.runAsync(() -> {
            uploadService.asyncUpload(dto, youtubeDownloaderService, bitchuteUploadMapper);
        });
    }
}
