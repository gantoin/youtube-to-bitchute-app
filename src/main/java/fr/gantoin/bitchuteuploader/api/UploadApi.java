package fr.gantoin.bitchuteuploader.api;

import java.io.File;
import java.io.IOException;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.github.kiulian.downloader.YoutubeException;

import lombok.RequiredArgsConstructor;

import domain.BitchuteVideo;
import fr.gantoin.bitchuteuploader.api.dto.BitchuteUploadDto;
import fr.gantoin.bitchuteuploader.service.YoutubeDownloaderService;
import fr.gantoin.bitchuteuploader.service.mapper.BitchuteUploadMapper;
import io.github.bonigarcia.wdm.WebDriverManager;

@RestController
@RequiredArgsConstructor
public class UploadApi {

    private final BitchuteUploadMapper bitchuteUploadMapper;

    private final YoutubeDownloaderService youtubeDownloaderService;

    @PostMapping("/upload")
    public void upload(@RequestBody BitchuteUploadDto dto) {
        try {
            WebDriverManager chromedriver = WebDriverManager.chromedriver();
            chromedriver.setup();
            BitchuteVideo downloadedVideo = youtubeDownloaderService.download(dto.getYoutubePath());
            bitchuteUploadMapper.map(chromedriver, dto).uploadVideo(downloadedVideo);
        } catch (YoutubeException | IOException e) {
            e.printStackTrace();
        } finally {
            deleteFolder(new File("my_videos"));
        }
    }

    public static void deleteFolder(File folder) {
        File[] files = folder.listFiles();
        if (files != null) {
            for (File f : files) {
                if (f.isDirectory()) {
                    deleteFolder(f);
                } else {
                    f.delete();
                }
            }
        }
    }
}
