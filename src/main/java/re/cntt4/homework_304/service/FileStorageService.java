package re.cntt4.homework_304.service;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.*;
import java.util.UUID;

@Service
public class FileStorageService {

    private final Path uploadDir = Paths.get("D:/JavaWedApplication/session15/HomeWork_30-4/uploads");

    public FileStorageService() throws IOException {
        if (!Files.exists(uploadDir)) {
            Files.createDirectories(uploadDir);
        }
    }

    public String storeFile(MultipartFile file) {
        String originalName = file.getOriginalFilename();
        String extension = "";
        if (originalName != null && originalName.contains(".")) {
            extension = originalName.substring(originalName.lastIndexOf("."));
        }
        String newFileName = UUID.randomUUID().toString() + extension;
        Path target = uploadDir.resolve(newFileName);
        try {
            file.transferTo(target.toFile());
        } catch (IOException e) {
            throw new RuntimeException("Upload file thất bại", e);
        }
        return newFileName;
    }
}
