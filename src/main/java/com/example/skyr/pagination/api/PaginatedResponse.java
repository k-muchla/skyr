package com.example.skyr.pagination.api;

import java.util.List;

public record PaginatedResponse<T>(List<T> data, PaginationResponse pagination) {
}
