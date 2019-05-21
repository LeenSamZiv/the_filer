package leensamziv.filer;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import java.io.File;
import java.io.IOException;
import java.util.*;

@RestController
@RequestMapping(value = "api/")
public class MainController {
    private String path = ResourceBundle.getBundle("application").getString("file.path");

    @GetMapping(value = "list")
    public FileBean[] list() {
        File file = new File(path);
        File[] files = file.listFiles();
        if (files != null) {
            return Arrays.stream(files).map(item -> new FileBean(item.getName(), item.length())).toArray(FileBean[]::new);
        } else {
            return new FileBean[]{};
        }
    }

    @PostMapping(value = "upload")
    public String upload(MultipartHttpServletRequest request) throws IOException {
        //Powered By https://blog.csdn.net/wanghailong_qd/article/details/72778264
        Iterator<String> fileNames = request.getFileNames();
        while (fileNames.hasNext()) {
            String fileName = fileNames.next();
            List<MultipartFile> fileList = request.getFiles(fileName);
            if (fileList.size() > 0) {
                Iterator<MultipartFile> fileIte = fileList.iterator();
                while (fileIte.hasNext()) {
                    MultipartFile multipartFile = fileIte.next();
                    String originalFilename = multipartFile.getOriginalFilename();
                    File dir = new File(path);
                    if (!dir.exists()) {
                        dir.mkdirs();
                    }
                    File dest = new File(path + originalFilename);
                    if (!(dest.exists())) {
                        multipartFile.transferTo(dest);
                    }
                    System.out.println("---------------------------------------------------");
                    System.out.println(path + originalFilename);
                    System.out.println(new Date().toString());
                    System.out.println("---------------------------------------------------");
                }
            }
        }
        return "😊 received";
    }
}
