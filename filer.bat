@echo off
for /f "tokens=4" %%a in ('route print^|findstr 0.0.0.0.*0.0.0.0') do (
 set IP=%%a
)
chcp 65001

echo 请访问 %IP%:8080 以上传文件
start D:\_the_filer_upload
java -jar target\the_filer-1.1.jar
