package at.ac.fhcampuswien.foodaddicts.dto;

import at.ac.fhcampuswien.foodaddicts.model.AppUserRole;

public record LoginResponse(Long userId, String username, AppUserRole role, String token) {
}
