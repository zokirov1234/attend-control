package com.company.util;

import com.company.model.dto.ResponseDTO;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

public final class ResponseBaseUtil {

    public ResponseBaseUtil() {
    }

    public static ResponseEntity<ResponseDTO<?>> buildSuccessResponse(Object data, String message) {
        ResponseDTO<?> responseDto = convertResponseDTO(data, message, Boolean.TRUE, HttpStatus.OK.value());
        return ResponseEntity.ok().body(responseDto);
    }

    public static ResponseEntity<ResponseDTO<?>> buildInternalErrorResponse(Object data, String message) {
        ResponseDTO<?> responseDto = convertResponseDTO(data, message, Boolean.FALSE, HttpStatus.INTERNAL_SERVER_ERROR.value());
        return ResponseEntity.ok().body(responseDto);
    }

    public static ResponseEntity<ResponseDTO<?>> buildForbiddenErrorResponse(Object data, String message) {
        ResponseDTO<?> responseDto = convertResponseDTO(data, message, Boolean.FALSE, HttpStatus.FORBIDDEN.value());
        return ResponseEntity.ok().body(responseDto);
    }

    public static ResponseEntity<ResponseDTO<?>> buildUnAuthorized(Object data, String message) {
        ResponseDTO<?> responseDto = convertResponseDTO(data, message, Boolean.FALSE, HttpStatus.UNAUTHORIZED.value());
        return ResponseEntity.ok().body(responseDto);
    }

    public static ResponseEntity<ResponseDTO<?>> buildSuccessResponseWithHeaders(Object data, String message, HttpHeaders headers) {
        ResponseDTO<?> responseDTO = convertResponseDTO(data, message, Boolean.TRUE, HttpStatus.OK.value());
        return ResponseEntity.ok().headers(headers).body(responseDTO);
    }

    private static ResponseDTO<?> convertResponseDTO(Object data, String message, Boolean success, int code) {
        ResponseDTO<Object> responseDto = new ResponseDTO<>();
        responseDto.setSuccess(success);
        responseDto.setData(data);
        responseDto.setCode(code);
        responseDto.setMessage(message);
        return responseDto;
    }
}
