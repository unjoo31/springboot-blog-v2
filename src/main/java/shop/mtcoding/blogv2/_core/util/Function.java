package shop.mtcoding.blogv2._core.util;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.UUID;

import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import shop.mtcoding.blogv2._core.error.ex.MyException;
import shop.mtcoding.blogv2._core.vo.MyPath;

@Component
public class Function {
    // 회원가입, 회원정보보기 이미지 저장 함수
    public String saveImage(MultipartFile imageFile){
        UUID uuid = UUID.randomUUID();
        String fileName = uuid + "_" + imageFile.getOriginalFilename();
        Path filePath = Paths.get(MyPath.IMG_PATH + fileName);

        try {
            Files.write(filePath, imageFile.getBytes());
        } catch (IOException e) {
            throw new MyException(e.getMessage());
        }

        return fileName;
    }
}
