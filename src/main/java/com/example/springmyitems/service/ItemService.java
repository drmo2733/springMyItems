package com.example.springmyitems.service;

import com.example.springmyitems.dto.CreateItemRequest;
import com.example.springmyitems.entity.Category;
import com.example.springmyitems.entity.Item;
import com.example.springmyitems.entity.ItemImage;
import com.example.springmyitems.entity.User;
import com.example.springmyitems.repository.CategoryRepository;
import com.example.springmyitems.repository.ItemImageRepository;
import com.example.springmyitems.repository.ItemRepository;
import com.example.springmyitems.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ItemService {

    private final ItemRepository itemRepository;
    private final ItemImageRepository itemImageRepository;
    private final UserRepository userRepository;
    private final CategoryRepository categoryRepository;

    @Value("${myitems.upload.path}")
    private String imagePath;

    public Item addItemFromItemRequest(CreateItemRequest createItemRequest, MultipartFile[] uploadedFiles) throws IOException {
        List<Category> categories = getCategories(createItemRequest);
        Item item = getItemFromRequest(createItemRequest, categories);
        itemRepository.save(item);
        saveItemImages(uploadedFiles, item);
        return item;
    }


    public Item save(Item item) {
        return itemRepository.save(item);
    }

    public void deleteById(int id) {
        itemRepository.deleteById(id);
    }

    public Item findById(int id) {
        return itemRepository.getById(id);
    }

    public List<Item> findAll() {
        return itemRepository.findAll();
    }

    public List<Item> findAllByUser(User user) {
        return itemRepository.findAllByUser(user);
    }

    private void saveItemImages(MultipartFile[] uploadedFiles, Item item) throws IOException {
        if (uploadedFiles.length != 0) {
            for (MultipartFile uploadedFile : uploadedFiles) {
                String fileName = System.currentTimeMillis() + "_" + uploadedFile.getOriginalFilename();
                File newFile = new File(imagePath + fileName);
                uploadedFile.transferTo(newFile);
                ItemImage itemImage = ItemImage.builder()
                        .name(fileName)
                        .item(item)
                        .build();

                itemImageRepository.save(itemImage);
            }

        }
    }

    private List<Category> getCategories(CreateItemRequest createItemRequest) {
        List<Category> categories = new ArrayList<>();
        for (Integer category : createItemRequest.getCategories()) {
            categories.add(categoryRepository.getById(category));
        }
        return categories;
    }

    private Item getItemFromRequest(CreateItemRequest createItemRequest, List<Category> categories) {
        return Item.builder()
                .id(createItemRequest.getId())
                .title(createItemRequest.getTitle())
                .description(createItemRequest.getDescription())
                .price(createItemRequest.getPrice())
                .user(userRepository.findById(createItemRequest.getUserId()).orElse(null))
                .categories(categories)
                .build();
    }
}
