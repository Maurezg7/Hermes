package maudev.backend.service;

import maudev.backend.model.entity.Server;
import maudev.backend.repository.ServerRepository;
import maudev.backend.service.impl.IServerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ServerService implements IServerService {
    @Autowired
    private ServerRepository serverRepository;

    @Override
    public List<Server> findAll() {
        return this.serverRepository.findAll();
    }

    @Override
    public Server findById(Long id) {
        return this.serverRepository.findById(id).orElse(null);
    }

    @Override
    public Server findByName(String name) {
        return this.serverRepository.findByName(name);
    }

    @Override
    public Server saveServer(Server server) {
        return this.serverRepository.save(server);
    }

    @Override
    public void deleteById(Long id) {
        this.serverRepository.deleteById(id);
    }

    @Override
    public void deleteByName(String name){
        this.serverRepository.deleteByName(name);
    }
}
