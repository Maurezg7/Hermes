package maudev.backend.service.impl;

import maudev.backend.model.entity.Chats;

import java.util.List;

public interface IChatsService {
    List<Chats> getAllChats();
    Chats getChatById(Long id);
    Chats saveChat(Chats chat);
    void deleteChat(Long id);
}
