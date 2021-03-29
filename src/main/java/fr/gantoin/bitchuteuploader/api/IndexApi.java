package fr.gantoin.bitchuteuploader.api;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
public class IndexApi {

    @GetMapping("/")
    public String index() {
        return "<a href='https://github.com/gantoin/youtube-to-bitchute-api'>https://github.com/gantoin/youtube-to-bitchute-api</a>";
    }

}
