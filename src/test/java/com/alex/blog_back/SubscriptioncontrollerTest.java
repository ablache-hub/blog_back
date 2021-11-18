/*
package com.alex.blog_back;

import com.alex.blog_back.auth.AppUser;
import com.alex.blog_back.auth.Role;
import com.alex.blog_back.config.DisableApplicationSecurityConfig;
import com.alex.blog_back.controller.AppUserController;
import com.alex.blog_back.repo.AppUserRepo;
import com.alex.blog_back.service.AppUserService;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Arrays;
import java.util.Collection;
import java.util.LinkedList;
import java.util.List;


@ContextConfiguration(classes = {DisableApplicationSecurityConfig.class})
@Import(AppUserController.class)
@Tag("controller_subscription")
@WebMvcTest(controllers = AppUserController.class)
public class SubscriptioncontrollerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private AppUserService appUserService;

    @MockBean
    private AppUserRepo appUserRepo;

    Collection<Role> list = new LinkedList<>(
            List.of(
                    new Role(1L, "ROLE_AUTEUR"))
    );

    @Test
    public void getAllRecords_success() throws Exception {

        AppUser user_1 = new AppUser(1L, null, "test1@gmail.com", "test", null,
                new LinkedList<>(
                        List.of(
                                new Role(null, "ROLE_AUTEUR")))
        );
        AppUser user_2 = new AppUser(2L, null, "test2@gmail.com", "test", null, list);
        AppUser user_3 = new AppUser(3L, null, "test3@gmail.com", "test", null, list);

    }

}*/
