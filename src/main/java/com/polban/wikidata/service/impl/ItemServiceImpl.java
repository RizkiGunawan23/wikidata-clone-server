package com.polban.wikidata.service.impl;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.polban.wikidata.dto.request.item.CreateItemRequest;
import com.polban.wikidata.dto.response.item.ItemResponse;
import com.polban.wikidata.exception.BadRequestException;
import com.polban.wikidata.mapper.ItemMapper;
import com.polban.wikidata.model.Item;
import com.polban.wikidata.model.ItemInfo;
import com.polban.wikidata.model.Language;
import com.polban.wikidata.model.User;
import com.polban.wikidata.repository.ItemInfoRepository;
import com.polban.wikidata.repository.ItemRepository;
import com.polban.wikidata.repository.LanguageRepository;
import com.polban.wikidata.service.ItemService;
import com.polban.wikidata.util.AuthenticationUtil;

@Service
public class ItemServiceImpl implements ItemService {
        @Autowired
        private ItemRepository itemRepository;

        @Autowired
        private ItemInfoRepository itemInfoRepository;

        @Autowired
        private LanguageRepository languageRepository;

        @Autowired
        private ItemMapper itemMapper;

        @Override
        @Transactional
        public ItemResponse createItem(CreateItemRequest request) {
                User user = AuthenticationUtil.getCurrentUser();

                Language language = languageRepository.findById(request.getLanguageCode())
                                .orElseThrow(() -> new BadRequestException(
                                                "Kode bahasa tidak ditemukan: " + request.getLanguageCode()));

                String qId = generateQId();

                Item item = Item.builder()
                                .qId(qId)
                                .itemInfos(new HashSet<>())
                                .statements(new HashSet<>())
                                .sitelinks(new HashSet<>())
                                .createdBy(user)
                                .modifiedBy(user)
                                .build();

                item = itemRepository.save(item);

                Set<ItemInfo> itemInfos = createItemInfosFromRequest(request, language);

                for (ItemInfo itemInfo : itemInfos) {
                        itemInfoRepository.save(itemInfo);
                        item.getItemInfos().add(itemInfo);
                }

                Item savedItem = itemRepository.save(item);

                return itemMapper.toCreateResponse(savedItem);
        }

        private String generateQId() {
                String qId;
                do {
                        Long maxNumber = itemRepository.findMaxQIdNumber();
                        Long nextNumber = (maxNumber != null) ? maxNumber + 1 : 1;
                        qId = "Q" + nextNumber;
                } while (itemRepository.existsById(qId));

                return qId;
        }

        private Set<ItemInfo> createItemInfosFromRequest(CreateItemRequest request, Language language) {
                Set<ItemInfo> itemInfos = new HashSet<>();

                // Create Label
                if (request.getLabel() != null && !request.getLabel().trim().isEmpty()) {
                        ItemInfo label = ItemInfo.builder()
                                        .id(UUID.randomUUID().toString())
                                        .value(request.getLabel().trim())
                                        .valueType(ItemInfo.ValueType.LABEL) // Sesuaikan dengan model Anda
                                        .language(language)
                                        .build();
                        itemInfos.add(label);
                }

                // Create Description
                if (request.getDescription() != null && !request.getDescription().trim().isEmpty()) {
                        ItemInfo description = ItemInfo.builder()
                                        .id(UUID.randomUUID().toString())
                                        .value(request.getDescription().trim())
                                        .valueType(ItemInfo.ValueType.DESCRIPTION) // Sesuaikan dengan model Anda
                                        .language(language)
                                        .build();
                        itemInfos.add(description);
                }

                // Create Alias
                if (request.getAlias() != null && !request.getAlias().trim().isEmpty()) {
                        ItemInfo alias = ItemInfo.builder()
                                        .id(UUID.randomUUID().toString())
                                        .value(request.getAlias().trim())
                                        .valueType(ItemInfo.ValueType.ALIAS) // Sesuaikan dengan model Anda
                                        .language(language)
                                        .build();
                        itemInfos.add(alias);
                }

                return itemInfos;
        }
}
