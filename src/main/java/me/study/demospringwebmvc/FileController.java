package me.study.demospringwebmvc;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.io.File;
import java.io.IOException;

@Controller
public class FileController {

    @Autowired
    private ResourceLoader resourceLoader;

    @GetMapping("/file")
    public String fileUploadForm(Model model) {
        return "files/index";
    }

    @PostMapping("/file")
    public String fileUpload(@RequestParam MultipartFile file, RedirectAttributes attributes) {
        // save
        String message = file.getOriginalFilename() + "is uploaded";
        attributes.addFlashAttribute("message", message);
        return "redirect:/file";
    }

    /**
     * ResponseEntity
     * 응답 상태 코드
     * 응답 헤더
     * 응답 본문
     */
    @GetMapping("file/{filename}")
    public ResponseEntity<Resource> fileDownload(@PathVariable String filename) throws IOException {
        Resource resource = resourceLoader.getResource("classpath:" + filename);
        File file = resource.getFile();
        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + resource.getFilename() + "\"") // 사용자가 다운받을때 이름
                .header(HttpHeaders.CONTENT_TYPE, "image/jpg") //어떤 파일인가
                .header(HttpHeaders.CONTENT_LENGTH, file.length() + "")
                .body(resource);
    }

}
