package com.tongji.microservice.teamsphere.gatewayservice.controller;

import com.tongji.microservice.teamsphere.dto.APIResponse;
import com.tongji.microservice.teamsphere.dto.fileservice.FileResponse;
import com.tongji.microservice.teamsphere.dubbo.api.FileService;
import com.tongji.microservice.teamsphere.dubbo.api.ProjectService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.apache.dubbo.config.annotation.DubboReference;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
        return fileService.getFileByProject(projectId);
    }
}
