package maudev.backend.service.impl;

import maudev.backend.model.entity.Server;

import java.util.List;

public interface IServerService {
    List<Server> findAll();
    Server findById(Long id);
    Server findByName(String name);
    Server saveServer(Server server);
    void deleteById(Long id);
    void deleteByName(String name);
}
