package com.uraditi.backend.controller;

import com.uraditi.backend.dto.CategoryDto;
import com.uraditi.backend.dto.CreateTaskDto;
import com.uraditi.backend.dto.SuccesDto;
import com.uraditi.backend.dto.enums.EntityType;
import com.uraditi.backend.service.FileService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.security.RolesAllowed;
import javax.validation.constraints.NotNull;
import java.io.IOException;

@RestController("File controller")
@RequestMapping("/api/file")
@Tag(name = "File controller", description = "Public file operations")
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class FileController {

    private final FileService fileService;

    @GetMapping("/")
    @Operation(description = "Returns a file for a given type and id")
    public ResponseEntity<byte[]> getAllCategories(@RequestParam EntityType type,
                                                   @RequestParam String id) {
        var fileResponse = fileService.getFile(id, type);
        String fileName = (String) fileResponse.get("fileName");
        byte[] fileBytes = (byte[]) fileResponse.get("file");

        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, getFileHeader(fileName))
                .contentLength(fileBytes.length)
                .contentType(MediaType.MULTIPART_FORM_DATA)
                .body(fileBytes);
    }

    @PostMapping(value = "/save", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @RolesAllowed({"uradiTi_user", "uradiTi_admin"})
    @ApiResponses({
            @ApiResponse(responseCode = "400", description = "Unsupported file type"),
            @ApiResponse(responseCode = "400", description = "File too big"),
            @ApiResponse(responseCode = "400", description = "Unsupported entity"),
            @ApiResponse(responseCode = "404", description = "Entity doesn't exist"),
            @ApiResponse(responseCode = "200", description = "File saved")
    })
    public ResponseEntity<SuccesDto> saveFile(@RequestParam EntityType type,
                                            @RequestParam String id,
                                            @RequestBody @NotNull MultipartFile file) throws IOException {
        fileService.saveFile(id, type, file);
        return ResponseEntity.ok(new SuccesDto(true));
    }

    private String getFileHeader(String fileName){
        return "attachment; filename=\"" +
                fileName +
                "\"";

    }
}
