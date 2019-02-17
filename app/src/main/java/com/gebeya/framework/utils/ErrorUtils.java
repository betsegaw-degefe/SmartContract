package com.gebeya.framework.utils;

import com.gebeya.smartcontract.model.data.dto.ErrorResponseDTO;
import com.gebeya.smartcontract.model.data.remote.RetrofitClient;

import java.io.IOException;
import java.lang.annotation.Annotation;
import java.util.Objects;

import okhttp3.ResponseBody;
import retrofit2.Converter;
import retrofit2.Response;

import static com.gebeya.framework.utils.Api.BASE_URL;

public class ErrorUtils {

    public static ErrorResponseDTO parseError(Response<?> response) {
        Converter<ResponseBody, ErrorResponseDTO> converter = RetrofitClient.getClient(BASE_URL)
              .responseBodyConverter(ErrorResponseDTO.class, new Annotation[0]);

        ErrorResponseDTO errorResponse;
        try {
            errorResponse = converter.convert(Objects.requireNonNull(response.errorBody()));
        } catch (IOException e) {
            return new ErrorResponseDTO();
        }
        return errorResponse;
    }
}
