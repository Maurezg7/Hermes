package maudev.backend.service;

import maudev.backend.model.entity.Chats;
import maudev.backend.repository.ChatsRepository;
import maudev.backend.service.impl.IChatsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ChatsService implements IChatsService {
    @Autowired
    private ChatsRepository chatsRepository;

    @Override
    public List<Chats> getAllChats() {
        return this.chatsRepository.findAll();
    }

    @Override
    public Chats getChatById(Long id) {
        return this.chatsRepository.findById(id).orElse(null);
    }

    @Override
    public Chats saveChat(Chats chat) {
        return this.chatsRepository.save(chat);
    }

    @Override
    public void deleteChat(Long id) {
        this.chatsRepository.deleteById(id);
    }
}
