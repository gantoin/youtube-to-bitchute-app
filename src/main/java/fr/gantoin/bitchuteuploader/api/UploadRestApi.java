package fr.gantoin.bitchuteuploader.api;

import java.io.File;
import java.io.IOException;
import java.util.concurrent.CompletableFuture;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.github.kiulian.downloader.YoutubeException;

import lombok.RequiredArgsConstructor;

import domain.BitchuteVideo;
import fr.gantoin.bitchuteuploader.api.dto.BitchuteUploadDto;
import fr.gantoin.bitchuteuploader.service.DeleteFolderService;
import fr.gantoin.bitchuteuploader.service.YoutubeDownloaderService;
import fr.gantoin.bitchuteuploader.service.mapper.BitchuteUploadMapper;
import io.github.bonigarcia.wdm.WebDriverManager;

@RestController
@RequiredArgsConstructor
public class UploadRestApi {

    private final BitchuteUploadMapper bitchuteUploadMapper;

    private final YoutubeDownloaderService youtubeDownloaderService;

    @PostMapping("/upload/without-ui")
    public void upload(@RequestBody BitchuteUploadDto dto) {
        CompletableFuture.runAsync(() -> {
            try {
                WebDriverManager chromedriver = WebDriverManager.chromedriver();
                chromedriver.setup();
                BitchuteVideo downloadedVideo = youtubeDownloaderService.download(dto.getYoutubePath());
                bitchuteUploadMapper.map(chromedriver, dto).uploadVideo(downloadedVideo);
            } catch (YoutubeException | IOException e) {
                e.printStackTrace();
            } finally {
                DeleteFolderService.deleteFolder(new File("my_videos"));
            }
        });
    }
}
