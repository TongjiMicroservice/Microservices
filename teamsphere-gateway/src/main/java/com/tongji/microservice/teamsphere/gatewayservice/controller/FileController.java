package com.tongji.microservice.teamsphere.gatewayservice.controller;

import cn.dev33.satoken.stp.StpUtil;
import com.obs.services.model.PutObjectRequest;
import com.tongji.microservice.teamsphere.dto.APIResponse;
import com.tongji.microservice.teamsphere.dto.fileservice.FileData;
import com.tongji.microservice.teamsphere.dto.fileservice.FileResponse;
import com.tongji.microservice.teamsphere.dubbo.api.FileService;
import com.tongji.microservice.teamsphere.gatewayservice.util.Loader;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.apache.dubbo.config.annotation.DubboReference;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.time.LocalDateTime;

@RestController
@RequestMapping("/api")
@Tag(name = "FileController", description = "文件微服务接口")
public class FileController {
    @DubboReference(check = false)
    private FileService fileService;

    @GetMapping("/file-by-project")
    @Operation(summary = "查看项目的全部文件", responses = {
            @ApiResponse(responseCode = "200", description = "调用成功",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = FileResponse.class))),
            @ApiResponse(responseCode = "400", description = "调用失败",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = FileResponse.class)))
    })
    public FileResponse getFileByProject(int projectId) {
        if(!StpUtil.isLogin()){
            return new FileResponse(APIResponse.notLoggedIn()) ;
        }
        System.out.println("projectId: " + projectId);
        return fileService.getFileByProject(projectId);
    }

    @PostMapping(value = "/file", consumes = "multipart/form-data")
    @Operation(summary = "上传文件", responses = {
            @ApiResponse(responseCode = "200", description = "调用成功",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = APIResponse.class))),
            @ApiResponse(responseCode = "400", description = "调用失败",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = APIResponse.class)))
    })
    public APIResponse upload(int userId, int projectId, @RequestPart MultipartFile file) {
        System.out.printf("收到文件:%s:%d\n", file.getOriginalFilename(), file.getSize());
//        if (!StpUtil.isLogin()) {
//            return new CreateTaskResponse(APIResponse.notLoggedIn());
//        }
        try {
            InputStream i = new ByteArrayInputStream(file.getBytes());
            PutObjectRequest putObjectRequest = new PutObjectRequest("test-micro", file.getOriginalFilename(), i);
            Loader.getObsClient().putObject(putObjectRequest);
            System.out.println("上传成功");
        } catch (Exception e) {
            e.printStackTrace();
            return APIResponse.fail("上传失败");
        }
        String name = file.getOriginalFilename();
        assert name != null;
        int i = name.indexOf('.');
        return fileService.upload(new FileData(
                Loader.getURL() + name,
                i < 0 ? "file" : name.substring(i + 1),
                name,
//                LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")),
                LocalDateTime.now(),
                userId,
                projectId,
                (int) file.getSize()
        ));
    }

    @PostMapping(value = "/file/star")
    @Operation(summary = "设为星标", responses = {
            @ApiResponse(responseCode = "200", description = "调用成功",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = APIResponse.class))),
            @ApiResponse(responseCode = "400", description = "调用失败",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = APIResponse.class)))
    })
    public APIResponse putStar(int fileId){
        if(!StpUtil.isLogin()){
            return APIResponse.notLoggedIn();
        }
        //System.out.printf("id %s\n" , StpUtil.getLoginId());
        int userId = Integer.parseInt(StpUtil.getLoginId().toString());
        return fileService.putStar(userId,fileId);
    }

    @DeleteMapping(value = "/file/star")
    @Operation(summary = "删除星标", responses = {
            @ApiResponse(responseCode = "200", description = "调用成功",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = APIResponse.class))),
            @ApiResponse(responseCode = "400", description = "调用失败",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = APIResponse.class)))
    })
    public APIResponse deleteStar(int fileId){
        if(!StpUtil.isLogin()){
            return APIResponse.notLoggedIn();
        }
        int userId = Integer.parseInt(StpUtil.getLoginId().toString());
        return fileService.deleteStar(userId,fileId);
    }
    @GetMapping("/file-by-star")
    @Operation(summary = "查看星标文件", responses = {
            @ApiResponse(responseCode = "200", description = "调用成功",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = FileResponse.class))),
            @ApiResponse(responseCode = "400", description = "调用失败",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = FileResponse.class)))
    })
    FileResponse getFileByStar() {
        if(!StpUtil.isLogin()){
            return new FileResponse(APIResponse.notLoggedIn()) ;
        }
        int userId = Integer.parseInt(StpUtil.getLoginId().toString());
        return fileService.getFileByStar(userId);
    }
}
