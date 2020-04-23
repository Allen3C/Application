package com.example.http.parser;

import com.example.http.request.IRequest;
import com.example.http.response.IResponse;
import com.example.http.result.IResult;

public interface IParser {
    IResult parseResponse(IRequest iRequest, IResponse iResponse);
}
