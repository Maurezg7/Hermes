package maudev.backend.service;

import maudev.backend.model.entity.Channel;
import maudev.backend.repository.ChannelRepository;
import maudev.backend.service.impl.IChannelService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;


@Service
public class ChannelService implements IChannelService {
    @Autowired
    private ChannelRepository channelRepository;

    @Override
    public List<Channel> getAllChannels() {
        return this.channelRepository.findAll();
    }

    @Override
    public Channel getChannelById(Long id) {
        return this.channelRepository.findById(id).orElse(null);
    }

    @Override
    public Channel saveChannel(Channel channel) {
        return this.channelRepository.save(channel);
    }

    @Override
    public void deleteChannel(Long id) {
        this.channelRepository.deleteById(id);
    }
    
    
}
