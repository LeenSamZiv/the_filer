package leensamziv.filer;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import java.io.File;
import java.io.IOException;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.ResourceBundle;

@RestController
@RequestMapping(value = "api/")
public class MainController {
    @PostMapping(value = "upload")
    public String upload(MultipartHttpServletRequest request) throws IOException {
        //Powered By https://blog.csdn.net/wanghailong_qd/article/details/72778264
        Iterator<String> fileNames = request.getFileNames();
        String path = ResourceBundle.getBundle("application").getString("file.path");
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
