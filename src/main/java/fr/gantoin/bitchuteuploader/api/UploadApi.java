package fr.gantoin.bitchuteuploader.api;

import java.util.concurrent.CompletableFuture;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import lombok.RequiredArgsConstructor;

import fr.gantoin.bitchuteuploader.api.dto.BitchuteUploadDto;
import fr.gantoin.bitchuteuploader.service.UploadService;
import fr.gantoin.bitchuteuploader.service.YoutubeDownloaderService;
import fr.gantoin.bitchuteuploader.service.mapper.BitchuteUploadMapper;

@Controller
@RequiredArgsConstructor
public class UploadApi {

    private final BitchuteUploadMapper bitchuteUploadMapper;

    private final YoutubeDownloaderService youtubeDownloaderService;

    private final UploadService uploadService;

    @GetMapping("/")
    public String uploadFrom(Model model) {
        model.addAttribute("upload", new BitchuteUploadDto());
        return "upload";
    }

    @PostMapping("/upload")
    public String upload(@ModelAttribute BitchuteUploadDto dto, Model model) {
        CompletableFuture.runAsync(() -> {
            model.addAttribute("upload", dto);
            uploadService.asyncUpload(dto, youtubeDownloaderService, bitchuteUploadMapper);
        });
        return "result";
    }

}
