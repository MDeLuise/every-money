package com.github.mdeluise.everymoney.authentication.payload.response;

import java.util.List;

public record UserInfoResponse(long id, String username, List<String> authorities) {
}
