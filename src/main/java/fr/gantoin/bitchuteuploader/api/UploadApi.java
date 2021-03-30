package fr.gantoin.bitchuteuploader.api;

import java.io.File;
import java.io.IOException;
import java.util.concurrent.CompletableFuture;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import com.github.kiulian.downloader.YoutubeException;

import lombok.RequiredArgsConstructor;

import domain.BitchuteVideo;
import fr.gantoin.bitchuteuploader.api.dto.BitchuteUploadDto;
import fr.gantoin.bitchuteuploader.service.YoutubeDownloaderService;
import fr.gantoin.bitchuteuploader.service.mapper.BitchuteUploadMapper;
import io.github.bonigarcia.wdm.WebDriverManager;

@Controller
@RequiredArgsConstructor
public class UploadApi {

    private final BitchuteUploadMapper bitchuteUploadMapper;

    private final YoutubeDownloaderService youtubeDownloaderService;

    @GetMapping("/")
    public String uploadFrom(Model model) {
        model.addAttribute("upload", new BitchuteUploadDto());
        return "upload";
    }

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
                deleteFolder(new File("my_videos"));
            }
        });
    }

    @PostMapping("/upload")
    public String upload(@ModelAttribute BitchuteUploadDto dto, Model model) {
        CompletableFuture.runAsync(() -> {
            model.addAttribute("upload", dto);
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
        });
        return "result";
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
