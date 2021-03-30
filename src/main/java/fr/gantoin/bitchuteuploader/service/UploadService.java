package fr.gantoin.bitchuteuploader.service;

import java.io.File;
import java.io.IOException;
import java.time.LocalDateTime;

import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.ModelAttribute;

import com.github.kiulian.downloader.YoutubeException;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import domain.BitchuteVideo;
import fr.gantoin.bitchuteuploader.api.dto.BitchuteUploadDto;
import fr.gantoin.bitchuteuploader.service.mapper.BitchuteUploadMapper;
import io.github.bonigarcia.wdm.WebDriverManager;

@Service
@RequiredArgsConstructor
@Slf4j
public class UploadService {

    public void asyncUpload(@ModelAttribute BitchuteUploadDto dto, YoutubeDownloaderService youtubeDownloaderService, BitchuteUploadMapper bitchuteUploadMapper) {
        try {
            log.info("Try to upload {}", dto.getYoutubePath());
            WebDriverManager chromedriver = WebDriverManager.chromedriver();
            chromedriver.setup();
            log.info("Chromedriver successfully set up");
            log.info("Start downloading video: {}", LocalDateTime.now());
            BitchuteVideo downloadedVideo = youtubeDownloaderService.download(dto.getYoutubePath());
            log.info("Video successfully downloaded: {}", LocalDateTime.now());
            log.info("Start uploading video on bitchute.com: {}", LocalDateTime.now());
            bitchuteUploadMapper.map(chromedriver, dto).uploadVideo(downloadedVideo);
            log.info("Video successfully uploaded: {}", LocalDateTime.now());
        } catch (YoutubeException | IOException e) {
            log.error("Error during process:{}", e.getMessage());
        } finally {
            DeleteFolderService.deleteFolder(new File("my_videos"));
            log.info("Folder content is reset");
        }
    }

}
