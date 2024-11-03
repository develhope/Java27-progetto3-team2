package com.develhope.Java27_progetto3_team2.menu.service;

import com.develhope.Java27_progetto3_team2.menu.model.MenuItem;
import com.develhope.Java27_progetto3_team2.menu.repository.MenuItemRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class MenuItemService {
private final MenuItemRepository menuItemRepository;

    public MenuItem getMenuItemById(Long menuItemId) {
        return menuItemRepository.getReferenceById(menuItemId);
    }
}
