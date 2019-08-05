package leensamziv.filer;

import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.*;

@RestController
@RequestMapping(value = "api/")
public class MainController {
    private String path = ResourceBundle.getBundle("application").getString("file.path");

    @GetMapping(value = "list")
    public FileBean[] list() {
        File[] files = new File(path).listFiles();
        if (files != null) {
            return Arrays.stream(files).map(
                    item -> new FileBean(item.getName(), item.lastModified(), item.length())
            ).toArray(FileBean[]::new);
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
                    System.out.println("-----" + new Date().toString() + "-----");
                    System.out.println(path + originalFilename);
                }
            }
        }
        return "😊 received";
    }

    @PostMapping(value = "download")
    public void download(@RequestBody FileBean bean, HttpServletResponse response) throws UnsupportedEncodingException {
        File[] files = new File(path).listFiles();
        if (files != null) {
            for (int i = 0; i < files.length; i++) {
                File file = files[i];
                if (
                        bean.lastModified == file.lastModified()
                                && bean.size == file.length()
                                && bean.name.equals(file.getName())
                ) {
                    //Powered By https://blog.csdn.net/eieiei438/article/details/83824375
                    String contentDisposition = "attachment;filename=" + URLEncoder.encode(file.getName(), "UTF-8");
                    response.setHeader("Content-Disposition", contentDisposition);
                    response.setContentType("application/octet-stream");
                    try {
                        FileInputStream fis = new FileInputStream(file.getAbsolutePath());
                        byte[] content = new byte[fis.available()];
                        fis.read(content);
                        fis.close();

                        ServletOutputStream sos = response.getOutputStream();
                        sos.write(content);

                        sos.flush();
                        sos.close();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
        }
    }
}
