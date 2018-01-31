package com.korobko.api;

import com.korobko.utils.Constants;

public class GenericReply {
    public Integer retCode = Constants.SUCCESS_CODE;
    public String apiVer = Constants.REST_API_VERSION;
    public String error_message;
}
