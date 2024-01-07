package com.timetraveller.tachyonizr.controller.dashboard;

import com.timetraveller.tachyonizr.service.AuthUserService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/dashboard")
public class DashboardRedirectController {
    private final AuthUserService authUserService;

    public DashboardRedirectController(AuthUserService authUserService) {
        this.authUserService = authUserService;
    }

    @GetMapping
    public String dashboard() {
        if (authUserService.authUserIsAdminGroup()) {
            return "redirect:/admin/dashboard";
        } else {
            return "redirect:/user/dashboard";
        }
    }
}